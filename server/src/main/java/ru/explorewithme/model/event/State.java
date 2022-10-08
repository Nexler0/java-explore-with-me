package ru.explorewithme.model.event;

/**
 * Enum набор статусов
 *
 * @see Event
 * @see ru.explorewithme.model.request.Request
 */

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
