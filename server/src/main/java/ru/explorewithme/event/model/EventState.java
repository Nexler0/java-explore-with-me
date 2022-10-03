package ru.explorewithme.event.model;

public enum EventState {
    PENDING("PENDING"),
    PUBLISHED("PUBLISHED"),
    CANCELED("CANCELED");

    private final String name;

    EventState(String canceled) {
        this.name = canceled;
    }

    public String toString() {
        return this.name;
    }
}
