package name.duzenko.farfaraway.renderer;

import java.io.IOException;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import name.duzenko.farfaraway.simulation.Simulator;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

public class DRenderer implements GLSurfaceView.Renderer {

	float viewerX=0, viewerY=0.5f, viewerZ=-2.3f;
	boolean viewerMoved=true;
    Simulator simulator = new Simulator();

	//MultisampleConfigChooser mConfigChooser;
	UniverseRenderer universeRenderer;
	long frames, lastFpsTick = SystemClock.uptimeMillis();
	float []mProjectionMatrix = new float[16], mViewMatrix = new float[16], mMVPMatrix = new float[16];
	
	@Override
	public void onDrawFrame(GL10 gl) {
		frames++;
		if(viewerMoved)
			adjustViewer();
        int clearMask = GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT;
        GLES20.glClear(clearMask);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        universeRenderer.draw(mMVPMatrix);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		System.out.println("onSurfaceChanged");
        GLES20.glViewport(0, 0, width, height);
        float closest = 0.1f;
        Matrix.frustumM(mProjectionMatrix, 0, -closest, closest, -closest*height/width, closest*height/width, closest, 20);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		System.out.println("onSurfaceCreated");
		try {
			universeRenderer = new UniverseRenderer(simulator.universe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		GLES20.glClearColor(0, 0, 0, 1.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable (GLES20.GL_BLEND);
		GLES20.glBlendFunc (GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	void adjustViewer() {
		System.out.println("adjust viewer");
		viewerMoved = false;
        Matrix.setLookAtM(mViewMatrix, 0, viewerX, viewerY, viewerZ, viewerX, 0, viewerZ+2, 0, 1, 0);
	}
	
	public long getFps() {
		long tick = SystemClock.uptimeMillis();
		if(tick==lastFpsTick)
			return -1;
		long fps = frames * 1000 / (tick - lastFpsTick);
		frames = 0;
		lastFpsTick = tick;
		return fps;
	}

	public long getIps() {
		if(simulator==null)
			return -1;
		else
			return simulator.getIps();
	}
}
