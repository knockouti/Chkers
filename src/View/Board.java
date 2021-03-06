package View;
import Contr.*;
import Contr.Cell;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.List;

/**
 * Created by Igor on 13.09.2016.
 */
public class Board implements PlayerInterface, Serializable {
    public static final int SIZE_FIELD = 64;
    public static final int SIZE_STRING = 8;
    private static Board board;
    private JPanel verticalNubmPanel;
    private JPanel horizontalNumbPanel;
    private static JPanel mainPanel;
    static ControllerServerInterface controller;
    List<Cell> borderCells;
    int numberTIck = 1;
    static
    PlayerInterface playerInterface;
    public static List<CellView> blackButtons;
    private List<CellView> whiteButtons;
    ChekersView chekersView;
    CellView cellViewOld;
    CellView cellView;
    CellView cellView1;

    public List<CellView> getBlackButtons() {
        return blackButtons;

    }

    public void setCellView1(CellView cellView1) {
        this.cellView1 = cellView1;
    }

    public void setCellView(CellView cellView) {
        this.cellView = cellView;

    }

    public void updateBOard() throws RemoteException {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 8));
        mainPanel.repaint();
        controller.startAddPlayer();
        board.getButtons();
        controller.setPlayerAlghoritm();
        board.setPositionButtons();
        board.setChekers();
        board.setListenerForCells();
        MainFrame mainFrame = new MainFrame(board);
    }

    public static void main(String[] args) throws RemoteException {


        board = new Board();
        try {

            Registry registry = LocateRegistry.getRegistry(null, 12345);
            controller = (ControllerServerInterface) registry.lookup("Server");
            playerInterface = (PlayerInterface) UnicastRemoteObject.exportObject(board, 0);
            controller.register(playerInterface);
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(8, 8));
            controller.startAddPlayer();
            board.getButtons();
            controller.setPlayerAlghoritm();
            board.setPositionButtons();
            board.setChekers();
            board.setListenerForCells();
            MainFrame mainFrame = new MainFrame(board);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBoard() throws RemoteException {
        controller.deletePlayer(playerInterface);
    }

    public Board() throws RemoteException {

    }

    public char getXNowAdress() {
        return cellView.getXAdress();
    }

    public int getYNowAdress() {
        return cellView.getYAdress();
    }

    public char getCellView1X() {
        return cellView1.getXAdress();
    }

    public int getCellView1Y() {
        return cellView1.getYAdress();
    }

    public char getXOldAdress() {
        return cellViewOld.getXAdress();
    }

    public int getYOldAdress() {
        return cellViewOld.getYAdress();
    }

    public void setChekersView(char xAdress, int yAdress, char oldXAdress, int oldYAdress, char oneXAdress, int oneYAdress) throws RemoteException {
        for (CellView cell : blackButtons) {
            if (cell.getXAdress() == xAdress && cell.getYAdress() == yAdress) {
                this.cellView = cell;
            }
            if (cell.getXAdress() == oldXAdress && cell.getYAdress() == oldYAdress) {
                this.cellViewOld = cell;
            }
            if (cell.getXAdress() == oneXAdress && cell.getYAdress() == oneYAdress) {
                this.cellView1 = cell;
            }
        }
        if (!controller.getBooleanBear()) {
            this.cellViewOld.setColorCell(0);
            this.cellViewOld.remove(this.cellViewOld.getChekersView());
            this.cellViewOld.repaint();
            this.cellView.setChekersView(this.cellViewOld.getChekersView());
            cellView.repaint();
            this.cellView.setColorCell(controller.getNumberPlayerInModel());
        } else {
            cellViewOld.setColorCell(0);
            controller.getBearCellsController().get(0).setColorCell(0);
            cellViewOld.remove(cellViewOld.getChekersView());
            cellViewOld.repaint();
            cellView1.remove(cellView1.getChekersView());
            cellView1.repaint();
            cellView.setChekersView(cellViewOld.getChekersView());
            cellView.repaint();
            cellView.setColorCell(controller.getNumberPlayerInModel());
        }
    }

    public void setListenerForCells() throws RemoteException {
        JLabel label = new JLabel("Не ваш ход");
        for (CellView cellView : blackButtons) {
            cellView.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    for (CellView cell : Board.blackButtons) {
                        cell.setBooleanBorder(false, cell);

                    }

                    try {
                        if (controller.getPlayerINterfaceNow().equals(playerInterface)) {
                            if (e.getButton() == MouseEvent.BUTTON1 && numberTIck == 1) {
                                try {
                                    if (!controller.getBooleanBear()) {
                                        if (cellView.getColor() == controller.getNumberPlayerInModel()) {
                                            System.out.println(cellView.xAdress + cellView.yAdress);
                                            controller.setCurrentCellController(cellView.getXAdress(), cellView.getYAdress());
                                            borderCells = controller.getBorderCellsForView();
                                            cellViewOld = cellView;
                                            for (CellView cell : getBlackButtons()) {
                                                for (Cell cell1 : borderCells) {
                                                    if (cell.getXAdress() == cell1.getXAdress() && cell.getYAdress() == cell1.getYAdress()) {

                                                        cell.setBooleanBorder(true, cellViewOld);

                                                    }
                                                }
                                            }
                                            numberTIck = 2;
                                        }
                                    } else {
                                        borderCells = controller.getBorderCellsForView();
                                        cellViewOld = cellView;
                                        for (CellView cell : getBlackButtons()) {
                                            for (Cell cell1 : borderCells) {
                                                for (Cell cell2 : controller.getBearCellsController()) {
                                                    if (cell.getXAdress() == cell1.getXAdress() && cell.getYAdress() == cell1.getYAdress()) {
                                                        if (cell2.getXAdress() == cell.getXAdress() && cell2.getYAdress() == cell.getYAdress())
                                                            cell.setBooleanBorder(true, cellViewOld);
                                                    }
                                                }
                                            }
                                        }
                                        numberTIck = 2;
                                    }
                                } catch (RemoteException e1) {
                                    e1.printStackTrace();
                                }
                            } else if (e.getButton() == MouseEvent.BUTTON3 && numberTIck == 2) {

                                for (CellView cell : getBlackButtons()) {
                                    for (Cell cell1 : borderCells) {
                                        if (cell.getXAdress() == cell1.getXAdress() && cell.getYAdress() == cell1.getYAdress()) {
                                            cell.setBooleanBorder(false, cellViewOld);
                                        }
                                    }
                                }
                                try {
                                    if (!controller.getBooleanBear()) {
                                        for (Cell cell : borderCells) {
                                            if (cellView.getXAdress() == cell.getXAdress() && cellView.getYAdress() == cell.getYAdress()) {
                                                setCellView(cellView);
                                                cellViewOld.setColorCell(0);
                                                cellViewOld.remove(cellViewOld.getChekersView());
                                                cellViewOld.repaint();
                                                cellView.setChekersView(cellViewOld.getChekersView());
                                                cellView.setColorCell(controller.getNumberPlayerInModel());
                                                controller.changeDiagonalPlayer1(cellView.getXAdress(), cellView.getYAdress(), cellViewOld.getXAdress(), cellViewOld.getYAdress());

                                            }
                                        }
                                        numberTIck = 1;

                                    } else {
                                        for (Cell cell : borderCells) {
                                            if (cellView.getXAdress() == cell.getXAdress() && cellView.getYAdress() == cell.getYAdress()) {
                                                for (CellView cellView1 : getBlackButtons()) {
                                                    if (cellView1.getXAdress() == controller.getBearCellsController().get(0).getXAdress() && cellView1.getYAdress() == controller.getBearCellsController().get(0).getYAdress()) {
                                                        setCellView(cellView);
                                                        setCellView1(cellView1);
                                                        cellViewOld.setColorCell(0);
                                                        controller.getBearCellsController().get(0).setColorCell(0);
                                                        cellViewOld.remove(cellViewOld.getChekersView());
                                                        cellViewOld.repaint();
                                                        cellView1.remove(cellView1.getChekersView());
                                                        cellView1.repaint();
                                                        cellView.setChekersView(cellViewOld.getChekersView());
                                                        cellView.setColorCell(controller.getNumberPlayerInModel());
                                                        controller.changeDiagonalPlayer1(cellView.getXAdress(), cellView.getYAdress(), cellViewOld.getXAdress(), cellViewOld.getYAdress());
                                                        numberTIck = 1;
                                                        controller.setBooleanBear(false);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (RemoteException e1) {
                                    e1.printStackTrace();
                                }
                            } else try {
                                if (cellView.getColor() == controller.getOppositeNumberPlayerForView()) {
                                    System.out.println("Это не ваша шашка!");
                                    numberTIck = 1;
                                } else if (e.getButton() == MouseEvent.BUTTON1 && numberTIck == 2) {
                                    System.out.println("Нажмите правую кнопку мыши");
                                    numberTIck = 1;
                                } else if (cellView.getColor() == 0) {
                                    System.out.println("Это gустое поле!");
                                    numberTIck = 1;
                                }
                            } catch (RemoteException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            System.out.println("Не ваш ход");
                        }
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                }
            });
        }
    }


    public void setPositionButtons() throws RemoteException {
        whiteButtons = new ArrayList<>();
        for (int i = 0; i < SIZE_FIELD / 2; i++) {
            CellView cellView = new CellView();
            cellView.setBackground(Color.white);
            whiteButtons.add(cellView);
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

    public void setChekers() throws RemoteException {
        for (int i = 0; i < 12; i++) {
            chekersView = new ChekersView(Color.red);
            chekersView.setColorChker();
            CellView cellView = blackButtons.get(i);
            blackButtons.get(i).setColorCell(1);
            controller.getBlackButtonsList().get(i).setColorCell(1);
            cellView.setChekersView(chekersView);

        }
        for (int i = 20; i < SIZE_FIELD / 2; i++) {
            chekersView = new ChekersView(Color.white);
            chekersView.setColorChker();
            CellView cellView = blackButtons.get(i);
            cellView.setChekersView(chekersView);
            blackButtons.get(i).setColorCell(2);
            controller.getBlackButtonsList().get(i).setColorCell(2);
            cellView.setLayout(new GridBagLayout());
            cellView.add(chekersView);
        }


    }


    public void getButtons() throws RemoteException {
        blackButtons = new ArrayList<>();
        int numbString = 1;
        for (int i = 1; i <= SIZE_STRING; i++) {
            if (numbString % 2 == 0) {
                for (char j = 'b'; j <= 'h'; j++) {
                    CellView cellView = new CellView(i, j);
                    cellView.setColorCell(0);
                    controller.setAdressCell(i, j);
                    blackButtons.add(cellView);
                    j++;
                }
            } else {
                for (char j = 'a'; j <= 'h'; j++) {
                    CellView cellView = new CellView(i, j);
                    controller.setAdressCell(i, j);
                    cellView.setColorCell(0);
                    blackButtons.add(cellView);
                    j++;
                }
            }
            numbString++;
        }
        for (JPanel panel : blackButtons) {
            mainPanel.add(panel);
        }

    }

    public JPanel getVerticalNubmPanel() throws RemoteException {
        verticalNubmPanel = new JPanel();
        verticalNubmPanel.setLayout(new BoxLayout(verticalNubmPanel, BoxLayout.Y_AXIS));
        verticalNubmPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        for (int i = SIZE_STRING; i > 0; i--) {
            JLabel label = new JLabel(String.valueOf(i));
            verticalNubmPanel.add(label);
            verticalNubmPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        }
        return verticalNubmPanel;
    }

    public JPanel getHorizontalNumbPanel() throws RemoteException {
        horizontalNumbPanel = new JPanel();
        horizontalNumbPanel.setLayout(new BoxLayout(horizontalNumbPanel, BoxLayout.X_AXIS));
        horizontalNumbPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        for (char i = 'A'; i <= 'H'; i++) {
            JLabel label = new JLabel(String.valueOf(i));
            horizontalNumbPanel.add(label);
            horizontalNumbPanel.add(Box.createRigidArea(new Dimension(95, 0)));
        }
        return horizontalNumbPanel;
    }

    public JPanel getMainPanel() throws RemoteException {
        return mainPanel;
    }
}
