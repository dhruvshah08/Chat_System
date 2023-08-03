

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSideInputThread implements Runnable{
    BufferedReader br;
    PrintWriter pw;
    Socket socket;
    public ServerSideInputThread(Socket socket){
            this.socket=socket;
    }
    @Override
    public void run(){
        try{
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(socket.getOutputStream(),true);
            while(true){
                String messageReceivedAtServer=br.readLine();//got the message at the server side from the user!
                if(messageReceivedAtServer!=null){
                    System.out.println("Message Received at server :"+messageReceivedAtServer);
                    //now send it to the receiver by transferring the message!
                    String arr[]=messageReceivedAtServer.split("/");
                    String sender=arr[0];
                    String message=arr[1];
                    String receiver=arr[2];
                    System.out.println(sender+" has sent "+ message+" to "+receiver);
                    Socket receiverSocket=Container.getSocketByName(receiver);
                    if(receiverSocket == null){
                        Database_Operations.addMessage(sender, message, receiver);
                        continue;
                    }
                    pw=new PrintWriter(receiverSocket.getOutputStream(),true);
                    pw.println(sender+"/"+message+"/"+receiver);//here message has been transferred!
                }
            }
        }catch(IOException e){}
    }
}
