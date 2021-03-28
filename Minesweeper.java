import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

class Minesweeper extends Game{
    private Square[][] squareGrid = new Square[9][9];
    private ResetButton resetButton;

    public Minesweeper(){
        super("Minesweeper!",800,600);
        this.setFocusable(true);
        this.requestFocus();

        Point pointArray[] = new Point[4];
        pointArray[0] = new Point(0, 0);
        pointArray[1] = new Point(0, 40);
        pointArray[2] = new Point(40, 40);
        pointArray[3] = new Point(40, 0);

        Point startingPoint = new Point(240, 150);
        for (int i = 0; i < 9; i++){
            startingPoint.setX(240);
            for (int j = 0; j < 9; j++){
                Square newSquare = new Square(pointArray, startingPoint.clone(), 0.0, 5);
                this.addMouseListener(newSquare);
                squareGrid[i][j] = newSquare;

                startingPoint.setX((startingPoint.getX() + 43.5));
            }
            startingPoint.setY((startingPoint.getY() + 43.5));
        }

        placeBombs(10);

        Point buttonPoints[] = new Point[4];
        buttonPoints[0] = new Point(0, 0);
        buttonPoints[1] = new Point(0, 40);
        buttonPoints[2] = new Point(60, 40);
        buttonPoints[3] = new Point(60, 0);
        Point buttonPos = new Point(150,100);

        resetButton = new ResetButton(buttonPoints, buttonPos, 0.0, 600, 800);
        this.addMouseListener(resetButton);

    }

    public void paint(Graphics brush){
        brush.setColor(Color.white);
        brush.fillRect(0,0,width,height);

        brush.setColor(Color.darkGray);
	    brush.drawString("M I N E S W E E P E R",350,100);

        resetButton.paint(brush);
        brush.setColor(Color.black);
        brush.drawString("reset",150,115);

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (squareGrid != null && squareGrid[i][j] != null){
                    squareGrid[i][j].paint(brush);
                }
            }
        }

        if (resetButton.getButtonStatus() == true){
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if (squareGrid != null && squareGrid[i][j] != null){
                        squareGrid[i][j].reset();
                        resetButton.reset();
                    }
                }
            }

            placeBombs(10);
        }

    }

    public void placeBombs(int numBombs){
        ArrayList<Square> shuffledSquares = new ArrayList<Square>();

        for (int i = 0; i < squareGrid.length; i++){
            for (int j = 0; j < squareGrid[0].length; j++){
                shuffledSquares.add(squareGrid[i][j]);
            }
        }
        Collections.shuffle(shuffledSquares);

        for (int k = 0; k < numBombs; k++){
            shuffledSquares.get(k).setBomb(true);
        }

    }

    public static void main (String[] args) {
        Minesweeper m = new Minesweeper();
        m.repaint();
    }

}