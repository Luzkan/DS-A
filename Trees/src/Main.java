import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final double NANO_TO_SEC = 1000000000d;
    private static int mode = 0;

    public static void main(String[] args) throws IOException {
        runProgram(mode);
    }

    private static void runProgram(int test) throws IOException {
        Tree<String> tree;
        switch(test){
            case 1: {
                System.out.println("[Binary Search Tree]");
                tree = new BST<>();
                interactionPanel(tree);
                break;
            }
            case 2: {
                System.out.println("[Red Black Tree]");
                tree = new RBT<>();
                interactionPanel(tree);
                break;
            }
            case 3:{
                System.out.println("[SPlay Tree]");
                tree = new SPlay<>();
                interactionPanel(tree);
                break;
            }
            default: {
                System.out.println("[Trees] Please choose program mode: ");
                System.out.println("Available: (BST - 1), (RBT - 2),  (SPL - 3)");
                Scanner s = new Scanner(System.in);
                mode = s.nextInt();
                runProgram(mode);
                break;
            }
        }
        System.out.println("Process finished successfully.\nProgram restarts.\n");
        runProgram(mode);
    }

    private static void interactionPanel(Tree<String> tree) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Commands:\n- insert <s>, delete <s>, search <s>, inorder\n- load <aspell, aspellReversed, aspellRandom, kjb, lotr, randomStrings, randomStrings1500k>>\n- test <i>, reset, restart");
        while (true) {
            String line = scanner.nextLine();
            String[] userInput = line.split(" ");
            switch (userInput[0]) {
                case "insert": {
                    tree.insert(userInput[1]);
                    break;
                }
                case "delete": {
                    tree.delete(userInput[1]);
                    break;
                }
                case "search": {
                    tree.search(userInput[1]);
                    break;
                }
                case "load": {
                    if (userInput.length != 2) {
                        System.out.println("Available Commands:\n- insert <s>, delete <s>, search <s>, inorder\n- load <aspell, aspellReversed, aspellRandom, kjb, lotr, randomStrings, randomStrings1500k>");
                        break;
                    }
                    // Check Loading Time
                    long start = System.nanoTime();
                    tree.load("src/txt/" + userInput[1] + ".txt");
                    long end = System.nanoTime();

                    double duration = (end - start) / NANO_TO_SEC;
                    System.out.println(duration);
                    break;
                }
                case "inorder": {
                    tree.inOrder();
                    break;
                }
                case "test": {
                    if (userInput.length != 2) {
                        System.out.println("Available Commands:\n- insert <s>, delete <s>, search <s>, inorder\n- load <aspell, aspellReversed, aspellRandom, kjb, lotr, randomStrings, randomStrings1500k>");
                        break;
                    }
                    runTests(tree, Integer.valueOf(userInput[1]));
                    break;
                }
                case "reset": {
                    runProgram(mode);
                    break;
                }
                case "restart": {
                    mode = 0;
                    runProgram(mode);
                    break;
                }
                default: {
                    System.out.println("Available Commands:\n- insert <s>, delete <s>, search <s>, inorder\n- load <aspell, aspellReversed, aspellRandom, kjb, lotr, randomStrings, randomStrings1500k>");
                    break;
                }
            }
        }
    }

    private static void runTests(Tree<String> tree, int numOfTests) throws IOException {
        String treeType = tree.getClass().getSimpleName();
        String[] fileNames = {"kjb", "lotr", "aspellReversed", "aspellRandom", "aspell", "randomStrings", "randomStrings1500k"};
        for (String fileName : fileNames) {

            File file = new File("src/tests/" + treeType + "_" + fileName + ".csv");

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(
                    "insert_time;insert_cmp_count;insert_modified_nodes;" +
                            "search_time;search_cmp_count;search_modified_nodes;" +
                            "delete_time;delete_cmp_count;delete_modified_nodes\n"
            );

            long start, end;

            for (int i = 0; i < numOfTests; i++) {
                start = System.nanoTime();
                tree.load("src/txt/" + fileName + ".txt");
                end = System.nanoTime();

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getResetComparisons())).append(";");
                fileWriter.append(String.valueOf(tree.getResetModifications())).append(";");

                start = System.nanoTime();
                tree.searchLoaded("src/txt/" + fileName + ".txt");
                end = System.nanoTime();

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getResetComparisons())).append(";");
                fileWriter.append(String.valueOf(tree.getResetModifications())).append(";");

                start = System.nanoTime();
                tree.unload("src/txt/" + fileName + ".txt");
                end = System.nanoTime();

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getResetComparisons())).append(";");
                fileWriter.append(String.valueOf(tree.getResetModifications())).append(";");

                fileWriter.append("\n");
                System.out.println("Test #" + i + ".");
            }
            fileWriter.flush();
            fileWriter.close();
        }
        System.out.println("All tests done.");
    }
}
