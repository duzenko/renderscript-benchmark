package name.duzenko.farfaraway;

import java.util.Timer;
import java.util.TimerTask;

import name.duzenko.farfaraway.renderer.DGLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    DGLSurfaceView glView;
    TextView fps, ips;
    Timer timer;
    char preset;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	System.out.println("MainActivity onCreate");
		Static.applicationContext = getApplicationContext();
        super.onCreate(savedInstanceState);
        loadLayout();
    }


    @Override
    protected void onResume() {
    	System.out.println("MainActivity onResume");
        super.onResume();
        if(glView!=null)
        	glView.onResume();
        TimerTask updateUI = new UpdateUITask();
        timer = new Timer();
        timer.scheduleAtFixedRate(updateUI, 0, 1000);
    }
     
    @Override
    protected void onPause() {
    	System.out.println("MainActivity onPause");
        timer.cancel();
        glView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
    	System.out.println("MainActivity onDestroy");
    	super.onDestroy();
    }
    
    class UpdateUITask extends TimerTask {

    	String sFps, sIps;
    	public void run() {
    		if(glView==null)
    			return;
    		sFps = String.valueOf(glView.renderer.getFps()) + " fps";
    		sIps = String.valueOf(glView.renderer.getIps()) + preset + " ips";
    		runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
		    		fps.setText(sFps);		
		    		ips.setText(sIps);
				}
				
			});
    	}
    }
    
	private RelativeLayout rl;

	void loadLayout() {
        preset = getIntent().getCharExtra("preset", '?');
        rl = new RelativeLayout(MainActivity.this);
        fps = new TextView(MainActivity.this);
        ips = new TextView(MainActivity.this);
        ips.setTextSize(24);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.topMargin = 30;
        ips.setLayoutParams(lp);
        fps.setTextColor(Color.WHITE);
        ips.setTextColor(0xFFFFFF99);
        ips.setText("Loading...");
        glView = new DGLSurfaceView(MainActivity.this);
        rl.addView(glView);        
        rl.addView(fps);
        rl.addView(ips);
        setContentView(rl);
    }
       
}