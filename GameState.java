import java.awt.event.*;

public class GameState
{
    private Dictionary dictionary;
    private Word currentGuess;
    private Word secretWord;
    private char[][] board;
    private int numGuess;
    private int totalGuesses;
    private boolean isInvalid;
    private boolean isFinished;
    private Feedback feedback;
    
    public GameState() {
        dictionary = new Dictionary("dictionary/word-list.txt");
        currentGuess = new Word();
        totalGuesses = 6;
        isInvalid = false;
        isFinished = false;
        
        secretWord = new Word(dictionary.getRandomStringWord());
        System.out.println(secretWord.toString());
        
        numGuess = 0;
        feedback = new Feedback(totalGuesses, secretWord);
        board = new char[totalGuesses][secretWord.length()];
    }
    
    public char[][] getBoard() {
        return board;
    }
    
    public boolean isFinished() {
        return isFinished;
    }
    
    public boolean isInvalid() {
        return isInvalid;
    }
    
    public Feedback getFeedback() {
        return feedback;
    }
    
    public int getNumGuess() {
        return numGuess;
    }
    
    private boolean isValid() {
        return currentGuess.isFiveChars() && dictionary.contains(currentGuess.toString());
    }
    
    public boolean isCorrectGuess() {
        String g = currentGuess.toString();
        String s = secretWord.toString();
        return g.equals(s);
    }
    
    public void evaluateGuess() {
        if(isCorrectGuess()) {
            isFinished = true;
            feedback.updateResponses(currentGuess, numGuess);
        } else if (isValid() && (numGuess < totalGuesses-1)) {
            feedback.updateResponses(currentGuess, numGuess);
            numGuess++;
            currentGuess = new Word();
        } else if (isValid()) {
            feedback.updateResponses(currentGuess, numGuess);
            isFinished = true;
        } else {
            isInvalid = true;
        }
    }
    
    private void updateBoard() {
        String word = currentGuess.toString();
        for (int i = 0; i < word.length(); i++) {
            board[numGuess][i] = word.charAt(i);
        }
        
        for (int i = word.length(); i < secretWord.length(); i++) {
            board[numGuess][i] = '-';
        }
    }
    
    public boolean writeLetter(char letter) {
        int previousLength = currentGuess.length();
        if (!currentGuess.isFiveChars() && Character.isLetter(letter)) {
            currentGuess.addLetter(Character.toUpperCase(letter));
            updateBoard();
        }
        return previousLength != currentGuess.length();
    }
    
    public void modifyGuess(int keycode) {
        if (keycode == KeyEvent.VK_BACK_SPACE) {
            isInvalid = false;
            if (currentGuess.length() > 0) {
                currentGuess.deleteLetter();
                updateBoard();
            }
        }
        
        if (keycode == KeyEvent.VK_ENTER && currentGuess.isFiveChars()) {
            evaluateGuess();
            updateBoard();
        }
    }
}   
