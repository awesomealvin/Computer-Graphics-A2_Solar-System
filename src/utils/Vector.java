package utils;

public class Vector {
	public float x;
	public float y;
	public float z;

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector() {
		x = 0f;
		y = 0f;
		z = 0f;
	}
	
	public Vector(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}

	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
	}

	public void add(Vector other, float deltaTime) {
		this.x += (other.x * deltaTime);
		this.y += (other.y * deltaTime);
		this.z += (other.z * deltaTime);
	}

	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public Vector multiplied(float value) {
		float x = this.x * value;
		float y = this.y * value;
		float z = this.z * value;
		return new Vector(x, y, z);
	}
	
	public void multiply(float value) {
		this.x *= value;
		this.y *= value;
		this.z *= value;
	}
	
	public static Vector multiply(Vector first, Vector second) {
		float x = first.x * second.x;
		float y = first.y * second.y;
		float z = first.z * second.z;
		
		return new Vector(x, y, z);
	}

	public static Vector subtract(Vector first, Vector second) {
		float x = first.x - second.x;
		float y = first.y - second.y;
		float z = first.z - second.z;

		return new Vector(x, y, z);
	}

	public float magnitude() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}

	public Vector normalized() {
		float x = this.x / magnitude();
		float y = this.y / magnitude();
		float z = this.z / magnitude();

		return new Vector(x, y, z);
	}
	
	public static Vector crossProduct(Vector a, Vector b) {
		float x = (a.y * b.z) - (a.z * b.y);
		float y = (a.z * b.x) - (a.x * b.z);
		float z = (a.x * b.y) - (a.y * b.x);
		
		return new Vector(x, y, z);
	}
}
