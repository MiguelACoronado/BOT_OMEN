package bot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextArea outputArea;

    private String selectedMood = null;
    private String selectedGenre = null;

    public ChatBotGUI() {
        frame = new JFrame("Recomendador de Películas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea));

        showMoodButtons();

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    private void showMoodButtons() {
        panel.add(new JLabel("¿Cuál es tu estado de ánimo hoy?"));
        String[] moods = {"Feliz", "Triste", "Emocionado", "Aburrido"};
        JPanel moodPanel = new JPanel();
        for (String mood : moods) {
            JButton button = new JButton(mood);
            button.addActionListener(e -> {
                selectedMood = mood.toLowerCase();
                outputArea.append("Estado de ánimo seleccionado: " + mood + "\n");
                panel.remove(moodPanel);
                showGenreButtons();
                frame.revalidate();
                frame.repaint();
            });
            moodPanel.add(button);
        }
        panel.add(moodPanel);
    }

    private void showGenreButtons() {
        panel.add(new JLabel("¿Qué género de película prefieres?"));
        String[] genres = {"Acción", "Comedia", "Drama", "Terror"};
        JPanel genrePanel = new JPanel();
        for (String genre : genres) {
            JButton button = new JButton(genre);
            button.addActionListener(e -> {
                selectedGenre = genre.toLowerCase();
                outputArea.append("Género seleccionado: " + genre + "\n");
                panel.remove(genrePanel);
                recommendMovie();
                frame.revalidate();
                frame.repaint();
            });
            genrePanel.add(button);
        }
        panel.add(genrePanel);
    }

    private void recommendMovie() {
        MovieRecommender recommender = new MovieRecommender();
        String recommendation = recommender.recommendMovie(selectedMood, selectedGenre);
        outputArea.append("\nRecomendación: " + recommendation);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatBotGUI::new);
    }
}

