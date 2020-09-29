import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A folder class that holds an unlimited number of text files.
 */
public class Folder {
    private ArrayList<TextFile> textfiles;
    private String name;

    public Folder(String name){
        this.name = name;
        textfiles = new ArrayList<TextFile>();
    }

    public void addFile(TextFile newFile){
        TextFile prevFile = existingFile(newFile.toString());
        if(prevFile == null){
            newFile.setContents();
            textfiles.add(newFile);
        }
        else{
            prevFile.setContents();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public void showFiles(){
        for(TextFile file: textfiles){
            System.out.println(file);
        }
    }

    public TextFile getFile(String name){
        TextFile file = null;
        try{
            file = existingFile(name);
            if(file == null){
                throw new FileNotFoundException();
            }
        }

        catch(FileNotFoundException e){
            System.err.println("File not found");
        }
        return file;
    }

    private TextFile existingFile(String name) {
        TextFile file = null;
        for (int i = 0; i < textfiles.size(); i++) {
            if (textfiles.get(i).toString().equals(name)) {
                file = textfiles.get(i);
            }
        }
        return file;
    }

    public boolean removeFile(String name){
        return textfiles.remove(getFile(name));
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Folder){
            Folder newFolder = (Folder) o;
            return this.textfiles.equals(newFolder.textfiles) && this.name.equals(newFolder.name);
        }
        return false;
    }
}
