package backend.academy.project1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class LocalDictionaryTest {

    @Test
    public void testGetCategories() {
        // Arrange
        LocalDictionary dictionary = new LocalDictionary();

        // Act
        Map<String, Map<String, String>> categories = dictionary.getCategories();

        // Assert
        assertNotNull(categories, "Categories should not be null");
        assertTrue(categories.containsKey("Двигатель внутреннего сгорания"), "Categories should contain 'Двигатель внутреннего сгорания'");
        assertTrue(categories.containsKey("Гоночные термины"), "Categories should contain 'Гоночные термины'");
        assertTrue(categories.containsKey("ООП"), "Categories should contain 'ООП'");
    }

    @Test
    public void testGetRandomWord() {
        // Arrange
        LocalDictionary dictionary = new LocalDictionary();
        Map<String, String> category = dictionary.getCategories().get("Двигатель внутреннего сгорания");

        // Act
        String word = dictionary.getRandomWord(category);

        // Assert
        assertNotNull(word, "Random word should not be null");
        assertTrue(category.containsKey(word), "Category should contain the random word");
    }

    @Test
    public void testGetCategory() {
        // Arrange
        LocalDictionary dictionary = new LocalDictionary();

        // Act
        Map<String, String> category = dictionary.getCategory(1);

        // Assert
        assertNotNull(category, "Category should not be null");
        assertTrue(dictionary.getCategories().containsValue(category), "Categories should contain the selected category");
    }

    @Test
    public void testGetCategoryRandom() {
        // Arrange
        LocalDictionary dictionary = new LocalDictionary();

        // Act
        Map<String, String> category = dictionary.getCategory(0);

        // Assert
        assertNotNull(category, "Category should not be null");
        assertTrue(dictionary.getCategories().containsValue(category), "Categories should contain the random category");
    }
}
