package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Shark extends Predator {
    // голос акулы
    private static final String voice = "Буль";

    public Shark(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
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