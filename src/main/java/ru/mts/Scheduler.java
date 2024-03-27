package ru.mts;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.repository.AnimalsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Scheduler {
    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private AnimalsRepository animalsRepository;
    private ObjectFactory<AnimalsRepository> animalsRepositoryObjectFactory;

    public Scheduler(ObjectFactory<AnimalsRepository> animalsRepositoryObjectFactory) {
        this.animalsRepositoryObjectFactory = animalsRepositoryObjectFactory;
    }

    /**
     * PostConstruct метод, который создаёт 2 потока с помощью ScheduledExecutorService и задаёт им имена, используя ThreadFactory.
     * Первый поток раз в 10 секунд вызывает метод findDuplicate из AnimalsRepositoryImpl.
     * Второй поток раз в 20 секунд вызывает метод findAverageAge из AnimalsRepositoryImpl.
     *
     * @author Nikita
     * @since 1.8
     */
    @PostConstruct
    public void runThreads() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2, new NamedThreadFactory());
        service.scheduleWithFixedDelay(() -> {
            animalsRepository = animalsRepositoryObjectFactory.getObject();
            logger.info("Duplicates of animals");
            for (Map.Entry<String, List<Animal>> node : animalsRepository.findDuplicate().entrySet()) {
                logger.info("Type: {}, Duplicates: {}", node.getKey(), node.getValue());
            }
        }, 0, 10, TimeUnit.SECONDS);

        service.scheduleWithFixedDelay(() -> {
            animalsRepository = animalsRepositoryObjectFactory.getObject();
            logger.info("Average animal age: {}", animalsRepository.findAverageAge());
        }, 0, 20, TimeUnit.SECONDS);
    }

    static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadsCounter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "MyThread-" + threadsCounter.incrementAndGet());
        }
    }

    /**
     * Метод, который раз в 30 секунд будет делать вызов методов
     * AnimalsRepository и выводить результаты в лог
     *
     * @author Nikita
     * @since 1.1
     */
    @Scheduled(fixedRate = 30000)
    public void printResults() {
        try {
            animalsRepository = animalsRepositoryObjectFactory.getObject();
            logger.info("Names of animals that were born in a leap year");
            for (Map.Entry<String, LocalDate> node : animalsRepository.findLeapYearNames().entrySet()) {
                logger.info("Name: {}, Birth Date: {}", node.getKey(), node.getValue());
            }

            int N = 25;
            logger.info("Names of animals that are more than {} y.o.", N);
            for (Map.Entry<Animal, Integer> node : animalsRepository.findOlderAnimal(N).entrySet()) {
                logger.info("Name: {}, Age: {}", node.getKey().getName(), node.getValue());
            }

//            logger.info("Duplicates of animals");
//            for (Map.Entry<String, List<Animal>> node : animalsRepository.findDuplicate().entrySet()) {
//                logger.info("Type: {}, Duplicates: {}", node.getKey(), node.getValue());
//            }

//            logger.info("Average animal age: {}", animalsRepository.findAverageAge());

            logger.info("Old and expensive");
            for (Animal animal : animalsRepository.findOldAndExpensive()) {
                logger.info(animal.getName());
            }

            logger.info("Min cost animals");
            for (String animalsName : animalsRepository.findMinCostAnimals()) {
                logger.info(animalsName);
            }
        } catch (NegativeArgumentException e) {
            logger.warn(e.getMessage());
        } catch (IllegalCollectionSizeException e) {
            logger.warn(e.getMessage());
        }
    }
}
