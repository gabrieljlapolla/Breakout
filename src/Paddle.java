/**
 * Holds information on the game paddle
 */
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 *
 * @author Gabriel Lapolla
 */
public class Paddle extends JComponent {

    
    public Paddle(){
        
    }
    
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(92, 22, 78));
        g.fillRect(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
        // TODO: Add rounded corners
    }
    
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(155, 40, 123));
        g.drawRect(0,0, getSize().width - 1, getSize().height - 1);
    }
    
    public void initPaddle(int windowWidth, int windowHeight) {
       Border compoundBorder = BorderFactory.createCompoundBorder();

        setBorder(compoundBorder);
        setSize(windowWidth / 15, windowHeight / 75);
        setBounds((windowWidth / 2) - (getSize().width / 2), windowHeight / 15 * 13,
                getSize().width, getSize().height);
    }
}
