import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Gabriel Lapolla
 */
public class Ball extends JComponent {

    private double xVelocity;
    private double yVelocity;
    private final double MIN_VELOCITY = 0.1;
    private double xCoord;
    private double yCoord;

    public Ball() {
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void setXCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public void setYCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public double getXCoord() {
        return xCoord;
    }

    public double getYCoord() {
        return yCoord;
    }

    /**
     * Sets default values and velocity based off a multiplier
     */
    public void initBall(int windowWidth, int windowHeight, int size) {
        setBackground(Color.GRAY);
        setFocusable(false);
        xCoord = windowWidth / 2;
        yCoord = windowHeight / 2;
        setBounds((int) xCoord, (int) yCoord, size, size);
        setRandomVelocity();
    }

    /**
     * Sets random xVelocity based from 0 to 1
     * Sets yVelocity based on xVelocity with an added velocity of 2
     */
    public void setRandomVelocity() {
        setXVelocity(Math.random());
        setYVelocity(2 - xVelocity);
    }

    /**
     * Moves location of ball based on current velocties and coordinates
     */
    public void moveBall() {
        // Makesure ball doesn't have too low of a velocity
        if (Math.abs(xVelocity) < MIN_VELOCITY) {
            if (xVelocity < 0) {
                xVelocity = -MIN_VELOCITY;
            } else {
                xVelocity = MIN_VELOCITY;
            }
        }
        if (Math.abs(yVelocity) < MIN_VELOCITY) {
            if (yVelocity < 0) {
                yVelocity = -MIN_VELOCITY;
            } else {
                yVelocity = MIN_VELOCITY;
            }
        }
        xCoord += xVelocity;
        yCoord += yVelocity;
        setBounds((int) xCoord, (int) yCoord, getSize().width, getSize().width);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(new Color(155, 40, 123));
        // g.fillOval((int) xCoord, (int) yCoord, getSize().width - 1, getSize().height
        // - 1);
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    public String toString() {
        return String.format("xCoord: %f yCoord: %f xVelocity: %f yVelocity: %f", xCoord, yCoord, xVelocity, yVelocity);
    }
}
