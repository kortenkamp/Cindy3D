package de.tum.in.jrealityplugin.jogl;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class PolygonRenderer extends PrimitiveRenderer<Polygon> {

	private ShaderProgram program = null;
	
	private int colorLoc;
	
	@Override
	public void dispose(GL gl) {
		if (program != null)
			program.destroy(gl.getGL2());
	}

	@Override
	public boolean init(GL gl) {
		GL2 gl2 = gl.getGL2();

		program = new ShaderProgram();
		
		ShaderCode vertexShader = loadShader(
				GL2.GL_VERTEX_SHADER,
				getClass()
						.getResource(
								"/de/tum/in/jrealityplugin/resources/shader/polygon.vert"));
		if (!vertexShader.compile(gl2))
			return false;
		ShaderCode fragmentShader = loadShader(
				GL2.GL_FRAGMENT_SHADER,
				getClass()
						.getResource(
								"/de/tum/in/jrealityplugin/resources/shader/polygon.frag"));
		if (!fragmentShader.compile(gl2))
			return false;

		if (!program.add(vertexShader))
			return false;

		if (!program.add(fragmentShader))
			return false;

		if (!program.link(gl.getGL2(), null))
			return false;

		colorLoc = gl2.glGetUniformLocation(program.program(), "polygonColor");

		return true;
	}
	
	@Override
	public void preRender(JOGLRenderState jrs) {
		GL2 gl2 = jrs.gl.getGL2();
		gl2.glUseProgram(program.program());
	}

	@Override
	protected void render(JOGLRenderState jrs, Polygon polygon) {
		GL2 gl2 = jrs.gl.getGL2();

		gl2
				.glUniform3fv(colorLoc, 1, polygon.color
						.getColorComponents(null), 0);

		gl2.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i < polygon.positions.length; ++i) {
			gl2.glNormal3d(polygon.normals[i].getX(),
					polygon.normals[i].getY(), polygon.normals[i].getZ());
			gl2.glVertex3d(polygon.positions[i].getX(), polygon.positions[i]
					.getY(), polygon.positions[i].getZ());
		}
		gl2.glEnd();
	}

	@Override
	public void postRender(JOGLRenderState jrs) {
		GL2 gl2 = jrs.gl.getGL2();
		gl2.glUseProgram(0);		
	}
}
