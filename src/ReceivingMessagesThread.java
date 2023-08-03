
import java.util.List;
import javax.swing.DefaultListModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dhruv
 */
public class ReceivingMessagesThread implements Runnable{
    DefaultListModel dm1;
    String receiver;
    public ReceivingMessagesThread(DefaultListModel dm1,String receiver){
        this.dm1=dm1;
        this.receiver=receiver;
    }
    @Override
    public void run(){
        System.out.println("Here!");
        while(ChatWindow.isChatWindowOpen){
           List<String>listOfMsg=ClientSide.getListofUnreadMessages(receiver);
            for(String message:listOfMsg){
                System.out.println("Message rec: "+message);
                dm1.addElement(message);
            }
        }
        System.out.println("Closed!");
    }
}
