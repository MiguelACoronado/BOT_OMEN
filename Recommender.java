package bot2;

import java.util.Set;

public interface Recommender {
    String recommend(String mood, String genre);
    Set<String> getGenresForMood(String mood);
}
