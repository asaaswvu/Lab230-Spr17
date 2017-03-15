import java.io.*;
import java.net.*;


class ClientHandler extends Thread{

    BufferedReader brIn;
    PrintWriter pwOut;
    Server server;
    Socket socket;
    
    ClientHandler(Socket sock, Server serv){
        try{
            brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pwOut = new PrintWriter(sock.getOutputStream(),true);
            server = serv;
            socket = sock;
        }catch(Exception e){
            System.out.println("Error initiating ClientHandler");
        }
    }
    
    public void run(){
        String line = new String();
        try{
            while (true){
                line = brIn.readLine();
                System.out.println(line);
		if(line == null) return;
                String [] data = line.split(";");
                switch (data[0]){
		    case "<send>":
			sendData(data[1].trim().split(","));
		    break;
                    case "<die>" :
                        die();
                    default:
                        pwOut.println("<error>");
                }         
                
            }
        }catch(SocketException x){
            System.out.println("socket disconnected");
        }catch(Exception e){
            System.out.print("Error: " + line);
        } 
    }
    public void sendData(String [] data) throws InterruptedException{
	for(int i = 0; i < data.length; i++){
	    String cur = data[i].trim();
	    if( (cur!=null) && (cur.length() > 0) ){
		pwOut.println("<send>;"+cur);
		Thread.sleep(1000);
	    }
	}
    }
    private void die(){
        try{
            socket.close();
            server.die();
        }catch(Exception e){
            //shutting down
        }
        System.exit(0);
    }
}
