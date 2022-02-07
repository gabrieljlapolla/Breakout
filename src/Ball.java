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
     * Sets random velocity based from 0 to 1
     */
    public void setRandomVelocity() {
        xVelocity = Math.random();
        yVelocity = Math.random();
    }

    /**
     * Moves location of ball based on current velocties and coordinates
     */
    public void moveBall() {
        xCoord += xVelocity;
        yCoord += yVelocity;
        setBounds((int) xCoord, (int) yCoord, getSize().width, getSize().width);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(new Color(155, 40, 123));
        //g.fillOval((int) xCoord, (int) yCoord, getSize().width - 1, getSize().height - 1);
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }
}
