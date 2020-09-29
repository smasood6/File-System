import java.util.Scanner;

/**
 * A text file class that allows the user to hold textual information.
 */
public class TextFile {
    private String data;
    private String name;
    public TextFile(String name){
        setName(name);
        data = "";
    }

    public TextFile(){
        data = "";
    }

    public void setContents(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter your data for your text file: \n");
        data = "";
        while(keyboard.hasNext()){
            data += keyboard.nextLine() + "\n";
            if (data.contains("wq")) {
                data = data.substring(0, data.indexOf("wq"));
                break;
            }
        }
    }

    public String contents(){
        return data;
    }

    public void setName(String name){
        if(name.indexOf(".txt") > 0){
            this.name = name;
        }
        else{
            this.name = name + ".txt";
        }
    }
    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof TextFile){
            TextFile newFile = (TextFile) o;
            return newFile.data.equals(this.data) && newFile.name.equals(this.name);
        }
        return false;
    }

    public static void main(String [] args){
        TextFile text = new TextFile("yes.txt");
        text.setContents();
        System.out.println("\n\n" + text.contents());
    }
}
