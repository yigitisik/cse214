/**
 * StorageTable class represent a storage table and
 * has action moves including getting or putting storage
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw6
 * Rec 02 Jamieson Barkume - Steven Secreti
 */

import java.io.*;
import java.util.*;
public class StorageTable extends Hashtable<Integer, Storage> implements Serializable{
    public static int serialVersionUID;

    /**
     * puts the box with a specific id into a specific storage
     * @param storageId for key of storage
     * @param storage for storage location
     * @throws IllegalArgumentException when invalid argument
     */
    public void putStorage(int storageId, Storage storage) throws IllegalArgumentException{
        if(containsKey(storageId) || storageId < 0 || storage == null){
            throw new IllegalArgumentException("Invalid argument.");
        }else{
            put(storageId, storage);
        }
    }

    /**
     * gets the box with a specific id
     * @param storageID for which box
     * @return either null for not found or the box itself when found
     */
    public Storage getStorage(int storageID){
        if(!containsKey(storageID)) {
            return null;
        }else{
            return get(storageID);
        }
    }
}
