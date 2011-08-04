package de.tum.in.cindy3dplugin.jogl.primitives.renderers.fixedfunc;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import de.tum.in.cindy3dplugin.jogl.primitives.Point;
import de.tum.in.cindy3dplugin.jogl.primitives.renderers.PrimitiveRenderer;
import de.tum.in.cindy3dplugin.jogl.renderers.JOGLRenderState;
import de.tum.in.cindy3dplugin.jogl.renderers.JOGLRenderState.CullMode;

public class PointRenderer extends PrimitiveRenderer<Point> {
	private static final int LOD_COUNT = 8;
	
    private LODMesh[] meshes = new LODMesh[LOD_COUNT];

	@Override
	public boolean loadShader(GL gl) {
		GL2 gl2 = gl.getGL2();
		
		for (int lod = 0; lod < LOD_COUNT; ++lod) {
			int stacks = lod + 2;
			int slices = 2 * stacks;
			
			int vertexCount = 2 + slices * (stacks - 1);
			int faceCount = 2 * slices + // stacks at poles, triangles
					(stacks - 2) * slices * 2; // inner stacks loops, quads (2
											   // triangles)
			
			LODMesh mesh = new LODMesh(3, vertexCount, faceCount);
			
			/*
			 * Generate vertices
			 */
			double[] vertex = new double[3];
			
			vertex[0] = vertex[1] = 0;
			vertex[2] = -1;
			mesh.putVertex(vertex);
			
			for (int stack = 1; stack < stacks; ++stack) {
				double latitude = (((double) stack) / stacks - 0.5) * Math.PI;
				for (int slice = 0; slice < slices; ++slice) {
					double longitude = ((double) slice) / slices * Math.PI * 2;
					vertex[0] = Math.cos(longitude)*Math.cos(latitude);
					vertex[1] = Math.sin(longitude)*Math.cos(latitude);
					vertex[2] = Math.sin(latitude);
					mesh.putVertex(vertex);
				}
			}
			
			vertex[0] = vertex[1] = 0;
			vertex[2] = 1;
			mesh.putVertex(vertex);

			/*
			 * Generate indices
			 */
			int stackOffset = 0;		// First vertex of current stack
			int nextStackOffset = 1;	// First vertex of next stack
			
			// South pole
			for (int slice = 0; slice < slices; ++slice) {
				mesh.putFace(
						stackOffset,
						nextStackOffset + (slice + 1) % slices,
						nextStackOffset + slice);
			}
			
			for (int stack = 1; stack < stacks - 1; ++stack) {
				stackOffset = nextStackOffset;
				nextStackOffset += slices;
				for (int slice = 0; slice < slices; ++slice) {
					mesh.putFace(
							stackOffset     + slice,
							nextStackOffset + (slice + 1) % slices,
							nextStackOffset + slice);
					mesh.putFace(
							stackOffset     + slice,
							stackOffset     + (slice + 1) % slices,
							nextStackOffset + (slice + 1) % slices);
				}
			}
			
			// North pole
			for (int slice = 0; slice < slices; ++slice) {
				mesh.putFace(
						nextStackOffset + slices,
						nextStackOffset + slice,
						nextStackOffset + (slice + 1) % slices);
			}

			mesh.finish(gl2);
			meshes[lod] = mesh;
		}
		
		return true;
	}

	@Override
	public void dispose(GL gl) {
		GL2 gl2 = gl.getGL2();
		for (LODMesh mesh : meshes) {
			mesh.dispose(gl2);
		}
	}

	@Override
	public void preRender(JOGLRenderState jrs) {
		GL2 gl2 = jrs.gl.getGL2();
		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl2.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		if (jrs.cullMode == CullMode.CULL_FRONT) {
			gl2.glEnable(GL2.GL_CULL_FACE);
			gl2.glCullFace(GL2.GL_FRONT);
		} else if (jrs.cullMode == CullMode.CULL_BACK) {
			gl2.glEnable(GL2.GL_CULL_FACE);
			gl2.glCullFace(GL2.GL_BACK);
		}
	}

	@Override
	public void postRender(JOGLRenderState jrs) {
		GL2 gl2 = jrs.gl.getGL2();
		gl2.glDisable(GL2.GL_NORMALIZE);
		gl2.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		gl2.glDisableClientState(GL2.GL_NORMAL_ARRAY);
	}

	@Override
	protected void render(JOGLRenderState jrs, Point point) {
		GL2 gl2 = jrs.gl.getGL2();

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glPushMatrix();
		gl2.glTranslated(point.x, point.y, point.z);
		gl2.glScaled(point.size, point.size, point.size);
		
		meshes[4].render(gl2);
		
		gl2.glPopMatrix();
	}
}
