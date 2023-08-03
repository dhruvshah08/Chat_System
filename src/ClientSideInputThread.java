

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.DefaultListModel;

public class ClientSideInputThread implements Runnable{
	Socket socket;
	BufferedReader br;
	PrintWriter pw;
	public ClientSideInputThread(Socket socket){
            this.socket=socket;
	}
	@Override
	public void run(){
            try{
                br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true){
                    String messageReceivedAtClient=br.readLine();
                    if(messageReceivedAtClient!=null){
                        System.out.println("Message received at client side: "+messageReceivedAtClient);
                        ClientSide.addMsgIntoList(messageReceivedAtClient);
                        String arr[]=messageReceivedAtClient.split("/");
                        String sender=arr[0];
                        String message=arr[1];
                        String receiver=arr[2];
                        FileOperations.addMessage(sender, message, receiver);
                    }
                }
            }catch(IOException e){}
	}
}