import javax.naming.NameNotFoundException;
import java.io.FileNotFoundException;

/**
 * An implicit binary tree class that supports the File Directory class.
 * Shows the current working directory based on the instance of the object.
 */
public class Node {
    private Folder data;
    private Node left;
    private Node right;
    private Node parent;

    public Node(Folder data){
        this.data = data;
        this.parent = null;
        right = null;
        left = null;
    }

    public Node add(String folderName) {
        Node newNode = new Node(new Folder(folderName));
        try {
            if(folderName.equals("")){
                throw new NameNotFoundException();
            }
            if (this.left == null) {
                this.left = newNode; 
                newNode.parent = this;
            }
            else if (this.right == null) {
                this.right = newNode;
                newNode.parent = this;
            }
            else {
                throw new IndexOutOfBoundsException();
            }
        }
        catch(IndexOutOfBoundsException e){
            System.err.println("Cannot add any more files to the current folder. Reached maximum capacity for sub folders.");
        }
        catch(NameNotFoundException e){
            System.err.println("Name for folder not specified");
        }
        return newNode;
    }

    public Folder getData(){
        return data;
    }

    public boolean hasChildren(){
        return this.left != null || this.right != null;
    }

    public Node getParent(){
        try{
            if(this.parent != null){
                return this.parent;
            }
            throw new IndexOutOfBoundsException();
        }
        catch(IndexOutOfBoundsException e){
            System.err.println("Root is top directory, cannot move past root.");
        }
        return this;
    }
    public Node getFolder(String name){
        Node founded = null;
        try {
            if (this.left != null && this.left.data.getName().equals(name)) {
                founded = this.left;
            } else if ( this.right != null && this.right.data.getName().equals(name)) {
                founded = this.right;
            } else {
                throw new FileNotFoundException();
            }
        }
        catch(FileNotFoundException e){
            System.err.println("Folder not found");
        }
        return founded;
    }

    public boolean removeChild(Node child){
        if(this.right == child){
            this.right = null;
            return true;
        }
        if(this.left == child){
            this.left = null;
            return true;
        }
        return false;
    }

    public static String currentPath(Node curr){
        if(curr.data.getName().equals("root")){
            return "/root";
        }
        return currentPath(curr.parent) + "/" + curr.data.getName();
    }

    public void print(){
        if(this.right != null){
            System.out.println(this.right.data.getName());
        }
        if(this.left != null){
            System.out.println(this.left.data.getName());
        }
        this.data.showFiles();
    }
}
