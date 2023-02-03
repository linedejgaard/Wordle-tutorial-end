import java.awt.*;
import javax.swing.JComponent;
import java.io.File;
import javax.imageio.ImageIO;

import java.awt.event.*;
import java.awt.event.KeyListener;

public class WordleGUI extends JComponent implements KeyListener
{
    private Image[] keyImages;
    private Keyboard keyboard;
    private char[][] letters;
    private int KEY_WIDTH, KEY_HEIGHT;
    
    private Image correctLetter, almostCorrectLetter, wrongLetter, unknownLetter;
    private Image correctKey, almostCorrectKey, wrongKey, unknownKey;
    private Image correctMessage, invalidMessage, finishMessage;
    private int LETTER_WIDTH, LETTER_HEIGHT;
    private Feedback feedback;
    
    private Image[] letterImages;
    private GameState gameState;

    
    public WordleGUI() throws Exception {
        KEY_WIDTH = 46;
        KEY_HEIGHT = 69;
        keyboard = new Keyboard();
        letters = keyboard.getLetters();
        
        setKeys();
        
        LETTER_HEIGHT = 75;
        LETTER_WIDTH = 75;
        gameState = new GameState();
        feedback = gameState.getFeedback();
        
        this.setFocusable(true);
        this.addKeyListener(this);
        
        setKeyImages();
        setLetterImages();
        
        setImages();
        setLetters();
    }
    
    private void setKeys() throws Exception {
        keyImages = new Image[26];
        for(int i = 0; i < 26; i++) {
            String path = "keys/" + keyboard.getLetterFromNumber(i) + ".png";
            keyImages[i] = ImageIO.read(new File(path)).getScaledInstance(KEY_WIDTH, KEY_HEIGHT, ABORT);
        }
    }
    
    private void setLetters() throws Exception {
        letterImages = new Image[26];
        for(int i = 0; i < letterImages.length; i++) {
            String path = "letters/" + keyboard.getLetterFromNumber(i)+ ".png";
            letterImages[i] = ImageIO.read(new File(path)).getScaledInstance(LETTER_WIDTH, LETTER_HEIGHT, ABORT);
        }
    }
    
    private int getX(int col, int rowLength) {
        int screenx = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        int W = 4;
        int n = (screenx/2)-((KEY_WIDTH+W)*(rowLength/2-col));
        if (rowLength%2==1) {
            return n-KEY_WIDTH/2;
        } else {
            return n+(W/2);
        }
    }
    
    private int getY(int row) {
        int start = 640;
        int H = 20;
        return start+(KEY_HEIGHT+H)*row;
    }
    
    private int getXGuess(int col) {
        int screenx = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int W = 15;
        int n = ((screenx/2)-((LETTER_WIDTH+W)*(5/2-col)));
        return n-(LETTER_WIDTH/2);
    }
        
    private int getYGuess(int row) {
        int start = 50;
        int H = 15;
        return start+(LETTER_HEIGHT+H)*row;
    }
    
    private int getXMessage() {
        int screenx = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int W = 300;
        return (screenx/2) - (W/2);
    }
        
    private int getYMessage() {
        return 230;
    }
    
    private void drawKey(Graphics g, int row, int col, int rowLength) {
        char letter = letters[row][col];
        int index = keyboard.getNumberInAlphabet(letter);
        int x = getX(col, rowLength);
        int y = getY(row);
        
        Response response = feedback.getLetterResponse(index);
        Image img = getKeyImgFromResponse(response);
        g.drawImage(img, x, y, this);
        
        Image image = keyImages[index];
        g.drawImage(image, x, y, this);
    }
    
    public void paint(Graphics g) {
        drawKeys(g);
        drawBoard(g);
        
        int x = getXMessage();
        int y = getYMessage();
        
        if (gameState.isFinished()) {
            if (gameState.isCorrectGuess()) {
                g.drawImage(correctMessage, x, y, this);
            } else {
                g.drawImage(finishMessage, x, y, this);
            }
        } else if (gameState.isInvalid()) {
            g.drawImage(invalidMessage, x, y, this);
        }
    }
    
