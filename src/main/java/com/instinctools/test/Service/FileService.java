package com.instinctools.test.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service class for {@link com.instinctools.test.Entity.FilenameSaver}
 *
 * @author Gleb Kuzmitski
 * @version 1.0
 */

public interface FileService {


    /**
     * This method getting a List of keys from {@link com.instinctools.test.Service.FileService#getSortedWordsMap(Long)},
     * by finding a filename from database by id,
     * also getting an another List of Strings from {@link FileService#getAllIncorrectWords()} ,
     * than loop through first list and try to finding the same words from another list, if this word is founded remove it.
     * Than return List of Strings with top ten most popular words in text,
     * or if the number of the words is less than 10 return a List of Strings with all the presented words.
     *
     * @param id id must not be {@literal null}
     * @return List of Strings with top ten most popular words in text,
     * or if the number of the words is less than 10 return a List of Strings with all the presented words
     * @throws IOException if the file which should be checked is not presented (check the application properties to set the folder)
     */
    List<String> getTopTenWords(Long id) throws IOException;

    /**
     *  This method try to find a text, which is downloaded to your pc by finding a specific filename in database by {@param id},
     *  than delete all none words elements and finds occurrence of this words to the text
     *  and adding this param to map value. Sort this map by value. Than return a sorted by value map.
     *
     * @param id must not be {@literal null}
     * @return a sorted by value(how many times the word is presented in text) map with all words from text
     * @throws IOException if the file which should be checked is not presented and delete file by id if IOException (check the application properties to set the folder)
     */
    Map<String , Integer> getSortedWordsMap(Long id) throws IOException;

    /**
     * Parenthesis/Brackets matching using Stack algorithm
     *
     * @param id must not be {@literal null}
     * @return true if brackets in text are placed correctly, false otherwise
     * @throws IOException if the file which should be checked is not presented (check the application properties to set the folder)
     */
    boolean isBracketsCorrect(Long id) throws IOException;

    /**
     * Return a Set of Strings with all the pronouns, the prepositions and the conjunctions
     *
     * @return a Set of Strings with all the pronouns, the prepositions and the conjunctions
     * @throws IOException if file with words which shouldn't be considered as repetitive is not presented (check the application properties to set the folder)
     */
    Set<String> getAllIncorrectWords() throws IOException;

    /**
     * Save a file to specific directory on PC,
     * add an encrypted filename and saves
     * {@link com.instinctools.test.Entity.FilenameSaver}
     *
     * @param file  must not be {@literal null}
     * @throws IOException if not cannot found a file (check the application properties to set the folder)
     */
    void saveFile(MultipartFile file) throws IOException;
}
