package arb.srg.filesystem.elements;

import java.util.LinkedList;

public class Folder extends FileSystemElement implements Cloneable{
    private String name;
    private Folder parentFolder;
    private LinkedList<FileSystemElement> fileSystemElementList = new LinkedList<FileSystemElement>();

    public Folder (String name){
        this.name = name;
    }

    public Folder (String name, Folder parentFolder){
        this.name = name;
        this.parentFolder = parentFolder;
    }

    public String getFileSystemElementName (){
        return name;
    }

    public void setFileSystemElementName (String name){
        this.name = name;
    }

    public Folder getParentFolder (){
        return parentFolder;
    }

    public LinkedList<FileSystemElement> getFileSystemElementList(){
        return this.fileSystemElementList;
    }

    @Override
    public Folder clone() throws CloneNotSupportedException{
        return (Folder) super.clone();
    }
}