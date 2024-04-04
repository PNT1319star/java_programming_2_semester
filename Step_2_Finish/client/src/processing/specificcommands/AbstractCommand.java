package processing.specificcommands;

public abstract class AbstractCommand {

    /** The name of the command. */
    private final String name;

    /** The description of the command. */
    private final String description;


    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public abstract void execute(String[] arg);

    /**
     * Retrieves the name of the command.
     *
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the description of the command.
     *
     * @return the description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the information about the command.
     */
    public abstract void getCommandInformation();

    /**
     * Returns a string representation of the AbstractCommand object.
     *
     * @return a string representation including the name and description of the command
     */
    @Override
    public String toString() {
        return this.getName() + " (" + this.getDescription() + ")";
    }

    /**
     * Calculates the hash code value for the AbstractCommand object.
     *
     * @return a hash code value based on the name and description of the command
     */
    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    /**
     * Checks if the AbstractCommand object is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && description.equals(other.description);
    }
}
