/**
 * Storage class represent a storage box registered with the company and
 * has attributes like id, client, and contents
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw6
 * Rec 02 Jamieson Barkume - Steven Secreti
 */

import java.io.Serializable;
public class Storage implements Serializable {
    public static long serialVersionUID;
    private int id;
    private String client;
    private String contents;

    /**
     * contructor of storage that takes in:
     * @param id for storage id
     * @param client for storage's client
     * @param contents for storage's contents
     */
    public Storage(int id, String client, String contents) {
        this.id = id;
        this.client = client;
        this.contents = contents;
    }

    /**
     * @return id of storage
     */
    public int getId() {
        return id;
    }

    /**
     * sets id of storage
     * @param id for new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return client associated with storage
     */
    public String getClient() {
        return client;
    }

    /**
     * sets client for the storage
     * @param client for new client
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return contents of storage
     */
    public String getContents() {
        return contents;
    }

    /**
     * sets contents for the storage
     * @param contents for new contents
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
