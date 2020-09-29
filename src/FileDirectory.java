import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The main program that runs the File system.
 */
public class FileDirectory {
    /**
     * private variable that lists all the available commands that the program uses.
     */
    private static final List commands = Arrays.asList("help", "pwd", "mkdir", "ls", "cd", "touch", "vi", "rmdir", "rm");

    /**
     * Main method for the program which runs Virtual File shell.
     * @param args Not used in this program.
     */
    public static void main(String [] args){
        Scanner keyboard = new Scanner(System.in);
        Node currentNode = new Node(new Folder("root"));
        String currentPath = "/root";
        System.out.println("Welcome to the Virtual File Terminal. Type help for more specifics on commands");
        String input = "";
        while(!input.equals("exit")){
        System.out.print(currentPath + ">");
        input = keyboard.nextLine().trim();
        currentNode = command(input, currentNode);
        currentPath = Node.currentPath(currentNode);
        }
    }

    /**
     * The command method that determines which command to do based upon the user input.
     * @param input The user's input that will help to determine which command to use.
     * @param curr The current directory.
     * @return returns the either the same current directory or a new directory based upon which commands were used.
     */
    public static Node command(String input, Node curr){
        String command = input;
        if(input.indexOf(" ") > 0){
            command = input.substring(0, input.indexOf(" "));
        }
        int commandIndex = commands.indexOf(command);
        switch(commandIndex){
            case 0:
                help();
                break;
            case 1:
                System.out.println(Node.currentPath(curr));
                break;
            case 2:
                makeDirectory(input, curr);
                break;
            case 3:
                listContents(input, curr);
                break;
            case 4:
                Node newCurr = changeDirectory(input, curr);
                return newCurr != null ? newCurr : curr;
            case 5:
                makeFile(input, curr);
                break;
            case 6:
                showFile(input, curr);
                break;
            case 7:
                removeDirectory(input, curr);
                break;
            case 8:
                removeFile(input, curr);
                break;
            default:
                if(!input.equals("exit")){
                    System.out.println("Command not found!");
                }
                break;
        }
        return curr;
    }

    /**
     * Shows the contents of a text file.
     * @param input The string that determines the location of the text file.
     * @param curr the current directory.
     */
    public static void showFile(String input, Node curr) {
        TextFile file = null;
        String fileName = "";
        if (input.contains("/")) {
            int fileIndex = input.lastIndexOf("/") + 1;
            fileName = input.substring(fileIndex);
            int startPath = input.indexOf(" ") + 1; //finds the spacing between the command and the actual path specified
            curr = findPath(input.substring(startPath, fileIndex), curr);
        }
        else {
            fileName = input.substring(input.indexOf(" ") + 1);
        }
        file = curr.getData().getFile(fileName);
        if(file != null){
            System.out.println(file.contents());
        }
    }

    /**
     * A method for the help command. Prints out features of the program and its terse commands.
     */
    public static void help(){
        System.out.println("Each directory can only support up to two directories with unlimited text files in each.");
        System.out.println("This program supports path finding for multiple commands (e.g. cd folder/subfolder/...)");
        System.out.println("List of available commands:\n");
        System.out.println("exit - exits the terminal");
        System.out.println("cd - moves the current directory. Arguments of \"..\" moves up to the parent directory");
        System.out.println("pwd - shows the current path");
        System.out.println("mkdir - makes a new directory");
        System.out.println("ls - shows all sub-directories and text files within the current directory");
        System.out.println("touch - creates/overwrites a text file");
        System.out.println("vi - views a text file available in the current directory");
        System.out.println("rm - removes a file of the current directory");
        System.out.println("rmdir - removes a subdirectory of the current directory");
    }

    /**
     * Creates a new directory in a location based upon the input.
     * @param input The string that determines where the newly created directory will be placed.
     * @param curr The current directory.
     */
    public static void makeDirectory(String input, Node curr){
        try {
            if (input.contains("/")) {
                int directoryIndex = input.lastIndexOf("/") + 1;
                String directoryName = input.substring(directoryIndex);
                int startPath = input.indexOf(" ") + 1; //finds the spacing between the command and the actual path specified
                curr = findPath(input.substring(startPath, directoryIndex), curr);
                curr.add(directoryName);
            } else {
                String directoryName = input.substring(input.indexOf(" ") + 1);
                curr.add(directoryName);
            }
        }
        catch(NullPointerException e){
            System.err.println("Path to new folder not found");
        }
    }

