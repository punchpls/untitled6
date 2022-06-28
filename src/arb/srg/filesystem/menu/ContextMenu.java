package arb.srg.filesystem.menu;

import java.util.Scanner;
import static java.lang.System.in;
import arb.srg.filesystem.manager.*;

public class ContextMenu {
    private final FileSystemManager fileSystemManager;

    public ContextMenu ( FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
    }

    public void callContextMenu () throws StringIndexOutOfBoundsException {
        Scanner sc = new Scanner(in);
        String input;
        String path;

        System.out.println("Input the command please in the console. Available commands: \n");
        System.out.println("newd - create a new folder");
        System.out.println("newf - create a new file");
        System.out.println("cd - change current directory. Input \"../\" for opening parent folder");
        System.out.println("ls - output the list of the files and folders in the current directory");
        System.out.println("rmf - delete a file");
        System.out.println("rmd - delete a folder");
        System.out.println("rnm - rename a file system unit");
        System.out.println("copy - copy a file system unit");
        System.out.println("exit session - exit the program");
        System.out.println();
        System.out.println("Hint: main folder is called \"main\"");
        System.out.println();
        try {
            do {
                input = sc.nextLine();
                if (!input.contains("/")){
                    if (input.contains("exit session")){
                        continue;
                    }
                    else{
                        System.out.println("Error! Incorrect input.");
                        callContextMenu();
                    }
                }
                switch (input.substring(0, input.indexOf(" "))) {
                    case "newd":
                        path = input.substring(0, input.lastIndexOf("/"));
                        String folderName = input.substring(input.lastIndexOf("/") + 1);
                        if (folderName.contains(" ")){
                            System.out.println("Error! The name of the element can't contain space.");
                            break;
                        }
                        fileSystemManager.addNewFileSystemElement(folderName,
                                fileSystemManager.setDirectoryPath(path), 1);
                        break;
                    case "newf":
                        path = input.substring(0, input.lastIndexOf("/"));
                        String fileName = input.substring(input.lastIndexOf("/") + 1);
                        if (fileName.contains(" ")){
                            System.out.println("Error! The name of the element can't contain space.");
                            break;
                        }
                        fileSystemManager.addNewFileSystemElement(fileName,
                                fileSystemManager.setDirectoryPath(path), 0);
                        break;
                    case "cd":
                        fileSystemManager.setFolder(fileSystemManager.setDirectoryPath(input));
                        break;
                    case "ls":
                        fileSystemManager.listCurrentDirectoryElements(fileSystemManager.setDirectoryPath(input));
                        break;
                    case "rmf":
                        fileName = input.substring(input.lastIndexOf("/") + 1);
                        path = input.substring(0, input.lastIndexOf("/"));
                        fileSystemManager.removeFileSystemElement(fileName,
                                fileSystemManager.setDirectoryPath(path), 0);
                        break;
                    case "rmd":
                        folderName = input.substring(input.lastIndexOf("/") + 1);
                        path = input.substring(0, input.lastIndexOf("/"));
                        fileSystemManager.removeFileSystemElement(folderName,
                                fileSystemManager.setDirectoryPath(path), 1);
                        break;
                    case "rnm":
                        String newName = input.substring(input.lastIndexOf(" ") + 1);
                        String substr = input.substring(0, input.lastIndexOf(" "));
                        String currentName = substr.substring(substr.lastIndexOf("/") + 1);
                        path = input.substring(0, input.lastIndexOf("/"));
                        fileSystemManager.changeFileSystemElementName(currentName, newName, fileSystemManager.setDirectoryPath(path));
                        break;
                    case "copy":
                        String copyElementName = input.substring(input.lastIndexOf(" ") + 1);
                        substr = input.substring(0, input.lastIndexOf(" "));
                        String copiedElementName = substr.substring(substr.lastIndexOf("/") + 1);
                        path = input.substring(0, input.lastIndexOf("/"));
                        fileSystemManager.copyFileSystemElement(copiedElementName, copyElementName,
                                fileSystemManager.setDirectoryPath(path));
                    case "exit session":
                        break;
                    default:
                        System.out.println("Wrong input! Try again please.");
                }
            } while (!input.equals("exit session"));
        } catch (StringIndexOutOfBoundsException | CloneNotSupportedException e) {
            System.out.println("Error!");
        }
    }
}