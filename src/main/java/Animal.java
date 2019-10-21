public class Animal
{
    private String type;
    private String name;
    private int birthYear;


    public String toString()
    {
        String animalListAsString = this.getType() + "," + this.getName() + "," + this.getBirthYear() + "\n";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}



