package ru.mts.service;

import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface CreateAnimalService {
    List<String> breeds = List.of("Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский");

    List<String> characters = List.of("Добрый", "Злой", "Игривый", "Вредный", "Верный", "Пугливый");

    /**
     * Метод для определения типов животных. Внутри вызвается метод для создания животных
     *
     * @author Nikita
     * @since 1.3
     */
    void defineTypeOfAnimals();

    /**
     * Метод для создания животных при помощи цикла do-while
     *
     * @return ConcurrentHashMap с созданными животными
     * @author Nikita
     * @since 1.5
     */
    ConcurrentHashMap<AnimalEnum, List<Animal>> createAnimals();

    /**
     * Метод для получения списка типов животных
     *
     * @return  потокобезопасный список типов созданных животных
     * @author Nikita
     * @since 1.4
     */
    CopyOnWriteArrayList<AnimalEnum> receiveAnimalTypes();

    /**
     * Метод для получения созданных животных
     *
     * @return ConcurrentHashMap из созданных животных
     * @author Nikita
     * @since 1.4
     */
    ConcurrentHashMap<AnimalEnum, List<Animal>> receiveCreatedAnimals();
}
