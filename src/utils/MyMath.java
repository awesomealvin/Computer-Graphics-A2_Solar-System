package utils;

public class MyMath {
	public static float planetScaleMultiplier = 1f;
	public static float planetDistanceScaleMultiplier = 50f;
	
	
	public static float planetTimeScaleMultplier = 1f;
	
	public static float lerp(float begin, float end, float interpolation) {
		float length = end - begin;
		return (length * interpolation) + begin;
	}
	
	
}
