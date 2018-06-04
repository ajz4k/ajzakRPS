package org.ajzak.games;

public abstract class Player {
    private String name;

    public Player(String inputName) {
        this.name = inputName;
    }

    public String getName() {
        return name;
    }

    public abstract String requestPlayerInput();
}
