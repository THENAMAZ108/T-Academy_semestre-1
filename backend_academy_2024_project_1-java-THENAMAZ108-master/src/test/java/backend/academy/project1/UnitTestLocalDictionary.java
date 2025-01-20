package backend.academy.project1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UnitTestLocalDictionary implements Dictionary {
    private static final Map<String, Map<String, String>> CATEGORIES = new HashMap<>();

    static {
        CATEGORIES.put("Двигатель внутреннего сгорания", new HashMap<>() {{
            put("поршень", "Двигается вверх и вниз внутри цилиндра.");
            put("коленвал", "Преобразует возвратно-поступательное движение в вращательное.");
            put("свеча", "Искрит для воспламенения топливной смеси.");
            put("карбюратор", "Смешивает воздух с топливом для внутреннего сгорания.");
            put("цилиндр", "Внутри него движется поршень.");
            put("клапан", "Контролирует поток воздуха и топлива в двигатель.");
            put("распредвал", "Открывает и закрывает клапаны двигателя.");
        }});

        CATEGORIES.put("Гоночные термины", new HashMap<>() {{
            put("шикана", "Резкий двойной поворот на трассе.");
            put("апекс", "Наивысшая точка поворота.");
            put("прижим", "Аэродинамическая сила, прижимающая машину к земле.");
            put("питстоп", "Остановка для дозаправки и обслуживания во время гонки.");
            put("грид", "Стартовые позиции машин в гонке.");
            put("спойлер", "Устройство для улучшения аэродинамики автомобиля.");
            put("дрифт", "Техника прохождения поворотов в управляемом заносе.");
        }});

        CATEGORIES.put("ООП", new HashMap<>() {{
            put("наследование", "Механизм, при котором один класс получает свойства другого.");
            put("полиморфизм", "Способность разных классов реагировать на один и тот же вызов метода.");
            put("инкапсуляция", "Объединение данных с методами, которые работают с этими данными.");
            put("абстракция", "Сокрытие сложных деталей реализации и показ только необходимых функций.");
            put("класс", "Шаблон для создания объектов.");
            put("объект", "Экземпляр класса.");
            put("интерфейс", "Тип в Java, который похож на класс и является коллекцией абстрактных методов.");
        }});
    }

    public UnitTestLocalDictionary() {

    }

    @Override
    public Map<String, Map<String, String>> getCategories() {
        return CATEGORIES;
    }

    @Override
    public String getRandomWord(Map<String, String> category) {
        Object[] keys = category.keySet().toArray();
        return (String) keys[new Random().nextInt(keys.length)];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> getCategory(int choice) {
        if (choice > 0 && choice <= CATEGORIES.size()) {
            return (Map<String, String>) CATEGORIES.values().toArray()[choice - 1];
        } else {
            return (Map<String, String>) CATEGORIES.values().toArray()[new Random().nextInt(CATEGORIES.size())];
        }
    }
}

