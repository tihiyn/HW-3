package service;

import model.Animal;

public class CreateAnimalServiceImpl implements CreateAnimalService {
    /**
     * Переопределённый метод для создания животных при помощи цикла do-while
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    @Override
    public Animal[] createAnimals() {
        // счётчик
        int counter = 1;
        // массив животных
        Animal[] animalsArray = new Animal[10];

        do {
            animalsArray[counter - 1] = commonCreating(counter);
        }
        while (counter++ < 10);

        return animalsArray;
    }

    /**
     * Перегруженный метод для создания животных при помощи цикла for.
     *  Аргументы: N - количество животных, которое необходимо создать
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    public Animal[] createAnimals(int N) {
        // массив животных
        Animal[] animalsArray = new Animal[N];
        for (int i = 0; i < N; i++) {
            animalsArray[i] = commonCreating(i + 1);
        }

        return animalsArray;
    }
}
