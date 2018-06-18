package com.instinctools.test.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Simple JavaBean domain object that represents a File.
 *
 * @author Gleb Kuzmitski
 * @version 1.0
 *
 */

@Entity
public class FilenameSaver {

    /**
     * FilenameSave ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * FilenameSave filename
     */
    private String filename;


    /**
     * @return id
     */
    public Long getId() {
        return id;
    }


    /**
     * @param id  the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return filename without uuid prefix
     */
    public String getFilenameNormally(){
        StringBuilder stringBuilder = new StringBuilder(filename);
        return stringBuilder.delete(0, 37).toString();
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
