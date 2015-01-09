package name.duzenko.farfaraway.simulation;

import name.duzenko.farfaraway.Static;
import name.duzenko.farfaraway.renderscript.Computer;
import android.os.SystemClock;

public class SimulatorThread extends Thread {
	
	Universe universe;
    Computer computer;
	boolean stopRequested;
	long iterations, lastFpsTick = SystemClock.uptimeMillis();
	
	@Override
	public void run() {
		System.out.println("SimulatorThread started");
		try {
			sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		universe.init();
		long lastDeltaTick = SystemClock.uptimeMillis(), zeroClocks = 0, nonzeroClocks = 0;
        computer = new Computer(universe);
		computer.createScript(Static.applicationContext);
		while(!stopRequested) {
	        long tick = SystemClock.uptimeMillis();
	        float delta = Math.min(40, tick - lastDeltaTick) / 1000f;
	        if(delta==0) 
	        	zeroClocks++;
	        else
	        	nonzeroClocks++;
	        lastDeltaTick = tick;
			//universe.iterate(delta);
			universe.generateComputerInput();
			computer.runScript(universe.computerData, delta);
			universe.parseComputerOutput(delta);
			iterations++; 
			if(iterations==-10)
				break;
		}
		System.out.println("SimulatorThread stopped " + zeroClocks + " " + nonzeroClocks);
	}

	public long getIps() {
		long tick = SystemClock.uptimeMillis();
		if(tick==lastFpsTick)
			return -1;
		long fps = iterations * 1000 / (tick - lastFpsTick);
		iterations = 0;
		lastFpsTick = tick;
		return fps;
	}

}
