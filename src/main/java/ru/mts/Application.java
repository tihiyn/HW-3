package ru.mts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        // инициализация контекста
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//                ApplicationContext.class
//        );
//
//        // создание бина animalsRepositoryImpl
//        AnimalsRepositoryImpl animalsRepository = context.getBean("animalsRepositoryImpl", AnimalsRepositoryImpl.class);
//
//        System.out.println("Имена животных, которые родились в високосный год: ");
//        for (String animal : animalsRepository.findLeapYearNames()) {
//            System.out.println(animal);
//        }
//        System.out.println();
//
//        int N = 25;
//        System.out.format("Имена животных, которым больше %d лет: \n", N);
//        for (Animal animal : animalsRepository.findOlderAnimal(N)) {
//            System.out.println(animal.getName());
//        }
//        System.out.println();
//
//        // вызов метода для поиска дубликатов
//        animalsRepository.findDuplicate();


        // первый объект для создания животных
        // анонимный класс, чтобы "добраться" до метода по умолчанию createAnimals()
//        CreateAnimalService firstCreator = new CreateAnimalService() {
//            // дополнительный переопределённый метод createAnimals()
//            @Override
//            public Animal[] createAnimals() {
//                return CreateAnimalService.super.createAnimals();
//            }
//        };
//        // вызов метода по умолчанию createAnimals()
//        Animal[] animalsArray = firstCreator.createAnimals();
//
//        // создание объекта для поиска
//        SearchService searchObject = new SearchServiceImpl();
//        // переменная для записи результата вызова метода findLeapYearNames
//        String[] leapArray = searchObject.findLeapYearNames(animalsArray);
//
//        // вывод содержимого массива leapArray
//        System.out.println("Имена животных, которые родились в високосный год: ");
//        for (String s : leapArray) System.out.println(s);
//        System.out.println();
//
//        // возраст для сравнения
//        int N = 20;
//        // переменная для записи результатов вызова метода findOlderAnimal
//        Animal[] olderArray = searchObject.findOlderAnimal(animalsArray, N);
//
//        // вывод имён животных, возраст которых больше N лет
//        System.out.format("Имена животных, которым больше %d лет: \n", N);
//        for (Animal animal : olderArray) System.out.println(animal.getName());
//        System.out.println();
//
//        // создание 3 животных, из которых 1 и 3 одинаковые
//        Animal cat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2015, 7, 26));
//        Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2010, 12, 1));
//        Animal sameCat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2015, 7, 26));
//        // массив из созданных вручную животных
//        Animal[] testArray = {cat, dog, sameCat, cat, dog};
//        // вызов метода findDuplicate
//        searchObject.findDuplicate(testArray);


//        // второй объект для создания животных
//        CreateAnimalService secondCreator = new CreateAnimalServiceImpl();
//        // вызов переопределённого метода createAnimals()
//        secondCreator.createAnimals();
//
//        // третий объект для создания определённого кличества животных
//        CreateAnimalServiceImpl thirdCreator = new CreateAnimalServiceImpl();
//        // вызов перегруженного метода createAnimals(int N)
//        thirdCreator.createAnimals(5);
    }
}
