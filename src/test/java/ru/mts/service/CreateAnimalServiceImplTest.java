package ru.mts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import ru.mts.AnimalsProperties;
import ru.mts.bpp.CreateAnimalServiceBeanPostProcessor;
import ru.mts.model.*;
import ru.mts.repository.AnimalsRepository;
import ru.mts.repository.AnimalsRepositoryImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@DisplayName("Class for testing animals-configure-starter")
@SpringBootTest
@ActiveProfiles("test")
public class CreateAnimalServiceImplTest {
    Animal cat1, cat2, cat3, dog1, wolf1, wolf2, shark1;
    ConcurrentHashMap<AnimalEnum, List<Animal>> animals;

    private AnimalsRepository animalsRepository;
    @Autowired
    private CreateAnimalServiceBeanPostProcessor createAnimalServiceBeanPostProcessor;
    @Autowired
    private AnimalsProperties animalsProperties;
    @MockBean
    private CreateAnimalServiceImpl createAnimalService;

    /**
     * Метод для инициализации животных
     *
     * @author Nikita
     * @since 1.4
     */
    private void initAnimals() {
        cat1 = new Cat("Британская", animalsProperties.getCatNames().get(1), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "������", LocalDate.now().minusYears(10).minusDays(1));
        cat2 = new Cat("Шотландская", animalsProperties.getCatNames().get(0), BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "������", LocalDate.of(2013, 4, 18));
        cat3 = new Cat("Сфинкс", animalsProperties.getCatNames().get(2), BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "�������", LocalDate.of(2008, 9, 9));

        dog1 = new Dog("Доберман", animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "����", LocalDate.now().minusYears(10));

        wolf1 = new Wolf("Японский", animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "�������", LocalDate.now().minusYears(10).plusDays(1));
        wolf2 = new Wolf("Полярный", animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "�������", LocalDate.of(1997, 2, 1));

        shark1 = new Shark("Молот", animalsProperties.getSharkNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "��������", LocalDate.of(1996, 6, 13));
    }

    @DisplayName("Test methods for creating animals")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void starterTest(int value) {
        animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

        when(createAnimalService.receiveAnimalTypes()).thenCallRealMethod();
        when(createAnimalService.receiveCreatedAnimals()).thenCallRealMethod();
        doCallRealMethod().when(createAnimalService).defineTypeOfAnimals();

        initAnimals();
        animals = new ConcurrentHashMap<>();
        switch (value) {
            case 0:
                animals.put(AnimalEnum.CAT, Arrays.asList(cat1, cat3, cat2));
                animals.put(AnimalEnum.DOG, Arrays.asList(dog1));
                animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, wolf2));
                animals.put(AnimalEnum.SHARK, Arrays.asList(shark1));
                break;
            case 1:
                animals.put(AnimalEnum.CAT, Arrays.asList(cat1, cat3, cat2));
                animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, wolf2));
                animals.put(AnimalEnum.SHARK, new ArrayList<>());
                break;
            case 2:
                animals.put(AnimalEnum.SHARK, Arrays.asList(shark1));
                animals.put(AnimalEnum.DOG, new ArrayList<>());
                animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, null));
                animals.put(AnimalEnum.CAT, new ArrayList<>());
                break;
            case 3:
                animals.put(AnimalEnum.CAT, new ArrayList<>());
                animals.put(AnimalEnum.DOG, new ArrayList<>());
                animals.put(AnimalEnum.WOLF, new ArrayList<>());
                animals.put(AnimalEnum.SHARK, new ArrayList<>());
                break;
            case 4:
                animals = null;
                break;
            case 5:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        when(createAnimalService.createAnimals()).thenReturn(animals);

        if (value == 4) {
            assertThrows(NullPointerException.class, () -> {
                createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
            });
        } else {
            createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
            animalsRepository.fillStorage();
            assertEquals(animals, animalsRepository.getAnimalStorage());
        }
    }

    @Configuration
    @ComponentScan
    static class SpringBootStarterTestConfig {
        @Bean
        public CreateAnimalServiceBeanPostProcessor createAnimalServiceBeanPostProcessor() {
            return new CreateAnimalServiceBeanPostProcessor();
        }
    }
}
