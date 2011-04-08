package de.tum.in.reitinge.test;

import java.awt.Color;

import de.tum.in.jrealityplugin.AppearanceState;
import de.tum.in.jrealityplugin.Cindy3DViewer;
import de.tum.in.jrealityplugin.jogl.JOGLViewer;

public class JOGLViewerTest {
	public static void main(String[] args) {
		JOGLViewer viewer = new JOGLViewer();
		
		viewer.setBackgroundColor(Color.white);
		
		viewer.begin();
//		colorSpiral(viewer);
//		circles(viewer);
		lines(viewer);
		viewer.end();
	}

	public static void colorSpiral(Cindy3DViewer viewer) {
		AppearanceState appearance = new AppearanceState(Color.red, 1.0);
		
		double height = 2;
		double radius = 1;
		int segments = 100;
		int rings = 20;

		for (int segment = 1; segment <= segments; ++segment) {
			double frac = (double) segment / segments;
			appearance.setColor(new Color(Color.HSBtoRGB((float) frac, 1, 1)));
			for (int ring = 1; ring <= rings; ++ring) {
				double rad = (double) ring / rings * radius;
				viewer.addPoint(Math.cos(frac * 2 * Math.PI) * rad,
						Math.sin(frac * 2 * Math.PI) * rad, (frac - 0.5)
								* height, appearance);
			}
		}
	}
	
	public static void circles(Cindy3DViewer viewer) {
		AppearanceState appearance = new AppearanceState(Color.red, 1.0);
		
		for (int i = -5; i <= 5; ++i) {
			for (int j = -5; j <= 5; ++j) {
				appearance.setColor(Color.red);
				viewer.addCircle(i * 2, j * 2, 0, 1, 0, 0, 1, appearance);
				appearance.setColor(Color.green);
				viewer.addCircle(i * 2, j * 2, 0, 0, 1, 0, 1, appearance);
				appearance.setColor(Color.blue);
				viewer.addCircle(i * 2, j * 2, 0, 0, 0, 1, 1, appearance);
			}
		}
	}
	
	public static void lines(Cindy3DViewer viewer) {
		AppearanceState appearance = new AppearanceState(Color.red, 1.0);
		
		viewer.addSegment(-10, 0, 0, 8, 0, -0.5, appearance);
		viewer.addLine(0, 2, 0, 5, 4, 0, appearance);
		viewer.addRay(0, 4, 0, 5, -2, 2, appearance);
		
		viewer.addPolygon(new double[][] { { 0, 0, 0 }, { 5, 0, 0 },
				{ 5, 5, 0 } }, null, appearance);
		
		viewer.addLineStrip(new double[][] { { 0, 0, 0 }, { 5, 0, 0 },
				{ 5, 5, 0 } }, appearance, true);
	}
}