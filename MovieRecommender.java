/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.BOT.OMEN;

import java.util.HashMap;
import java.util.Map;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App extends TelegramLongPollingBot {

    private final MovieRecommender recommender = new MovieRecommender();
    private final Map<String, UserSession> sessions = new HashMap<>();

    public static void main(String[] args) throws Exception {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new App());
        } catch (Exception e) {
            // Ignorar error de borrado webhook
            if (e.getMessage().contains("Error removing old webhook")) {
                System.out.println("Advertencia: no había webhook previo que borrar, ignorando.");
            } else {
                e.printStackTrace();
            }
        }
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
            String chatId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText().toLowerCase();

            UserSession session = sessions.computeIfAbsent(chatId, k -> new UserSession());
            String response = "";

            switch (session.state) {
                case START:
                    session.mood = message;
                    session.state = ChatState.ASK_GENRE;
                    response = "Entiendo, estás " + message + ". ¿Qué género te gustaría ver? (por ejemplo: comedia, drama, acción)";
                    break;

                case ASK_GENRE:
                    String genre = message;
                    response = recommender.recommend(session.mood, genre);
                    session.state = ChatState.DONE;
                    break;

                case DONE:
                    response = "¿Quieres otra recomendación? Dime cómo te sientes.";
                    session.state = ChatState.START;
                    break;
            }

            sendMessage(chatId, response);
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
