public class Keyboard {
    private char[] row1 = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'};
    private char[] row2 = {'A', 'S', 'D', 'F', 'G', 'H', 'J','K', 'L'};
    private char[] row3 = {'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
    
    private char[][] letters = {row1, row2, row3};
    
    public Keyboard() {
        //tom
    }
    
    public char[][] getLetters() {
        return letters;
    }
    
    public char getLetterFromNumber(int number) {
        if (number == 0) {
            return 'A';
        } else if (number == 1) {
            return 'B';
        } else if (number == 2) {
            return 'C';
        } else if (number == 3) {
            return 'D';
        } else if (number == 4) {
            return 'E';
        } else if (number == 5) {
            return 'F';
        } else if (number == 6) {
            return 'G';
        } else if (number == 7) {
            return 'H';
        } else if (number == 8) {
            return 'I';
        } else if (number == 9) {
            return 'J';
        } else if (number == 10) {
            return 'K';
        } else if (number == 11) {
            return 'L';
        } else if (number == 12) {
            return 'M';
        } else if (number == 13) {
            return 'N';
        } else if (number == 14) {
            return 'O';
        } else if (number == 15) {
            return 'P';
        } else if (number == 16) {
            return 'Q';
        } else if (number == 17) {
            return 'R';
        } else if (number == 18) {
            return 'S';
        } else if (number == 19) {
            return 'T';
        } else if (number == 20) {
            return 'U';
        } else if (number == 21) {
            return 'V';
        } else if (number == 22) {
            return 'W';
        } else if (number == 23) {
            return 'X';
        } else if (number == 24) {
            return 'Y';
        } else if (number == 25) {
            return 'Z';
        }
        
        return '-'; 
        
    }
    
    
    public int getNumberInAlphabet(char letter) {
        if (letter == 'A') {
            return 0;
        } else if (letter == 'B') {
            return 1;
        } else if (letter == 'C') {
            return 2;
        } else if (letter == 'D') {
            return 3;
        } else if (letter == 'E') {
            return 4;
        } else if (letter == 'F') {
            return 5;
        } else if (letter == 'G') {
            return 6;
        } else if (letter == 'H') {
            return 7;
        } else if (letter == 'I') {
            return 8;
        } else if (letter == 'J') {
            return 9;
        } else if (letter == 'K') {
            return 10;
        } else if (letter == 'L') {
            return 11;
        } else if (letter == 'M') {
            return 12;
        } else if (letter == 'N') {
            return 13;
        } else if (letter == 'O') {
            return 14;
        } else if (letter == 'P') {
            return 15;
        } else if (letter == 'Q') {
            return 16;
        } else if (letter == 'R') {
            return 17;
        } else if (letter == 'S') {
            return 18;
        } else if (letter == 'T') {
            return 19;
        } else if (letter == 'U') {
            return 20;
        } else if (letter == 'V') {
            return 21;
        } else if (letter == 'W') {
            return 22;
        }else if (letter == 'X') {
            return 23;
        } else if (letter == 'Y') {
            return 24;
        } else if (letter == 'Z') {
            return 25;
        }
        return -1;
    }
    
    
    public boolean isLetter(char c) {
        char uppercase = Character.toUpperCase(c);
        if (getNumberInAlphabet(uppercase) != -1) {
            return true;
        }
        return false;
    }
        
}
