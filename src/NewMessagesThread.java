
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dhruv
 */
public class NewMessagesThread implements Runnable{
    String receiver;
    PrintWriter pw;
    public NewMessagesThread(String receiver){
        this.receiver=receiver;
    }
    @Override
    public void run(){
        List<String> listOfMessages=Database_Operations.getMessagesWhenOffline(receiver);
        for(String formattedMessage:listOfMessages){
            String arr[]=formattedMessage.split(" - ");
            String sender=arr[0];
            String message=arr[1];
            Socket socket=Container.getSocketByName(receiver);
            try{
                pw=new PrintWriter(socket.getOutputStream(),true);
                if(socket!=null){
                    pw.println(sender+"/"+message+"/"+receiver);
                }
            }catch(IOException e){}
        }
    }
}
