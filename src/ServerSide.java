

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    public static void main(String[]args){
    	try(ServerSocket serverSocket=new ServerSocket(5000)){
            System.out.println("Waiting..");
            while(true){
                Socket socket=serverSocket.accept();
                BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Client Connected!");
                String userName=br.readLine();
                Container.addClient(userName,socket);
                Container.printListOfClients();
                Thread thread=new Thread(new NewMessagesThread(userName));
                thread.start();
                try{
                    thread.join();
                }catch(InterruptedException e){}
                ServerSideInputThread inputThread=new ServerSideInputThread(socket);
                Thread thread1=new Thread(inputThread);
                thread1.start();
                    //now the input stream of client 1 must be the output stream of client 2
                    //first make a thread of input and output of thread which will take the input and give the output.
            }
        }catch(IOException e){
            System.out.println("IOException Caught in server!"+e.getMessage());
        }finally{
            System.out.println("Entered in finally of ServerCode");
        }
    }
}
