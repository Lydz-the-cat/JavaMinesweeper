import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Square extends Polygon implements MouseListener{
    public static int numClicked = 0;

    private int sideLength;
    private int proximity = 0;
    
    private boolean isClicked = false;
    private boolean isBomb = false;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean flagged = false;

    private Minesweeper minesweeper;
    private static BufferedImage bombBufferedImage;
    private static int bombWidth;
    private static int bombHeight;
    private static BufferedImage flagBufferedImage;
    private static int flagWidth;
    private static int flagHeight;

    public Square(Point[] inShape, Point inPosition, double inRotation, int inSideLength, Minesweeper game){
        super(inShape, inPosition, inRotation);

        sideLength = inSideLength;
        minesweeper = game;
        if (bombBufferedImage == null){
            try{
                bombBufferedImage = ImageIO.read(new File("bomb.jpg"));
                bombWidth = bombBufferedImage.getWidth();
                bombHeight = bombBufferedImage.getHeight();
            } catch (IOException ioE){
                System.out.println("Error: The file does not exist! " + ioE.getMessage());
            }
        }
        if (flagBufferedImage == null){
            try{
                flagBufferedImage = ImageIO.read(new File("flag.png"));
                flagWidth = flagBufferedImage.getWidth();
                flagHeight = flagBufferedImage.getHeight();
            } catch (IOException ioE){
                System.out.println("Error: The file does not exist! " + ioE.getMessage());
            }
        }
    }

    public void paint(Graphics brush){
        Point[] squarePoints = getPoints();
        int xvalues[] = new int[squarePoints.length];
        int yvalues[] = new int[squarePoints.length];
        for (int i = 0; i < squarePoints.length; i++){
            xvalues[i] = (int) squarePoints[i].getX();
            yvalues[i] = (int) squarePoints[i].getY();
        }

        if (gameWon){
            revealSquare();
            if (isBomb){
                flagged = true;
            }
        }

        if (flagged == true){
            brush.drawImage(flagBufferedImage, xvalues[0], yvalues[0], xvalues[2], yvalues[2], 0, 0, flagWidth, flagHeight, null);
        } else if (isClicked == false){
            brush.setColor(Color.lightGray);
            brush.fillPolygon(xvalues, yvalues, squarePoints.length);
        } else{
            if (isBomb){
                brush.drawImage(bombBufferedImage, xvalues[0], yvalues[0], xvalues[2], yvalues[2], 0, 0, bombWidth, bombHeight, null);
                
            } else{
                brush.setColor(Color.white);
                brush.fillPolygon(xvalues, yvalues, squarePoints.length);
            }
        }
        
        if (isClicked == true){
            if (this.getProx() > 0){
                brush.setColor(Color.BLACK);
                brush.drawString(String.valueOf(this.getProx()), (int) position.getX() + 7, (int) position.getY() + 10);
            }
        }

    }

    public void reset(){
        numClicked = 0;
        isClicked = false;
        isBomb = false;
        gameOver = false;
        proximity = 0;
        flagged = false;
        gameWon = false;
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

    public void setGameWon(boolean newStatus){
        gameWon = newStatus;
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
            if (gameWon == false){
                if (isClicked == false){
                    if (e.isControlDown()){
                        if (flagged == true){
                            flagged = false;
                        } else{
                            flagged = true;
                        }
                    } else if (flagged == false){
                        isClicked = true;
                        minesweeper.gameStarted = true;
                        if (isBomb == true){
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
                        } else{
                            numClicked++;
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