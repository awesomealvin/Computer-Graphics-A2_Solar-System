package viewer;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import shapes.*;
import utils.Camera;
import utils.Vector;

public class ISSViewer implements GLEventListener, KeyListener {

	private static int WIN_HEIGHT = 800;
	private static int WIN_WIDTH = 1200;
	private Camera camera;
	private GLUT glut;

	// lighting
	float globalAmbient[] = { 0.4f, 0.4f, 0.4f, 1 }; // global light properties
	public float[] lightPosition = { 0.0f, 0.0f, 0.0f, 1.0f };
	public float[] ambientLight = { 0.5f, 0.5f, 0.5f, 1 };
	public float[] diffuseLight = { 0.5f, 0.5f, 0.5f, 0.8f };

	private GLU glu;

	private double previousTime;

	Sun sun;

	Planet venus;
	Planet earth;
	Planet moon;
	Planet mars;
	Planet phobos;
	Planet deimos;
	
	Planet[] planets;
	Orbit[] orbits;

	@Override
	public void display(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();

		// clear the depth and color buffers
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		camera.draw(gl);

		lights(gl);

		double currentTime = System.currentTimeMillis() / 1000.0;
		float deltaTime = (float) (currentTime - previousTime);

		if (previousTime > 0.0) {
			// Update Stuff Here
			
			// SUN
			gl.glPushMatrix();
			{
				sun.update(deltaTime);
				gl.glTranslatef(sun.position.x, sun.position.y, sun.position.z);
				gl.glPushMatrix();
				{
					sun.rotate(deltaTime);
					sun.draw();
				}
				gl.glPopMatrix();

				// MERCURY
				orbits[Body.MERCURY.ordinal()].draw();
				Planet mercury = planets[Body.MERCURY.ordinal()];
				gl.glPushMatrix();
				{
					mercury.update(deltaTime);
					gl.glTranslatef(mercury.position.x, mercury.position.y, mercury.position.z);
					gl.glPushMatrix();
					{
						mercury.rotate(deltaTime);
						mercury.draw();
					}
					gl.glPopMatrix();
				}
				gl.glPopMatrix();

				// VENUS
				orbits[Body.VENUS.ordinal()].draw();
				Planet venus = planets[Body.VENUS.ordinal()];
				gl.glPushMatrix();
				{
					venus.update(deltaTime);
					gl.glTranslatef(venus.position.x, venus.position.y, venus.position.z);
					gl.glPushMatrix();
					{
						venus.rotate(deltaTime);
						venus.draw();
					}
					gl.glPopMatrix();
				}
				gl.glPopMatrix();

				// EARTH
				orbits[Body.EARTH.ordinal()].draw();
				Planet earth = planets[Body.EARTH.ordinal()];
				gl.glPushMatrix();
				{
					earth.update(deltaTime);
					gl.glTranslatef(earth.position.x, earth.position.y, earth.position.z);
					gl.glPushMatrix();
					{
						earth.rotate(deltaTime);
						earth.draw();
					}
					gl.glPopMatrix();
					
					orbits[Body.MOON.ordinal()].draw();
					Planet moon = planets[Body.MOON.ordinal()];
					moon.update(deltaTime);
					gl.glTranslatef(moon.position.x, moon.position.y, moon.position.z);
					gl.glPushMatrix();
					{
						moon.rotate(deltaTime);
						moon.draw();	
					}
					gl.glPopMatrix();
				}
				gl.glPopMatrix();

				// MARS
				orbits[Body.MARS.ordinal()].draw();
				Planet mars = planets[Body.MARS.ordinal()];
				gl.glPushMatrix();
				{
					mars.update(deltaTime);
					gl.glTranslatef(mars.position.x, mars.position.y, mars.position.z);
					gl.glPushMatrix();
					{
						mars.rotate(deltaTime);
						mars.draw();
					}
					gl.glPopMatrix();
					
					gl.glPushMatrix();
					{
						orbits[Body.PHOBOS.ordinal()].draw();
						Planet phobos = planets[Body.PHOBOS.ordinal()];
						phobos.update(deltaTime);
						gl.glTranslatef(phobos.position.x, phobos.position.y, phobos.position.z);
						gl.glPushMatrix();
						{
							phobos.rotate(deltaTime);
							phobos.draw();
						}
						gl.glPopMatrix();
					}
					gl.glPopMatrix();
					
					orbits[Body.DEIMOS.ordinal()].draw();
					Planet deimos = planets[Body.DEIMOS.ordinal()];
					deimos.update(deltaTime);
					gl.glTranslatef(deimos.position.x, deimos.position.y, deimos.position.z);
					gl.glPushMatrix();
					{
						deimos.rotate(deltaTime);
						deimos.draw();
					}
					gl.glPopMatrix();
				}
				gl.glPopMatrix();

			}
			sun.drawCorona();
			gl.glPopMatrix();
			
			camera.update(deltaTime);

		}
		previousTime = currentTime;

		gl.glFlush();

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		gl.setSwapInterval(1);

		// enable depth test and set shading mode
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_LINE_SMOOTH);

