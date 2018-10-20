package shapes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import utils.Color;
import utils.MyMath;
import utils.Rotation;
import utils.Vector;

import com.jogamp.opengl.util.texture.*;

public class Planet extends QuadricObject{
	
	public float radius;
	public float orbitDistance;
	public float orbitTime; // in seconds
	public Color color;
	
	public static final int SLICES = 60;
	public static final int STACKS = 60;
	
	private float currentAngle = 180f;
	private float t = 0f;
	
	public boolean isMoon;
	
	protected String textureLocation;
	protected Texture texture;
	protected boolean hasTexture;
	protected Color textureColor;
	
	protected Rotation startRotation;
	protected Rotation rotation;
	protected float rotationDuration;
	private float t2 = 0f;
	
	public static float degrees;
	public static Axis a = Axis.X;
	
	// This was used for testing the rotation to try to align the earth
	enum Axis {
		X(new Vector(0.75f,0f,0.25f)), Y(new Vector(0f,1f,0f)), Z(new Vector(0f,0f,1f)) {
			@Override
			public Axis next() {
				return Axis.values()[0];
			}
		};
		
		public final Vector v;
		
		Axis(Vector v) {
			this.v = v;
		}
		
		public Vector get() {
			return v;
		}
		
		public Axis next() {
			return Axis.values()[(this.ordinal() +1)];
		}
	}


	public Planet(GL2 gl, GLU glu, float radius, float orbitDistance, float orbitTime, Color color, boolean isMoon, String textureLocation, Color textureColor, Rotation startRotation, Rotation rotation, float rotationDuration) {
		super(gl, glu);
		this.radius = radius;
		this.orbitDistance = orbitDistance;
		this.orbitTime = orbitTime;
		this.color = color;
		this.isMoon = isMoon;
		this.textureLocation = textureLocation;
		this.textureColor = textureColor;
		this.startRotation = startRotation;
		this.rotation = rotation;
		this.rotationDuration = rotationDuration;
		
		init();
	}
	
	public Planet(GL2 gl, GLU glu) {
		super(gl, glu);
	}
	
	protected void init() {
		try {
			texture = TextureIO.newTexture(new File(textureLocation), true);
			hasTexture = true;
		} catch (IOException e){
			hasTexture = false;
		}
	}

	@Override
	public void draw() {
		if (hasTexture) {
			texture.enable(gl);
			texture.bind(gl);
		} else {
			gl.glColor4f(color.r, color.g, color.b, color.a);
		}

	
		gl.glColor4f(textureColor.r, textureColor.g, textureColor.b, textureColor.a);
		glu.gluQuadricTexture(quad, true);
		glu.gluSphere(quad, (double)radius, SLICES, STACKS);
		
		
		if (hasTexture) {
			texture.disable(gl);
		}
	}

	@Override
	public void update(float deltaTime) {
		orbit(deltaTime);
	}
	
	public void rotate(float deltaTime) {
		rotation.angle = MyMath.lerp(Rotation.MIN_ANGLE, Rotation.MAX_ANGLE, t2);
		t2 += deltaTime / rotationDuration;
		t2 = (t2 > 1f) ? 0f : t2;
		
		gl.glRotatef(startRotation.angle, startRotation.x, startRotation.y, startRotation.z);

		gl.glRotatef(rotation.angle, rotation.x, rotation.y, rotation.z);
	}
	
	private void orbit(float deltaTime) {
		
		currentAngle = MyMath.lerp(0f, 360f, t);
		t += deltaTime / orbitTime;
		t = (t > 1f) ? 0f : t;
		
		currentAngle = (currentAngle > 360f) ? 0f : currentAngle;
		
		float radians = (float) Math.toRadians(currentAngle);
		
		float x = (float) (Math.cos(radians) * orbitDistance);
		float z = (float) (Math.sin(radians) * orbitDistance);
		
		this.position.x = x;
		this.position.z = z;
	}
	
	public static void addDegrees(float value) {
		degrees+=value;
		degrees = (degrees > 360f)?0f : degrees;
		degrees = (degrees < 0f) ? 360f:degrees;
		print();
		
	}
	
	public static void toggleAxis() {
		a = a.next();
		print();
	}
	
	public static void print() {
		System.out.println("Degrees: "+degrees + " | Axis Vector: " + a.get().toString());
	}
	
	
}
