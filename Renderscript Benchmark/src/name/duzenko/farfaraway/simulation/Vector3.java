package name.duzenko.farfaraway.simulation;

public class Vector3 {
	public float x, y, z;
	
	public Vector3() {
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 a, Vector3 b) {
		x = a.x - b.x;
		y = a.y - b.y;
		z = a.z - b.z;
	}
	
	public Vector3(Vector3 a, float b) {
		this(a.x, a.y, a.z);
		MultiplyScalar(b);
	}
	
	public void copyFrom(Vector3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	void add(Vector3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	void madd(Vector3 v, float m) {
		x += v.x*m;
		y += v.y*m;
		z += v.z*m;
	}
	
	float length() {
		return (float) Math.sqrt(x*x+y*y+z*z);
	}
	
	float length2() {
		return (float) x*x+y*y+z*z;
	}
	
	float Normalize() {
		float Result = length();
		if (Result!=0) 
			MultiplyScalar(1/Result);
		return Result;
	}
	
	Vector3 MultiplyScalar(float d) {
		x *= d;
		y *= d;
		z *= d;
		return this;
	}
	
	float DotProduct(Vector3 v) {
		return x*v.x + y*v.y + z*v.z;
	}
	
	Vector3 CrossProduct(Vector3 AVector3D) {
		Vector3 Result = new Vector3(); 
		Result.x = y * AVector3D.z - z * AVector3D.y;
		Result.y = z * AVector3D.x - x * AVector3D.z;
		Result.z = x * AVector3D.y - y * AVector3D.x;
		return Result;
	}
	
}
