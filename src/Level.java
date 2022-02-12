
/**
 * Contains variables and methods for creating and managing the game level
 * including the bricks in the game which are stored in a linked list
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

    public Level(int numBricksPerRow, int numBricksPerColumn) {
        this.numBricksPerRow = numBricksPerRow;
        this.numBricksPerColumn = numBricksPerColumn;
    }

    public void setNumBricksPerRow(int numBricksPerRow) {
        this.numBricksPerRow = numBricksPerRow;
    }

    public void setNumBricksPerColumn(int numRows) {
        this.numBricksPerColumn = numRows;
    }

    public void setNumBricks(int numBricks) {
        this.numBricks = numBricks;
    }

    public int getNumBricksPerRow() {
        return numBricksPerRow;
    }

    public int getNumBricksPerColumn() {
        return numBricksPerColumn;
    }

    public int getNumBricks() {
        return numBricks;
    }

    public Brick getFirstBrick() {
        return firstBrick;
    }

    /**
     * Removes a brick from the list of bricks and the window
     * 
     * @param destroyed The brick that has been destroyed
     */
    public void destroyBrick(Brick destroyed, JFrame window) throws Exception {
        window.remove(destroyed); // Remove from JFrame
        // Remove destroyed brick from linked list of bricks
        Brick b = getFirstBrick();
        if (b == destroyed) { // Destroyed is first brick in list
            firstBrick = b.getNextBrick();
        } else {
            // Loop through list until destroyed brick is found
            while (b.getNextBrick() != destroyed && b.getNextBrick() != null) {
                b = b.getNextBrick();
            }
            if (b.getNextBrick() == null) {
                throw new Exception("Brick not found in game");
            }
            if (destroyed.getNextBrick() == null) { // Destroyed brick is last brick in list
                b.setNextBrick(null);
            } else {
                b.setNextBrick(destroyed.getNextBrick()); // Remove destroyed brick from list
            }
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
    public void addBricks(int height, int width,
            JFrame window) {
        this.numBricks = numBricksPerColumn * numBricksPerRow;

        // Add bricks on top of playspace
        int brickWidth = (width - 200) / numBricksPerRow;
        int brickHeight = height / 40;
        int brickX = width / 25;
        int brickY = (height / 15) + (height / 20);
        int brickXSpacer = 0;
        int brickYSpacer = 0;

        Brick current;
        Brick prev = new Brick(brickX, brickY, null);
        firstBrick = prev;
        for (int i = 1; i <= numBricksPerRow * numBricksPerColumn; i++) {
            // Create brick object
            current = new Brick(brickX, brickY, null);
            prev.setNextBrick(current);

            // Set brick location
            current.setBounds(brickX, brickY, brickWidth, brickHeight);
            current.setEnabled(false); // Turn off button
            window.add(current);
            brickX += brickXSpacer + brickWidth;

            // Move to new row
            if (i % numBricksPerRow == 0) {
                brickY += brickYSpacer + brickHeight;
                brickX = width / 25;
            }
            prev = current;
        }
    }
}
