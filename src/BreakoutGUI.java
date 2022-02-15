import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;
 // https://coolors.co/e2fcef-9b287b-5c164e-402039-170f11
/**
 *
 * @author Gabriel Lapolla
 */
public class BreakoutGUI extends JFrame {

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private JButton topButton;
    private JLabel gameOver;
    private JLabel youWin;

    public BreakoutGUI(Ball ball, Paddle paddle) {
        super("Breakout");
        SCREEN_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
                .getWidth();
        SCREEN_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
                .getHeight();
    }

    public int getScreenWidth() {
        return this.SCREEN_WIDTH;
    }

    public int getScreenHeight() {
        return this.SCREEN_HEIGHT;
    }
    
    public void initialize(Ball ball, Paddle paddle) {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setIconImage(new ImageIcon("src/resources/breakoutIcon.png").getImage());
        getContentPane().setBackground(new Color(23, 15, 17)); // Set window color
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add button to quit game at top
        topButton = new JButton("<html><h1>BREAKOUT - CLICK TO EXIT</h1></html>");
        topButton.setSelected(false);
        topButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        topButton.setBackground(new Color(64, 32, 57));
        topButton.setForeground(new Color(226, 252, 239));
        topButton.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 15);
        add(topButton);

        // Create label to state game over
        gameOver = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOver.setBounds((SCREEN_WIDTH / 2) - (SCREEN_WIDTH / 6), (SCREEN_HEIGHT / 2) - (SCREEN_HEIGHT / 10),
                SCREEN_WIDTH / 3, SCREEN_HEIGHT / 5);
        gameOver.setForeground(new Color(226, 252, 239));
        gameOver.setFont(new Font("Serif", Font.BOLD, SCREEN_WIDTH / 20));
        gameOver.setVisible(false);
        add(gameOver);

        // Create label to state game won
        youWin = new JLabel("YOU WIN", SwingConstants.CENTER);
        youWin.setBounds((SCREEN_WIDTH / 2) - (SCREEN_WIDTH / 6), (SCREEN_HEIGHT / 2) - (SCREEN_HEIGHT / 10),
                SCREEN_WIDTH / 3, SCREEN_HEIGHT / 5);
        youWin.setForeground(new Color(226, 252, 239));
        youWin.setFont(new Font("Serif", Font.BOLD, SCREEN_WIDTH / 20));
        youWin.setVisible(false);
        add(youWin);

        add(paddle);
        add(ball);
        setVisible(true);
    }

    public void createEvents(Timer timer) {
        // Close program when clicking button
        topButton.addActionListener((ActionEvent e) -> {
            timer.stop();
            dispose();
        });

        // Change button color on mouse hover
        topButton.addMouseListener(new MouseInputAdapter() {
            public void mouseEntered(MouseEvent e) {
                topButton.setBackground(new Color(155, 40, 123));
            }

            public void mouseExited(MouseEvent e) {
                topButton.setBackground(new Color(64, 32, 57));
            }
        });

        // Add listener to stop timer when window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
                dispose();
            }
        });
    }

    public void gameOver() {
        gameOver.setVisible(true);
    }

    public void youWin() {
        youWin.setVisible(true);
    }
}
