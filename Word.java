import java.util.ArrayList;

public class Word
{
    private String currentWord;
    private int wordlength;
    
    public Word() {
        currentWord = "";
        wordlength = 5;
    }
    
    public Word(String word) {
        currentWord = word;
        wordlength = currentWord.length();
    }
    
    public void addLetter(char letter) {
        currentWord = currentWord + letter;
    }
    
    public void deleteLetter() {
        if (currentWord.length() > 0) {
            currentWord = currentWord.substring(0,currentWord.length()-1);
        }
    }
    
    public boolean isFiveChars() {
        return currentWord.length() == wordlength;
    }
    
    public String toString() {
        return currentWord;
    }
    
    public boolean constains(String letter) {
        return currentWord.contains(letter);
    }
    
    public int length() {
        return currentWord.length();
    }
    
    private int numberOfEqualLetters(char letter) {
        int counter = 0;
        for (int i = 0; i<currentWord.length(); i++) {
            if (currentWord.charAt(i) == letter) {
                counter++;
            }
        }
        return counter;
    }
    
    private boolean lettersIsUnique(char letter) {
        return numberOfEqualLetters(letter) <= 1;
    }  
    
    public ArrayList<Integer> getIndexOf(char letter) {
        int index = currentWord.indexOf(letter);
        ArrayList<Integer> occurrences = new ArrayList<>();
        
        while (index >= 0) {
            occurrences.add(index);
            index = currentWord.indexOf(letter, index+1);
        }
        
        return occurrences;
    }
    
    private Response[] getInitResponses() {
        Response[] responses = new Response[wordlength];
        
        for (int i = 0; i < responses.length; i++) {
            responses[i] = Response.UNKNOWN;
        }
        
        return responses;
    }
    
    private Response getResponseLetterIsUnique(char secretLetter, char guessLetter, String secretWord) {
        if (secretLetter == guessLetter) {
            return Response.CORRECT;
        } else if (secretWord.contains(""+guessLetter)) {
            return Response.ALMOST_CORRECT;
        } else {
            return Response.WRONG;
        }
    }
    
    public ArrayList<Integer> completelyCorrectIndexes(ArrayList<Integer> indexesOfGuess, ArrayList<Integer> indexesOfSec, Response[] responses) {
        ArrayList<Integer> completelyCorrectIndexes = new ArrayList<>();
        
        for (Integer indexOfGuess: indexesOfGuess) {
            for (Integer indexOfSecret: indexesOfSec) {
                if (indexOfGuess == indexOfSecret) {
                    responses[indexOfGuess] = Response.CORRECT;
                    completelyCorrectIndexes.add(indexOfGuess);
                }
            }
        }
        
        return completelyCorrectIndexes;
    }
    
    private Response[] setRemainingResponses(ArrayList<Integer> indexesOfGuess, ArrayList<Integer> indexesOfSec, Response[] responses) {
        int numOfSecretLettersBack = indexesOfSec.size();
        
        for (Integer indexOfGuess : indexesOfGuess) {
            if (numOfSecretLettersBack > 0) {
                numOfSecretLettersBack--;
                responses[indexOfGuess] = Response.ALMOST_CORRECT;               
            } else {
                responses[indexOfGuess] = Response.WRONG;               
            }
        }
        
        return responses;
    }
    
    public Response[] getLetterResponses(Word guess) {
        String secretWord = currentWord;
        String guessWord = guess.toString();
        Response[] responses = getInitResponses();
        
        for(int i = 0; i < wordlength ; i++) {
            char secretLetter = secretWord.charAt(i);
            char guessLetter = guessWord.charAt(i);
            Response currentResponse = responses[i];
            
            if (currentResponse == Response.UNKNOWN) {
                if (guess.lettersIsUnique(guessLetter)) {
                    responses[i] = getResponseLetterIsUnique(secretLetter, guessLetter, secretWord);
                } else {
                    ArrayList<Integer> indexesOfSec = getIndexOf(guessLetter);
                    ArrayList<Integer> indexesOfGuess = guess.getIndexOf(guessLetter);
                    ArrayList<Integer> completelyCorrectIndexes = completelyCorrectIndexes(indexesOfGuess, indexesOfSec, responses);
                    
                    indexesOfSec.removeAll(completelyCorrectIndexes);
                    indexesOfGuess.removeAll(completelyCorrectIndexes);
                    
                    responses = setRemainingResponses(indexesOfGuess, indexesOfSec, responses);
                }
            }
            
        }   
        return responses;
    }
}
