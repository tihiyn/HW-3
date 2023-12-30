package service;

import model.Animal;

public interface SearchService {
    /**
     * Метод ищет животных, родившихся в високосный год.
     * На вход подаётся массив животных.
     * На выходе - массив из имён животных.
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    String[] findLeapYearNames(Animal[] animalsArray);

    /**
     * Метод ищет животных, возраст которых больще N лет, где N - аргумент метода.
     * На вход подаётся массив животных.
     * На выходе - массив животных.
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    Animal[] findOlderAnimal(Animal[] animalsArray, int N);

    /**
     * Метод выводит на экран дубликаты животных
     * На вход подаётся массив животных.
     * На выходе - массив животных.
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    void findDuplicate(Animal[] animalsArray);
}
