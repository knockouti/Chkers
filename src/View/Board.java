package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Igor on 13.09.2016.
 */
public class Board {
    public static final int SIZE_FIELD = 64;
    public static final int SIZE_STRING = 8;
    private JPanel verticalNubmPanel;
    private JPanel horizontalNumbPanel;
    private JPanel mainPanel;
    private List<Cell> blackButtons;
    private List<Cell> whiteButtons;

    public Board() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 8));

        this.getButtons();
        this.setPositionButtons();
    }

    public void setPositionButtons() {
        whiteButtons = new ArrayList<>();
        for (int i = 0; i < SIZE_FIELD / 2; i++) {
            Cell cell = new Cell();
            cell.setBackground(Color.white);
            whiteButtons.add(cell);
        }

        for (int i = 7; i >= 0; i--) {
            if (i % 2 != 0) {
                for (int j = 0; j < 4; j++) {
                    mainPanel.add(whiteButtons.get(4 * i + j));
                    mainPanel.add(blackButtons.get(4 * i + j));
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    mainPanel.add(blackButtons.get(4 * i + j));
                    mainPanel.add(whiteButtons.get(4 * i + j));

                }
            }
        }

    }

    public void getButtons() {
        blackButtons = new ArrayList<>();
        int numbString = 1;
        for (int i = 1; i <= SIZE_STRING; i++) {
            if (numbString % 2 == 0) {
                for (char j = 'b'; j <= 'h'; j++) {
                    Cell cell = new Cell(i, j);
                    blackButtons.add(cell);
                    j++;
                }
            } else {
                for (char j = 'a'; j <= 'h'; j++) {
                    Cell cell = new Cell(i, j);
                    blackButtons.add(cell);
                    j++;
                }

            }
            numbString++;
        }
        for (JPanel panel : blackButtons) {
            mainPanel.add(panel);
        }

    }

    public JPanel getVerticalNubmPanel() {
        verticalNubmPanel = new JPanel();
        verticalNubmPanel.setLayout(new BoxLayout(verticalNubmPanel, BoxLayout.Y_AXIS));

        verticalNubmPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        for (int i = 8; i > 0; i--) {
            JLabel label = new JLabel(String.valueOf(i));
            verticalNubmPanel.add(label);
            verticalNubmPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        }
        return verticalNubmPanel;
    }

    public JPanel getHorizontalNumbPanel() {
        horizontalNumbPanel = new JPanel();
        horizontalNumbPanel.setLayout(new BoxLayout(horizontalNumbPanel, BoxLayout.X_AXIS));
        horizontalNumbPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        for (char i = 'A'; i <= 'H'; i++) {
            JLabel label = new JLabel(String.valueOf(i));
            horizontalNumbPanel.add(label);
            horizontalNumbPanel.add(Box.createRigidArea(new Dimension(118, 0)));
        }
        return horizontalNumbPanel;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
