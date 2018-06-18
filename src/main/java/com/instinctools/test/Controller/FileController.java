package com.instinctools.test.Controller;


import com.instinctools.test.Repo.FileRepo;
import com.instinctools.test.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 * Controller for {@link com.instinctools.test.Entity.FilenameSaver}'s pages.
 *
 * @author Gleb Kuzmitski
 * @version 1.0
 */

@Controller
public class FileController {

    /**
     * uploadPath is a String presentation to a folder,
     * where the text will be stored(check the application properties to set the folder)
     */
    @Value("${upload.path}")
    private String uploadPath;


    private final FileService fileService;
    private final FileRepo fileRepo;

    /**
     * Constructor which is used to set
     * {@link com.instinctools.test.Service.FileService},
     * {@link com.instinctools.test.Repo.FileRepo}
     *
     * @param fileService {@link com.instinctools.test.Service.FileService}
     * @param fileRepo {@link com.instinctools.test.Repo.FileRepo}
     */
    @Autowired
    public FileController(FileService fileService, FileRepo fileRepo) {
        this.fileService = fileService;
        this.fileRepo = fileRepo;
    }


    /**
     * Return a web page with all presented files
     *
     * @param model is used to add to page all files from database
     * @return web page
     * @throws IOException if the file which should be checked is not presented
     */
    @GetMapping()
    public String startPage(Model model) throws IOException {
        model.addAttribute("texts" , fileRepo.findAll());
        return "start";
    }


    /**
     * This post method is responsible to uploading the file
     *
     * @param model is used to add to page all files from database
     * @param file is used to find a form which is responsible to upload the file
     * @return redirect to {@link com.instinctools.test.Controller.FileController#startPage(Model)}
     * @throws IOException if the file which should be checked is not presented
     */
    @PostMapping("saveFile")
    public String addFile(Model model ,@RequestParam("file") MultipartFile file) throws IOException {
        fileService.saveFile(file);
        model.addAttribute("texts" , fileRepo.findAll());
        return "redirect:/start";
    }


    /**
     * This get method is responsible to display work of {@link com.instinctools.test.Service.FileService#getTopTenWords(Long)}
     * on web page
     *
     * @param id id must not be {@literal null}
     * @param model is used to add a List of Strings with top 10 most popular words in text to web page
     * @return a web page (uploadView.ftl)
     * @throws IOException if the file which should be checked is not presented
     */
    @GetMapping("information/{id}")
    public String getList(@PathVariable Long id ,
                          Model model) throws IOException {
        List<String> topTenWords = fileService.getTopTenWords(id);
        if (topTenWords.size()<10){
            model.addAttribute("message" , "Sorry but "
                    + fileRepo.getOne(id).getFilenameNormally()
                    + " has only " + topTenWords.size()
                    + " words which satisfies the conditions" );
        }
        model.addAttribute("topWords" , topTenWords);
        model.addAttribute("filename" , fileRepo.getOne(id).getFilenameNormally());
        return "uploadView";
    }



    /**
     * This method can be used to display {@link com.instinctools.test.Service.FileService#getTopTenWords(Long)}
     * on web page by sending a json response
     *
     * @param id id id must not be {@literal null}
     * @return a json response
     * @throws IOException if the file which should be checked is not presented
     */
   /* @GetMapping("/information/{id}")
    @ResponseBody
    public List<String> test(@PathVariable Long id) throws IOException {
        return fileService.getTopTenWords(id);
    }*/




    /**
     * This get method is responsible to display work of {@link com.instinctools.test.Service.FileService#isBracketsCorrect(Long)}
     * on web page
     *
     * @param id id must not be {@literal null}
     * @param model is used to add correct or incorrect to web page
     * @return a web page (brackets.ftl)
     * @throws IOException if the file which should be checked is not presented
     */
    @GetMapping("check/{id}")
    public String checkBrackets(@PathVariable Long id,
                                Model model) throws IOException{
        model.addAttribute("file" , fileRepo.getOne(id).getFilenameNormally());
       if ( fileService.isBracketsCorrect(id)){
           model.addAttribute("checked" , "correctly");
       }else {
           model.addAttribute("checked" , "incorrectly");
       }
       return "brackets";
    }
}
