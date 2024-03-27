package ru.mts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mts.AnimalsProperties;
import ru.mts.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Scope("prototype")
@EnableConfigurationProperties(AnimalsProperties.class)
public class CreateAnimalServiceImpl implements CreateAnimalService {

    private static Logger logger = LoggerFactory.getLogger(CreateAnimalServiceImpl.class);
//    private List<AnimalEnum> animalTypes;
    private CopyOnWriteArrayList<AnimalEnum> animalTypes;
//    private Map<AnimalEnum, List<Animal>> createdAnimals;
    private ConcurrentHashMap<AnimalEnum, List<Animal>> createdAnimals;

    private AnimalsProperties animalsProperties;

    public CreateAnimalServiceImpl(AnimalsProperties animalsProperties) {
        this.animalsProperties = animalsProperties;
    }

    @Override
    public CopyOnWriteArrayList<AnimalEnum> receiveAnimalTypes() {
        return animalTypes;
    }

    @Override
    public ConcurrentHashMap<AnimalEnum, List<Animal>> receiveCreatedAnimals() {
        return createdAnimals;
    }

    /**
     * Метод, который генерирует случайную дату.
     *
     * @return случайная дата.
     * @author Nikita
     * @since 1.1
     */
    private LocalDate createRandomDate() {
        int year = ThreadLocalRandom.current().nextInt(2023 - 1970 + 1) + 1970;
        int month = ThreadLocalRandom.current().nextInt(12) + 1;
        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = ThreadLocalRandom.current().nextInt(daysInMonth) + 1;

        return LocalDate.of(year, month, day);
    }

    /**
     * Метод, отвечающий непосредственно за создание животного.
     *
     * @return объект животного.
     * @author Nikita
     * @since 1.1
     */
    private Animal commonCreating(int counter) {
        Animal animal;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        int animalTypeNumber = ThreadLocalRandom.current().nextInt(4);
        BigDecimal randCost = new BigDecimal(ThreadLocalRandom.current().nextDouble(10000, 500000)).setScale(2, RoundingMode.HALF_UP);

        animal = switch (animalTypeNumber) {
            case 0 ->
                    new Cat(breeds.get(ThreadLocalRandom.current().nextInt(3)), animalsProperties.getCatNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters.get(ThreadLocalRandom.current().nextInt(6)), createRandomDate());
            case 1 ->
                    new Dog(breeds.get(ThreadLocalRandom.current().nextInt(3, 6)), animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters.get(ThreadLocalRandom.current().nextInt(6)), createRandomDate());
            case 2 ->
                    new Shark(breeds.get(ThreadLocalRandom.current().nextInt(6, 9)), animalsProperties.getSharkNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters.get(ThreadLocalRandom.current().nextInt(6)), createRandomDate());
            case 3 ->
                    new Wolf(breeds.get(ThreadLocalRandom.current().nextInt(9, 12)), animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters.get(ThreadLocalRandom.current().nextInt(6)), createRandomDate());
            default -> null;
        };

        logger.info("{}-ое животное: {}", counter, animal.getClass().getName());
        logger.info("Порода: {}", animal.getBreed());
        logger.info("Кличка: {}", animal.getName());
        logger.info("Цена: {}", animal.getCost());
        logger.info("Характер: {}", animal.getCharacter());
        logger.info("Голос: {}", animal.getVoice());
        logger.info("День рождения животного: {}", animal.getBirthDate().format(formatter));

        return animal;
    }

    @Override
    public void defineTypeOfAnimals() {
        createdAnimals = createAnimals();

        animalTypes = new CopyOnWriteArrayList<>();
        for (Map.Entry<AnimalEnum, List<Animal>> node : createdAnimals.entrySet()) {
            if (node.getKey() != null) {
                for (Animal animal : node.getValue()) {
                    animalTypes.add(node.getKey());
                }
            }
        }
    }

    @Override
    public ConcurrentHashMap<AnimalEnum, List<Animal>> createAnimals() {
        int counter = 1;

        createdAnimals = new ConcurrentHashMap<>();
        for (AnimalEnum animalEnum : AnimalEnum.values()) {
            createdAnimals.put(animalEnum, new ArrayList<>());
        }

        do {
            Animal animal = commonCreating(counter);
            String[] splitStr = animal.getClass().toString().split("\\.");
            createdAnimals.get(AnimalEnum.valueOf(splitStr[splitStr.length - 1].toUpperCase())).add(animal);
        }
        while (counter++ < 10);

        return createdAnimals;
    }


    /**
     * Перегруженный метод для создания животных при помощи цикла for.
     * Аргументы: N - количество животных, которое необходимо создать
     *
     * @return HashMap из созданных животных
     * @author Nikita
     * @since 1.1
     */
    public ConcurrentHashMap<AnimalEnum, List<Animal>> createAnimals(int N) {
        createdAnimals = new ConcurrentHashMap<>();
        for (AnimalEnum animalEnum : AnimalEnum.values()) {
            createdAnimals.put(animalEnum, new ArrayList<>());
        }

        for (int i = 0; i < N; i++) {
            Animal animal = commonCreating(i + 1);
            String[] splitStr = animal.getClass().toString().split("\\.");
            createdAnimals.get(AnimalEnum.valueOf(splitStr[splitStr.length - 1].toUpperCase())).add(animal);
        }

        return createdAnimals;
    }
}
