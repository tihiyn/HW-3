package ru.mts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.*;
import ru.mts.repository.AnimalsRepository;
import ru.mts.repository.AnimalsRepositoryImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@DisplayName("Class for unit testing")
class JUnitTest {
    @Nested
    @DisplayName("Class for testing method equals from AbstractAnimal")
    public class AbstractAnimalTest {
        @Test
        @DisplayName("Test override method equals")
        public void equals() {
            // создание животных
            Animal cat1 = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
            Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2000, 12, 1));
            Animal cat2 = new Cat("Сфинкс", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2400, 1, 5));
            Animal anotherDog = new Dog("Немецкая овчарка", "Жучка", BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 10, 12));
            Animal sameCat1 = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
            Animal stillCat1 = cat1;

            assertNotEquals(cat1, dog);
            assertNotEquals(cat1, cat2);
            assertEquals(cat1, sameCat1);
            assertEquals(cat1, stillCat1);
            assertEquals(sameCat1, stillCat1);
            assertEquals(cat1, cat1);
            assertNotEquals(anotherDog, null);

            stillCat1 = null;
            assertEquals(stillCat1, stillCat1);
            assertEquals(stillCat1, null);
            assertNotEquals(stillCat1, sameCat1);
            sameCat1 = null;
            assertEquals(stillCat1, sameCat1);
        }
    }

    @Nested
    @DisplayName("Class for testing methods from AnimalsRepositoryImpl")
    @ExtendWith(MockitoExtension.class)
    public class AnimalsRepositoryImplTest {
        @Mock
        CreateAnimalService createAnimalService;
        Animal cat1, cat2, cat3, cat4, dog1, dog2, wolf1, wolf2, shark1, shark2, sameCat2, sameShark1, sameCat3;

        /**
         * Своя реализация метода contains.
         * Метод проверяет, содержится ли в списке из HashMap заданная HashMap
         *
         * @param list список из HashMap
         * @param targetMap заданная HashMap
         * @return {@code true} если list содержит targetMap
         * @author Nikita
         * @since 1.5
         */
        public static boolean containsHashMap(List<HashMap<Animal, Integer>> list, Map<Animal, Integer> targetMap) {
            for (HashMap<Animal, Integer> map : list) {
                if (map.equals(targetMap)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Метод для инициализации животных
         *
         * @author Nikita
         * @since 1.4
         */
        private void initAnimals() {
            cat1 = new Cat("Британская", "Misa", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1));
            cat2 = new Cat("Шотландская", "Lelik", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18));
            cat3 = new Cat("Сфинкс", "Richard", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9));
            cat4 = new Cat("Сфинкс", "Richard", BigDecimal.valueOf(13760.23).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2004, 2, 14));


            dog1 = new Dog("Доберман", "Bobik", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10));
            dog2 = new Dog("Лабрадор", "Druzhok", BigDecimal.valueOf(32000.33).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2020, 2, 15));


            wolf1 = new Wolf("Японский", "Klik", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1));
            wolf2 = new Wolf("Полярный", "Volchok", BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1997, 2, 1));

            shark1 = new Shark("Молот", "Sharki", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));
            shark2 = new Shark("Белая", "Volna", BigDecimal.valueOf(1500000.99).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2019, 1, 10));

            sameCat2 = new Cat("Шотландская", "Lelik", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18));
            sameShark1 = new Shark("Молот", "Sharki", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));
            sameCat3 = new Cat("Сфинкс", "Richard", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9));
        }

        @DisplayName("Test method findLeapYearNames")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
        public void findLeapYearNames(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            ConcurrentHashMap<AnimalEnum, List<Animal>> animals = new ConcurrentHashMap<>();
            CopyOnWriteArrayList<AnimalEnum> animalTypes;
            Map<String, LocalDate> leapYearAnimals = new HashMap<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF, AnimalEnum.SHARK));

                    leapYearAnimals.put("CAT " + cat3.getName(), LocalDate.of(2008, 9, 9));
                    leapYearAnimals.put("SHARK " + shark1.getName(), LocalDate.of(1996, 6, 13));
                    break;
                case 1:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF));
                    break;
                case 2:
                    animals.put(AnimalEnum.SHARK, List.of(shark1));
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, null));
                    animals.put(AnimalEnum.CAT, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>(Arrays.asList(AnimalEnum.SHARK, AnimalEnum.WOLF, null));
                    break;
                case 3:
                    animals.put(AnimalEnum.CAT, new ArrayList<>());
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, new ArrayList<>());
                    animals.put(AnimalEnum.SHARK, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 4:
                    animals = null;

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 5:
                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 6:
                    animals.put(AnimalEnum.CAT, List.of(cat3, cat1, cat4));
                    animals.put(AnimalEnum.SHARK, List.of(shark1));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.SHARK));

                    leapYearAnimals.put("CAT " + cat3.getName(), LocalDate.of(2008, 9, 9));
                    leapYearAnimals.put("SHARK " + shark1.getName(), LocalDate.of(1996, 6, 13));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
            when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);

            animalsRepository.fillStorage();
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findLeapYearNames();
                });
            } else {
                assertEquals(leapYearAnimals, animalsRepository.findLeapYearNames());
            }
        }

        @DisplayName("Test method findOlderAnimal")
        @ParameterizedTest(name = "Array of animals, more than {arguments} y.o.")
        @ValueSource(ints = {10, 9, 20, 50, 24, 12, 18, 0, -2})
        public void findOlderAnimal(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            ConcurrentHashMap<AnimalEnum, List<Animal>> animals = new ConcurrentHashMap<>();

            initAnimals();
            animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
            animals.put(AnimalEnum.DOG, List.of(dog1));
            animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
            animals.put(AnimalEnum.SHARK, List.of(shark1));

            CopyOnWriteArrayList<AnimalEnum> animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF, AnimalEnum.SHARK));

            List<HashMap<Animal, Integer>> outputResults = List.of(
                    new HashMap<>() {{
                        put(cat3, 15);
                        put(wolf2, 27);
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(cat1, 10);
                        put(cat2, 10);
                        put(cat3, 15);
                        put(dog1, 10);
                        put(wolf2, 27);
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(wolf2, 27);
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(null, 0);
                    }},

                    new HashMap<>() {{
                        put(dog1, 10);
                    }},

                    new HashMap<>() {{
                        put(cat1, 10);
                        put(cat2, 10);
                        put(dog1, 10);
                    }},

                    new HashMap<>()
            );

            switch (value) {
                case 24:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(new ConcurrentHashMap<>() {{
                        put(AnimalEnum.CAT, Arrays.asList(cat1, null));
                        put(AnimalEnum.DOG, List.of(dog1));
                    }});
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(new CopyOnWriteArrayList<>(Arrays.asList(AnimalEnum.CAT, null, AnimalEnum.DOG)));
                    break;
                case 12:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(new ConcurrentHashMap<>() {{
                        put(AnimalEnum.CAT, new ArrayList<>());
                        put(AnimalEnum.DOG, new ArrayList<>());
                    }});
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(new CopyOnWriteArrayList<>());
                    break;
                case 18:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(null);
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(new CopyOnWriteArrayList<>());
                    break;
                case 0:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(new ConcurrentHashMap<>() {{
                        put(AnimalEnum.CAT, new ArrayList<>(List.of(cat1, cat2)));
                        put(AnimalEnum.DOG, new ArrayList<>(List.of(dog1)));
                    }});
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.DOG)));
                    break;
                default:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);
                    break;
            }

            animalsRepository.fillStorage();
            if (value == 24 || value == 18) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findOlderAnimal(value);
                });
            } else if (value == -2) {
                assertThrows(NegativeArgumentException.class, () -> {
                    animalsRepository.findOlderAnimal(value);
                });
            }
            else {
                assertTrue(containsHashMap(outputResults, animalsRepository.findOlderAnimal(value)));
            }
        }

        @DisplayName("Test method findDuplicate")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
        public void findDuplicate(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            ConcurrentHashMap<AnimalEnum, List<Animal>> animals = new ConcurrentHashMap<>();
            CopyOnWriteArrayList<AnimalEnum> animalTypes;
            Map<String, List<Animal>> duplicates = new HashMap<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, sameCat3, cat3, cat2, sameCat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1, sameShark1, shark1, sameShark1));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT,
                            AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF,
                            AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK));

                    duplicates.put("class ru.mts.model.Cat", List.of(cat3, sameCat3, cat3, cat2, sameCat2));
                    duplicates.put("class ru.mts.model.Shark", List.of(shark1, sameShark1, shark1, sameShark1));
                    break;
                case 1:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1, shark1, shark1));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF,
                            AnimalEnum.WOLF, AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK));

                    duplicates.put("class ru.mts.model.Shark", List.of(shark1, shark1, shark1));
                    break;
                case 2:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF,
                            AnimalEnum.WOLF, AnimalEnum.SHARK));
                    break;
                case 3:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, Arrays.asList(null, null));

                    animalTypes = new CopyOnWriteArrayList<>(Arrays.asList(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF));

                    break;
                case 4:
                    animals = null;

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 5:
                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 6:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat2, cat3, sameCat2, cat2, cat1));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1, shark1));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT,
                            AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF,
                            AnimalEnum.SHARK, AnimalEnum.SHARK));

                    duplicates.put("class ru.mts.model.Cat", List.of(cat1, cat2, sameCat2, cat2, cat1));
                    duplicates.put("class ru.mts.model.Shark", List.of(shark1, shark1));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
            when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);

            animalsRepository.fillStorage();
            if (value == 3 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findDuplicate();
                });
            } else {
                assertEquals(duplicates, animalsRepository.findDuplicate());
            }
        }

        @DisplayName("Test findAverageAge method")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void findAverageAge(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            ConcurrentHashMap<AnimalEnum, List<Animal>> animals = new ConcurrentHashMap<>();
            CopyOnWriteArrayList<AnimalEnum> animalTypes;
            double averageAge = 0;

            initAnimals();
            switch (value) {
                case 0:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF, AnimalEnum.SHARK));

                    averageAge = 15.43;
                    break;
                case 1:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF));

                    averageAge = 13.2;
                    break;
                case 2:
                    animals.put(AnimalEnum.SHARK, List.of(shark1));
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, null));
                    animals.put(AnimalEnum.CAT, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>(Arrays.asList(AnimalEnum.SHARK, AnimalEnum.WOLF, null));
                    break;
                case 3:
                    animals.put(AnimalEnum.CAT, new ArrayList<>());
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, new ArrayList<>());
                    animals.put(AnimalEnum.SHARK, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 4:
                    animals = null;

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 5:
                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
            when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);

            animalsRepository.fillStorage();
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findAverageAge();
                });
            } else {
                assertEquals(averageAge, animalsRepository.findAverageAge());
            }
        }

        @DisplayName("Test findOldAndExpensive method")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void findOldAndExpensive(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            ConcurrentHashMap<AnimalEnum, List<Animal>> animals = new ConcurrentHashMap<>();
            CopyOnWriteArrayList<AnimalEnum> animalTypes;
            List<Animal> oldAndExpensiveAnimals = new ArrayList<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3));
                    animals.put(AnimalEnum.DOG, List.of(dog1, dog2));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1));
                    animals.put(AnimalEnum.SHARK, List.of(shark1, shark2));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT,
                            AnimalEnum.DOG, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.SHARK, AnimalEnum.SHARK));

                    oldAndExpensiveAnimals = List.of(shark1, wolf1);
                    break;
                case 1:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.SHARK, List.of(shark2));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT,
                            AnimalEnum.DOG, AnimalEnum.SHARK));

                    break;
                case 2:
                    animals.put(AnimalEnum.SHARK, List.of(shark1));
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, null));
                    animals.put(AnimalEnum.CAT, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>(Arrays.asList(AnimalEnum.SHARK, AnimalEnum.WOLF, null));
                    break;
                case 3:
                    animals.put(AnimalEnum.CAT, new ArrayList<>());
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, new ArrayList<>());
                    animals.put(AnimalEnum.SHARK, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 4:
                    animals = null;

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 5:
                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
            when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);

            animalsRepository.fillStorage();
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findOldAndExpensive();
                });
            } else {
                assertEquals(oldAndExpensiveAnimals, animalsRepository.findOldAndExpensive());
            }
        }

        @DisplayName("Test findMinCostAnimals method")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void findMinCostAnimals(int value) throws IllegalCollectionSizeException {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            ConcurrentHashMap<AnimalEnum, List<Animal>> animals = new ConcurrentHashMap<>();
            CopyOnWriteArrayList<AnimalEnum> animalTypes;
            List<String> minCostAnimals = new ArrayList<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put(AnimalEnum.CAT, List.of(cat3));
                    animals.put(AnimalEnum.DOG, List.of(dog1, dog2));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1));
                    animals.put(AnimalEnum.SHARK, List.of(shark1, shark2));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.DOG,
                            AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.SHARK, AnimalEnum.SHARK));

                    // cat3, dog1, dog2
                    // Richard, Bobik, Druzhok
                    // Richard, Druzhok, Bobik
                    minCostAnimals = List.of(cat3.getName(), dog2.getName(), dog1.getName());
                    break;
                case 1:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.SHARK, List.of(shark2));

                    animalTypes = new CopyOnWriteArrayList<>(List.of(AnimalEnum.CAT, AnimalEnum.CAT,
                            AnimalEnum.DOG, AnimalEnum.SHARK));

                    // cat1, cat2, dog1
                    // Misa, Lelik, Bobik
                    minCostAnimals = List.of(cat1.getName(), cat2.getName(), dog1.getName());
                    break;
                case 2:
                    animals.put(AnimalEnum.SHARK, List.of(shark1));
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, null));
                    animals.put(AnimalEnum.CAT, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>(Arrays.asList(AnimalEnum.SHARK, AnimalEnum.WOLF, null));
                    break;
                case 3:
                    animals.put(AnimalEnum.CAT, new ArrayList<>());
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, new ArrayList<>());
                    animals.put(AnimalEnum.SHARK, new ArrayList<>());

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 4:
                    animals = null;

                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                case 5:
                    animalTypes = new CopyOnWriteArrayList<>();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
            when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);

            animalsRepository.fillStorage();
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findMinCostAnimals();
                });
            } else if (value == 3 || value == 5) {
                assertThrows(IllegalCollectionSizeException.class, () -> {
                    animalsRepository.findMinCostAnimals();
                });
            }
            else {
                assertEquals(minCostAnimals, animalsRepository.findMinCostAnimals());
            }
        }
    }
}
