package ru.netology.delivery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final String[] CITIES = {
            "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань",
            "Нижний Новгород", "Челябинск", "Самара", "Омск", "Ростов-на-Дону"
    };

    private static final String[] FIRST_NAMES = {
            "Александр", "Мария", "Иван", "Елена", "Сергей", "Ольга"
    };

    private static final String[] LAST_NAMES = {
            "Иванов", "Петрова", "Сидоров", "Смирнова", "Кузнецов", "Васильева"
    };

    public static class Registration {
        public static User generateUser(String locale) {
            Random random = new Random();
            return new User(
                    CITIES[random.nextInt(CITIES.length)],
                    LAST_NAMES[random.nextInt(LAST_NAMES.length)] + " " + FIRST_NAMES[random.nextInt(FIRST_NAMES.length)],
                    "+7" + String.format("%010d", random.nextInt(1_000_000_000))
            );
        }
    }

    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static class User {
        private final String city;
        private final String name;
        private final String phone;

        public User(String city, String name, String phone) {
            this.city = city;
            this.name = name;
            this.phone = phone;
        }

        public String getCity() { return city; }
        public String getName() { return name; }
        public String getPhone() { return phone; }
    }
}