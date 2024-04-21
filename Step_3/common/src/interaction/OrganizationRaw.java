package interaction;

import data.Address;
import data.Coordinates;
import data.OrganizationType;

import java.io.Serializable;

public class OrganizationRaw implements Serializable {
    private final String name;
    private final Coordinates coordinates;
    private final Float annualTurnover;
    private final String fullName;
    private final Integer employeesCount;
    private final OrganizationType type;
    private final Address address;

    public OrganizationRaw(String name, Coordinates coordinates, Float annualTurnover, String fullName, Integer employeesCount, OrganizationType type, Address address) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }
    public String getFullName() {
        return fullName;
    }
    public Integer getEmployeesCount() {
        return employeesCount;
    }
    public OrganizationType getType() {
        return type;
    }
    public Address getAddress() {
        return address;
    }

}
