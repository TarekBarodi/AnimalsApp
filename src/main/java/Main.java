import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        List <Animal> animals;
        animals = readAnimalFromCSV();

        // sorting the animals alphabetically by name
        List<Animal> sortedAnimals = animals.stream().sorted(Comparator.comparing(Animal::getName)).collect(Collectors.toList());

        // print the sorted animal list
        printListOfAnimals(sortedAnimals);

        // the below code is the one used to create the method easily.
        //List<Animal> filterAnimalList = filteredAnimalList (animals, FilterOptions.TYPE, "shark");

        //filtering swimmer objects from animals list, and casting to Swimmer type and collect again to list.
        List<Swimmer> swimmers = animals.stream().filter(a -> a instanceof Swimmer).map(x -> (Swimmer) x).collect(Collectors.toList());

        List<Swimmer> sortedSwimmers = getSortedSwimmers(swimmers);

        /**
        writeSwimmerCSV(sortedSwimmers);

        printSwimmersThroughServer(sortedSwimmers);
         */

    }


    private static List<Swimmer> getSortedSwimmers(List<Swimmer> swimmers)
    {
        //Swimmer interface does not have a birth date field, therefore we filter out the objects that are of type
        //Animal
        List<Animal> animalSwimmers = swimmers.stream().map(x -> (Animal) x).collect(Collectors.toList());

        //Sorting the Animal list that are of Swimmer type chronologically by birth date and
        //return a list of Swimmer type
        List<Swimmer> sortedSwimmersByBirthYear = animalSwimmers.stream()
                .sorted(Comparator.comparing(Animal::getBirthYear)).map(x -> (Swimmer)x)
                .collect(Collectors.toList());

        return sortedSwimmersByBirthYear;
    }

    /**
     *
    private static void printSwimmersThroughServer(List<Swimmer> swimmers)
    {
        //Convert the list of swimmers to a string
        String swimmersString = "";

        for (Swimmer swimmer: swimmers)
        {
            swimmersString = swimmersString + (swimmer.getClass().cast(swimmer)).toString();
        }

        //Try connect to the server on an unused port eg 9991. A successful connection will return a socket
        try(ServerSocket serverSocket = new ServerSocket(9991))
        {
            Socket connectionSocket = serverSocket.accept();

            //Create Output stream for the connection
            InputStream inputFromServer = connectionSocket.getInputStream();
            OutputStream serverPrintOut = connectionSocket.getOutputStream();

            Scanner scanner = new Scanner(swimmersString);
            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                serverPrintOut.println((line));
            }

            System.out.println(inputFromServer.readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/


    public static void writeSwimmerCSV(List<Swimmer> swimmers) throws IOException {
        //Convert the list of swimmers to a string
        String swimmersString = "";
        for (Swimmer swimmer: swimmers) {
            swimmersString = swimmersString + (swimmer.getClass().cast(swimmer)).toString();
        }
        Path path = Paths.get("/Users/tarekbarodi/Desktop/AnimalsApp/Animals.csv");
        Files.writeString(path,swimmersString);
    }



    /**
     * This method return the filtered list by type, name or year of birth
     * (for year of birth we we convert text into integer)
     * @param animals  the list of the animals which we search in
     * @param filterOption determine the type, name, year
     * @param text look up in specific field
     * @return
     */
    public static List<Animal> filteredAnimalList(List<Animal> animals, FilterOptions filterOption, String text)
    {
        List<Animal> filteredAnimals = null;
        switch (filterOption)
        {
            case TYPE:
                filteredAnimals = animals.stream().filter(a -> a.getType().contains(text)).collect(Collectors.toList());
                break;
            case NAME:
                filteredAnimals = animals.stream().filter(a -> a.getName().contains(text)).collect(Collectors.toList());
                break;
            case YEAR:
                filteredAnimals = animals.stream().filter(a -> a.getBirthYear()==Integer.parseInt(text)).collect(Collectors.toList());
                break;
        }
        return filteredAnimals;
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

                // adding the animal object to the list before preceding to the next line
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
