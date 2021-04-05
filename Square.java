import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Square extends Polygon implements MouseListener{
    private int sideLength;
    private int proximity = 0;
    
    private boolean isClicked = false;
    private boolean isBomb = false;
    private boolean gameOver = false;

    private Minesweeper minesweeper;

    public Square(Point[] inShape, Point inPosition, double inRotation, int inSideLength, Minesweeper game){
        super(inShape, inPosition, inRotation);

        sideLength = inSideLength;
        minesweeper = game;
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
            brush.setColor(Color.lightGray);
        } else{
            if (isBomb){
                brush.setColor(Color.red);
            } else{
                brush.setColor(Color.white);
            }
        }
        brush.fillPolygon(xvalues, yvalues, squarePoints.length);
        if (isClicked == true){
            if (this.getProx() > 0){
                brush.setColor(Color.BLACK);
                brush.drawString(String.valueOf(this.getProx()), (int) position.getX() + 7, (int) position.getY() + 10);
            }
        }

    }

    public void reset(){
        isClicked = false;
        isBomb = false;
        gameOver = false;
        proximity = 0;
    }

    public boolean isBomb(){
        return isBomb;
    }

    public void setBomb(boolean bomb){
        isBomb = bomb;
        proximity = -1;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public boolean isClicked(){
        return isClicked;
    }

    public void revealSquare(){
        isClicked = true;
    }

    public int getProx(){
        return proximity;
    }

    public void setProx(int newProx){
        proximity = newProx;
    }

    public void mouseClicked(MouseEvent e) {
        Point clickPoint = new Point(e.getX(), e.getY());

        if (this.contains(clickPoint)){
            isClicked = true;
            if (this.isBomb()){
                gameOver = true;
            }
            else if (proximity == 0){
                Square[][] squareGrid = minesweeper.getGrid();

                for (int i = 0; i < squareGrid.length; i++){
                    for (int j = 0; j < squareGrid[0].length; j++){
                        if (squareGrid[i][j] == this){
                            minesweeper.revealBlanks(i, j);
                        }
                    }
                }
            }
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