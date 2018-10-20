package shapes;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import utils.Vector;

public class Orbit extends MovableObject {

	private float orbitSmoothness = 360f;
	private Vector[] orbitPoints;

	private final float ORBIT_RADIUS;

	public static ViewMode viewMode = ViewMode.ALL;

	private enum ViewMode {
		OFF, ALL, PLANETS {
			// Overrides so the last option returns the first value
			@Override
			public ViewMode next() {
				return values()[0];
			}
		};

		public ViewMode next() {
			return values()[this.ordinal() + 1];
		}
	}

	private boolean isMoon;

	public Orbit(GL2 gl, float orbitRadius, boolean isMoon) {
		super(gl);
		this.ORBIT_RADIUS = orbitRadius;
		this.isMoon = isMoon;
		setupOrbit();
	}

	private void setupOrbit() {
		ArrayList<Vector> orbitPoints = new ArrayList<Vector>();

		float angleDifference = 360f / orbitSmoothness;
		float currentAngle = 0f;
		for (int i = 0; i <= orbitSmoothness; i++) {
			float radians = (float) Math.toRadians(currentAngle);
			float x = (float) (ORBIT_RADIUS * Math.cos(radians));
			float y = 0f;
			float z = (float) (ORBIT_RADIUS * Math.sin(radians));

			Vector point = new Vector(x, y, z);
			orbitPoints.add(point);
			currentAngle += angleDifference;
		}

		this.orbitPoints = new Vector[orbitPoints.size()];

		for (int i = 0; i < this.orbitPoints.length; i++) {
			this.orbitPoints[i] = orbitPoints.get(i);
		}
	}

	@Override
	public void draw() {
		if ((viewMode == ViewMode.ALL) || (viewMode == ViewMode.PLANETS && !isMoon)) {
			// Draw Orbit
			gl.glEnable(GL2.GL_BLEND);
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

			gl.glPointSize(20f);
			gl.glColor4f(1f, 1f, 1f, 0.25f);

			gl.glBegin(GL2.GL_LINE_LOOP);
			for (Vector v : orbitPoints) {
				gl.glVertex3f(v.x, v.y, v.z);
			}
			gl.glEnd();

			gl.glDisable(GL2.GL_BLEND);
		}
	}

	public static void toggle() {
		viewMode = viewMode.next();
	}

	@Override
	public void update(float deltaTime) {
		// nope
	}

}
