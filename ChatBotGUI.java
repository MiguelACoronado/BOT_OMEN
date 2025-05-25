package bot;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ChatBotGUI extends JFrame {

    private final JTextArea chatArea = new JTextArea();
    private final JTextField inputField = new JTextField();

    private final MovieRecommender recommender = new MovieRecommender();
    private final Map<String, UserSession> sessions = new HashMap<>();
    private final String USER_ID = "local_user";

    public ChatBotGUI() {
        setTitle("Chatbot Recomendador de Películas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (!input.isEmpty()) {
                addUserMessage(input);
                processUserMessage(input.toLowerCase());
                inputField.setText("");
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputField, BorderLayout.SOUTH);

        sessions.put(USER_ID, new UserSession());
        addBotMessage("¡Hola! ¿Cómo te sientes hoy? Por ejemplo: feliz, triste, aburrido");
    }

    private void addUserMessage(String message) {
        chatArea.append("Tú: " + message + "\n");
    }

    private void addBotMessage(String message) {
        chatArea.append("Bot: " + message + "\n\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void processUserMessage(String message) {
        UserSession session = sessions.get(USER_ID);
        String response;

        switch (session.state) {
            case START:
                session.mood = message;
                session.state = ChatState.ASK_GENRE;
                response = "Entiendo, estás " + message + ". ¿Qué género te gustaría ver? (ej: comedia, drama, acción)";
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

            default:
                response = "No te entendí. Por favor, dime cómo te sientes.";
                session.state = ChatState.START;
                break;
        }

        addBotMessage(response);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatBotGUI gui = new ChatBotGUI();
            gui.setVisible(true);
        });
    }
}
