package name.duzenko.farfaraway.simulation;

public class Universe {
	
	public static float g = 1f, starRadius = 1f/60, starMass = starRadius*starRadius*starRadius;
	public static int starsCount;
	public Star []stars = new Star[starsCount];
	public float positions[] = new float[3*starsCount];
	public boolean colorsReady;
	
	public Universe() {
		for (int i = 0; i < stars.length; i++) 
			stars[i] = new Star();
	}
	
	public void init() {
		System.out.println("init() begin");
		for (int i = 0; i < stars.length; i++) 
			stars[i].init(1f*i/stars.length);
		colorsReady = true;
		Vector3 grav = new Vector3();
		for (int i = 0; i < stars.length; i++) {
			Star s = stars[i];
			grav.copyFrom(s.position);
			grav.MultiplyScalar(starMass*g*starsCount);
			Vector3 v = grav, cn = new Vector3(new Vector3(), s.position);
			cn.Normalize();
			v = cn.MultiplyScalar(Math.abs(v.DotProduct(cn)));
			float f = v.Normalize();///sqr(abs(center.y)+1);
			v = v.CrossProduct(Star.up);
			s.speed = v.MultiplyScalar((float) Math.sqrt(s.position.length()*f));
			
		}
		System.out.println("init() end");		
	}
	
	void calcGravity(float delta) {
		for (int i = 0; i < stars.length; i++) {
			stars[i].accelerate(delta, gravityAt(stars[i]));
		}
	}
	
	void iterate(float delta) {
        calcGravity(delta);
        for(int i=0; i<stars.length; i++) {
        	//stars[i].speed -= 0.001f*Math.signum(stars[i])/(1+stars[i]*stars[i]);
           	stars[i].position.madd(stars[i].speed, delta);				
        }
	}
	
	float []computerData = new float[4*starsCount];
	void generateComputerInput() {
		for(int i=0; i<stars.length; i++) {
			computerData[i*4] = stars[i].position.x;
			computerData[i*4+1] = stars[i].position.y;
			computerData[i*4+2] = stars[i].position.z;
		}
		//System.out.println("compdata " + computerData[0] + " " + computerData[1] + " " + computerData[2]);
	}
	
	Vector3 acc = new Vector3();
	void parseComputerOutput(float delta) {
		for(int i=0; i<stars.length; i++) {
			acc.x = computerData[i*4];
			acc.y = computerData[i*4+1];
			acc.z = computerData[i*4+2];
			stars[i].speed.add(acc);
		}		
        for(int i=0; i<stars.length; i++) {
        	Star s = stars[i];
        	s.position.madd(stars[i].speed, delta);
			positions[3*i] = s.position.x;
			positions[3*i+1] = s.position.y;
			positions[3*i+2] = s.position.z;
        }
	}
	
	Vector3 gravityAt1 = new Vector3(), gravityAt2 = new Vector3();
	Vector3 gravityAt(Star point) {
		gravityAt1.x = gravityAt1.y = gravityAt1.z = 0;
		for (int i = 0; i < stars.length; i++) {
			stars[i].getGravityAt(point, gravityAt2);
			gravityAt1.add(gravityAt2);
		}
		return gravityAt1;
	}

}
