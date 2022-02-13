
/**
 * Contains variables and methods for creating and managing the game level
 * including the bricks in the game which are stored in a doubly linked list
 */
import javax.swing.JFrame;

/**
 *
 * @author Gabriel Lapolla
 */
public class Level {

    private int numBricksPerRow;
    private int numBricksPerColumn;
    public int numBricks;
    private Brick firstBrick;
    private Brick lastBrick;

    public Level(int numBricksPerRow, int numBricksPerColumn) {
        this.numBricksPerRow = numBricksPerRow;
        this.numBricksPerColumn = numBricksPerColumn;
        this.numBricks = numBricksPerColumn * numBricksPerRow;
    }

    public void setNumBricks(int numBricks) {
        this.numBricks = numBricks;
    }

    public int getNumBricks() {
        return numBricks;
    }

    public Brick getFirstBrick() {
        return firstBrick;
    }

    public Brick getLastBrick() {
        return this.lastBrick;
    }

    /**
     * Removes a brick from the list of bricks and the window
     * 
     * @param destroyed The brick that has been destroyed
     */
    public void destroyBrick(Brick destroyed, JFrame window) throws Exception {
        window.remove(destroyed); // Remove from JFrame
        // Brick is only brick in list
        if (destroyed.getNextBrick() == null && destroyed.getPrevBrick() == null) {
            firstBrick = null;
            lastBrick = null;
        } else if (destroyed.getPrevBrick() == null) { // Destroyed brick is first brick
            firstBrick = destroyed.getNextBrick();
            destroyed.getNextBrick().setPrevBrick(null);
        } else if (destroyed.getNextBrick() == null) { // Destroyed brick is last brick
            destroyed.getPrevBrick().setNextBrick(null);
        } else { // Remove references to destroyed brick from prev and next bricks
            destroyed.getPrevBrick().setNextBrick(destroyed.getNextBrick());
            destroyed.getNextBrick().setPrevBrick(destroyed.getPrevBrick());
        }
        window.repaint();
    }

    /**
     * This method adds the bricks to the window
     *
     * @param height The window height
     * @param width  The window width
     * @param window The game window
     */
    public void addBricks(int height, int width, JFrame window) {
        // Add bricks on top of playspace
        int brickWidth = (width - 200) / numBricksPerRow;
        int brickHeight = height / 40;
        // Starting coordinates
        int brickX = width / 25;
        int brickY = (height / 15) + (height / 20);

        Brick current;
        Brick prev = new Brick(brickX, brickY, null, null);
        firstBrick = prev;
        for (int i = 1; i <= numBricks; i++) {
            // Create brick object
            current = new Brick(brickX, brickY, prev, null);
            prev.setNextBrick(current);

            // Set brick location
            current.setBounds(brickX, brickY, brickWidth, brickHeight);
            current.setEnabled(false); // Turn off button
            window.add(current);
            brickX += brickWidth;

            // Move to new row
            if (i % numBricksPerRow == 0) {
                brickY += brickHeight;
                brickX = width / 25;
            }
            prev = current;
            lastBrick = current;
        }
    }
}
