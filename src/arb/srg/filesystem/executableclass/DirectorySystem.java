package arb.srg.filesystem.executableclass;

import arb.srg.filesystem.elements.*;
import arb.srg.filesystem.manager.*;
import arb.srg.filesystem.menu.*;

public class DirectorySystem {
    public static void main(String[] args) {
        Folder initialFolder = new Folder("main");
        FileSystemManager fileSystemManager = new FileSystemManager(initialFolder);
        ContextMenu contextMenu = new ContextMenu(fileSystemManager);
        contextMenu.callContextMenu();
    }
}
