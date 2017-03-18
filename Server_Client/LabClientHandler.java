import java.io.*;
import java.net.*;


class LabClientHandler extends Thread{

    BufferedReader brIn;
    PrintWriter pwOut;
    LabServer server;
    Socket socket;

    LabClientHandler(Socket sock, LabServer serv){
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
                String [] data = line.split(",");
		System.out.println("@ START SERVER" + data[0].toString());
                switch (data[0]){
                    case "<send>":
                        addValue(data);
                    break;
                    case "<die>" :
                        die();
                    default:
                        pwOut.println("<error here>");
                }
            }
        }catch(SocketException x){
            System.out.println("socket disconnected");
        }catch(Exception e){
            System.out.print("Error: " + line);
        }
    }
    private void addValue(String [] data){
      System.out.println(data.toString());
	    for(int i=1; i<data.length; i++){
        System.out.println("Client -> Server : Sent " + data[i]);
		    if(server.addValue(data[i])){
			//pwOut.println("<val>"+data[i]);
		    }else{
			pwOut.println("Double Comma OR no value b/w commas" + data[i].toString());
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
