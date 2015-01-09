package name.duzenko.farfaraway.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class DGLSurfaceView extends GLSurfaceView {
	
	private static float TOUCH_SCALE_FACTOR = 0.01f;

	public DRenderer renderer;

	private float mPreviousX;

	private float mPreviousY;

    public DGLSurfaceView(Context context) {
		super(context);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(new MultisampleConfigChooser());
        setRenderer(renderer = new DRenderer());
        //setRenderMode(RENDERMODE_WHEN_DIRTY);
        //((DRenderer) renderer).mConfigChooser = mConfigChooser;
        TOUCH_SCALE_FACTOR = 1f/context.getResources().getDisplayMetrics().xdpi;
	}
    
    @Override
    public void onResume() {
        renderer.simulator.start();
    	super.onResume();
    }
    
    @Override
    public void onPause() {
        renderer.simulator.stop();
    	super.onPause();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                renderer.viewerX += (dx) * TOUCH_SCALE_FACTOR;  // = 180.0f / 320
                renderer.viewerZ += (dy) * TOUCH_SCALE_FACTOR;  // = 180.0f / 320
                renderer.viewerMoved = true;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}