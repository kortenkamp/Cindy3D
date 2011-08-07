package de.tum.in.cindy3dplugin.jogl.lighting;

import javax.media.opengl.GL2;

import de.tum.in.cindy3dplugin.Cindy3DViewer;
import de.tum.in.cindy3dplugin.LightInfo;
import de.tum.in.cindy3dplugin.LightInfo.LightType;
import de.tum.in.cindy3dplugin.jogl.Util;

public class LightManager {	
	boolean compileShader = false;
	
	Light[] lights = new Light[Cindy3DViewer.MAX_LIGHTS];
	
	public LightManager() {
		LightInfo info = new LightInfo();
		info.position = new double[] {0,0,0};
		info.type = LightType.POINT_LIGHT;
		setLight(0, info);
	}
	
	public void setLight(int light, LightInfo info) {
		if (lights[light] == null || lights[light].getType() != info.type) {
			compileShader = true;
			
			switch (info.type) {
			case POINT_LIGHT:
				lights[light] = new PointLight();
				break;
			case DIRECTIONAL_LIGHT:
				lights[light] = new DirectionalLight();
				break;
			case SPOT_LIGHT:
				lights[light] = new SpotLight();
				break;
			default:
				return;
			}
		}
		
		if (!lights[light].isEnabled()) {
			lights[light].setEnabled(true);
			compileShader = true;
		}
		
		if (info.ambient != null) {
			lights[light].setAmbientColor(info.ambient);
		}
		if (info.diffuse != null) {
			lights[light].setDiffuseColor(info.diffuse);
		}
		if (info.specular != null) {
			lights[light].setSpecularColor(info.specular);
		}
		if (info.frame != null) {
			lights[light].setLightFrame(info.frame);
		}

		switch (info.type) {
			case POINT_LIGHT:
				PointLight pLight = (PointLight)lights[light];
				
				if (info.position != null) {
					pLight.setPosition(Util.toVector(info.position));
				}
				break;
			case DIRECTIONAL_LIGHT:
				DirectionalLight dLight = (DirectionalLight)lights[light];
				if (info.direction != null) {
					dLight.setDirection(Util.toVector(info.direction));
				}
				break;
			case SPOT_LIGHT:
				break;
		}
	}
	
	public String getShaderFillIn() {
		String str = "vec3 eye = -normalize(ecPoint);\n";
		for (int i = 0; i < Cindy3DViewer.MAX_LIGHTS; ++i) {
			if (lights[i] != null && lights[i].isEnabled()) {
				str += lights[i].getShaderFillIn(i) + "\n";
			}
		}
		return str;
	}
	
	public boolean getCompileShader() {
		return compileShader;
	}

	public void setCompileShader(boolean compileShader) {
		this.compileShader = compileShader;
	}
	
	public void setGLState(GL2 gl) {
		// Viewer assumed to be at (0,0,0) in eye coordinates
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 1);
		
		for (int i = 0; i < Cindy3DViewer.MAX_LIGHTS; ++i) {
			if (lights[i] != null) {
				lights[i].setGLState(gl, GL2.GL_LIGHT0 + i);
			}
		}
	}

	public void disableLight(int light) {
		if (!lights[light].isEnabled()) {
			return;
		}
		
		lights[light].setEnabled(false);
		compileShader = true;
	}

}
