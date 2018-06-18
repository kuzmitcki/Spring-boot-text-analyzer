package com.instinctools.test.Repo;

import com.instinctools.test.Entity.FilenameSaver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for {@link com.instinctools.test.Entity.FilenameSaver}
 * (check the application.properties to configure database)
 *
 * @author Gleb Kuzmitski
 * @version 1.0
 */

public interface FileRepo extends JpaRepository<FilenameSaver, Long> {

}
