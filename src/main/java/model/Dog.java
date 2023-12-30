package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Dog extends Pet {
    // голос собаки
    private static final String voice = "Гав";

    public Dog(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
