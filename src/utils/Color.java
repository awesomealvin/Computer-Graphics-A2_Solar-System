package utils;

public class Color {
	public float r;
	public float g;
	public float b;
	public float a;
	
	public Color(float r, float g, float b) {
		this.r= r;
		this.g= g;
		this.b=b;
		this.a = 1f;
	}
	public Color(float r, float g, float b, float a) {
		this(r, g, b);
		this.a = a;
	}
	
	public Color(int r, int g, int b) {
		this.r = (float)r/255f;
		this.g = (float)g/255f;
		this.b = (float)b/255f;
		this.a = 1f;
	}
	
	public Color(int r, int g, int b, float a) {
		this(r, g, b);
		this.a = a;
	}
	
	public Color(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}
	
	public static Color white() {
		return new Color(1f, 1f, 1f, 1f);
	}
	
	public static Color lerp(Color begin, Color end, float interpolation) {
		float r = MyMath.lerp(begin.r, end.r, interpolation);
		float g = MyMath.lerp(begin.g, end.g, interpolation);
		float b = MyMath.lerp(begin.b, end.b, interpolation);
		float a = MyMath.lerp(begin.a, end.a, interpolation);
		
		return new Color(r,g,b,a);
	}
}
