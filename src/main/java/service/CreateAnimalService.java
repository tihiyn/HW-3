package service;

import model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public interface CreateAnimalService {
    // массив пород (по 3 для каждого животного)
    String[] breeds = new String[]{"Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский"};
    // массив имён (по 3 для каждого животного)
    String[] names = new String[]{"Мурзик", "Васька", "Матроскин", "Жучка", "Дружок", "Бобик", "Аква", "Шарки", "Волна", "Клык", "Волчок", "Серый"};
    // массив характеров (по 3 для каждого животного)
    String[] characters = new String[]{"Добрый", "Злой", "Игривый", "Вредный", "Верный", "Пугливый"};

    /**
     * Метод, который генерирует случайную дату
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    default LocalDate createRandomDate() {
        // случайный год
        int year = ThreadLocalRandom.current().nextInt(2023 - 1970 + 1) + 1970;
        // случайный месяц
        int month = ThreadLocalRandom.current().nextInt(12) + 1;
        // количество дней в месяце
        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        // случайный день
        int day = ThreadLocalRandom.current().nextInt(daysInMonth) + 1;

        return LocalDate.of(year, month, day);
    }

    /**
     * Метод, отвечающий непосредственно за создание животного
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    default Animal commonCreating(int counter) {
        // переменная класса model.Animal
        Animal animal;
        // формат даты
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // сгенерируем случайное число от 0 до 3, которое будет сопоставлено с одним из классов животных
        int numOfClass = ThreadLocalRandom.current().nextInt(4);
        // сгенерируем случайную цену
        BigDecimal randCost = new BigDecimal(ThreadLocalRandom.current().nextDouble(10000, 500000)).setScale(2, RoundingMode.HALF_UP);

        // создание животного
        animal = switch (numOfClass) {
            case 0 ->
                    new Cat(breeds[ThreadLocalRandom.current().nextInt(3)], names[ThreadLocalRandom.current().nextInt(3)], randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            case 1 ->
                    new Dog(breeds[ThreadLocalRandom.current().nextInt(3, 6)], names[ThreadLocalRandom.current().nextInt(3, 6)], randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            case 2 ->
                    new Shark(breeds[ThreadLocalRandom.current().nextInt(6, 9)], names[ThreadLocalRandom.current().nextInt(6, 9)], randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            case 3 ->
                    new Wolf(breeds[ThreadLocalRandom.current().nextInt(9, 12)], names[ThreadLocalRandom.current().nextInt(9, 12)], randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            default -> null;
        };
        // логирование
        System.out.format("%d-ое животное: %s\n", counter, animal.getClass().getName());
        System.out.println("Порода: " + animal.getBreed());
        System.out.println("Кличка: " + animal.getName());
        System.out.println("Цена: " + animal.getCost());
        System.out.println("Характер: " + animal.getCharacter());
        System.out.println("Голос: " + animal.getVoice());
        System.out.println("День рождения животного: " + animal.getBirthDate().format(formatter));
        System.out.println();

        return animal;
    }

    /**
     * Метод для создания животных при помощи цикла while
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    default Animal[] createAnimals() {
        // счётчик
        int counter = 0;
        // массив животных
        Animal[] animalsArray = new Animal[10];
        while (counter++ < 10) {
            animalsArray[counter - 1] = commonCreating(counter);
        }

        return animalsArray;
    }
}
