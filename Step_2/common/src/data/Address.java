package data;

/**
 * The {@code Address} class represents a street address.
 * It encapsulates the street information and provides methods for retrieval,
 * equality comparison, and generating a hash code.
 */
public class Address {
    /**
     * The street information of the address.
     */
    private String street; //Поле может быть null

    /**
     * Constructs an {@code Address} object with the specified street.
     *
     * @param street The street information of the address.
     */
    public Address(String street) {
        this.street = street;
    }

    /**
     * Gets the street information of the address.
     *
     * @return The street information.
     */
    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return street;
    }

    @Override
    public int hashCode() {
        return street.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Address address = (Address) obj;
        return street.equals(address.street);
    }
}

