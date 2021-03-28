import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Square extends Polygon implements MouseListener{
    private int sideLength;

    private boolean isClicked = false;
    private boolean isBomb = false;

    public Square(Point[] inShape, Point inPosition, double inRotation, int inSideLength){
        super(inShape, inPosition, inRotation);

        sideLength = inSideLength;
    }

    public void paint(Graphics brush){
        Point[] squarePoints = getPoints();
        int xvalues[] = new int[squarePoints.length];
        int yvalues[] = new int[squarePoints.length];
        for (int i = 0; i < squarePoints.length; i++){
            xvalues[i] = (int) squarePoints[i].getX();
            yvalues[i] = (int) squarePoints[i].getY();
        }
        if (isClicked == false){
            brush.setColor(Color.GRAY);
        } else{
            if (isBomb){
                brush.setColor(Color.RED);
            } else{
                brush.setColor(Color.WHITE);
            }
        }
        brush.fillPolygon(xvalues, yvalues, squarePoints.length);

    }

    public void reset(){
        isClicked = false;
        isBomb = false;
    }

    public boolean isBomb(){
        return isBomb;
    }

    public void setBomb(boolean bomb){
        isBomb = bomb;
    }

    public void mouseClicked(MouseEvent e) {
        Point clickPoint = new Point(e.getX(), e.getY());

        if (this.contains(clickPoint)){
            isClicked = true;
        }
    }

    public void mousePressed(MouseEvent e) {

    }
 
    public void mouseReleased(MouseEvent e) {

    }
 
    public void mouseEntered(MouseEvent e) {

    }
 
    public void mouseExited(MouseEvent e) {

    }

}