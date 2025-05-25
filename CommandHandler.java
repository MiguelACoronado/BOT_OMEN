package bot;

public interface CommandHandler {
    boolean canHandle(String message);
    String handle(String message);
}

