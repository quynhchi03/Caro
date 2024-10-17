package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame implements ActionListener {    // frame: Khung
    private JPanel panel;     // bang dieu khien
    private JLabel label;
    private JButton newGame;

    private int hang = 15;
    private int cot = 15;
    private String kyTu = "X";
    private boolean[][] check = new boolean[hang][cot];

    private JButton[][] buttons = new JButton[hang][cot];

    public MyWindow() {
        super("Caro");    //tieu de
        this.setSize(900, 1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set dong mac dinh
        this.setLayout(null);

        panel = new JPanel();
        panel.setBounds(0, 200, 800, 800);
        panel.setLayout(new GridLayout(hang, cot));   // GridLayout: bo cuc dang luoi
        panel.setBackground(Color.WHITE);
        this.add(panel);
        for (int i = 0; i < hang; i++) {
            for (int j = 0; j < cot; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);

                check[i][j] = true;
            }
        }

        label = new JLabel();
        label.setBounds(50, 50, 200, 50);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.GREEN);
        this.add(label);

        newGame = new JButton("New Game");
        newGame.setBounds(300, 100, 200, 100);
        newGame.addActionListener(this);

        this.add(newGame);

        this.setVisible(true);   // xuat hien man hinh
    }

    void doiKyTu() {
        if (kyTu.equals("X")) {
            kyTu = "O";
        } else {
            kyTu = "X";
        }
    }

    Color getColor(String kyTu) {
        if (kyTu.equals("X")) {
            return Color.RED;
        } else {
            return Color.BLUE;
        }
    }

    int check(int i, int j, int dx, int dy, String kyTu) {
        i = i += dx;
        j = j += dy;

        if (i < 0 || i >= hang || j < 0 || j >= cot) {
            return 0;
        }

        if (buttons[i][j].getText() == kyTu) {
            return 1 + check(i, j, dx, dy, kyTu);
        }
        return 0;
    }

    boolean checkWin(int i, int j) {
        String kyTu2 = buttons[i][j].getText();
        int count1 = check(i, j, 1, 0, kyTu2) + check(i, j, -1, 0, kyTu2);
        int count2 = check(i, j, 1, 1, kyTu2) + check(i, j, -1, -1, kyTu2);
        int count3 = check(i, j, 0, 1, kyTu2) + check(i, j, 0, -1, kyTu2);
        int count4 = check(i, j, 1, -1, kyTu2) + check(i, j, -1, 1, kyTu2);

        return count1 == 4 || count2 == 4 || count3 == 4 || count4 == 4;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            this.dispose();
            new MyWindow();
        } else {
            for (int i = 0; i < hang; i++) {
                for (int j = 0; j < cot; j++) {
                    if (e.getSource() == buttons[i][j]) {
                        if (check[i][j]) {
                            buttons[i][j].setText(kyTu);
                            buttons[i][j].setForeground(getColor(kyTu));
                            check[i][j] = false;
                            doiKyTu();
                        }

                        if (checkWin(i, j)) {
                            doiKyTu();
                            label.setText(buttons[i][j].getText() + " wins");
                            for (int a = 0; a < hang; a++) {
                                for (int b = 0; b < cot; b++) {
                                    buttons[a][b].setEnabled(false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
