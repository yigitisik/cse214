/**
 * StorageManager class represents a managing point of the store with storage table attribute and executes
 * the all desired choices
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw6
 * Rec 02 Jamieson Barkume - Steven Secreti
 */

import java.io.*;
import java.util.*;

public class StoreManager {
    private static StorageTable storageTable;

    /**
     * the method for all actions and movements that is meant for store manager to do onto the storage table
     * @param args for syntax of all args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        storageTable = new StorageTable();
        try {
            FileInputStream file = new FileInputStream("storage.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            storageTable = (StorageTable) inStream.readObject();
            inStream.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found.\n" +
                               "An empty StorageTable object is created and will be used instead.");
        } catch (IOException ioe) {
            System.out.println("Input output error.");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found.");
        }

        boolean isQuit = false;
        Storage storage;
        int id;
        String client;
        String contents;
        System.out.println("Hello, and welcome to Rocky Stream Storage Manager");
        while(!isQuit) {
            System.out.println("\n" +
                            "P - Print all storage boxes\n" +
                            "A - Insert into storage box\n" +
                            "R - Remove contents from a storage box\n" +
                            "C - Select all boxes owned by a particular client\n" +
                            "F - Find a box by ID and display its owner and contents\n" +
                            "Q - Quit and save workspace\n" +
                            "X - Quit and delete workspace\n"+
                            "Please select an option:");
            String choice = scan.nextLine().toLowerCase();
            switch (choice) {
                case "p":
                    StringBuilder table = new StringBuilder(String.format("%-15s%-35s%-25s", "Box#", "Contents", "Owner"));
                    table.append("\n----------------------------------------------------------------");

                    ArrayList<Integer> tableKeys = new ArrayList<>(storageTable.keySet());
                    Collections.sort(tableKeys);
                    for (int i: tableKeys) {
                        storage = storageTable.getStorage(i);
                        table.append("\n" + String.format("%-15s%-35s%-25s",
                                storage.getId(), storage.getContents(), storage.getClient()));
                    }
                    System.out.println(table);
                    break;

                case "a":
                    System.out.println("Please enter ID:");
                    try{
                        id = scan.nextInt();
                        scan.nextLine();
                        if(id<0 || storageTable.containsKey(id)){throw new IllegalArgumentException();}
                    } catch (IllegalArgumentException iae){
                        System.out.println("Invalid argument due to either \"< 0\" or id/key already exists\n" +
                                           "Please try again.");
                        break;
                    }
                    System.out.println("Please enter client:");
                    client = scan.nextLine();
                    System.out.println("Please enter contents:");
                    contents = scan.nextLine();
                    storage = new Storage(id, client, contents);
                    storageTable.putStorage(id, storage);
                    System.out.println("Storage " + id + " set!");
                    break;

                case "r":
                    System.out.println("Please enter ID:");
                    id = scan.nextInt();
                    scan.nextLine();
                    if(storageTable.containsKey(id)){
                        storageTable.remove(id);
                        System.out.println("Box " + id + " is now removed.");
                    }else{
                        System.out.println("Box not found for removal.");
                    }
                    break;

                case "c":
                    boolean isFound = false;
                    System.out.println("Please enter the name of the client:");
                    client = scan.nextLine();

                    StringBuilder tableByClient = new StringBuilder(String.format("%-15s%-35s%-25s", "Box#", "Contents", "Owner"));
                    tableByClient.append("\n----------------------------------------------------------------");

                    ArrayList<Integer> tableKeysByClient = new ArrayList<>(storageTable.keySet());
                    Collections.sort(tableKeysByClient);
                    for (int i : tableKeysByClient) {
                        storage = storageTable.getStorage(i);
                        if (storage.getClient().equals(client)) {
                            tableByClient.append(
                                    String.format("\n" + "%-15s%-35s%-25s",
                                    storage.getId(), storage.getContents(), storage.getClient()));
                            isFound = true;
                        }
                    }
                    tableByClient.append("\n");
                    if (isFound) {
                        System.out.println(tableByClient);
                    } else {
                        System.out.println("Box not found based on given params.");
                    }
                    break;

                case "f":
                    System.out.println("Please enter ID:");
                    id = scan.nextInt();
                    scan.nextLine();
                    storage = null;

                    StringBuilder tableById = new StringBuilder(String.format("%-15s%-35s%-25s", "Box#", "Contents", "Owner"));
                    tableById.append("\n----------------------------------------------------------------");

                    ArrayList<Integer> tableKeysById = new ArrayList<>(storageTable.keySet());
                    Collections.sort(tableKeysById);
                    for (int i : tableKeysById) {
                        storage = storageTable.getStorage(i);
                        if (storage.getId() == id) {
                            tableById.append(
                                    String.format("\n" + "%-15s%-35s%-25s",
                                            storage.getId(), storage.getContents(), storage.getClient()));
                        }
                    }
                    tableById.append("\n");

                    if (storage != null) {
                        System.out.println(tableById);
                    } else {
                        System.out.println("Box not found based on given params.");
                    }
                    break;

                case "q":
                    isQuit = true;
                    System.out.println("Storage Manager is quitting, current storage is saved for next session.");
                    try{
                        FileOutputStream file = new FileOutputStream("storage.obj");
                        ObjectOutputStream outStream = new ObjectOutputStream(file);
                        outStream.writeObject(storageTable);
                        outStream.close();
                    } catch (FileNotFoundException fnfe) {
                        System.out.println("File not found.");
                    } catch (IOException ioe) {
                        System.out.println("Input output error.");
                    }
                    break;

                case "x":
                    isQuit = true;
                    System.out.println("Storage Manager is quitting, all data is being erased.");
                    File erasedDataFile = new File("storage.obj");
                    erasedDataFile.delete();
                    break;

                default:
                    System.out.println("Invalid argument, try again.");
                    break;

            }
        }
    }
}
