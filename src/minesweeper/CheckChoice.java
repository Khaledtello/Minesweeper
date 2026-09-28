package minesweeper;


import java.awt.*;

import static minesweeper.GameFile.*;

public class CheckChoice {
    public static void check (int y , int x) {
        switch (cell[y][x].getNumber()) {
            case -1 -> {
                lose();
                cell[y][x].setBackground(Color.RED);
            }

            case 0 -> {
                cell[y][x].changeCellStatus();
                floodFill(y , x);
                checkWin();
            }

            default -> {
                cell[y][x].changeCellStatus();
                checkWin();
            }
        }
    }

    public static void floodFill(int y , int x) {
        for (int i=-1 ; i<=1 ; i++)
            for (int j=-1 ; j<=1 ; j++)
                if (y+i>=0 && y+i< row && x+j>=0 && x+j< column && !cell[y+i][x+j].isOpened() && !cell[y+i][x+j].isFlagged())
                    check(y+i , x+j);
    }

    public static void checkWin () {
        if (numOfOpened + numOfMines == row * column) {
            gameStatusText.setText("YOU WON");

            for (int i=0 ; i<row ; i++)
                for (int j=0 ; j<column ; j++)
                    if (cell[i][j].isMine()) {
                        cell[i][j].addFlag();
                        cell[i][j].setOpened(true);
                    }
        }
    }

    public static void lose () {
        for (int i=0 ; i<row ; i++)
            for (int j=0 ; j<column ; j++)
                if (!cell[i][j].isOpened())
                    cell[i][j].changeCellStatus();

        gameStatusText.setText("YOU LOST");
    }
}
