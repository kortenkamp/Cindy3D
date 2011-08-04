package de.tum.in.cindy3dplugin.jogl.primitives.renderers.shader;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import org.apache.commons.math.geometry.Vector3D;
import org.apache.commons.math.linear.RealMatrix;

import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import de.tum.in.cindy3dplugin.jogl.Util;
import de.tum.in.cindy3dplugin.jogl.primitives.Line;
import de.tum.in.cindy3dplugin.jogl.primitives.renderers.LineRendererBase;
import de.tum.in.cindy3dplugin.jogl.renderers.JOGLRenderState;

public class LineRenderer extends LineRendererBase {
	private ShaderProgram program = null;

	private int transformLoc;
	private int originLoc;
	private int directionLoc;
	private int radiusLoc;
	private int lengthLoc;

	@Override
	public void dispose(GL gl) {
		if (program != null)
			program.destroy(gl.getGL2());
	}

	@Override
	public boolean loadShader(GL gl) {
		GL2 gl2 = gl.getGL2();

		program = new ShaderProgram();
		ShaderCode vertexShader = Util.loadShader(GL2.GL_VERTEX_SHADER,
				"cylinder.vert");
		if (!vertexShader.compile(gl2))
			return false;
		ShaderCode fragmentShader = Util.loadShader(GL2.GL_FRAGMENT_SHADER,
				"cylinder.frag");
		if (!fragmentShader.compile(gl2))
			return false;

		if (!program.add(vertexShader))
			return false;
		if (!program.add(fragmentShader))
			return false;
		if (!program.link(gl.getGL2(), null))
			return false;

		transformLoc = gl2.glGetUniformLocation(program.program(),
				"cylinderTransform");
		originLoc = gl2
				.glGetUniformLocation(program.program(), "cylinderPoint");
		directionLoc = gl2.glGetUniformLocation(program.program(),
				"cylinderDirection");
		radiusLoc = gl2.glGetUniformLocation(program.program(),
				"cylinderRadius");
		lengthLoc = gl2.glGetUniformLocation(program.program(),
				"cylinderLength");

		return true;
	}

	@Override
	public void postRender(JOGLRenderState jrs) {
		GL2 gl2 = jrs.gl.getGL2();
		gl2.glUseProgram(0);
	}

	@Override
	public void preRender(JOGLRenderState jrs) {
		GL2 gl2 = jrs.gl.getGL2();
		gl2.glUseProgram(program.program());
	}

	@Override
	protected void render(JOGLRenderState jrs, Line line) {
		GL2 gl2 = jrs.gl.getGL2();
		// Get the model view matrix
		RealMatrix modelView = jrs.camera.getTransform();

		// All computations are made in camera space, so first
		// transform the two points of the line into camera space
		// by multiplying with the modelview matrix			
		Vector3D p1 = Util.transformVector(modelView, line.p1);
		Vector3D p2 = Util.transformVector(modelView, line.p2);

		// Compute orientation of the cylinder and its length, assuming
		// a line segment is about to be drawn
		Vector3D direction = p2.subtract(p1);

		double cylinderLength = -1;
		switch (line.lineType) {
		case SEGMENT:
			cylinderLength = direction.getNorm();
			break;
		case RAY:
			cylinderLength = 0;
			break;
		case LINE:
			cylinderLength = -1;
			break;
		}
		direction = direction.normalize();

		Endpoints endPoints = clipLineAtFrustum(jrs.camera, p1, p2,
				line.lineType);
		// After shifting the end points of the ray/line to the maximal
		// visible positions, the size and orientation for the OBB is needed
		RealMatrix cylinder = buildOBBTransform(endPoints, line.radius);

		gl2.glUniformMatrix4fv(transformLoc, 1, true,
				Util.matrixToFloatArray(cylinder), 0);

		// Draw [-1,1]^3 cube which is transformed into the OBB during
		// processing the vertices on gpu
		gl2.glUniform1f(lengthLoc, (float) cylinderLength);
		gl2.glUniform1f(radiusLoc, (float) line.radius);
		gl2.glUniform3f(originLoc, (float) p1.getX(), (float) p1.getY(),
				(float) p1.getZ());
		gl2.glUniform3f(directionLoc, (float) direction.getX(),
				(float) direction.getY(), (float) direction.getZ());

		gl2.glBegin(GL2.GL_QUADS);
			gl2.glVertex3d(-1, -1, -1);
			gl2.glVertex3d(-1, -1, 1);
			gl2.glVertex3d(-1, 1, 1);
			gl2.glVertex3d(-1, 1, -1);

			gl2.glVertex3d(1, -1, 1);
			gl2.glVertex3d(1, -1, -1);
			gl2.glVertex3d(1, 1, -1);
			gl2.glVertex3d(1, 1, 1);

			gl2.glVertex3d(1, -1, 1);
			gl2.glVertex3d(-1, -1, 1);
			gl2.glVertex3d(-1, -1, -1);
			gl2.glVertex3d(1, -1, -1);

			gl2.glVertex3d(-1, 1, 1);
			gl2.glVertex3d(1, 1, 1);
			gl2.glVertex3d(1, 1, -1);
			gl2.glVertex3d(-1, 1, -1);

			gl2.glVertex3d(1, 1, 1);
			gl2.glVertex3d(-1, 1, 1);
			gl2.glVertex3d(-1, -1, 1);
			gl2.glVertex3d(1, -1, 1);

			gl2.glVertex3d(-1, -1, -1);
			gl2.glVertex3d(-1, 1, -1);
			gl2.glVertex3d(1, 1, -1);
			gl2.glVertex3d(1, -1, -1);
		gl2.glEnd();
	}
}