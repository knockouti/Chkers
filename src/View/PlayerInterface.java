package View;
import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Created by Igor on 12.10.2016.
 */
public interface PlayerInterface extends Remote  {
    public void updateBOard() throws RemoteException;
    public void setListenerForCells() throws RemoteException;
    public char getXNowAdress() throws RemoteException;
    public int getYNowAdress() throws RemoteException;
    public char getXOldAdress()throws RemoteException;
    public int getYOldAdress() throws RemoteException;
    public void setChekersView(char xAdress, int yAdress, char xOldAdress, int yOldAdress,char x, int y) throws RemoteException;
    public void setPositionButtons() throws RemoteException;
    public char getCellView1X()throws RemoteException;
    public int getCellView1Y() throws RemoteException;
    public void setChekers() throws RemoteException ;
    public  void getButtons() throws RemoteException;
    public JPanel getVerticalNubmPanel() throws RemoteException;
    public JPanel getHorizontalNumbPanel()throws RemoteException;
    public JPanel getMainPanel()throws RemoteException;
}
