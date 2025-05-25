/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.BOT.OMEN;

package bot;

import java.util.HashMap;
import java.util.Map;

public class MovieRecommender {

    private final Map<String, Map<String, String>> recommendations = new HashMap<>();

    public MovieRecommender() {
        Map<String, String> feliz = new HashMap<>();
        feliz.put("comedia", "La Gran Apuesta, Superbad, The Intouchables");
        feliz.put("accion", "Mad Max: Fury Road, John Wick");

        Map<String, String> triste = new HashMap<>();
        triste.put("drama", "Her, The Pursuit of Happyness, Manchester by the Sea");
        triste.put("romance", "La La Land, Call Me by Your Name");

        Map<String, String> aburrido = new HashMap<>();
        aburrido.put("suspenso", "Inception, Gone Girl");
        aburrido.put("aventura", "Indiana Jones, Jurassic Park");

        recommendations.put("feliz", feliz);
        recommendations.put("triste", triste);
        recommendations.put("aburrido", aburrido);
    }

    public String recommend(String mood, String genre) {
        if (recommendations.containsKey(mood) && recommendations.get(mood).containsKey(genre)) {
            return "Películas recomendadas para cuando estás " + mood + " y quieres ver " + genre + ":\n" +
                    recommendations.get(mood).get(genre);
        } else {
            return "Lo siento, no tengo recomendaciones para ese estado de ánimo o género.";
        }
    }
}
