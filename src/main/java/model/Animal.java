package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Animal {
    /**
     * Метод, который возвращает породу животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    String getBreed();

    /**
     * Метод, который возвращает имя животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    String getName();

    /**
     * Метод, который возвращает цену животного в магазине.
     * Значение округляется до 2 знаков после запятой.
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    BigDecimal getCost();

    /**
     * Метод, который возвращает характер животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    String getCharacter();

    /**
     * Метод, который возвращает голос животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    String getVoice();

    /**
     * Метод, который возвращает день рождения животного
     *
     * @Since 1.1
     * @Author Nikita
     */
    LocalDate getBirthDate();
}
