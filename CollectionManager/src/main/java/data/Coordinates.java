package data;

/**
 * The {@code Coordinates} class represents a set of geographical coordinates.
 * It encapsulates the x and y coordinates and provides methods for retrieval,
 * equality comparison, and generating a hash code.
 */
public class Coordinates {
    private Long x; //Поле не может быть null
    private long y;

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

    public void setX(Long x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return The y-coordinate.
     */
    public long getY() {
        return y;
    }
    public void setY(long y) {
        this.y = y;
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
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Coordinates coord = (Coordinates) obj;
        return x.equals(coord.x) && y == coord.y;
    }
}