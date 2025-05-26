package bot2;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TelegramBotController extends TelegramLongPollingBot {

    private final Recommender recommender;

    public TelegramBotController(Recommender recommender) {
        this.recommender = recommender;
    }

    public static void startBot(Recommender recommender) throws Exception {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new TelegramBotController(recommender));
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
                sendMoodOptions(update.getMessage().getChatId().toString());
            } else if (update.hasCallbackQuery()) {
                String[] data = update.getCallbackQuery().getData().split(":");
                String chatId = update.getCallbackQuery().getMessage().getChatId().toString();

                if (data[0].equals("salir")) {
                    sendText(chatId, "¡Gracias por usar el bot! 🎬🍿");
                    return;
                }

                if (data.length == 1) {
                    sendGenreOptions(chatId, data[0]); // mood selected
                } else if (data.length == 2) {
                    String mood = data[0];
                    String genre = data[1];
                    String recommendation = recommender.recommend(mood, genre);
                    sendText(chatId, recommendation);

                    // Repetir el flujo
                    sendMoodOptions(chatId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMoodOptions(String chatId) throws Exception {
        SendMessage message = new SendMessage(chatId, "¿Cómo te sientes? 😀");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (String mood : List.of("feliz", "triste", "aburrido", "emocionado")) {
            InlineKeyboardButton button = new InlineKeyboardButton(mood);
            button.setCallbackData(mood);
            rows.add(List.of(button));
        }

        InlineKeyboardButton salir = new InlineKeyboardButton("Salir ❌");
        salir.setCallbackData("salir");
        rows.add(List.of(salir));

        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);
        execute(message);
    }

    private void sendGenreOptions(String chatId, String mood) throws Exception {
        SendMessage message = new SendMessage(chatId, "¿Qué género prefieres cuando estás " + mood + "? 🤔");

        Set<String> genres = recommender.getGenresForMood(mood);
        if (genres.isEmpty()) {
            sendText(chatId, "Lo siento, no hay géneros disponibles para ese estado de ánimo.");
            return;
        }

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (String genre : genres) {
            InlineKeyboardButton button = new InlineKeyboardButton(genre);
            button.setCallbackData(mood + ":" + genre);
            rows.add(List.of(button));
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);
        execute(message);
    }

    private void sendText(String chatId, String text) throws Exception {
        SendMessage msg = new SendMessage(chatId, text);
        execute(msg);
    }

    @Override
    public String getBotUsername() {
        return BotCredentials.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotCredentials.BOT_TOKEN;
    }
}