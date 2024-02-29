package data;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * The {@code Organization} class represents information about an organization.
 * It includes details such as ID, name, coordinates, creation date, annual turnover,
 * full name, employees count, type, and postal address.
 */
public class Organization  implements  Comparable<Organization>,Cloneable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Float annualTurnover;
    private String fullName;
    private Integer employeesCount;
    private OrganizationType type;
    private Address postalAddress;

    /**
     * Constructs an organization with the specified details.
     *
     * @param id the organization ID
     * @param name the organization name
     * @param coordinates the organization coordinates
     * @param annualTurnover the organization annual turnover
     * @param fullName the organization full name
     * @param employeesCount the organization employees count
     * @param type the organization type
     * @param postalAddress the organization postal address
     */
    public Organization(Integer id, String name, Coordinates coordinates, Float annualTurnover,
                        String fullName, Integer employeesCount, OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now() ;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public Float getAnnualTurnover() {
        return annualTurnover;
    }
    public void setAnnualTurnover(Float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Integer getEmployeesCount() {
        return employeesCount;
    }
    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }
    public OrganizationType getType() {
        return type;
    }
    public void setType(OrganizationType type) {
        this.type = type;
    }
    public Address getPostalAddress() {
        return postalAddress;
    }
    public void setPostalAddress(Address postalAddress){
        this.postalAddress = postalAddress;
    }

    @Override
    public int compareTo(Organization organization) {
        if (this.annualTurnover - organization.getAnnualTurnover() > 0) return 1;
        if (this.annualTurnover - organization.getAnnualTurnover() == 0) return 0;
        else return -1;
    }

    @Override
    public String toString() {
        String info = "Organization № " + id ;
        info += " (added " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")\n";
        info += "Name: " + name + "\n";
        info += "Coordinates: " + coordinates + "\n";
        info += "Annual Turnover: " + annualTurnover + "\n";
        info += "Full Name: " + fullName + "\n";
        info += "Employees Count: " + employeesCount + "\n";
        info += "Type: " + type + "\n";
        info += "Postal Address: " + postalAddress + "\n";

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
            Organization cloned = (Organization) super.clone();
            return cloned;
        } catch (CloneNotSupportedException exception) {
            return null;
        }
    }
}

