
/* для добавления условной функции "переключателя" с целью разделения компилятором того,
файл перед ним или папка, добавил небольшой костыль в виде аргумента типа fileSystemUnitType int - эдакий
переключатель состояния, где 1 - это обозначение класса папка, а 0 - обозначение класса файл
 */
//
//
//

package arb.srg.filesystem.manager;

import arb.srg.filesystem.elements.*;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


public class FileSystemManager{
    private Folder folder;
    private final Folder initialFolder;
    private int folderExistenceCheck; /*если чек = 1, значит, требуеммая папка найдена; в противном
    случае (чек = 0) папки с таким именем не существует. Потом поймешь, зачем эта переменная*/

    public FileSystemManager ( Folder folder ) {
        this.folder = folder;
        initialFolder = this.folder;
    }

    public void setFolder (Folder folder){
        this.folder = folder;
    }

    public Folder createNewFileSystemDirectory ( String name ) {
        return new Folder(name, this.folder);
    }

    public File createNewFileSystemFile ( String name ) {
        return new File(name);
    }

    public void addNewFileSystemElement ( String name, Folder folder, int fileSystemUnitType ) throws NullPointerException {
        try {
            if (name.equals("")) {
                System.out.println("Error! The file system element can't have null name.");
                return;
            }
            if (name.equals("../")) {
                System.out.println("Error! The file system element can't have parent folder call as name.");
                return;
            }
            if (name.contains("main")) {
                System.out.println("Error! The file system element can't contain initial folder reference in the name.");
                return;
            }
            if (name.contains("/")) {
                System.out.println("Error! The file system element can't contain slash in the name.");
                return;
            }
            for (FileSystemElement f : folder.getFileSystemElementList()) {
                if (f.getFileSystemElementName().equals(name)) {
                    System.out.println("Error! This file system element already exists. Try again: ");
                    return;
                }
            }
            if (fileSystemUnitType == 1) {
                folder.getFileSystemElementList().add(createNewFileSystemDirectory(name));
            } else if (fileSystemUnitType == 0) {
                folder.getFileSystemElementList().add(createNewFileSystemFile(name));
            }
        } catch (NullPointerException e){
            System.out.println("Error! Check the input and try once again.");
            return;
        }
    }

    public void removeFileSystemElement (String name, Folder folder, int fileSystemUnitType)
            throws ConcurrentModificationException {
        try {
            for (FileSystemElement f : folder.getFileSystemElementList()) {
                if (f.getFileSystemElementName().equals(name)) {
                    switch (fileSystemUnitType) {
                        case 1:
                            if (f instanceof Folder) {
                                folder.getFileSystemElementList().remove(f);
                            } else {
                                System.out.println("Error! This folder doesn't exist.");
                            }
                            break;
                        case 0:
                            if (f instanceof File) {
                                folder.getFileSystemElementList().remove(f);
                            } else {
                                System.out.println("Error! This file doesn't exist.");
                            }
                            break;
                        default:
                            return;
                    }
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    public void listCurrentDirectoryElements (Folder folder) throws NullPointerException{
        try {
            if (folder == null){
                return;
            }
            for (FileSystemElement f : folder.getFileSystemElementList()) {
                System.out.println(f.getFileSystemElementName());
            }
        } catch (NullPointerException e){
            System.out.println("Reason: null folder.");
        }
    }

    public void changeFileSystemElementName (String oldName, String newName, Folder folder) {
        if (oldName.equals(newName)){
            System.out.println("Error! Check correctness of the input.");
            return;
        }
        for (FileSystemElement f : folder.getFileSystemElementList()){
            if (f.getFileSystemElementName().equals(oldName)) {
                f.setFileSystemElementName(newName);
                return;
            } else {
                System.out.println("Error! The folder with such name doesn't exist.");
            }
        }
    }

    public void copyFileSystemElement (String copiedElementName, String copyElementName, Folder folder)
            throws ConcurrentModificationException, CloneNotSupportedException, NoSuchElementException {
        try {
            for (FileSystemElement f : folder.getFileSystemElementList()) {
                if (f.getFileSystemElementName().equals(copiedElementName)) {
                    if (f instanceof Folder) {
                        Folder newFolder = ((Folder) f).clone();
                        newFolder.setFileSystemElementName(copyElementName);
                        folder.getFileSystemElementList().add(newFolder);
                    } else if (f instanceof File) {
                        File newFile = ((File) f).clone();
                        newFile.setFileSystemElementName(copyElementName);
                        folder.getFileSystemElementList().add(newFile);
                    } else {
                        System.out.println("Error! This file system element doesn't exist.");
                        break;
                    }
                }
            }
        } catch (ConcurrentModificationException | CloneNotSupportedException | NoSuchElementException e) {
            return;
        }
    }

    public Folder setDirectoryPath (String input) throws NullPointerException, StringIndexOutOfBoundsException {
        Folder childFolder;
        Folder workingFolder = this.folder;
        if (input.charAt(input.indexOf(" ") + 1) == '~'){
            try {
                input = input.substring(input.indexOf("~"));
                String[] directories = input.split("/");
                for (String directory : directories) {
                    if (directory.equals("")) {
                        break;
                    } else if (directory.equals("~") && (directories.length == 1)) {
                        return this.folder;
                    } else {
                        for (FileSystemElement f : workingFolder.getFileSystemElementList()) {
                            if (f.getFileSystemElementName().equals(directory)) {
                                workingFolder = (Folder) f;
                                folderExistenceCheck = 1;
                                break;
                            } else if (!f.getFileSystemElementName().equals(directory)) {
                                folderExistenceCheck = 0;
                            }
                        }
                    }
                }
                if (folderExistenceCheck == 0){
                    workingFolder = null;
                }
            } catch (NullPointerException e) {
                System.out.println("Reason: null folder.");
            }
        } else if (input.charAt(input.indexOf(" ") + 1) == '/'){
            workingFolder = initialFolder;
            try {
                input = input.substring(input.indexOf("/") + 1);
                String[] directories = input.split("/");
                    for (String directory : directories) {
                        if (directory.equals("")) {
                            break;
                        } else if (directory.equals("main") && (directories.length == 1)) {
                            return workingFolder;
                        }
                        for (FileSystemElement f : workingFolder.getFileSystemElementList()) {
                            if (f.getFileSystemElementName().equals(directory)) {
                                workingFolder = (Folder) f;
                                folderExistenceCheck = 1;
                                break;
                            } else if (!f.getFileSystemElementName().equals(directory)) {
                                folderExistenceCheck = 0;
                            }
                        }
                    }
                if (folderExistenceCheck == 0){
                    workingFolder = null;
                }
            } catch (NullPointerException e) {
                System.out.println("Reason: null folder.");
            }
        } else if (input.split(" ")[1].equals("../")){
                childFolder = this.folder;
                this.folder = this.folder.getParentFolder();
                if (this.folder == null) {
                    System.out.println("Initial folder / reached.");
                    this.folder = childFolder;
                    return this.folder;
            }
        } else {
            System.out.println("Error! Check the input and try once again.");
            return null;
        }
        return workingFolder;
    }
}

