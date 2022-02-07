import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Robot;
import javax.swing.JFrame;

/**
 *
 * @author Gabriel Lapolla
 */
public class Breakout {

    private Paddle paddle;
    private Ball ball;
    private Level level;
    private final int WIDTH;
    private final int HEIGHT;
    private JFrame window;
    private int lives = 3;
    private Robot mouseControl;

    public Breakout(Ball ball, Level level, Paddle paddle,
            int width, int height, JFrame window) {
        this.ball = ball;
        this.level = level;
        this.paddle = paddle;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.window = window;
        try {
            mouseControl = new Robot();
        } catch (AWTException e) {
            System.err.println("Error initializing game");
        }
        level.addBricks(10, 10, HEIGHT, WIDTH, window);
        window.repaint();
    }

    /**
     * This method checks if the ball has collided with anything or if it has
     * reached the bottom and changes ball velocity and location
     *
     * @return Returns 0 if still in play, returns -1 if ball is out
     */
    public int checkBall() {
        // Check if ball has hit a side, top, or bottom
        if (ball.getXCoord() <= 0 || ball.getXCoord() >= WIDTH - ball.getWidth()) { // Side
            ball.setXVelocity(-ball.getXVelocity());
            System.out.println("Ball hit side.");
        }
        if (ball.getYCoord() <= HEIGHT / 15) { // Top
            ball.setYVelocity(-ball.getYVelocity());
            System.out.println("Ball hit top.");
        } else if (ball.getYCoord() >= HEIGHT) { // Bottom
            System.out.println("Ball hit bottom.");
            return -1;
        }

        // TODO: Change ball angle when hitting different locations on paddle
        // Check if ball hits paddle when ball is near paddle
        if (ball.getYCoord() >= paddle.getY() - paddle.getHeight()
                && ball.getYCoord() < paddle.getY()) {
            if (ball.getXCoord() > paddle.getX()
                    && ball.getXCoord() < paddle.getX() + paddle.getWidth()) {
                // Get location on paddle where ball hit (0-100 from left)
                int hitLoc = ((int) ball.getXCoord() - paddle.getX()) * 100 / paddle.getWidth();
                System.out.printf("Bounce off paddle at %d\n", hitLoc);
                ball.setYVelocity(-ball.getYVelocity());
                /*if (hitLoc < 50) { // Hits left side of paddle
                    ball.setyVelocity(-ball.getYVelocity() * (hitLoc / 100));
                } else{ // Hits right side of paddle
                    ball.setyVelocity(-ball.getYVelocity()* (hitLoc / 100));
                }*/
                System.out.println("Ball yVelocity: " + ball.getYVelocity());
            }
        }

        ball.moveBall();
        return 0;
    }

    /**
     * This method checks if the paddle has reached the edge of the play space
     * and moves the paddle to the mouse's x location
     *
     * @param mouseX Mouse's x coordinate
     */
    public void movePaddle(int mouseX) {
        // Don't move paddle outside window
        if (mouseX >= paddle.getWidth() / 2
                && mouseX <= WIDTH - paddle.getWidth() / 2) {
            // Move paddle to mouse's x location
            paddle.setBounds(mouseX - paddle.getWidth() / 2, paddle.getY(),
                    paddle.getWidth(), paddle.getHeight());
        }
    }

    /**
     * Checks if the ball has collided with any of the bricks
     *
     * @return 0 if no bricks are hit or -1 if one is hit
     *
     */
    public int checkBricks() {
        // Get ball location
        int ballX = ball.getX();
        int ballY = ball.getY();
        // TODO: Optimize checking if ball has hit a brick
        for (Brick b : level.getBrickList()) {
            int brickX = b.getBrickX();
            int brickY = b.getBrickY();
            if (ballX >= brickX && ballX <= brickX + b.getWidth()
                    && ballY >= brickY && ballY <= brickY + b.getHeight()) {

                // FIXME: weird collisions with bricks
                // Check if ball hit side or top/bottom
                if (ballX < brickX + 5 || ballX > brickX + b.getWidth() + 5) {
                    ball.setXVelocity(-ball.getXVelocity());
                    System.out.println("Hit side of a brick.");
                } else {
                    ball.setYVelocity(-ball.getYVelocity());
                    System.out.println("Hit top/bottom of a brick.");
                }

                // TODO: Change way bricks are removed from window and brickList
                window.remove(b);
                b.setBrickX(0);
                b.setBrickY(0);
                window.repaint();
                return -1;
            }
        }
        return 0;
    }

    /**
     * @return Returns 0 if game is over, 1 if still in game, and 2 if game is won
     */
    public int loopGame() {
        // Get mouse x location
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        // Move back cursor if it goes too far
        if (mouseX < 0) {
            mouseControl.mouseMove(0, MouseInfo.getPointerInfo().getLocation().y);
        } else if (mouseX > WIDTH) {
            mouseControl.mouseMove(WIDTH,
                    MouseInfo.getPointerInfo().getLocation().y);
        }

        movePaddle(mouseX);

        // Check if ball is out of bounds
        if (checkBall() == -1) {
            if (lives > 1) {
                System.out.println("Lives: " + lives);
                // TODO: add lives counter on screen
                lives--;
                ball.initBall(WIDTH, HEIGHT, ball.getSize().width);
                
            } else {
                window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                System.out.println("Out of lives: Game over!");
                return 0;
            }
        }
        // Check if all bricks are destroyed
        if (checkBricks() == -1) {
            level.numBricks--;
            if (level.numBricks == 0) {
                return 2;
            }
        }

        return 1;
    }
}
