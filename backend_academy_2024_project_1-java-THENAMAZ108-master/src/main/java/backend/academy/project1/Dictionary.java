package backend.academy.project1;

import java.util.Map;

public interface Dictionary {
    Map<String, Map<String, String>> getCategories();

    String getRandomWord(Map<String, String> category);

    Map<String, String> getCategory(int choice);
}
