
/** 
 * Contains information on the bricks in the game
 * Bricks are node of a doubly linekd list contained in level object
 * 
 * @author Gabriel Lapolla
 */
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Brick extends JComponent {
    private int brickX;
    private int brickY;
    private Brick nextBrick;
    private Brick prevBrick;

    public Brick(int brickX, int brickY, Brick prevBrick, Brick nextBrick) {
        this.brickX = brickX;
        this.brickY = brickY;
        this.prevBrick = prevBrick;
        this.nextBrick = nextBrick;
    }

    public void setBrickX(int brickX) {
        this.brickX = brickX;
    }

    public void setBrickY(int brickY) {
        this.brickY = brickY;
    }

    public void setNextBrick(Brick nextBrick) {
        this.nextBrick = nextBrick;
    }

    public void setPrevBrick(Brick prevBrick) {
        this.prevBrick = prevBrick;
    }

    public int getBrickX() {
        return this.brickX;
    }

    public int getBrickY() {
        return this.brickY;
    }

    public Brick getNextBrick() {
        return this.nextBrick;
    }

    public Brick getPrevBrick() {
        return this.prevBrick;
    }

    protected void paintComponent(Graphics g) {
        g.setColor(new Color(92, 22, 78));
        g.fillRect(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(new Color(155, 40, 123));
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
    }

    public String toString() {
        return String.format("BrickX: %d, BrickY: %d", brickX, brickY);
    }
}
