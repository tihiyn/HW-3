package service;

import model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SearchServiceImplTest {
    @Test
    public void findLeapYearNames() {
        // создание объекта для поиска
        SearchService searchObject = new SearchServiceImpl();

        // создание животных
        Animal cat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
        Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2000, 12, 1));
        Animal wolf = new Wolf("Японский", "Волчок", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1998, 3, 24));
        Animal shark = new Shark("Молот", "Шарки", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(2050, 6, 13));
        Animal anotherCat = new Cat("Сфинкс", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2400, 1, 5));
        Animal anotherDog = new Dog("Немецкая овчарка", "Жучка", BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 10, 12));

        // исходный массив животных
        Animal[] inputArray = {cat, dog, wolf, shark, anotherCat, anotherDog};
        // массив имён животных, которые родились в високосном году
        String[] outputArray = {dog.getName(), anotherCat.getName()};
        // сравнение массивов
        assertArrayEquals(outputArray, searchObject.findLeapYearNames(inputArray));
    }

    @Test
    void findOlderAnimal() {
        // создание объекта для поиска
        SearchService searchObject = new SearchServiceImpl();

        // создание животных
        Animal cat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2013, 12, 29));
        Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2013, 12, 30));
        Animal wolf = new Wolf("Японский", "Волчок", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(2013, 12, 31));
        Animal shark = new Shark("Молот", "Шарки", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1995, 6, 13));
        Animal anotherCat = new Cat("Сфинкс", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2023, 1, 5));
        Animal anotherDog = new Dog("Немецкая овчарка", "Жучка", BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2015, 10, 12));

        // исходный массив животных
        Animal[] inputArray = {cat, dog, wolf, shark, anotherCat, anotherDog};
        // массив имён животных, возраст которых больше N
        Animal[] outputArray = {cat, dog, shark};
        //сравнение массивов
        assertArrayEquals(outputArray, searchObject.findOlderAnimal(inputArray, 9));
    }
}