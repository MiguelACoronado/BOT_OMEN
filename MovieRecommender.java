/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.BOT.OMEN;

package bot;

import java.util.HashMap;
import java.util.Map;

public class MovieRecommender {
    private Map<String, Map<String, String>> recommendations;

    public MovieRecommender() {
        recommendations = new HashMap<>();

        // Estado de ánimo: feliz
        Map<String, String> felizRecs = new HashMap<>();
        felizRecs.put("comedia", "Forrest Gump, Intouchables");
        felizRecs.put("acción", "Guardianes de la Galaxia, Spiderman: No Way Home");
        felizRecs.put("drama", "La La Land, The Pursuit of Happyness");
        felizRecs.put("terror", "Scary Movie, Shaun of the Dead");
        recommendations.put("feliz", felizRecs);

        // Estado de ánimo: triste
        Map<String, String> tristeRecs = new HashMap<>();
        tristeRecs.put("drama", "La vida es bella, El niño con el pijama de rayas");
        tristeRecs.put("comedia", "Yes Man, School of Rock");
        tristeRecs.put("acción", "John Wick, Gladiator");
        tristeRecs.put("terror", "The Others, El orfanato");
        recommendations.put("triste", tristeRecs);

        // Estado de ánimo: emocionado
        Map<String, String> emocionadoRecs = new HashMap<>();
        emocionadoRecs.put("acción", "Mad Max: Fury Road, The Dark Knight");
        emocionadoRecs.put("comedia", "Deadpool, The Mask");
        emocionadoRecs.put("drama", "Whiplash, Rush");
        emocionadoRecs.put("terror", "It, A Quiet Place");
        recommendations.put("emocionado", emocionadoRecs);

        // Estado de ánimo: aburrido
        Map<String, String> aburridoRecs = new HashMap<>();
        aburridoRecs.put("comedia", "The Office (serie), Superbad");
        aburridoRecs.put("acción", "Fast & Furious, Kingsman");
        aburridoRecs.put("drama", "The Social Network, The Imitation Game");
        aburridoRecs.put("terror", "Saw, Final Destination");
        recommendations.put("aburrido", aburridoRecs);
    }

    // Método Base
    public String recommend(String mood, String genre) {
        if (recommendations.containsKey(mood) && recommendations.get(mood).containsKey(genre)) {
            return "Películas recomendadas para cuando estás " + mood + " y quieres ver " + genre + ":\n" +
                    recommendations.get(mood).get(genre);
        } else {
            return "Lo siento, no tengo recomendaciones para ese estado de ánimo o género.";
        }
    }

    // NUEVO método para usar con la interfaz gráfica
    public String recommendMovie(String mood, String genre) {
        return recommend(mood, genre);
    }
}

