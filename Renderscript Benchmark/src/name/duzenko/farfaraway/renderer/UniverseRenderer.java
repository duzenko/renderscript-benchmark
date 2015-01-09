package name.duzenko.farfaraway.renderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import name.duzenko.farfaraway.Static;
import name.duzenko.farfaraway.simulation.Universe;

import android.opengl.GLES20;

public class UniverseRenderer {
	
	Universe universe;

	private int mProgram;

    private FloatBuffer vertexBuffer, colorBuffer;
    
	public static String readRawTextFile(String resId) throws IOException
    {
		InputStream inputStream = Static.applicationContext.getAssets().open(resId);

		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;
		StringBuilder text = new StringBuilder();

		try {
			while ((line = buffreader.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			return null;
		}
		return text.toString();
    }
	
	public static int loadShader(int type, String shaderCode){

		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}
	
	public UniverseRenderer(Universe universe) throws IOException {
		System.out.println("UniverseRenderer start");
		this.universe = universe;
	    int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, readRawTextFile("shaders/star.vs"));
	    int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, readRawTextFile("shaders/star.fs"));

	    mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
	    GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
	    GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
	    GLES20.glLinkProgram(mProgram);                  // creates OpenGL ES program executables
	    System.out.println(mProgram + " " + vertexShader + " " + fragmentShader);
	    
        ByteBuffer bb = ByteBuffer.allocateDirect(Universe.starsCount * 3 * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        
        bb = ByteBuffer.allocateDirect(Universe.starsCount * 3 * 4);
        bb.order(ByteOrder.nativeOrder());
        colorBuffer = bb.asFloatBuffer();

		GLES20.glUseProgram(mProgram);
	    int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
	    GLES20.glEnableVertexAttribArray(mPositionHandle);
	    GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

	    int mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
	    GLES20.glEnableVertexAttribArray(mColorHandle);
	    GLES20.glVertexAttribPointer(mColorHandle, 3, GLES20.GL_FLOAT, false, 0, colorBuffer);

		System.out.println("UniverseRenderer finish");
	}
	
	boolean colorSet;
	
	public void draw(float []matrix) {
		int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); 
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);
		
		vertexBuffer.put(universe.positions);
		vertexBuffer.position(0);
		if(universe.colorsReady && !colorSet) {
			for (int i = 0; i < Universe.starsCount; i++) {
				colorBuffer.put(universe.stars[i].color.x);			
				colorBuffer.put(universe.stars[i].color.y);			
				colorBuffer.put(universe.stars[i].color.z);			
			}
			colorBuffer.position(0);
			colorSet = true;
		}

	    // Draw the triangle
	    GLES20.glDrawArrays(GLES20.GL_POINTS, 0, universe.stars.length);
	}

}
