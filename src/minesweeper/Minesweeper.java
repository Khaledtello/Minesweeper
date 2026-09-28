package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static minesweeper.GameFile.*;

public class Minesweeper implements ActionListener {

    public static JFrame frame;
    private static Game game;
    private static JPanel option, level;
    private JButton nextGameBu, easyBu, mediumBu, hardBu, back;
    private JComboBox<String> cb1;
    private JComboBox<String> cb2;

    public Minesweeper(){
        frame = new JFrame("Minesweeper");
        frame.setResizable(false);
        frame.setSize(700 , 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        setOptionPanel();
        setLevelPanel();

        game = new Game();
        game.setLayout(new BorderLayout());

        frame.add(option);
        frame.setVisible(true);
    }

    private void setOptionPanel(){
        JLabel label1 = new JLabel("Columns");
        label1.setBounds (150, 150, 200, 30);

        JLabel label2 = new JLabel("Rows");
        label2.setBounds (150, 275, 200, 30);

        String[] nm = new String[9];
        for(int i=0; i<9; i++)
            nm[i]= String.valueOf(i+8);

        cb1 = new JComboBox<>(nm);
        cb1.setBounds(250, 150, 200, 30);

        cb2 = new JComboBox<>(nm);
        cb2.setBounds(250, 275, 200, 30);

        nextGameBu = new JButton("Next =>");
        nextGameBu.setBounds(250, 400, 200, 30);
        nextGameBu.setFocusable(false);
        nextGameBu.addActionListener(this);

        option = new JPanel();
        option.setLayout(null);
        option.add(nextGameBu);
        option.add(label1);
        option.add(label2);
        option.add(cb1);
        option.add(cb2);
    }

    private void setLevelPanel(){
        easyBu = new JButton("Easy");
        easyBu.setBounds(250, 150, 200, 30);
        easyBu.setFocusable(false);
        easyBu.addActionListener(this);

        mediumBu = new JButton("Medium");
        mediumBu.setBounds(250, 250, 200, 30);
        mediumBu.setFocusable(false);
        mediumBu.addActionListener(this);

        hardBu = new JButton("Hard");
        hardBu.setBounds(250, 350, 200, 30);
        hardBu.setFocusable(false);
        hardBu.addActionListener(this);


        back = new JButton("Back");
        back.setBounds(250, 500, 200, 30);
        back.setFocusable(false);
        back.addActionListener(this);

        level = new JPanel();
        level.setLayout(null);
        level.add(easyBu);
        level.add(mediumBu);
        level.add(hardBu);
        level.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextGameBu) {
            column = Integer.parseInt(Objects.requireNonNull(cb1.getSelectedItem()).toString());
            row = Integer.parseInt(Objects.requireNonNull(cb2.getSelectedItem()).toString());
            changePanel(option, level);
        }

        if (e.getSource() == easyBu) {
            numOfMines = (column * row) /7;
            game.initGame();
            changePanel(level , game);
        }

        if (e.getSource() == mediumBu) {
            numOfMines = (column * row)/5;
            game.initGame();
            changePanel(level , game);
        }

        if (e.getSource() == hardBu) {
            numOfMines = (column * row)/3;
            game.initGame();
            changePanel(level , game);
        }

        if (e.getSource() == back)
            changePanel(level, option);
    }

    private static void changePanel (JPanel p1, JPanel p2) {
        p1.setVisible(false);
        frame.remove(p1);
        p2.setVisible(true);
        frame.add(p2);
    }

    public static void newGame() {
        game.initGame();
        changePanel(game , option);
    }
}
