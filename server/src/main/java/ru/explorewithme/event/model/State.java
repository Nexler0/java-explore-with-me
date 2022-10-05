package ru.explorewithme.event.model;

public enum State {
    PENDING("PENDING"),
    PUBLISHED("PUBLISHED"),
    CANCELED("CANCELED"),
    CONFIRMED("CONFIRMED"),
    REJECTED("REJECTED");

    private final String name;

    State(String canceled) {
        this.name = canceled;
    }

    public String toString() {
        return this.name;
    }
}
