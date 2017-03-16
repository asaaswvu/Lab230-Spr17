import java.io.*;
import java.net.*;


class BrowserInfo extends Thread{
    
    
    
    
    
    public void run(){
        try{
            ServerSocket ss = new ServerSocket(80);
            
            Socket aSocket = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            
            while (br.ready()){
                
                System.out.println(br.readLine());    
                
            }
            
            
        
        }catch(IOException e){
            System.out.println("IOException thrown");
        }
    }
    
    
    public static void main(String args[]){
        
        new BrowserInfo().start();
        
    }
    
}