package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cat extends Pet {
    // голос кошки
    private static final String voice = "Мяу";

    public Cat(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
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
