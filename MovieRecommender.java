/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.BOT.OMEN;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MovieRecommender implements Recommender {

    private final Map<String, Map<String, String>> data = new HashMap<>();

    public MovieRecommender() {
        Map<String, String> feliz = new HashMap<>();
        feliz.put("comedia", "Intouchables, Superbad");
        feliz.put("accion", "Mad Max, John Wick");

        Map<String, String> triste = new HashMap<>();
        triste.put("drama", "Her, The Pursuit of Happyness");
        triste.put("romance", "La La Land, Blue Valentine");

        Map<String, String> aburrido = new HashMap<>();
        aburrido.put("aventura", "Indiana Jones, Jurassic Park");
        aburrido.put("suspenso", "Inception, Gone Girl");

        Map<String, String> emocionado = new HashMap<>();
        emocionado.put("aventura", "The Avengers, Ready Player One");
        emocionado.put("ciencia ficción", "Interstellar, The Matrix");
        emocionado.put("musical", "The Greatest Showman, Moulin Rouge");

        data.put("feliz", feliz);
        data.put("triste", triste);
        data.put("aburrido", aburrido);
        data.put("emocionado", emocionado);
    }

    @Override
    public String recommend(String mood, String genre) {
        return data.getOrDefault(mood, Map.of())
                   .getOrDefault(genre, "No tengo recomendaciones para ese estado de ánimo y género 😩");
    }

    @Override
    public Set<String> getGenresForMood(String mood) {
        return data.getOrDefault(mood, Map.of()).keySet();
    }
}

