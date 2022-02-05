/** *************************************************************************
 * Revision History 
 ****************************************************************************
 * 12/18/20 Written by Gabriel Lapolla
 ****************************************************************************/

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author Gabriel Lapolla
 */
public class Ball extends JComponent {
    
    
    private final int windowY;
    private final int windowX;
    private int xVelocity;
    private int yVelocity;
    private int radius;

    public Ball(int windowY, int windowX, int radius) {
        this.windowY = windowY;
        this.windowX = windowX;
        this.radius = radius;
    }

    public void setxVelocity(int xVelocity){
        this.xVelocity = xVelocity;
    }
    
    public void setyVelocity(int yVelocity){
        this.yVelocity = yVelocity;
    }
    
    public int getxVelocity() {
        return xVelocity;
    }
    
    public int getyVelocity() {
        return yVelocity;
    }
    
    public int getRadius(){
        return this.radius;
    }
    
    /**
     * Sets default values and velocity based off a multiplier
     */
    public void initBall(){
        setBackground(Color.GRAY);
        setFocusable(false);
        setBounds(windowY/2, windowX/2, radius, radius);
        setRandomVelocity(1);
    }
    
    /**
     * Sets random velocity based on multiplier
     * @param multiplier A multiplier to change velocity
     */
    public void setRandomVelocity(int multiplier){
        Random random = new Random();
        // Get a random negative to modify direction
        int negative = 0;
        while (negative == 0) { // Get 1 or -1
            negative = random.nextInt(3) - 1;
        }
        // Random between 1 -> 3
        xVelocity = (random.nextInt(3) + 1) * multiplier * negative; 
        yVelocity = (random.nextInt(3) + 1) * multiplier * negative;
    }
    
    // This method paints the ball
    protected void paintComponent(Graphics g) {
        g.setColor(Color.gray);
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
    g.setColor(Color.darkGray);
    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
  }
}
