package org.csjchoisoojong.data;

import java.io.Serializable;

/**
 * The {@code Coordinates} class represents a set of geographical coordinates.
 * It encapsulates the x and y coordinates and provides methods for retrieval,
 * equality comparison, and generating a hash code.
 */
public class Coordinates implements Serializable {
    private final Long x; //Поле не может быть null
    private final long y;

    /**
     * Constructs a {@code Coordinates} object with the specified x and y coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Coordinates(Long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return The x-coordinate.
     */
    public Long getX() {
        return x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return The y-coordinate.
     */
    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x = " + getX() + ", y = " + getY();
    }

    @Override
    public int hashCode() {
        return x.hashCode() + Long.hashCode(y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates coord = (Coordinates) obj;
        return x.equals(coord.x) && y == coord.y;
    }
}