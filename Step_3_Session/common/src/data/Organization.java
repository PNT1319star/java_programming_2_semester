package data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * The {@code Organization} class represents information about an organization.
 * It includes details such as ID, name, coordinates, creation date, annual turnover,
 * full name, employees count, type, and postal address.
 */
public class Organization implements Cloneable, Serializable {
    private final Integer id;
    private String name;
    private final Coordinates coordinates;
    private final ZonedDateTime creationDate;
    private final Float annualTurnover;
    private final String fullName;
    private final Integer employeesCount;
    private final OrganizationType type;
    private final Address postalAddress;
    private final String userName;

    /**
     * Constructs an organization with the specified details.
     *
     * @param id             the organization ID
     * @param name           the organization name
     * @param coordinates    the organization coordinates
     * @param creationDate   creationTime of this organization
     * @param annualTurnover the organization annual turnover
     * @param fullName       the organization full name
     * @param employeesCount the organization employees count
     * @param type           the organization type
     * @param postalAddress  the organization postal address
     * @param userName       the user having created this organization
     */
    public Organization(Integer id, String name, Coordinates coordinates, ZonedDateTime creationDate, Float annualTurnover,
                        String fullName, Integer employeesCount, OrganizationType type, Address postalAddress, String userName) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public String getFullName() {
        return fullName;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public String getUsername() {
        return userName;
    }

    public int compareTo(Float annualTurnover) {
        if (this.annualTurnover - annualTurnover > 0) return 1;
        if (this.annualTurnover - annualTurnover == 0) return 0;
        else return -1;
    }

    @Override
    public String toString() {
        String info = "Organization № " + id;
        info += " (added " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")\n";
        info += "Name: " + name + "\n";
        info += "Coordinates: " + coordinates + "\n";
        info += "Annual Turnover: " + annualTurnover + "\n";
        info += "Full Name: " + fullName + "\n";
        info += "Employees Count: " + employeesCount + "\n";
        info += "Type: " + type + "\n";
        info += "Postal Address: " + postalAddress + "\n";
        info += "User: " + userName + "\n";
        info += "\n";

        return info;
    }

    @Override
    public int hashCode() {
        return id.hashCode() + name.hashCode() + coordinates.hashCode() + creationDate.hashCode()
                + annualTurnover.hashCode() + fullName.hashCode() + employeesCount.hashCode()
                + type.hashCode() + postalAddress.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Organization otherOrganization = (Organization) obj;

        return Objects.equals(id, otherOrganization.id) &&
                Objects.equals(name, otherOrganization.name) &&
                Objects.equals(coordinates, otherOrganization.coordinates) &&
                Objects.equals(creationDate, otherOrganization.creationDate) &&
                Objects.equals(annualTurnover, otherOrganization.annualTurnover) &&
                Objects.equals(fullName, otherOrganization.fullName) &&
                Objects.equals(employeesCount, otherOrganization.employeesCount) &&
                Objects.equals(type, otherOrganization.type) &&
                Objects.equals(postalAddress, otherOrganization.postalAddress);
    }

    @Override
    public Organization clone() {
        try {
            return (Organization) super.clone();
        } catch (CloneNotSupportedException exception) {
            return null;
        }
    }
}

