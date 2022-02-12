import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
/**
 *
 * @author Gabriel Lapolla
 */
public class Breakout {

    private BreakoutGUI gui;
    private Paddle paddle;
    private Ball ball;
    private Level level;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private int DELAY = 2;
    public int lives = 3;
    private JFrame window;
    private Robot mouseControl;

    public Breakout() {
        gui = new BreakoutGUI(ball, paddle);
        window = gui;
        SCREEN_WIDTH = gui.getScreenWidth();
        SCREEN_HEIGHT = gui.getScreenHeight();
        initGame();
    }

    private void initGame() {
        ball = new Ball();
        ball.initBall(SCREEN_WIDTH, SCREEN_HEIGHT);
        level = new Level(5, 5);
        paddle = new Paddle();
        paddle.initPaddle(SCREEN_WIDTH, SCREEN_HEIGHT);
        try {
            mouseControl = new Robot();
        } catch (AWTException e) {
            System.err.println("Error initializing game");
        }
        level.addBricks(SCREEN_HEIGHT, SCREEN_WIDTH, window);

        gui.initialize(ball, paddle);

        // Timer to loop game logic every DELAY ms
        Timer timer = new Timer();
        TimerTask move = new TimerTask() {
            @Override
            public void run() { // FIXME: stop program correctly when window is forced closed
                switch (loopGame()) {
                    case 0:
                        timer.cancel();
                        gui.gameOver();
                        break;
                    case 2:
                        timer.cancel();
                        gui.youWin();
                        break;
                }
            }
        };

        gui.createEvents(timer);
        window.repaint();

        timer.scheduleAtFixedRate(move, 0, DELAY);
    }

    /**
     * This method checks if the ball has collided with anything or if it has
     * reached the bottom and changes ball velocity and location
     *
     * @return Returns 0 if still in play, returns -1 if ball is out
     */
    public int checkBall() {
        // Check if ball has hit a side, top, or bottom
        if (ball.getXCoord() <= 0 || ball.getXCoord() >= SCREEN_WIDTH - ball.getWidth()) { // Side
            ball.setXVelocity(-ball.getXVelocity());
        }
        if (ball.getYCoord() <= SCREEN_HEIGHT / 15) { // Top
            ball.setYVelocity(-ball.getYVelocity());
        } else if (ball.getYCoord() >= SCREEN_HEIGHT) { // Bottom
            return -1;
        }

        // Check if ball hits paddle when ball is near paddle
        if (ball.getYCoord() >= paddle.getY() - paddle.getHeight()
                && ball.getYCoord() < paddle.getY()) {
            if (ball.getXCoord() > paddle.getX()
                    && ball.getXCoord() < paddle.getX() + paddle.getWidth()) {
                // Change ball velocity based on where on the paddle it hits
                // the edges of the paddle change the velocity more than near the center
                double hitLoc = ((int) ball.getXCoord() - paddle.getX()) * 100 / paddle.getWidth();
                if (hitLoc < 50) { // Hits left side of paddle
                    ball.changeXVelocity(- ((50 - hitLoc) / 100));
                    ball.setYVelocity(-ball.getYVelocity());
                } else { // Hits right side of paddle
                    ball.changeXVelocity(((50 - (hitLoc - 50)) / 100));
                    ball.setYVelocity(-ball.getYVelocity());
                }
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
                && mouseX <= SCREEN_WIDTH - paddle.getWidth() / 2) {
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
        Brick b = level.getFirstBrick();
        while (b != null) {
            int brickX = b.getBrickX();
            int brickY = b.getBrickY();
            if (ballX >= brickX && ballX <= brickX + b.getWidth()
                    && ballY >= brickY && ballY <= brickY + b.getHeight()) {

                // FIXME: weird collisions with top and sides of bricks
                // Check if ball hit side or top/bottom of brick
                if (ballX < brickX + 5 || ballX > brickX + b.getWidth() + 5) {
                    ball.setXVelocity(-ball.getXVelocity());
                } else {
                    ball.setYVelocity(-ball.getYVelocity());
                }

                // TODO: Change way bricks are removed from window and brickList
                try {
                    level.destroyBrick(b, window);
                } catch (Exception e) {
                    System.err.println("Error destroying brick.");
                }
                
                return -1;
            }
            b = b.getNextBrick();
        }
        return 0;
    }

    /**
     * @return Returns 0 if game is over, 1 if still in game, and 2 if game is won
     */
    public int loopGame() {
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        // Move back cursor if it goes too far
        if (mouseX < 0) {
            mouseControl.mouseMove(0, MouseInfo.getPointerInfo().getLocation().y);
        } else if (mouseX > SCREEN_WIDTH) {
            mouseControl.mouseMove(SCREEN_WIDTH,
                    MouseInfo.getPointerInfo().getLocation().y);
        }

        movePaddle(mouseX);

        // Check if ball is out of bounds or has collided with anything
        if (checkBall() == -1) {
            if (lives > 1) {
                // TODO: add lives counter on screen
                lives--;
                ball.initBall(SCREEN_WIDTH, SCREEN_HEIGHT);
            } else {
                return 0; // Game lost
            }
        }
        // Check if a brick is destroyed
        if (checkBricks() == -1) {
            level.numBricks--;
            if (level.numBricks == 0) {
                return 2; // Game won
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        new Breakout();
    }
}
