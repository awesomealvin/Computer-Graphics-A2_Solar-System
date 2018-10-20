package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import utils.Color;
import utils.MyMath;
import utils.Rotation;

public enum Body {
	
	MERCURY(1f, 0.39f, -88f, new Color(194, 159, 121), false, "./textures/2k_mercury.jpg", Color.white(), new Rotation(), new Rotation(0f, 0f, 1f, 0f), 58.647f),
	VENUS(2.449f, 0.72f, -224.7f, new Color(180, 107, 28), false, "./textures/2k_venus_surface.jpg", Color.white(), new Rotation(), new Rotation(0f, 0f, 1f, 0f), -243f),
	EARTH(2.653f, 1f, -365.2f, new Color(62, 75, 154), false, "./textures/2k_earth_daymap.jpg", Color.white(), new Rotation(270f, 0.75f, 0f, 0.25f), new Rotation(0f, 0f, 0f, 1f), 1f),
	MOON(0.713f, (0.013f * 8f), -27.3f, new Color(232, 232, 232), true, "./textures/2k_moon.jpg", Color.white(), new Rotation(), new Rotation(0f, 0f, 1f, 0f), 27.32f),
	MARS(1.388f, 1.52f, -687f, new Color(238, 126, 88), false, "./textures/2k_mars.jpg", Color.white(), new Rotation(), new Rotation(0f, 0.3f, 0.7f, 0f), 1.025f),
	PHOBOS((0.0045f * 15f), (0.005f * 8f), -0.32f, new Color(183, 148, 126), true, "./textures/mar1kuu2.jpg",new Color(183, 148, 126), new Rotation(), new Rotation(0f, 0f, 1f, 0f), 0.29f),
	DEIMOS((0.005f * 15f), (0.007f * 8f), -1.3f, new Color(220, 186, 149), true, "./textures/mar2kuu2.jpg", new Color(220, 186, 149), new Rotation(), new Rotation(0f, 0f, 1f, 0f), 1.2625f);
	
	public final float radius;
	public final float orbitDistance;
	public final float orbitTime;
	public final Color color;
	public final boolean isMoon;
	public final String textureLocation;
	public final Color textureColor;
	public final Rotation startRotation;
	public final Rotation rotation;
	public final float rotationDuration;
	
	Body(float radius, float orbitDistance, float orbitTime, Color color, boolean isMoon, String textureLocation, Color textureColor, Rotation startRotation, Rotation rotation, float rotationDuration) {
		this.radius = radius * MyMath.planetScaleMultiplier;
		this.orbitDistance = orbitDistance * MyMath.planetDistanceScaleMultiplier;
		this.orbitTime = orbitTime * MyMath.planetTimeScaleMultplier;
		this.color = color;
		this.isMoon = isMoon;
		this.textureLocation = textureLocation;
		this.textureColor = textureColor;
		this.startRotation = startRotation;
		this.rotation = rotation;
		this.rotationDuration = rotationDuration *MyMath.planetTimeScaleMultplier;
	}
	
	public Planet createPlanet(GL2 gl, GLU glu) {
		return new Planet(gl, glu, radius, orbitDistance, orbitTime, color, isMoon, textureLocation, textureColor, startRotation, rotation, rotationDuration);
	}
	
}
