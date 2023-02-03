import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Dictionary
{
    private ArrayList<String> words;
    
    public Dictionary(String filename) {
        words = new ArrayList<>();
        try{
            File wordList = new File(filename);
            Scanner scanner = new Scanner(wordList);
            
            while(scanner.hasNext()) {
                String word = scanner.next();
                if (word.length() == 5) {
                    words.add(word.toUpperCase());
                }
            }
            
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<String> getWords() {
        return words;
    }
    
    public boolean contains(String word) {
        return words.contains(word);
    }
    
    public String getRandomStringWord() {
        Random random = new Random();
        int randomNumber = random.nextInt(words.size());
        
        String randomWord = words.get(randomNumber);
        return randomWord;
    }
}
