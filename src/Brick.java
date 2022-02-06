/** *************************************************************************
 * Revision History
 ****************************************************************************
 * 12/20/20 Written by Gabriel Lapolla
 *************************************************************************** */
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 *
 * @author Gabriel Lapolla
 */
public class Brick extends JComponent {
    private int brickX;
    private int brickY;
    private final Border border;

    public Brick(int brickX, int brickY, Border border) {
        this.brickX = brickX;
        this.brickY = brickY;
        this.border = border;
    }
    
    public void setBrickX(int brickX){
        this.brickX = brickX;
    }
    
    public void setBrickY(int brickY){
        this.brickY = brickY;
    }
    
    public int getBrickX(){
        return this.brickX;
    }
    
    public int getBrickY(){
        return this.brickY;
    }

    // This method paints the brick
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(92, 22, 78));
        g.fillRect(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        setBorder(border);
        g.setColor(new Color(155, 40, 123));
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
    }
}
