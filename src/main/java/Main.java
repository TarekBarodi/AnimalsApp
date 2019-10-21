import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args) throws IOException {
        List <Animal> animals;
        animals = readAnimalFromCSV();

        // sorting
        List<Animal> sortedAnimals = animals.stream().sorted(Comparator.comparing(Animal::getName)).collect(Collectors.toList());

        printListOfAnimals(sortedAnimals);

    }

    /***
     * printing all the animal which excist in the list (not from file because the data already moved to the list)
     * @param animals list of the animals pass to print
     */
    public static void printListOfAnimals(List<Animal> animals)
    {
        for (Animal animal : animals)
            System.out.println(animal.getType() + "," + animal.getName() + "," + animal.getBirthYear());

    }

    /***
     * read the CSV files and create instance of subclasses for each animal depends on the type on it and if the animal
     * does not belong to any type create an default instance of animal and the year of birth should not be between
     * 0-9 otherwise the entire entry will be invalid and object will not be created and no object will be added to
     * the list.
     * add the file to a list and return it.
     * @return List<Animal>
     */
    public static List<Animal> readAnimalFromCSV() throws IOException {
        // create a list to contain all animals objects
        List<Animal> list = new ArrayList<>();

        // Scanner is to read the file line by line.

        Scanner scannerFile = new Scanner(Paths.get("/Users/tarekbarodi/Desktop/AnimalsApp/Animals.csv"));

        // read line by line, continue reading the file till the end.
        while (scannerFile.hasNextLine()) {

            // line String holding the entire line in each loop
            String line = scannerFile.nextLine();

            // scanner to read items separated by comma in just one line
            Scanner scannerLine = new Scanner(line);
            scannerLine.useDelimiter(",");

            // extracting the dat items as strings to local variables
            String type = scannerLine.next();
            String name = scannerLine.next();
            String strYear = scannerLine.next();

            // no animal to be created or added to the list unless the yea of the birth is a valid year
            if (isValidYear(strYear)) {

                // Declare animal without intiation and this will initiate is done because all local variable to be initiated as per compiler that is why null is a must
                Animal animal;

                switch (type) {
                    case "golden retriever":
                        animal = new GoldenRetriever();
                        break;

                    case "dolphin":
                        animal = new Dolphin();
                        break;

                    case "duck":
                        animal = new Duck();
                        break;

                    case "bengal cat":
                        animal = new BengalCat();
                        break;

                    case "chicken":
                        animal = new Chicken();
                        break;

                    case "arabian horse":
                        animal = new ArabianHorse();
                        break;

                    case "german shepherd":
                        animal = new GermanShepherd();
                        break;

                    case "great white shark":
                        animal = new GreatWhiteShark();
                        break;

                    case "parakeet":
                        animal = new Parakeet();
                        break;

                    default:
                        animal = new Animal();
                }

                // setting all the fields of animal object
                animal.setType(type);
                animal.setName(name);
                animal.setBirthYear(Integer.parseInt(strYear));

                // adding the animal object to the list before proceding to the next line
                list.add(animal);
            }
                else
                {
                    System.out.println("Invalid entry of birth year, for the " + type + "");

                }
        }

        return list;
    }


    /***
     * A valid year should 4 characters and is equal or before today's date.
     * @param strYear the year as string
     * @return boolean
     */
    private static boolean isValidYear(String strYear) {
        // trimming all spaces before and after the string.
        strYear = strYear.trim();

        // remove all spaces between the characters
        strYear = strYear.replaceAll("[\\s+]", "");

        // remove all characters which are not decimal 0 and 9.
        strYear = strYear.replaceAll("[^0-9,]", "");


        try {
            // if the remaining decimal characters are exactly four
            if (strYear.length() == 4) {
                // the birthday should equal or before today's date
                DateFormat dateFormat = new SimpleDateFormat("yyyy");
                Date birthDate = dateFormat.parse(strYear + "-01-01");
                Date todayDate = new Date();

                return birthDate.before(todayDate);
            } else {
                return false;
            }
        }
        catch (ParseException e) {
            return false;
        }
    }
}
