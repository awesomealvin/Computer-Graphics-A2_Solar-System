package utils;

public class Rotation {
	public final static float MAX_ANGLE = 360f;
	public final static float MIN_ANGLE = 0f;
	
	public float angle;
	public float x;
	public float y;
	public float z;
	
	public Rotation(float angle, float x, float y, float z) {
		this.angle = angle;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Rotation() {
		this.angle = 0f;
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
	}
	
	/*
	public void addAngle(float value) {
		this.angle += value;
		
		if (angle > 360f) {
			float excess = angle - 360f;
			angle = 0f;
			addAngle(excess);
		}
		
	}
	*/
}
