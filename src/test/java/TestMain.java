import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.*;

public class TestMain
{

    @Test
    public void testFilteredAnimalListFail() throws IOException {
        List<Animal> animalList;

        animalList = Main.readAnimalFromCSV();

        List<Animal> filteredList = Main.filteredAnimalList(animalList, FilterOptions.TYPE, "lion");

        String filteredString = "";
        if (filteredList.isEmpty()) {
            filteredString = "";
        } else {
            filteredString = filteredList.get(0).toString();
        }

        String record = "";

        assertTrue(filteredString.equals(record));
    }

    @Test
    public void testFilteredAnimalListSuccess() throws IOException {
        List <Animal> animalList;

        animalList = Main.readAnimalFromCSV();

        List <Animal> filteredList = Main.filteredAnimalList(animalList,FilterOptions.TYPE, "har");

        String filteredString = filteredList.get(0).toString();
        String record = "great white shark,jaws,2005\n";

        assertTrue(filteredString.equals(record));


    }




    @Test
    public void testPrintListOfAnimalsToPrintListOfAnimals() throws IOException
    {
        List <Animal> animalList;

        animalList = Main.readAnimalFromCSV();

        String animalListAsString = "";

        for (Animal animal : animalList)
        {
            animalListAsString = animalListAsString + animal.getType() + "," + animal.getName() + "," + animal.getBirthYear() + "\n";
        }

        // Keep current System.out with us
        PrintStream oldOut = System.out;

        // Create a ByteArrayOutputStream so that we can get the output
        // from the call to print
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Change System.out to point out to our stream
        System.setOut(new PrintStream(baos));

        Main.printListOfAnimals(animalList);

        // Reset the System.out
        System.setOut(oldOut);

        // Our baos has the content from the print statement
        String output = new String(baos.toByteArray());




        // Add some assertions out output
        assertTrue(output.equals(animalListAsString));

    }
}
