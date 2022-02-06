import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Gabriel Lapolla
 */
public class BreakoutGUI extends JFrame {

    private int width;
    private int height;
    private final int DELAY = 5;
    private Paddle paddle;
    private Ball ball;
    private Level level;
    private JButton topButton;
    private JLabel gameOver;
    private Breakout game;

    public BreakoutGUI() {
        super("Breakout");
        initialize();
        game = new Breakout(ball, level, paddle, width, height, this);
        createEvents();
    }

    /**
     * This method creates a fullscreen window and adds three buttons at the top
     *
     * @author Gabriel Lapolla
     */
    public void initialize() {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        //device.setFullScreenWindow(this); // Set to full screen

        // Monitor width and height
        width = device.getDisplayMode().getWidth();
        height = device.getDisplayMode().getHeight();

        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));

        // Set icon
        ImageIcon icon = new ImageIcon("breakoutIcon.png");
        setIconImage(icon.getImage());

        getContentPane().setBackground(new Color(65, 65, 65)); // Set window color
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window

        setLayout(null);

        // Set to close window on exit
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add button to quit game at top
        topButton = new JButton("<html><h1>BREAKOUT - CLICK TO EXIT</h1></html>");
        topButton.setSelected(false);
        topButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        topButton.setBounds(0, 0, width, height / 15);
        add(topButton);

        // Create label to state game over
        gameOver = new JLabel("GAME OVER");
        gameOver.setBounds((width / 2) - (width / 6), (height / 2) - (height / 10), width / 3, height / 5);
        gameOver.setForeground(Color.RED);
        gameOver.setFont(new Font("Serif", Font.BOLD, width / 20));
        gameOver.setVisible(false);
        add(gameOver);

        // Create and add paddle to window
        paddle = new Paddle();
        paddle.initPaddle(width, height);
        add(paddle);

        // Create ball and add to window
        ball = new Ball(width, height, width / 100);
        ball.initBall();
        add(ball);

        // Create level and bricks
        level = new Level(5, 1);

        setVisible(true);
    }

    private void createEvents() {
        // Timer to loop game logic every DELAY ms
        Timer timer = new Timer();
        TimerTask move = new TimerTask() {
            @Override
            public void run() {
                if (game.loopGame() == 0) {
                    timer.cancel();
                    gameOver.setVisible(true);
                }
            }
        };
        
        topButton.addActionListener((ActionEvent e) -> {
            System.out.println("Closing game...");
            timer.cancel();
            dispose();
        });

        timer.scheduleAtFixedRate(move, 0, DELAY);
    }

    public static void main(String[] args) {
        new BreakoutGUI();
    }

}
