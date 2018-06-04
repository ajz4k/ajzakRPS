package org.ajzak.games;

public class Komputer extends Player {
    private final String[] CHOICES = { "kamien", "papier", "nozyczki" };

    public Komputer(String name) {
        super(name);
    }

    public String requestPlayerInput() {
        int randomInt = (int) (Math.random() * 3);

        return CHOICES[randomInt];
    }
}

