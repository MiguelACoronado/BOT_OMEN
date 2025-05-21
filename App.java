/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.BOT.OMEN;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App extends TelegramLongPollingBot {

    public static void main(String[] args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new App());
    }

    @Override
    public String getBotUsername() {
        return "Omen_Bot";
    }

    @Override
    public String getBotToken() {
        return "8017509892:AAFv6FAVbBs_HZJoruUGWJIhyWN1tp_owXA";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            String response;
            if (message.startsWith("/recomienda")) {
                String[] parts = message.split(" ", 3);
                if (parts.length < 3) {
                    response = "Uso: /recomienda [estado de ánimo] [género]";
                } else {
                    String mood = parts[1].toLowerCase();
                    String genre = parts[2].toLowerCase();
                    response = MovieRecommender.recommend(mood, genre);
                }
            } else {
                response = "¡Hola! Usa /recomienda [estado de ánimo] [género] para obtener una recomendación.";
            }

            SendMessage sendMessage = new SendMessage(chatId, response);
            try {
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
