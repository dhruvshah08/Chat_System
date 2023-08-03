
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dhruv
 */
public class FileOperations {
    static File f;
    static PrintStream pw;
    static {
        f=new File(UserWindow.username+".txt");
        try{
            pw=new PrintStream(new FileOutputStream(f,true));
        }catch(FileNotFoundException e){}   
    }
    public static void createNewFile(){
        pw.println("<user>"+UserWindow.username+"</user>");
    }
    public static void addMessage(String sender,String message,String receiver){
        pw.println("<"+sender+">"+message+"</"+receiver+">");
    }
    public static List<String> fetchMessages(String username){
        String entireContent=fileRead().trim();
        String h2TextGroup="<(\\w+)>(.*)</(\\w+)>";
    	Pattern h2TextPattern=Pattern.compile(h2TextGroup);
    	Matcher htTextMatcher=h2TextPattern.matcher(entireContent);
    	List<String> listOfPrevMsgs=new ArrayList<>();
        while(htTextMatcher.find())
    	{
            String sender=htTextMatcher.group(1);
            String message=htTextMatcher.group(2);
            String receiver=htTextMatcher.group(3);
            if(!"user".equals(sender)){
                //got into the details 
                if(username.equals(sender) || username.equals(receiver)){
                    listOfPrevMsgs.add(sender+"/"+message);
                }
            }
    	}
        System.out.println("Sending: "+listOfPrevMsgs.size()+" messages");
        return listOfPrevMsgs;
        
    } 
    private static String fileRead(){
        String text="";
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line="";
            while((line=br.readLine())!=null){
//                System.out.println(line);
                text+=line+"\n";
            }
            br.close();
        }
        catch(FileNotFoundException e){}
        catch(Exception e){}
        return text;
        //make an object in void main and call the above function using it..		
    }
}
