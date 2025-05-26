/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.BOT.OMEN;

public class App {
    public static void main(String[] args) {
        try {
            Recommender recommender = new MovieRecommender();
            TelegramBotController.startBot(recommender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
