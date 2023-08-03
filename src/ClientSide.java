

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;

public class ClientSide extends Thread{
    private static String name;
    private static PrintWriter pw;
    private static List<String> listOfUnreadMessages;
    public ClientSide(String name){
        this.name=name;
        listOfUnreadMessages=new LinkedList<>();
        start();
    }
    public static void sendMessage(String message,String receiver){
        String formattedMessage=name+"/"+message+"/"+receiver;//getting the formatted string which would help to recognice the reciever at the server sde of the code!
        System.out.println("Message sent from sender: "+formattedMessage);
        pw.println(formattedMessage);
    } 
    public static void addMsgIntoList(String msg){
        listOfUnreadMessages.add(msg);
    }
    public static void removeMessage(String msg){
    }
    public static List<String> getListofUnreadMessages(String userName){
        List<String> listOfMessages=new ArrayList<>();
        for(int i=0;i<listOfUnreadMessages.size();i++){
            String formattedMessage=listOfUnreadMessages.get(i);//in format sender/message/receiver
            String arr[]=formattedMessage.split("/");
            String sender=arr[0];
            String message=arr[1];
            if(sender.equals(userName)){
                listOfMessages.add(message);
            }
            listOfUnreadMessages.remove(i);
            i--;
        }
        return listOfMessages;
    }
    @Override
    public void run(){
    	try(Socket socket=new Socket("localhost",5000)){
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(socket.getOutputStream(),true);
            pw.println(name);
            //got the name of the server!
            ClientSideInputThread inputThread=new ClientSideInputThread(socket);
            Thread thread=new Thread(inputThread);
            thread.start();
            while(true){
                //waiting indefinately!
            }
        }catch(IOException e){
	    	System.out.println("IOException Caught in client!"+e.getMessage()); 
        }finally{
            System.out.println("Entered in finally of ClientCode");
        }	
    }
}
