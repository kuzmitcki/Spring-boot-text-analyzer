package com.instinctools.test.Service;

import com.instinctools.test.Entity.FilenameSaver;
import com.instinctools.test.Repo.FileRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link FileService} interface
 *
 * @author Gleb Kuzmitski
 * @version 1.0
 */


@Service
public class FileServiceImpl implements FileService {

    private final static  Logger LOGGER =  LoggerFactory.getLogger(FileService.class);
    private  Scanner reader;

    private final FileRepo fileRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.read}")
    private String uploadRead;

    @Autowired
    public FileServiceImpl(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    @Override
    public List<String> getTopTenWords(Long id) throws IOException {
        List<String> list = new ArrayList<>(getSortedWordsMap(fileRepo.getOne(id).getId()).keySet());
        Set<String> allIncorrectWords = getAllIncorrectWords();
        for (Iterator<String> iter = list.listIterator(); iter.hasNext(); ) {
            String s = iter.next();
            for (String word : allIncorrectWords) {
                if (s.equalsIgnoreCase(word)) {
                    iter.remove();
                }
            }
        }
        List<String> topTenWords = new LinkedList<>();
        if (list.size() <= 10) {
            return list;
        }
        for (int i = 0; i < 10; i++) {
            topTenWords.add(i, list.get(i));
        }
        return topTenWords;
    }

    @Override
    public Map<String, Integer> getSortedWordsMap(Long id){
        Scanner reader = null;
        ArrayList<String> wordList = new ArrayList<>();
        try {
            reader = new Scanner(new FileReader(uploadPath + "/" + fileRepo.getOne(id).getFilename()));
            while (reader.hasNext()) {
                String lowerCase = reader.next().toLowerCase();
                if (lowerCase.contains("n't")) {
                    String deleteSpecChars = lowerCase.replace("n't", "");
                    wordList.add(deleteSpecChars);
                } else {
                    wordList.add(lowerCase);
                }
            }
                String myRegex = "[^a-zA-Z0-9]";
                int index = 0;
                for (String s : wordList) {
                wordList.set(index++, s.replaceAll(myRegex, ""));
            }
            Map<String, Integer> map = new HashMap<>();
            for (String str : wordList) {
                map.merge(str, 1, (a, b) -> a + b);
            }

            return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors
                            .toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        }catch (IOException e){
            fileRepo.deleteById(id);
            LOGGER.info("Fail to load file " + e.getMessage());
        } finally {
            assert reader != null;
            reader.close();
        }
        return new HashMap<>();
    }

    @Override
    public boolean isBracketsCorrect(Long id) throws IOException {
        Scanner scanner = new Scanner(new FileReader(uploadPath + "/" + fileRepo.getOne(id).getFilename()));
        Stack<Character> first = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()){
            stringBuilder.append(scanner.next());
        }
        String str = stringBuilder.toString();
        if (str.isEmpty())
            return true;

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < str.length(); i++)
        {
            char current = str.charAt(i);
            if (current == '{' || current == '(' || current == '[')
            {
                stack.push(current);
            }


            if (current == '}' || current == ')' || current == ']')
            {
                if (stack.isEmpty())
                    return false;

                char last = stack.peek();
                if (current == '}' && last == '{' || current == ')' && last == '(' || current == ']' && last == '[')
                    stack.pop();
                else
                    return false;
            }

        }

        return stack.isEmpty();
    }


    @Override
    public Set<String> getAllIncorrectWords() throws IOException {
        Set<String> incorrectWords = new HashSet<>();
        try (Scanner readFile = new Scanner(new FileReader(uploadRead + "/" + "words.txt"))) {
            while (readFile.hasNext()) {
                incorrectWords.add(readFile.next());
            }
            return incorrectWords;
        }
    }
    public void saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            FilenameSaver filenameSaver = new FilenameSaver();
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile +  "!"  + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            LOGGER.info("FilenameSaver successfully uploaded");
            filenameSaver.setFilename(resultFilename);
            fileRepo.save(filenameSaver);
        }else {
            LOGGER.info("Problem with uploading file");
        }
    }

}
