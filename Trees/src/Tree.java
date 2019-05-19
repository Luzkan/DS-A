import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class Tree<KeyType extends Comparable<KeyType>> {

    public int comparisons = 0;
    int getResetComparisons() {
        int result = comparisons;
        this.comparisons = 0;
        return result;
    }

    public int modifications = 0;
    int getResetModifications() {
        int result = modifications;
        this.modifications = 0;
        return result;
    }

    void addCompModf(int comparisons, int modifications) {
        this.comparisons += comparisons;
        this.modifications += modifications;
    }

    public abstract void insert(KeyType value);
    public abstract void delete(KeyType value);
    public abstract void search(KeyType value);
    public abstract void inOrder();

    public void load(KeyType fileName) {
        try {
            FileReader fileReader = new FileReader((String) fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Keyfix<KeyType> keyfix = new Keyfix<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    KeyType toInsert = keyfix.fix((KeyType) s);
                    if (toInsert != null) insert(toInsert);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unload(KeyType fileName) {
        try {
            FileReader fileReader = new FileReader((String) fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Keyfix<KeyType> keyfix = new Keyfix<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    KeyType toDelete = keyfix.fix((KeyType) s);
                    if (toDelete != null) delete(toDelete);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void searchLoaded(KeyType fileName) {
        try {
            FileReader fileReader = new FileReader((String) fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Keyfix<KeyType> keyfix = new Keyfix<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    KeyType toSearch = keyfix.fix((KeyType) s);
                    if (toSearch != null) search(toSearch);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
