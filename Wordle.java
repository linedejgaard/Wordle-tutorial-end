import java.awt.Color;
import javax.swing.JFrame;

public class Wordle
{
    public static void main(String[] args) {
        
        try {
            JFrame frame = new JFrame();
            frame.setTitle("Wordle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.black);
            
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            
            WordleGUI gui = new WordleGUI();
            frame.getContentPane().add(gui);
        } catch (Exception e) {
            System.out.println("Could not make the WordleGUI frame: " + e);
        }
        
    }
}
