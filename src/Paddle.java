
/**
 * Holds information on the game paddle
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author Gabriel Lapolla
 */
public class Paddle extends JComponent {

    public Paddle() {

    }

    /**
     * Mouse paddle to be in line with the x coordiante of the cursor
     * 
     * @param mouseX X coordinate of mouse cursor
     */
    public void movePaddleToMouse(int mouseX) {
        setBounds(mouseX - getWidth() / 2, getY(),
                getWidth(), getHeight());
        paintImmediately(getBounds());
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(92, 22, 78));
        g2d.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, getSize().height, getSize().height);
        super.paintComponent(g2d);
    }

    public void initPaddle(int windowWidth, int windowHeight) {
        setSize(windowWidth / 10, windowHeight / 75);
        setBounds((windowWidth / 2) - (getSize().width / 2), windowHeight / 15 * 13,
                getSize().width, getSize().height);
    }
}
