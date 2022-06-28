package arb.srg.filesystem.elements;

public class File extends FileSystemElement implements Cloneable{
    private String name;

    public File (String name){
        this.name = name;
    }

    public String getFileSystemElementName (){
        return name;
    }

    public void setFileSystemElementName (String name){
        this.name = name;
    }

    @Override
    public File clone() throws CloneNotSupportedException{
        return (File) super.clone();
    }
}