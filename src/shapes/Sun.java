package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;


import utils.Color;
import utils.MyMath;
import utils.Rotation;

public class Sun extends Planet{
	
	
	private final Color coronaColorStart = new Color(255,255, 0, 0.15f);
	private final Color coronaColorEnd = new Color(255, 170, 0, 0.1f);
	private Color currentCoronaColor;
	private final float CORONA_RADIUS = 5.35f * MyMath.planetScaleMultiplier;
	private GLUquadric coronaQuad;
	private float coronaTransitionTime = 2f;
	private float currentTime = 0f;
	private boolean pulseUp = true;	
	
	public Sun(GL2 gl, GLU glu) {
		super(gl, glu);
		this.color =  new Color(255, 255, 0);
		this.radius = 5f * MyMath.planetScaleMultiplier;
		this.textureLocation = "./textures/2k_sun.jpg";
		this.textureColor = new Color(1f, 1f, 1f, 1f);
		currentCoronaColor = new Color(coronaColorStart);
		coronaQuad = glu.gluNewQuadric();
		this.startRotation = new Rotation();
		this.rotation = new Rotation(0f, 0f, 0.9f, 0.1f);
		this.rotationDuration = 1.0196f;
		init();
	}

	
	public void drawCorona() {
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		gl.glColor4f(currentCoronaColor.r, currentCoronaColor.g, currentCoronaColor.b, currentCoronaColor.a);
		glu.gluSphere(coronaQuad, (double)CORONA_RADIUS, SLICES, STACKS);
		gl.glDisable(GL2.GL_BLEND);
	}

	@Override
	public void update(float deltaTime) {
		if (pulseUp) {
			currentTime += deltaTime;
			if (currentTime > coronaTransitionTime) {
				pulseUp = false;
			}
		} else if (!pulseUp){
			currentTime -= deltaTime;
			if (currentTime <= 0) {
				pulseUp = true;
			}
		}
		
		currentCoronaColor = Color.lerp(coronaColorStart, coronaColorEnd, currentTime/coronaTransitionTime);
	}

}
