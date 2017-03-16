import java.net.*;
import java.util.ArrayList;
import java.io.IOException;

class LabServer extends Thread{

    ArrayList<String> values;
    ServerSocket ss;

    LabServer(){
        values = new ArrayList<String>();   
    }

    public void run(){
        try{
        ss = new ServerSocket(50000);   //high port numbers aren't normally dedicated
        System.out.println("Server Started");
        while(true){
            Socket client = ss.accept();
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
        if(value.equalsIgnoreCase("quit")){
            return false;
        }else{
	    values.add(value);
            return true;
        }
    }

    public static void main(String args[]){
        new LabServer().start();
    }

}
