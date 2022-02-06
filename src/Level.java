
/**
 * Contains variables and methods for creating and managing the game level
 * including the bricks in the game
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Gabriel Lapolla
 */
public class Level {

    private int numBricksPerRow;
    private int numBricksPerColumn;
    public int numBricks;
    private Brick[] brickList;

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

    public Brick[] getBrickList() {
        return brickList;
    }

    /**
     * This method adds the bricks to the window
     *
     * @author Gabriel Lapolla
     * @param numRows    Number of brick rows
     * @param numColumns Number of brick columns
     * @param height     The window height
     * @param width      The window width
     * @param window     The game window
     */
    public void addBricks(int numRows, int numColumns, int height, int width,
            JFrame window) {
        this.numBricks = numBricksPerColumn * numBricksPerRow;
        Border lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        // Add bricks on top of playspace
        int brickWidth = (width - 200) / numBricksPerRow;
        int brickHeight = height / 40;
        int brickX = width / 25;
        int brickY = (height / 15) + (height / 20);
        int brickXSpacer = 0;
        int brickYSpacer = 0;

        // Modify each brickX and brickY to alter location in loops
        brickList = new Brick[numBricksPerRow * numBricksPerColumn];

        int counter = 0;
        for (int i = 0; i < brickList.length; i++) {
            // Create brick object
            brickList[i] = new Brick(brickX, brickY, lineBorder);
            // Set brick location
            brickList[i].setBounds(brickX, brickY, brickWidth, brickHeight);
            brickList[i].setEnabled(false); // Turn off button
            window.add(brickList[i]);
            counter++;
            brickX += brickXSpacer + brickWidth;

            // Move to new row
            if (counter % numBricksPerRow == 0) {
                brickY += brickYSpacer + brickHeight;
                brickX = width / 25;
            }
        }
    }
}
