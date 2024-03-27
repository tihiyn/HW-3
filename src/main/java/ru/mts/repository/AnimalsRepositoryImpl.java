package ru.mts.repository;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;
import ru.mts.service.CreateAnimalService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Scope("prototype")
public class AnimalsRepositoryImpl implements AnimalsRepository {

    private static Logger logger = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);

//    private Map<AnimalEnum, List<Animal>> animalStorage;
    private ConcurrentHashMap<AnimalEnum, List<Animal>> animalStorage;

    private CreateAnimalService createAnimalService;

    public AnimalsRepositoryImpl(CreateAnimalService createAnimalService) {
        this.createAnimalService = createAnimalService;
    }

    @Override
    public Map<AnimalEnum, List<Animal>> getAnimalStorage() {
        return animalStorage;
    }

    /**
     * PostConstruct-метод для наполнения "хранилища"
     *
     * @author Nikita
     * @since 1.4
     */
    @PostConstruct
    @Override
    public void fillStorage() {
        animalStorage = createAnimalService.receiveCreatedAnimals();

        logger.info("Типы созданных животных");
        for (AnimalEnum type : createAnimalService.receiveAnimalTypes()) {
            logger.info(String.valueOf(type));
        }
        System.out.println();
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        return animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream()
                        .filter(animal -> Year.of(animal.getBirthDate().getYear()).isLeap())
                        .map(animal -> Map.entry(entry.getKey().toString() + " " + animal.getName(), animal.getBirthDate())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing));
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int N) {

        if (N >= 0) {
            Animal oldestAnimal = animalStorage.entrySet().stream()
                    .filter(entry -> entry.getKey() != null)
                    .flatMap(entry -> entry.getValue().stream()).min((animal1, animal2) -> {
                        if (animal1.getBirthDate().isBefore(animal2.getBirthDate()))
                            return -1;
                        else if (animal1.getBirthDate().equals(animal2.getBirthDate()))
                            return 0;
                        else
                            return 1;
                    })
                    .orElse(null);

            Map<Animal, Integer> olderAnimals = animalStorage.entrySet().stream()
                    .filter(entry -> entry.getKey() != null)
                    .flatMap(entry -> entry.getValue().stream()
                            .filter(animal -> Period.between(animal.getBirthDate(), LocalDate.now()).getYears() > N)
                            .map(animal -> Map.entry(animal, Period.between(animal.getBirthDate(), LocalDate.now()).getYears())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if (olderAnimals.isEmpty() && oldestAnimal != null)
                olderAnimals.put(oldestAnimal, Period.between(oldestAnimal.getBirthDate(), LocalDate.now()).getYears());

            return olderAnimals;
        } else {
            throw new NegativeArgumentException("Age can't be a negative");
        }
    }

    /**
     * Метод выводит на экран множество дубликатов животных.
     * Метод может быть вызван только из метода findDuplicate().
     *
     * @param dupl множество дубликатов
     * @author Nikita
     * @since 1.2
     */
    private void printSetOfDuplicates(Set<Animal> dupl) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int counter = 0;


        System.out.println("Animal duplicates: ");
        for (Animal animal : dupl) {
            System.out.format("%d-ый дубликат: %s\n", counter + 1, animal.getClass().getName());
            System.out.println("Порода: " + animal.getBreed());
            System.out.println("Кличка: " + animal.getName());
            System.out.println("Цена: " + animal.getCost());
            System.out.println("Характер: " + animal.getCharacter());
            System.out.println("Голос: " + animal.getVoice());
            System.out.println("День рождения животного: " + animal.getBirthDate().format(formatter));
            System.out.println();
            counter++;
        }
    }

    @Override
    public Map<String, List<Animal>> findDuplicate() {
        return animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream()
                        .filter(animal -> Collections.frequency(entry.getValue(), animal) > 1))
                .collect(Collectors.groupingBy(animal -> animal.getClass().toString(), Collectors.mapping(animal -> animal, Collectors.toList())));
    }

    public double findAverageAge() {
        double averageAge = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream())
                .mapToInt(animal -> Period.between(animal.getBirthDate(), LocalDate.now()).getYears())
                .average()
                .orElse(0);

        BigDecimal result = new BigDecimal(averageAge).setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    @Override
    public List<Animal> findOldAndExpensive() {
        double averageCost = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream())
                .mapToDouble(animal -> animal.getCost().doubleValue())
                .average()
                .orElse(0);

//        System.out.println(averageCost);

        return animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream())
                .filter(animal -> animal.getCost().doubleValue() > averageCost &&
                        Period.between(animal.getBirthDate(), LocalDate.now()).getYears() > 5)
                .sorted((animal1, animal2) -> {
                    if (animal1.getBirthDate().isBefore(animal2.getBirthDate()))
                        return -1;
                    else if (animal1.getBirthDate().equals(animal2.getBirthDate()))
                        return 0;
                    else
                        return 1;
                })
                .toList();
    }

    @Override
    public List<String> findMinCostAnimals() throws IllegalCollectionSizeException {
        long numOfAnimals = animalStorage.values().stream()
                .mapToLong(List::size)
                .sum();

        if (numOfAnimals >= 3) {
            return animalStorage.entrySet().stream()
                    .filter(entry -> entry.getKey() != null)
                    .flatMap(entry -> entry.getValue().stream())
                    .sorted(Comparator.comparingDouble(animal -> animal.getCost().doubleValue()))
                    .map(Animal::getName)
                    .limit(3)
                    .sorted(Comparator.reverseOrder())
                    .toList();
        } else {
            throw new IllegalCollectionSizeException("Low number of animals; Expected: 3, actual: ", numOfAnimals);
        }
    }
}
