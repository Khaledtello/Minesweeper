package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static minesweeper.GameFile.*;

public class Cell extends JButton {
    private int number;
    private boolean isMine;
    private boolean isOpened;
    private boolean isFlagged;
    private static ImageIcon mineIcon;
    private static ImageIcon cellIcon;
    private static ImageIcon[] numIcon;
    private static ImageIcon flagIcon;

    public Cell () {
        setFocusable(false);
        setIcon(cellIcon);
    }

    public static void renderingImages () {
        mineIcon = new ImageIcon();
        cellIcon = new ImageIcon();
        numIcon = new ImageIcon[9];
        flagIcon = new ImageIcon();

        flagIcon = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/flag.png"))));
        mineIcon = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/mine.png"))));
        cellIcon = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/cell.png"))));
        numIcon[0] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number0.png"))));
        numIcon[1] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number1.png"))));
        numIcon[2] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number2.png"))));
        numIcon[3] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number3.png"))));
        numIcon[4] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number4.png"))));
        numIcon[5] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number5.png"))));
        numIcon[6] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number6.png"))));
        numIcon[7] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number7.png"))));
        numIcon[8] = resize(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../resources/number8.png"))));
    }

    public void changeToZero () {
        setIcon(numIcon[0]);
    }

    public void changeToCell () {
        setIcon(cellIcon);
    }

    public void addFlag() {
        setIcon(flagIcon);
    }

    public void removeFlag() {
        setIcon(cellIcon);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void changeCellStatus() {
        setOpened(true);
        GameFile.numOfOpened++;

        switch (number) {
            case -1 -> {setBackground(Color.lightGray); setIcon(mineIcon);}
            case 0 -> setIcon(numIcon[0]);
            case 1 -> setIcon(numIcon[1]);
            case 2 -> setIcon(numIcon[2]);
            case 3 -> setIcon(numIcon[3]);
            case 4 -> setIcon(numIcon[4]);
            case 5 -> setIcon(numIcon[5]);
            case 6 -> setIcon(numIcon[6]);
            case 7 -> setIcon(numIcon[7]);
            case 8 -> setIcon(numIcon[8]);
        }
    }

    private static ImageIcon resize(ImageIcon im) {
        BufferedImage bi = new BufferedImage(700/column , 650/row , BufferedImage.TRANSLUCENT);
        Graphics2D gd = bi.createGraphics();
        gd.drawImage(im.getImage() , 0 , 0 , 700/column , 650/row , null);
        gd.dispose();
        return new ImageIcon(bi);
    }
}
