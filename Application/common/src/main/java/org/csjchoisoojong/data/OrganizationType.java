package org.csjchoisoojong.data;

import java.io.Serializable;

/**
 * The {@code OrganizationType} enum represents different types of organizations.
 */
public enum OrganizationType implements Serializable {
    PUBLIC,
    GOVERNMENT,
    TRUST,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;

    /**
     * Returns a comma-separated string of all organization type names.
     *
     * @return A string containing all organization type names, separated by commas.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType organizationType : values()) {
            nameList.append(organizationType.name()).append(",");
        }
        return nameList.substring(0,nameList.length() -2);
    }
}