		camera = new Camera();
		camera.draw(gl);

		glut = new GLUT();
		glu = new GLU();
		
		initializePlanets(gl);
	}
	
	private void initializePlanets(GL2 gl) {
		sun = new Sun(gl, glu);


		planets = new Planet[Body.values().length];

		orbits = new Orbit[planets.length];
		for (int i = 0; i < planets.length; i++) {
			planets[i] = Body.values()[i].createPlanet(gl, glu);
			orbits[i] = new Orbit(gl, planets[i].orbitDistance, planets[i].isMoon);
		}
		
		
	}

	public void lights(GL2 gl) {
		// set the global ambient light level
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbient, 0);
		// set light 0 properties
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		// normalise the normal surface vectors
		gl.glEnable(GL2.GL_NORMALIZE);
		// position light 0 at the origin
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
		// enable light 0
		gl.glEnable(GL2.GL_LIGHT0);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);
	}

	public static void main(String[] args) {
		Frame frame = new Frame("Inner Solar System Viewer");
		GLCanvas canvas = new GLCanvas();
		ISSViewer app = new ISSViewer();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);
		frame.add(canvas);
		frame.setSize(WIN_WIDTH, WIN_HEIGHT);
		final FPSAnimator animator = new FPSAnimator(canvas, 60);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.setFocusable(true);
		canvas.requestFocus();
		animator.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			camera.moveZ(1);
			break;
		case KeyEvent.VK_A:
			camera.moveX(-1);
			break;
		case KeyEvent.VK_S:
			camera.moveZ(-1);
			break;
		case KeyEvent.VK_D:
			camera.moveX(1);
			break;
		case KeyEvent.VK_LEFT:
			camera.rotateYaw(1);
			break;
		case KeyEvent.VK_RIGHT:
			camera.rotateYaw(-1);
			break;
		case KeyEvent.VK_UP:
			camera.rotatePitch(1);
			break;
		case KeyEvent.VK_DOWN:
			camera.rotatePitch(-1);
			break;
		case KeyEvent.VK_E:
			camera.moveY(1);
			break;
		case KeyEvent.VK_C:
			camera.moveY(-1);
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_W:
			camera.moveZ(0);
			break;
		case KeyEvent.VK_A:
			camera.moveX(0);
			break;
		case KeyEvent.VK_S:
			camera.moveZ(0);
			break;
		case KeyEvent.VK_D:
			camera.moveX(0);
			break;
		case KeyEvent.VK_LEFT:
			camera.rotateYaw(0);
			break;
		case KeyEvent.VK_RIGHT:
			camera.rotateYaw(0);
			break;
		case KeyEvent.VK_UP:
			camera.rotatePitch(0);
		case KeyEvent.VK_DOWN:
			camera.rotatePitch(0);
		case KeyEvent.VK_E:
			camera.moveY(0);
			break;
		case KeyEvent.VK_C:
			camera.moveY(0);
			break;
		case KeyEvent.VK_O:
			Orbit.toggle();
			break;
		case KeyEvent.VK_I:
			Planet.addDegrees(10f);
			break;
		case KeyEvent.VK_U:
			Planet.addDegrees(-10f);
			break;
		case KeyEvent.VK_Y:
			Planet.toggleAxis();
			break;
		default:
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
