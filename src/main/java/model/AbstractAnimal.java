package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {
    // порода животного
    protected String breed;
    // имя животного
    protected String name;
    // цена животного в магазине
    protected BigDecimal cost;
    // характер животного
    protected String character;
    // день рождения животного в формате dd-MM-yyyy
    protected LocalDate birthDate;

    public AbstractAnimal(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.birthDate = birthDate;
    }

    // реализация геттеров
    @Override
    public String getBreed() {
        return breed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String getCharacter() {
        return character;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    // переопределённая версия метода equals, которая сравнивает объекты по всем полям
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractAnimal that = (AbstractAnimal) o;
        return Objects.equals(breed, that.breed) && Objects.equals(name, that.name) && Objects.equals(cost, that.cost) && Objects.equals(character, that.character) && Objects.equals(birthDate, that.birthDate);
    }
}
