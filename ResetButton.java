import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//Going to be turned into an interface later so that various buttons with various purposes can be created
//For now, this button resets the board (unclicks all the squares)

public class ResetButton extends Polygon implements MouseListener{
    private int width;
    private int height;

    private boolean isClicked = false;
    private boolean isHovered = false;

    public ResetButton(Point[] inShape, Point inPosition, double inRotation, int inWidth, int inHeight){
        super(inShape, inPosition, inRotation);
        width = inWidth;
        height = inHeight;
    }
    
    public void paint(Graphics brush){
        Point[] buttonPoints = getPoints();
        int xvalues[] = new int[buttonPoints.length];
        int yvalues[] = new int[buttonPoints.length];
        for (int i = 0; i < buttonPoints.length; i++){
            xvalues[i] = (int) buttonPoints[i].getX();
            yvalues[i] = (int) buttonPoints[i].getY();
        }
        
        if (isHovered == false){
            brush.setColor(Color.gray);
        } else{
            brush.setColor(Color.darkGray);
        }
        brush.fillPolygon(xvalues, yvalues, buttonPoints.length);
    }

    public boolean getButtonStatus(){
        return isClicked;
    }
    
    public void reset(){
        isClicked = false;
    }

    public void mouseClicked(MouseEvent e) {
        Point clickPoint = new Point(e.getX(), e.getY());

        if (this.contains(clickPoint)){
            isClicked = true;

        }
    }
     
    public void mouseEntered(MouseEvent e) {
        /*
        Point hoverPoint = new Point(e.getX(), e.getY());

        if (this.contains(hoverPoint)){
            isHovered = true;
        }
        */
    }
 
    public void mouseExited(MouseEvent e) {
        /*
        Point hoverPoint = new Point(e.getX(), e.getY());

        if (this.contains(hoverPoint)){
            isHovered = false;
        }
        */
    }

    public void mousePressed(MouseEvent e) {

    }
 
    public void mouseReleased(MouseEvent e) {
        
    }

}