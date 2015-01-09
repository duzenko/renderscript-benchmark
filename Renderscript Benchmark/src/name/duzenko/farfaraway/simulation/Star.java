package name.duzenko.farfaraway.simulation;

import java.util.Random;

public class Star {
	
	static Vector3 up = new Vector3(0, 1, 0);
	public Vector3 position = new Vector3(), speed = new Vector3(), color = new Vector3();
	
    static Random random = new Random();
    
	void init(float p) {
	    double a = p*2*Math.PI; //Random()*2*Pi;
	    float a2 = 0;//-sqr(sqr(sqr(random*0.96)))*Pi/2*sign(random-0.5);
	    float d = 1;//random*0.0+sqr(random*2);
	    position.x = (float) (d*Math.sin(a)*Math.cos(a2));
	    //center.y := (random-0.5)/10;//d*sin(a2);
	    position.z = (float) (d*Math.cos(a)*Math.cos(a2));
		color.x = random.nextFloat()*0.5f + 0.5f;
		color.y = random.nextFloat()*0.5f + 0.5f;
		color.z = random.nextFloat()*0.5f + 0.5f;
	}

	void getGravityAt(Star point, Vector3 Result) {
		Result.x = position.x - point.position.x;
		Result.y = position.y - point.position.y;
		Result.z = position.z - point.position.z;
		float d = Result.Normalize();
		if (d < (2*Universe.starRadius))        
			d = 2*Universe.starRadius;
		Result.MultiplyScalar(Universe.g*Universe.starMass/(d*d));
	}
	
	Vector3 tmp = new Vector3();
	void accelerate(float deltaT, Vector3 acc) {
		  if (deltaT == 0)
		    return;
		  tmp.x = acc.x * deltaT;
		  tmp.y = acc.y * deltaT;
		  tmp.z = acc.z * deltaT;
		  speed.add(tmp);
	}

}
