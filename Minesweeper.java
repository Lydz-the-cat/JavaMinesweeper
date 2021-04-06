import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

class Minesweeper extends Game{
    private Square[][] squareGrid = new Square[9][9];
    private int bombNum = 10;

    private ResetButton resetButton;
    private ResetButton playAgainButton;
    private boolean gameOver = false;
    private boolean gameWon = false;

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
                Square newSquare = new Square(pointArray, startingPoint.clone(), 0.0, 5, this);
                this.addMouseListener(newSquare);
                squareGrid[i][j] = newSquare;

                startingPoint.setX((startingPoint.getX() + 43.5));
            }
            startingPoint.setY((startingPoint.getY() + 43.5));
        }

        placeBombs(bombNum);
        placeNums();

        //Array of points for the reset button on the left
        Point buttonPoints1[] = new Point[4];
        buttonPoints1[0] = new Point(0, 0);
        buttonPoints1[1] = new Point(0, 40);
        buttonPoints1[2] = new Point(60, 40);
        buttonPoints1[3] = new Point(60, 0);
        Point buttonPosReset = new Point(150,100);

        Point buttonPoints2[] = new Point[4];
        buttonPoints2[0] = new Point(0, 0);
        buttonPoints2[1] = new Point(0, 40);
        buttonPoints2[2] = new Point(80, 40);
        buttonPoints2[3] = new Point(80, 0);
        Point buttonPosGameOver = new Point(400, 260);

        resetButton = new ResetButton(buttonPoints1, buttonPosReset, 0.0, 600, 800);
        playAgainButton = new ResetButton(buttonPoints2, buttonPosGameOver, 0.0, 600, 800);
        this.addMouseListener(resetButton);
        this.addMouseListener(playAgainButton);

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

        if (gameOver != true && gameWon != true) {
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if (squareGrid[i][j].isGameOver()){
                        
                        gameOver = true;
                        playAgainButton.reset();
                        
                        revealSquares();
                        break;
                    }
                }
            }
        }

        if (gameOver) {
            brush.setColor(Color.lightGray);
            brush.fillRect(320,200,200,120);
            brush.setColor(Color.black);
            brush.drawString("Game Over!",385,230);

            playAgainButton.paint(brush);
            brush.setColor(Color.black);
            brush.drawString("play again",390,275);
        }

        if (Square.numClicked == ((squareGrid.length * squareGrid[0].length) - bombNum)){
            if (gameWon == false){
                playAgainButton.reset();
                gameWon = true;               
            }

            for (int i = 0; i < squareGrid.length; i++){
                for (int j = 0; j < squareGrid.length; j++){
                    squareGrid[i][j].setGameWon(true);
                }
            }

            brush.setColor(Color.green);
            brush.fillRect(320,200,200,120);
            brush.setColor(Color.black);
            brush.drawString("You won!",390,230);

            playAgainButton.paint(brush);
            brush.setColor(Color.black);
            brush.drawString("play again",390,275);
        }

        if (resetButton.getButtonStatus() == true){
            gameOver = false;
            gameWon = false;
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if (squareGrid != null && squareGrid[i][j] != null){
                        squareGrid[i][j].reset();
                        resetButton.reset();
                    }
                }
            }

            placeBombs(10);
            placeNums();
            
        }

        if (playAgainButton.getButtonStatus() == true && (gameOver == true || gameWon == true)){
            gameOver = false;
            gameWon = false;
            for (int i = 0; i < squareGrid.length; i++){
                for (int j = 0; j < squareGrid[0].length; j++){
                    if (squareGrid != null && squareGrid[i][j] != null){
                        squareGrid[i][j].reset();
                        playAgainButton.reset();
                    }
                }
            }

            placeBombs(10);
            placeNums();
        }
    }

    private void placeBombs(int numBombs){
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

    //The following 3 methods deal with placing the numbers indicating the squares' proximity to a mine
    private void placeNums(){
        for (int i = 0; i < squareGrid.length; i++){
            for (int j = 0; j < squareGrid[0].length; j++){
                int prox = checkNums(i, j);
                squareGrid[i][j].setProx(prox);
            }
        }

    }
    private int checkNums(int x, int y){
        int numBombs = 0;
        if (squareGrid[x][y].isBomb()){
            numBombs = -1;
        } else{
            if (isWithinGrid(x-1, y+1)){
                if (squareGrid[x-1][y+1].isBomb()){
                    numBombs++;
                }
            }
            if (isWithinGrid(x, y+1)){
                if (squareGrid[x][y+1].isBomb()){
                    numBombs++;
                }
            }
            if (isWithinGrid(x+1,y+1)){
                if (squareGrid[x+1][y+1].isBomb()){
                    numBombs++;
                }
            }
            if (isWithinGrid(x-1,y)){
                if (squareGrid[x-1][y].isBomb()){
                    numBombs++;
                }
            }
            if (isWithinGrid(x+1,y)){
                if (squareGrid[x+1][y].isBomb()){
                    numBombs++;
                }
            }
            if (isWithinGrid(x-1,y-1)){
                if (squareGrid[x-1][y-1].isBomb()){
                    numBombs++;
                }
            }
            if (isWithinGrid(x,y-1)){
                if (squareGrid[x][y-1].isBomb()){
                    numBombs++;
                }
            }
            if (isWithinGrid(x+1,y-1)){
                if (squareGrid[x+1][y-1].isBomb()){
                    numBombs++;
                }
            }
        }

        return numBombs;
    }

    //A helper method for determining if a square is a corner/edge square
    private boolean isWithinGrid(int x, int y){
        if (x >= 0 && x < squareGrid.length && y >= 0 && y < squareGrid[0].length){
            return true;
        }
        else{
            return false;
        }
    }

    //If a square with no bombs around it is clicked,
    public void revealBlanks(int x, int y){
        squareGrid[x][y].revealSquare();
        Square.numClicked++;
        if (squareGrid[x][y].getProx() == 0){
            if (isWithinGrid(x-1, y+1)){
                if (squareGrid[x-1][y+1].isClicked() == false){
                    revealBlanks(x-1, y+1);
                }
            }
            if (isWithinGrid(x, y+1)){
                if (squareGrid[x][y+1].isClicked() == false){
                    revealBlanks(x, y+1);
                }
            }
            if (isWithinGrid(x+1, y+1)){
                if (squareGrid[x+1][y+1].isClicked() == false){
                    revealBlanks(x+1, y+1);
                }
            }
            if (isWithinGrid(x-1, y)){
                if (squareGrid[x-1][y].isClicked() == false){
                    revealBlanks(x-1, y);
                }
            }
            if (isWithinGrid(x+1, y)){
                if (squareGrid[x+1][y].isClicked() == false){
                    revealBlanks(x+1, y);
                }
            }
            if (isWithinGrid(x-1, y-1)){
                if (squareGrid[x-1][y-1].isClicked() == false){
                    revealBlanks(x-1, y-1);
                }
            }
            if (isWithinGrid(x, y-1)){
                if (squareGrid[x][y-1].isClicked() == false){
                    revealBlanks(x, y-1);
                }
            }
            if (isWithinGrid(x+1, y-1)){
                if (squareGrid[x+1][y-1].isClicked() == false){
                    revealBlanks(x+1, y-1);
                }
            }
        }
    }

    //This method is used to uncover all of the squares on the gameboard
    private void revealSquares(){
        for (int i = 0; i < squareGrid.length; i++){
            for (int j = 0; j < squareGrid[0].length; j++){
                squareGrid[i][j].revealSquare();
            }
        }
    }

    public Square[][] getGrid(){
        return squareGrid;
    }
    public static void main (String[] args) {
        Minesweeper m = new Minesweeper();
        m.repaint();
    }

}