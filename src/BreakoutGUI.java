import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Gabriel Lapolla
 */
public class BreakoutGUI extends JFrame {

    private int width;
    private int height;

    private Paddle paddle;
    private Ball ball;
    private Level level;
    private JButton topButton;

    public BreakoutGUI() {
        super("Breakout");
        initialize();
        createEvents();
        Breakout game = new Breakout(ball, level, paddle, width, height, this);
        game.run();
    }

    /**
     * This method creates a fullscreen window and adds three buttons at the top
     *
     * @author Gabriel Lapolla
     */
    public void initialize() {
        GraphicsEnvironment graphics
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        device.setFullScreenWindow(this); // Set to full screen
        
        // Monitor width and height
        width = device.getDisplayMode().getWidth();
        height = device.getDisplayMode().getHeight();

        //Hide cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        // Set icon
        ImageIcon icon = new ImageIcon("breakoutIcon.png");
        setIconImage(icon.getImage());

        //                                          setSize(width, height / 15); // Set window size
        getContentPane().setBackground(new Color(65, 65, 65)); // Set window color
        setExtendedState(this.MAXIMIZED_BOTH); // Maximize window

        setLayout(null);

        // Set to close window on exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add button to quit game at top
        topButton = new JButton("BREAKOUT - CLICK TO EXIT");
        topButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        topButton.setBounds(0, 0, width, height / 15);
        add(topButton);

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
                topButton.addActionListener((ActionEvent e) -> {
                    System.out.println("Closing game...");
                    System.exit(0);
                });
    }

    public static void main(String[] args) {
        BreakoutGUI gui = new BreakoutGUI();
    }

}
