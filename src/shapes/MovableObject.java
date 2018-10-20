package shapes;

import com.jogamp.opengl.GL2;

import utils.Vector;

public abstract class MovableObject {
	
	protected GL2 gl;
	public Vector position = new Vector(0f, 0f, 0f);

	public MovableObject(GL2 gl) {
		this.gl = gl;
	}
	
	public abstract void draw();
	public abstract void update(float deltaTime);
	
	public void translate(float x, float y, float z) {
		gl.glTranslatef(x, y, z);
	}

}