    /**
     * List the contents of either the current directory, ancestor, or descendant directory based upon the String input.
     * @param input The String that determines the directory path that ls will take.
     * @param curr the current directory.
     */
    public static void listContents(String input, Node curr){
        if (input.contains("ls ")) {
            curr = findPath(input.substring(input.indexOf(" ") + 1), curr);
        }
        if(curr != null){
            curr.print();
        }
    }

    /**
     * Changes the current directory to either a ancestor, or descendant directory.
     * @param input The string input that determines which directory the new current will be placed in.
     * @param curr The current directory.
     * @return returns the the new directory current will be in.
     */
    public static Node changeDirectory(String input, Node curr) {
        String newDirectory = input.substring(input.indexOf(" ") + 1);
        curr = findPath(newDirectory, curr);
        return curr;
    }

    /**
     * Creates a new text file in a directory based on the String input.
     * @param input The directory path to place the new text file.
     * @param curr The current directory.
     */
    public static void makeFile(String input, Node curr){
        TextFile file = null;
        if (input.contains("/")) {
            int fileIndex = input.lastIndexOf("/") + 1;
            file = new TextFile(input.substring(fileIndex));
            int startPath = input.indexOf(" ") + 1; //finds the spacing between the command and the actual path specified
            curr = findPath(input.substring(startPath, fileIndex), curr);
        }
        else {
            String newFile = input.substring(input.indexOf(" ") + 1);
            file = new TextFile(newFile);
        }
        curr.getData().addFile(file);
    }

    /**
     * Removes a text file in a directory based on the path.
     * @param input The string that shows the directory path to the textfile.
     * @param curr The current directory.
     */
    public static void removeFile(String input, Node curr){
        String fileName = "";
        if (input.contains("/")) {
            int fileIndex = input.lastIndexOf("/") + 1;
            fileName = input.substring(fileIndex);
            int startPath = input.indexOf(" ") + 1; //finds the spacing between the command and the actual path specified
            curr = findPath(input.substring(startPath, fileIndex), curr);
        }
        else {
            fileName = input.substring(input.indexOf(" ") + 1);
        }
        curr.getData().removeFile(fileName);
    }

    /**
     * Removes a subdirectory based on String input.
     * @param input String that provides which subdirectory to delete.
     * @param curr The current directory.
     */
    public static void removeDirectory(String input, Node curr){
        Node folder = null;
        String directoryName = "";
        if(input.equals("rmdir ..")){
            System.out.println("Cannot delete your parent directory");
            return;
        }
        if(input.contains("/")){
            int directoryIndex = input.lastIndexOf("/") + 1;
            directoryName = input.substring(directoryIndex);
            int startPath = input.indexOf(" ") + 1; //finds the spacing between the command and the actual path specified
            curr = findPath(input.substring(startPath, directoryIndex), curr);
            folder = curr.getFolder(directoryName);
            if(folder != null){
                if(!folder.hasChildren()){
                    curr.removeChild(folder);
                }
                else{
                    System.out.println("Cannot delete directory with its own subdirectories");
                }
            }
        }
        else {
            directoryName = input.substring(input.indexOf(" ") + 1);
            folder = curr.getFolder(directoryName);
            if(folder != null){
                curr.removeChild(folder);
            }
        }
    }

    /**
     * Changes the current directory and moves it based on the input that will make it traverse it's sub directories.
     * @param input The directory path that will change the current directory.
     * @param curr The current directory.
     * @return returns the new descendant directory.
     */
    public static Node findPath(String input, Node curr){
        Scanner keyboard = new Scanner(input);
        keyboard.useDelimiter("/");
        while(keyboard.hasNext()){
            String nextNode = keyboard.next();
            if(nextNode.equals("..")){
                curr = curr.getParent();
            }
            else{
                curr = curr.getFolder(nextNode);
            }
        }
        return curr;
    }
}
