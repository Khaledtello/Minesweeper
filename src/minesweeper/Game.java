package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import static minesweeper.GameFile.*;

public class Game extends JPanel implements MouseListener {
    protected static JPanel grid;
    private JPanel barPanel;
    private JButton newGameButton;

    public Game () {
        setGameStatusText();
        setNewGameButton();
        setBarPanel();
    }

    public void initGame () {
        numOfOpened = 0;
        numOfFlagged = 0;
        firstCell = false;
        cell = new Cell[row][column];
        Cell.renderingImages();

        gameStatusText.setText("MINES: " + numOfMines);
        setGrid();

        add(grid , BorderLayout.CENTER);
        add(barPanel , BorderLayout.NORTH);
    }

    public void setGrid () {
        grid = new JPanel();
        grid.setBackground(Color.BLACK);
        grid.setLayout(new GridLayout(row , column , 0 , 0));

        for (int i = 0; i< row; i++)
            for (int j = 0; j< column; j++) {
                cell[i][j] = new Cell();
                cell[i][j].addMouseListener(this);
                grid.add(cell[i][j]);
            }
    }

    public void setGameStatusText () {
        gameStatusText = new JLabel();
        gameStatusText.setHorizontalAlignment(JLabel.CENTER);
        gameStatusText.setForeground(Color.RED);
        gameStatusText.setFont(new Font("MV Boli" , Font.BOLD , 40));
    }

    public void setNewGameButton () {
        newGameButton = new JButton("new game");
        newGameButton.setBackground(Color.BLACK);
        newGameButton.setForeground(Color.RED);
        newGameButton.setFont(new Font("MV Boli" , Font.BOLD , 20));
        newGameButton.setFocusable(false);
        newGameButton.addActionListener(e -> {
            grid.setVisible(false);
            remove(grid);
            Minesweeper.newGame();
        });
    }

    public void setBarPanel () {
        barPanel = new JPanel(new BorderLayout());
        barPanel.setBackground(Color.BLACK);
        barPanel.add(gameStatusText, BorderLayout.CENTER);
        barPanel.add(newGameButton , BorderLayout.EAST);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        for (int i=0 ; i<row ; i++)
            for (int j=0 ; j<column ; j++)
                if (e.getSource() == cell[i][j]) {
                    if (!firstCell) {
                        distributeMines(i , j);
                        setCellsNumbers();
                        firstCell = true;
                    }

                    if (!cell[i][j].isFlagged() && !cell[i][j].isOpened() && SwingUtilities.isLeftMouseButton(e))
                        CheckChoice.check(i , j);

                    else if (!cell[i][j].isFlagged() && cell[i][j].isOpened() && SwingUtilities.isLeftMouseButton(e)) {
                        int number = 0;
                        for (int k = -1; k <=1 ; k++)
                            for (int h = -1; h <=1 ; h++)
                                if (i+ k >=0 && i+ k < row && j+ h >=0 && j+ h < column && cell[i+ k][j+ h].isFlagged())
                                    number++;
                        if (number >= cell[i][j].getNumber()) {
                            for (int k = -1; k <=1 ; k++)
                                for (int h = -1; h <=1 ; h++)
                                    if (i+ k >=0 && i+ k < row && j+ h >=0 && j+ h < column && !cell[i+ k][j+ h].isFlagged() && !cell[i+ k][j+ h].isOpened())
                                        CheckChoice.check(i+k , j+h);
                        }
                        else {
                            for (int k = -1; k <=1 ; k++)
                                for (int h = -1; h <=1 ; h++)
                                    if (i+ k >=0 && i+ k < row && j+ h >=0 && j+ h < column && !cell[i+ k][j+ h].isFlagged() && !cell[i+ k][j+ h].isOpened())
                                        cell[i+k][j+h].changeToZero();
                        }
                    }

                    else if (!cell[i][j].isOpened() && SwingUtilities.isRightMouseButton(e)) {
                        if (cell[i][j].isFlagged()) {
                            cell[i][j].removeFlag();
                            numOfFlagged--;
                            gameStatusText.setText("MINES: " + (numOfMines - numOfFlagged));
                            cell[i][j].setFlagged(false);
                        }
                        else if (numOfFlagged < numOfMines) {
                            cell[i][j].addFlag();
                            numOfFlagged++;
                            gameStatusText.setText("MINES: " + (numOfMines - numOfFlagged));
                            cell[i][j].setFlagged(true);
                        }
                    }
                }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i=0 ; i<row ; i++)
            for (int j=0 ; j<column ; j++)
                if (e.getSource() == cell[i][j] && cell[i][j].isOpened() && SwingUtilities.isLeftMouseButton(e)) {
                    int number = 0;
                    for (int k = -1; k <=1 ; k++)
                        for (int h = -1; h <=1 ; h++)
                            if (i+ k >=0 && i+ k < row && j+ h >=0 && j+ h < column && cell[i+ k][j+ h].isFlagged())
                                number++;
                    if (number < cell[i][j].getNumber())
                        for (int k = -1; k <=1 ; k++)
                            for (int h = -1; h <=1 ; h++)
                                if (i+ k >=0 && i+ k < row && j+ h >=0 && j+ h < column && !cell[i+ k][j+ h].isFlagged() && !cell[i+ k][j+ h].isOpened())
                                    cell[i+k][j+h].changeToCell();
                }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    private void distributeMines (int y , int x) {
        ArrayList<Integer> xPosition = new ArrayList<>(GameFile.numOfMines);
        ArrayList<Integer> yPosition = new ArrayList<>(GameFile.numOfMines);

        // distribute mines randomly
        Random random = new Random();
        int c , r ;
        boolean b ;
        for (int i=0 ; i<GameFile.numOfMines ; ) {
            c = random.nextInt(column);
            r = random.nextInt(row);

            // we need to check that there are no mine in the first cell has chosen or around it
            b = true;
            for (int k=-1 ; k<=1 ; k++)
                for (int j=-1 ; j<=1 ; j++)
                    if (y+k == r && x+j == c) {
                        b = false;
                        break;
                    }

            //we need to check that there are no mine in the same cell
            if (b)
                for (int j=0 ; j<xPosition.size() ; j++)
                    if (c == xPosition.get(j) && r == yPosition.get(j)) {
                        b = false;
                        break;
                    }

            if (b) {
                xPosition.add(c);
                yPosition.add(r);
                i++;
            }
        }

        // add mines to cells
        for (int i=0 ; i<GameFile.numOfMines ; i++)
            cell[yPosition.get(i)][xPosition.get(i)].setMine(true);
    }

    private void setCellsNumbers () {
        int number ;
        for (int y = 0; y < row; y++)
            for (int x = 0; x < column; x++) {
                if (!cell[y][x].isMine()) {
                    number = 0;
                    for (int i=-1 ; i<=1 ; i++)
                        for (int j=-1 ; j<=1 ; j++)
                            if (y+i>=0 && y+i< row && x+j>=0 && x+j< column && cell[y+i][x+j].isMine())
                                number++;
                    cell[y][x].setNumber(number);
                }
                else
                    cell[y][x].setNumber(-1);
            }
    }
}