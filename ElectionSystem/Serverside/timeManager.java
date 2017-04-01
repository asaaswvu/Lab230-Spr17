import java.util.Date;

public class timeManager extends Thread{
	Server server;
	Date currTime;
	final int UPDATE_INTERVAL_SECONDS = 5;

	timeManager(Server serv) {
		this.server = serv;
	}
	public void run() {
		while(true){
			currTime = new Date();
			server.logToGUI("ACTIVE: "+server.activeElections.toString());
			server.logToGUI("COMPLETE: "+server.completeElections.toString());
			for(Election e :  server.elections.values()){
				if(e.getStatus() != Election.status.ACTIVE && currTime.after(e.startDate) && currTime.before(e.endDate)){
					server.logToGUI("OPENING POLLS @ " + e.getElectionName());
					server.changeElectionActive(e.getElectionName());
					e.openPoll();
				}
				else if(currTime.after(e.endDate) && e.getStatus() != Election.status.COMPLETE){
					server.logToGUI("Closing POLLS @ " + e.getElectionName());
					server.changeElectionComplete(e.getElectionName());
					e.closePoll();
				}
				if(!server.completeElections.contains(e.getElectionName()) && e.getStatus() == Election.status.COMPLETE){
					server.changeElectionComplete(e.getElectionName());
				}
			}
			try {
				Thread.sleep(UPDATE_INTERVAL_SECONDS * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
