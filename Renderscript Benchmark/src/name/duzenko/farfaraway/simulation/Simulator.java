package name.duzenko.farfaraway.simulation;

public class Simulator {
	
	public Universe universe = new Universe();
	SimulatorThread thread;
	
	public Simulator() {
		start();
	}

	public void start() {
		if(thread!=null)
			return;
    	System.out.println("Simulator start");
		thread = new SimulatorThread();
		thread.universe = universe;
		thread.start();
	}

	public void stop() {
		if(thread==null)
			return;
		thread.stopRequested = true;
		thread = null;
	}
	
	public long getIps() {
		SimulatorThread thread = this.thread;
		if(thread==null)
			return -1;
		else {
			universe = thread.universe;
			return thread.getIps();
		}
	}
}