    private void drawKeys(Graphics g) {
        for (int row = 0; row < letters.length; row++) {
            int rowLength = letters[row].length;
            for(int col = 0; col < rowLength; col++) {
                drawKey(g, row, col, rowLength);
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        char keychar = e.getKeyChar();
        if (keyboard.isLetter(keychar)) {
            boolean isModified = gameState.writeLetter(keychar);
            if (isModified) {
                repaint();
            }        
        }
        
        if (gameState.isFinished()) {
            repaint();
        }
        
        int keycode = e.getKeyCode();
        gameState.modifyGuess(keycode);
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    private void setLetterImages() throws Exception {
        correctLetter = ImageIO.read(new File("letters/Correct.png")).getScaledInstance(LETTER_WIDTH, LETTER_HEIGHT, ABORT);
        almostCorrectLetter = ImageIO.read(new File("letters/Almost_correct.png")).getScaledInstance(LETTER_WIDTH, LETTER_HEIGHT, ABORT);
        wrongLetter = ImageIO.read(new File("letters/Wrong.png")).getScaledInstance(LETTER_WIDTH, LETTER_HEIGHT, ABORT);
        unknownLetter = ImageIO.read(new File("letters/Unknown.png")).getScaledInstance(LETTER_WIDTH, LETTER_HEIGHT, ABORT);
    }
    
    private void setKeyImages() throws Exception {
        correctKey = ImageIO.read(new File("keys/Correct.png")).getScaledInstance(KEY_WIDTH, KEY_HEIGHT, ABORT);
        almostCorrectKey = ImageIO.read(new File("keys/Almost_correct.png")).getScaledInstance(KEY_WIDTH, KEY_HEIGHT, ABORT);
        wrongKey = ImageIO.read(new File("keys/Wrong.png")).getScaledInstance(KEY_WIDTH, KEY_HEIGHT, ABORT);
        unknownKey = ImageIO.read(new File("keys/Unknown.png")).getScaledInstance(KEY_WIDTH, KEY_HEIGHT, ABORT);
    }
    
    private void setImages() throws Exception {
        correctMessage = ImageIO.read(new File("messages/Correct.png"));
        finishMessage = ImageIO.read(new File("messages/Finish.png"));
        invalidMessage = ImageIO.read(new File("messages/Invalid.png"));
    }
    
    private Image getKeyImgFromResponse(Response response) {
        if (response == Response.CORRECT) {
            return correctKey;
        } else if (response == Response.ALMOST_CORRECT) {
            return almostCorrectKey;
        } else if (response == Response.WRONG) {
            return wrongKey;
        } else if (response == Response.UNKNOWN) {
            return unknownKey;
        } else {
            return null;
        }
    }
    
    private Image getLetterImgFromResponse(Response response) {
        if (response == Response.CORRECT) {
            return correctLetter;
        } else if (response == Response.ALMOST_CORRECT) {
            return almostCorrectLetter;
        } else if (response == Response.WRONG) {
            return wrongLetter;
        } else if (response == Response.UNKNOWN) {
            return unknownLetter;
        } else {
            return null;
        }
    }
    
    private void drawGuessLetter(Graphics g, char[][] board, int row, int col) {
        char letter = board[row][col];
        int x = getXGuess(col);
        int y = getYGuess(row);
        if (keyboard.isLetter(letter)) {
            int index = keyboard.getNumberInAlphabet(letter);
            Response response = feedback.getGuessResponse(row, col);
            Image img = getLetterImgFromResponse(response);
            g.drawImage(img, x, y, this);
            
            Image image = letterImages[index];
            g.drawImage(image, x, y, this);
        } else {
            Image img = getLetterImgFromResponse(Response.UNKNOWN);
            g.drawImage(img, x, y, this);
        }
    }
    
    private void drawBoard(Graphics g) {
        char[][] board = gameState.getBoard();
        
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                drawGuessLetter(g, board, row, col);
            }
        }
    }
}
