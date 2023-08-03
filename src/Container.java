

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Container {
    private static ConcurrentHashMap<String,Socket> mapOfClients;
    static{
    	mapOfClients=new ConcurrentHashMap<>();
    }
    public static void addClient(String name,Socket socket){
    	mapOfClients.put(name,socket);
    }
    public static Socket getSocketByName(String name){
    	return mapOfClients.get(name);
    }
    public static void printListOfClients(){
    	System.out.println("printing list of clients....");
        for(String name:mapOfClients.keySet()){
    		System.out.print(name+" -> ");
    	}
    }
}

