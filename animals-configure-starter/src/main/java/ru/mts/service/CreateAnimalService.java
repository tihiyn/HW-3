package ru.mts.service;

import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;

import java.util.List;

public interface CreateAnimalService {
    // массив пород (по 3 для каждого животного)
    String[] breeds = new String[]{"Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский"};

    // массив характеров (по 3 для каждого животного)
    String[] characters = new String[]{"Добрый", "Злой", "Игривый", "Вредный", "Верный", "Пугливый"};

    /**
     * Метод для определения типов животных. Внутри вызвается метод для создания животных
     *
     * @since 1.3
     * @author Nikita
     */
    void defineTypeOfAnimals();

    /**
     * Метод для создания животных при помощи цикла do-while
     *
     * @return массив созданных животных
     * @since  1.1
     * @author Nikita
     */
    Animal[] createAnimals();

    /**
     * Метод для получения списка типов животных
     *
     * @return список типов созданных животных
     * @since  1.4
     * @author Nikita
     */
    List<AnimalEnum> receiveAnimalType();

    /**
     * Метод для получения массива животных
     *
     * @return массив созданных животных
     * @since  1.4
     * @author Nikita
     */
    Animal[] receiveAnimalsArray();
}
