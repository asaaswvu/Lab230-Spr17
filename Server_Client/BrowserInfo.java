import java.io.*;
import java.net.*;

class BrowserInfo extends Thread{
    public void run(){
        try{
            System.out.println("Start @ Try");
            ServerSocket ss = new ServerSocket(1025);
            System.out.println("Socket Made");
            Socket aSocket = ss.accept();
            System.out.println("ASocket");
            BufferedReader br = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
	    System.out.println("BuffReader Made");
            while (br.ready()){
                System.out.println(br.readLine());      
            }            
        
        }
	catch(IOException e){
            System.out.println("IOException thrown\n" + e.getMessage());
        }
    }
    
    public static void main(String args[]){
        new BrowserInfo().start();
    }
    
}
