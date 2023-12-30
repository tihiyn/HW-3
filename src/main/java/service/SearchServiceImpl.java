package service;

import model.Animal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class SearchServiceImpl implements SearchService {
    @Override
    public String[] findLeapYearNames(Animal[] inputArray) {
        // массив для имён животных, родившихся в високосных год
        String[] outputArray = new String[inputArray.length];
        // счётчик
        int counter = 0;

        for (Animal animal : inputArray) {
            // год рождения животного
            int year = animal.getBirthDate().getYear();
            // проверка года на високосность
            if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))) {
                outputArray[counter] = animal.getName();
                counter++;
            }
        }

        return Arrays.copyOf(outputArray, counter);
    }

    @Override
    public Animal[] findOlderAnimal(Animal[] inputArray, int N) {
        // массив для животных, возраст которых больше N лет
        Animal[] outputArray = new Animal[inputArray.length];
        // счётчик
        int counter = 0;
        // возраст
        int age;

        for (Animal animal : inputArray) {
            age = LocalDate.now().getYear() - animal.getBirthDate().getYear();

            // корректировка возраста
            if (LocalDate.now().getDayOfYear() < animal.getBirthDate().getDayOfYear())
                age -= 1;

            if (age > N) {
                outputArray[counter] = animal;
                counter++;
            }
        }

        return Arrays.copyOf(outputArray, counter);
    }

    @Override
    public void findDuplicate(Animal[] inputArray) {
        // формат даты
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // массив дубликатов животных
        Animal[] duplicates = new Animal[inputArray.length];
        // флаг
        boolean flag = false;
        // счётчик
        int counter = 0;

        for (int i = 0; i < inputArray.length - 1; i++) {
            for (int j = i + 1; j < inputArray.length; j++) {
                // проверка равенства объектов
                if (inputArray[i].equals(inputArray[j])) {
                    flag = false;
                    // проверка наличия найденного дубликата в массиве дубликатов
                    for (Animal animal : duplicates) {
                        if (animal != null && animal.equals(inputArray[i])) {
                            flag = true;
                            break;
                        }
                    }
                    // если найденного дубликата в массиве дубликатов нет, то добавляемя его туда
                    if (!flag) {
                        duplicates[counter] = inputArray[i];
                        counter++;
                    }
                }
            }
        }

        // вывод дубликатов
        System.out.println("Дубликаты: ");
        for (int i = 0; i < counter; i++) {
            System.out.format("%d-ый дубликат: %s\n", i + 1, duplicates[i].getClass().getName());
            System.out.println("Порода: " + duplicates[i].getBreed());
            System.out.println("Кличка: " + duplicates[i].getName());
            System.out.println("Цена: " + duplicates[i].getCost());
            System.out.println("Характер: " + duplicates[i].getCharacter());
            System.out.println("Голос: " + duplicates[i].getVoice());
            System.out.println("День рождения животного: " + duplicates[i].getBirthDate().format(formatter));
            System.out.println();
        }
    }
}
