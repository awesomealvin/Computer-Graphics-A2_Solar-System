package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public abstract class QuadricObject extends MovableObject{

	protected GLUquadric quad;
	protected GLU glu;
	
	public QuadricObject(GL2 gl, GLU glu) {
		super(gl);
		this.glu = glu;
		quad = glu.gluNewQuadric();
//		glu.gluQuadricDrawStyle(quad, GLU.GLU_SMOOTH);
//		glu.gluQuadricTexture(quad, true);
//		glu.gluQuadricNormals(quad, GLU.GLU_SMOOTH);
	}

	

}
