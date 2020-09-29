import java.io.FileNotFoundException;

public class BinaryTree {
/*
    private Node root;

    public BinaryTree(Node root){
        this.root = root;
    }

    public void add(Node curr, Node newNode){
        try {
            if (curr.left == null) {
                curr.left = newNode;
                newNode.parent = curr;
            }
            else if (curr.right == null) {
                curr.right = newNode;
                newNode.parent = curr;
            }
            else {
                throw new IndexOutOfBoundsException();
            }
        }
        catch(IndexOutOfBoundsException e){
            System.err.println("Cannot add any more files to the current folder. Reached maximum capacity for sub folders.");
        }
    }

    public Node getRoot(){
        return root;
    }
    public Node getFolder(Node curr, String name){
        Node founded = null;
        try {
            if (curr.left.data.getName().equals(name)) {
                founded = curr.left;
            } else if (curr.right.data.getName().equals(name)) {
                founded = curr.right;
            } else {
                throw new FileNotFoundException();
            }
        }
        catch(FileNotFoundException e){
            System.err.println("Path to folder not found");
        }
        return founded;
    }

    public static String currentPath(Node curr){
        if(curr.data.getName().equals("root")){
            return "/root";
        }
        return currentPath(curr.parent) + "/" + curr.data.getName();
    }
*/
}
