package GameCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardAndLogic extends JFrame implements ActionListener {
    JTextField[] boxes = new JTextField[9];
    JButton CrossButton, ZeroButton, ResetButton;
    JTextField lastFocusedField = null;
    JLabel Winning, PlayerScoreNumber1, PlayerScoreNumber2;

    int playerScore1 = 0;
    int playerScore2 = 0;
    static String win;
    boolean isTurn = true;

    public BoardAndLogic(){
        super("Tic-Tac-Toe Game");

        JLabel Head = new JLabel("Tic-Tac-Toe Game");
        Head.setFont(new Font("Raleway", Font.BOLD, 35));
        Head.setBounds(620, 20,400, 50);
        add(Head);

        Winning = new JLabel();
        Winning.setFont(new Font("Raleway", Font.BOLD, 30));
        Winning.setBounds(650, 90,400, 40);
        Winning.setForeground(Color.RED);
        add(Winning);

        int x = 580, y = 200;
        for (int i = 0; i < 9; i++) {
            boxes[i] = new JTextField();
            boxes[i].setBounds(x, y, 100, 100);
            boxes[i].setFont(new Font("System", Font.BOLD, 35));
            boxes[i].setHorizontalAlignment(JTextField.CENTER);
            boxes[i].addFocusListener(focusListener);
            boxes[i].setFocusable(true);
            add(boxes[i]);

            if ((i + 1) % 3 == 0) {
                y += 150;
                x = 580;
            } else {
                x += 140;
            }
        }

        JLabel PlayersSymbol = new JLabel("Player 1 Symbol : X, Player 2 Symbol : 0");
        PlayersSymbol.setBounds(20, 170, 500, 25);
        PlayersSymbol.setFont(new Font("System", Font.BOLD, 20));
        add(PlayersSymbol);

        JLabel ScoreLabel = new JLabel("Player Scores");
        ScoreLabel.setBounds(20, 230, 200, 30);
        ScoreLabel.setFont(new Font("System", Font.BOLD, 25));
        add(ScoreLabel);

        JLabel Player1 = new JLabel("Player 1 : ");
        Player1.setBounds(20, 280, 100, 25);
        Player1.setFont(new Font("System", Font.BOLD, 20));
        add(Player1);

        PlayerScoreNumber1 = new JLabel(String.valueOf(playerScore1));
        PlayerScoreNumber1.setBounds(130, 280, 200, 25);
        PlayerScoreNumber1.setFont(new Font("System", Font.BOLD, 20));
        PlayerScoreNumber1.setForeground(Color.red);
        add(PlayerScoreNumber1);

        JLabel Player2 = new JLabel("Player 2 : ");
        Player2.setBounds(20, 325, 100, 25);
        Player2.setFont(new Font("System", Font.BOLD, 20));
        add(Player2);

        PlayerScoreNumber2 = new JLabel(String.valueOf(playerScore2));
        PlayerScoreNumber2.setBounds(130, 325, 200, 25);
        PlayerScoreNumber2.setFont(new Font("System", Font.BOLD, 20));
        PlayerScoreNumber2.setForeground(Color.red);
        add(PlayerScoreNumber2);

        CrossButton = new JButton("X");
        CrossButton.setBounds(550,670, 130, 40);
        CrossButton.setFont(new Font("System", Font.BOLD, 35));
        CrossButton.setHorizontalAlignment(JTextField.CENTER);
        CrossButton.setBackground(Color.BLACK);
        CrossButton.setForeground(Color.WHITE);
        CrossButton.addActionListener(this);
        add(CrossButton);

        ResetButton = new JButton("Reset");
        ResetButton.setBounds(710,670, 130, 40);
        ResetButton.setFont(new Font("System", Font.BOLD, 25));
        ResetButton.setBackground(Color.BLACK);
        ResetButton.setForeground(Color.WHITE);
        ResetButton.addActionListener(this);
        add(ResetButton);

        ZeroButton = new JButton("0");
        ZeroButton.setBounds(870,670, 130, 40);
        ZeroButton.setFont(new Font("System", Font.BOLD, 35));
        ZeroButton.setBackground(Color.BLACK);
        ZeroButton.setForeground(Color.WHITE);
        ZeroButton.addActionListener(this);
        add(ZeroButton);

        RectangleBox panel = new RectangleBox();
        add(panel);

        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }


    FocusListener focusListener = new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            lastFocusedField = (JTextField) e.getSource(); // Store the last active field
        }
    };

    static String CheckWinner(JTextField[] boxes){
        String[][] WinningCombination = {
                {boxes[0].getText(), boxes[1].getText(), boxes[2].getText()},
                {boxes[3].getText(), boxes[4].getText(), boxes[5].getText()},
                {boxes[6].getText(), boxes[7].getText(), boxes[8].getText()},
                {boxes[0].getText(), boxes[3].getText(), boxes[6].getText()},
                {boxes[1].getText(), boxes[4].getText(), boxes[7].getText()},
                {boxes[2].getText(), boxes[5].getText(), boxes[8].getText()},
                {boxes[0].getText(), boxes[4].getText(), boxes[8].getText()},
                {boxes[2].getText(), boxes[4].getText(), boxes[6].getText()}
        };

        for(String[] combination : WinningCombination){
            if(!combination[0].isEmpty() && combination[0].equals(combination[1]) && combination[1].equals(combination[2])){
                win = combination[0];
                return win;
            }
        }

        for(JTextField box : boxes){
            if(box.getText().isEmpty()){
                return "";
            }
        }

        return "Draw";
    }

    public void actionPerformed(ActionEvent actionEvent){
        if (lastFocusedField == null || !lastFocusedField.getText().isEmpty()) {
            return;
        }

        if(actionEvent.getSource() == CrossButton && isTurn){
            if (lastFocusedField != null) {
                lastFocusedField.setText("X");
                isTurn = false;
                CrossButton.setEnabled(false);
                ZeroButton.setEnabled(true);
            }
        } else if(actionEvent.getSource() == ZeroButton && !isTurn){
            if (lastFocusedField != null) {
                lastFocusedField.setText("0");
                isTurn = true;
                ZeroButton.setEnabled(false);
                CrossButton.setEnabled(true);
            }
        } else if(actionEvent.getSource() == ResetButton){
            playerScore1 = 0;
            playerScore2 = 0;
            PlayerScoreNumber1.setText(String.valueOf(0));
            PlayerScoreNumber2.setText(String.valueOf(0));
            Winning.setText("");
            for(JTextField box : boxes){
                box.setText("");
            }

            isTurn = true;
            CrossButton.setEnabled(true);
            ZeroButton.setEnabled(true);
        }

        String result = CheckWinner(boxes);
        if(!result.isEmpty()){
            Winning.setText("Player " + result + " is Won");
            if(result.equals("X")){
                playerScore1++;
                PlayerScoreNumber1.setText(String.valueOf(playerScore1));
            }
            if(result.equals("0")){
                playerScore2++;
                PlayerScoreNumber2.setText(String.valueOf(playerScore2));
            }
            for (JTextField box : boxes) {
                box.setText("");
            }
            isTurn = true;
            CrossButton.setEnabled(true);
            ZeroButton.setEnabled(true);
        }
    }

    public static class RectangleBox extends JPanel {
        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);

            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setStroke(new BasicStroke(5));

            graphics.setColor(Color.BLACK);
            graphics.drawRect(550, 170, 450, 450);
        }
    }
}


