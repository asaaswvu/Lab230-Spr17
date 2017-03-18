import java.net.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.*;


class LabServer extends Thread{

    ArrayList<String> values;
    ServerSocket ss;
    Socket client;

    LabServer(){
        values = new ArrayList<String>();
    }

    public void run(){
        try{
        ss = new ServerSocket(50000);   //high port numbers aren't normally dedicated
        System.out.println("Server Started");
        while(true){
            client = ss.accept();
            new LabClientHandler(client,this).start();
            System.out.println("Started ClientHandler");
        }
        }catch(SocketException f){
            System.out.println("null");

        }catch(IOException e){
            System.out.print("Server IOException");
            e.printStackTrace();
        }
    }

    public void die(){
        try{
            ss.close();
        }catch(IOException e){
            //don't care, shutting down
        }
        System.exit(0);
    }
    public boolean addValue(String value){
      value = value.trim();
      System.out.println("Server Received: " + value);
        if(value.equalsIgnoreCase("")){
             return false;
         }
         else{
	    values.add(value);
      sendValues(value);
            return true;
       }
    }

    public void sendValues(String value){
      System.out.println("Server -> Client : Sent " + value +"\n(CheckClientLog)\n");
      try{
      PrintWriter pwOut = new PrintWriter(client.getOutputStream(),true);
        if(value.equals("<EOL>")){
          pwOut.println("<EOL>");
        }
        else{
         pwOut.println("<val>"+value);
       }
     }
   catch(IOException e){
       System.out.print("Server IOException");
       e.printStackTrace();
   }
    }

    public static void main(String args[]){
        new LabServer().start();
    }
}
