import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

/**
 *
 * @author Gabriel Lapolla
 */
public class Ball extends JComponent {

    private double xVelocity;
    private double yVelocity;
    private final double MIN_VELOCITY = 0.2;
    private final double MAX_VELOCITY = 2;
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
    public void initBall(int windowWidth, int windowHeight) {
        setBackground(Color.GRAY);
        setFocusable(false);
        this.xCoord = windowWidth / 2;
        this.yCoord = windowHeight / 2;
        setBounds((int) xCoord, (int) yCoord, windowWidth / 100, windowWidth / 100);
        setRandomVelocity();
    }

    /**
     * Sets random xVelocity based from 0 to 1
     * Sets yVelocity based on xVelocity with a combined velocity of 2
     */
    public void setRandomVelocity() {
        Random random = new Random();
        xVelocity = Math.random() * (random.nextBoolean() ? 1 : -1);
        // Negative yVelocity so ball goes up initally
        yVelocity = -(MAX_VELOCITY - Math.abs(xVelocity));
    }

    /**
     * Increases xVelocity but also changes yVelocity to keep a combined total
     * velocity of MAX_VELOCITY
     * 
     * @param increaseAmount Amount to increase xVelocity by
     */
    public void changeXVelocity(double increaseAmount) {
        // if xVelocity would exceed max velocity,
        // set it to max velocity - min velocity and set yVelocity to min
        if (Math.abs(xVelocity + increaseAmount) >= MAX_VELOCITY) {
            xVelocity = (xVelocity > 0) ? MAX_VELOCITY - MIN_VELOCITY : -(MAX_VELOCITY - MIN_VELOCITY);
            yVelocity = (yVelocity > 0) ? MIN_VELOCITY : -MIN_VELOCITY;
        } else {
            xVelocity += increaseAmount;
            yVelocity = (yVelocity > 0) ? MAX_VELOCITY - Math.abs(xVelocity) : -(MAX_VELOCITY - Math.abs(xVelocity));
        }
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
