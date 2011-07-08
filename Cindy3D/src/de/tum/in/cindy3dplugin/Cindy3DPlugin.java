package de.tum.in.cindy3dplugin;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import de.cinderella.api.cs.CindyScript;
import de.cinderella.api.cs.CindyScriptPlugin;
import de.cinderella.math.Vec;
import de.tum.in.cindy3dplugin.Cindy3DViewer.MeshTopology;
import de.tum.in.cindy3dplugin.jogl.JOGLViewer;

/**
 * Implementation of the plugin interface
 */
@SuppressWarnings("unchecked")
public class Cindy3DPlugin extends CindyScriptPlugin {
	private Cindy3DViewer cindy3d = null;
	
	/**
	 * Stack of saved point appearances
	 * @see Cindy3DPlugin#gsave3d()
	 * @see Cindy3DPlugin#grestore3d()
	 */
	private Stack<AppearanceState> pointAppearanceStack;
	/**
	 * The current point appearance
	 */
	private AppearanceState pointAppearance;

	/**
	 * Stack of saved line appearances
	 * @see Cindy3DPlugin#gsave3d()
	 * @see Cindy3DPlugin#grestore3d()
	 */
	private Stack<AppearanceState> lineAppearanceStack;
	/**
	 * The current line appearance
	 */
	private AppearanceState lineAppearance;
	
	/**
	 * Stack of saved polygon appearances
	 * @see Cindy3DPlugin#gsave3d()
	 * @see Cindy3DPlugin#grestore3d()
	 */
	private Stack<AppearanceState> polygonAppearanceStack;
	/**
	 * The current polygon appearance
	 */
	private AppearanceState polygonAppearance;

	/**
	 * Modifiers for the current CindyScript function call
	 */
	private Hashtable modifiers;
	

	public Cindy3DPlugin() {
		pointAppearanceStack = new Stack<AppearanceState>();
		pointAppearance = new AppearanceState(Color.RED, 1, 1);
		lineAppearanceStack = new Stack<AppearanceState>();
		lineAppearance = new AppearanceState(Color.BLUE, 1, 1);
		polygonAppearanceStack = new Stack<AppearanceState>();
		polygonAppearance = new AppearanceState(Color.GREEN, 1, 1);
	}

	@Override
	public void register() {
		if (cindy3d == null)
			//cindy3d = new JRealityViewer();
			cindy3d = new JOGLViewer();
	}

	@Override
	public void unregister() {
		if (cindy3d != null)
			cindy3d.shutdown();
		cindy3d = null;
	}

	@Override
	public void setModifiers(Hashtable m) {
		modifiers = m;
	}

	@Override
	public Hashtable getModifiers() {
		return modifiers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cinderella.api.cs.CindyScriptPlugin#getAuthor()
	 */
	@Override
	public String getAuthor() {
		return "Jan Sommer und Matthias Reitinger";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cinderella.api.cs.CindyScriptPlugin#getName()
	 */
	@Override
	public String getName() {
		return "Cindy3D";
	}

	/**
	 * Squares the given number
	 * 
	 * @param x
	 * @return The square of x
	 */
	@CindyScript("square")
	public double square(double x) {
		return x * x;
	}

	/**
	 * Prepares drawing of 3D objects. Must be called before any 3D drawing
	 * function. TODO: List these functions
	 */
	@CindyScript("begin3d")
	public void begin3d() {
		cindy3d.begin();
	}

	/**
	 * Finalizes the drawing of 3D objects. Displays all objects drawn since the
	 * last call to <code>begin3d</code>.
	 */
	@CindyScript("end3d")
	public void end3d() {
		cindy3d.end();
	}

	/**
	 * Draws a point in 3D space
	 * @param vec Euclidean coordinates of the point
	 */
	@CindyScript("draw3d")
	public void draw3d(ArrayList<Double> vec) {
		if (vec.size() != 3)
			return;

		cindy3d.addPoint(vec.get(0), vec.get(1), vec.get(2), pointAppearance);
	}

	/**
	 * Draws a line in 3D space
	 * @param vec1 Euclidean coordinates of the first endpoint
	 * @param vec2 Euclidean coordinates of the second endpoint
	 */
	@CindyScript("draw3d")
	public void draw3d(ArrayList<Double> vec1, ArrayList<Double> vec2) {
		if (vec1.size() != 3 || vec2.size() != 3)
			return;
		
		// Fill in default modifiers
		Hashtable<String, Object> modifiers = new Hashtable<String, Object>();
		modifiers.put("type", "Segment");
		
		// Apply overrides
		modifiers.putAll(this.modifiers);
		
		String type = modifiers.get("type").toString();
		
		if (type.equals("Segment")) {
			cindy3d.addSegment(vec1.get(0), vec1.get(1), vec1.get(2),
					vec2.get(0), vec2.get(1), vec2.get(2), lineAppearance);
		} else if (type.equals("Line")) {
			cindy3d.addLine(vec1.get(0), vec1.get(1), vec1.get(2),
					vec2.get(0), vec2.get(1), vec2.get(2), lineAppearance);
		} else if (type.equals("Ray")) {
			cindy3d.addRay(vec1.get(0), vec1.get(1), vec1.get(2),
					vec2.get(0), vec2.get(1), vec2.get(2), lineAppearance);
		}
	}
	
	/**
	 * Connects the given list of points by line segments
	 * @param points List of points
	 */
	@CindyScript("connect3d")
	public void connect3d(ArrayList<Vec> points) {
		double vertices[][] = new double[points.size()][3];

		for (int i = 0; i < points.size(); ++i) {
			vertices[i][0] = points.get(i).getXR();
			vertices[i][1] = points.get(i).getYR();
			vertices[i][2] = points.get(i).getZR();
		}

		cindy3d.addLineStrip(vertices, lineAppearance, false);
	}

	/**
	 * Draws the outline of a polygon
	 * @param points Vertices of the polygon
	 */
	@CindyScript("drawpoly3d")
	public void drawpoly3d(ArrayList<Vec> points) {
		double vertices[][] = new double[points.size()][3];

		for (int i = 0; i < points.size(); ++i) {
			vertices[i][0] = points.get(i).getXR();
			vertices[i][1] = points.get(i).getYR();
			vertices[i][2] = points.get(i).getZR();
		}

		cindy3d.addLineStrip(vertices, polygonAppearance, true);
	}

	/**
	 * Draws a polygon
	 * 
	 * @param points Vertices of the polygon
	 */
	@CindyScript("fillpoly3d")
	public void fillpoly3d(ArrayList<Vec> points) {
		fillpoly3d(points, null);
	}
	
	/**
	 * Draws a polygon with specifying vertex specific normals
	 * @param points Vertices of the polygon
	 * @param normals Normals for each vertex
	 */
	@CindyScript("fillpoly3d")
	public void fillpoly3d(ArrayList<Vec> points, ArrayList<Vec> normals) {
		if (points.size() == 0
				|| (normals != null && points.size() != normals.size()))
			return;
		
		double vertices[][] = new double[points.size()][3];
		double normal[][] = (normals == null) ? null : new double[normals
				.size()][3];
		
		for (int i = 0; i < points.size(); ++i) {
			vertices[i][0] = points.get(i).getXR();
			vertices[i][1] = points.get(i).getYR();
			vertices[i][2] = points.get(i).getZR();
			if (normals != null) {
				normal[i][0] = normals.get(i).getXR();
				normal[i][1] = normals.get(i).getYR();
				normal[i][2] = normals.get(i).getZR();
			}
		}
		cindy3d.addPolygon(vertices, normal, polygonAppearance);
	}
	
	/**
	 * Draws a filled circle with the specified center, radius and orientation
	 * @param center Center
	 * @param normal Orientation
	 * @param radius Radius
	 */
	@CindyScript("fillcircle3d")
	public void fillcircle3d(ArrayList<Double> center, ArrayList<Double> normal,
			double radius) {
		if (center.size() != 3 || normal.size() != 3) return;
		cindy3d.addCircle(center.get(0), center.get(1), center.get(2),
				normal.get(0), normal.get(1), normal.get(2),
				radius, pointAppearance);
	}
	
	/**
	 * Draws a grid based mesh. Normals are generated automatically.
	 * @param rows Number of rows of vertices
	 * @param columns Number of columns of vertices
	 * @param points Vertex positions
	 */
	@CindyScript("mesh3d")
	public void mesh3d(int rows, int columns, ArrayList<Vec> points) {
		if (rows < 0 || columns < 0 || rows * columns != points.size())
			return;

		// Fill in default modifiers
		Hashtable<String, Object> modifiers = new Hashtable<String, Object>();
		modifiers.put("normaltype", "perface");
		modifiers.put("topology", "open");

		// Apply overrides
		modifiers.putAll(this.modifiers);

		String type = modifiers.get("normaltype").toString();

		boolean perVertex = false;
		if (type.equals("pervertex"))
			perVertex = true;

		double[][] vertices = new double[points.size()][3];
		for (int i = 0; i < points.size(); ++i) {
			Vec v = points.get(i);
			vertices[i][0] = v.getXR();
			vertices[i][1] = v.getYR();
			vertices[i][2] = v.getZR();
		}
		
		String topologyStr = modifiers.get("topology").toString();
		MeshTopology topology = MeshTopology.OPEN;
		if (topologyStr.equals("open")) {
			topology = MeshTopology.OPEN;
		} else if (topologyStr.equals("onesided")) {
			topology = MeshTopology.ONE_SIDED;
		} else if (topologyStr.equals("twosided")) {
			topology = MeshTopology.TWO_SIDED;
		}
		
		cindy3d.addMesh(rows, columns, vertices, perVertex, topology,
				polygonAppearance);
	}
	
	/**
	 * Draws a grid based mesh. Normals are specified manually
	 * @param rows Number of rows of vertices
	 * @param columns Number of columns of vertices
	 * @param points Vertex positions
	 * @param normals Vertex normals
	 */
	@CindyScript("mesh3d")
	public void mesh3d(int rows, int columns, ArrayList<Vec> points,
			ArrayList<Vec> normals) {
		if (rows < 0 || columns < 0 || rows * columns != points.size()
				|| rows * columns != normals.size())
			return;
		
		double[][] vertices = new double[points.size()][3];
		double[][] perVertex = new double[normals.size()][3];
		for (int i=0; i<points.size(); ++i) {
			Vec v = points.get(i);
			vertices[i][0] = v.getXR();
			vertices[i][1] = v.getYR();
			vertices[i][2] = v.getZR();
			v = normals.get(i);
			perVertex[i][0] = v.getXR();
			perVertex[i][1] = v.getYR();
			perVertex[i][2] = v.getZR();
		}
		
		// Fill in default modifiers
		Hashtable<String, Object> modifiers = new Hashtable<String, Object>();
		modifiers.put("topology", "open");

		// Apply overrides
		modifiers.putAll(this.modifiers);

		String topologyStr = modifiers.get("topology").toString();
		MeshTopology topology = MeshTopology.OPEN;
		if (topologyStr.equals("open")) {
			topology = MeshTopology.OPEN;
		} else if (topologyStr.equals("onesided")) {
			topology = MeshTopology.ONE_SIDED;
		} else if (topologyStr.equals("twosided")) {
			topology = MeshTopology.TWO_SIDED;
		}
		
		cindy3d.addMesh(rows, columns, vertices, perVertex, topology,
				polygonAppearance);
	}
	
	@CindyScript("drawsphere3d")
	public void sphere3d(ArrayList<Double> center, double radius) {
		if (center.size() != 3)
			return;
		cindy3d.addSphere(center.get(0), center.get(1), center.get(2),
				radius, polygonAppearance);
	}
	
	/**
	 * Pushes the current appearance on the appearance stack
	 * @see Cindy3DPlugin#grestore3d()
	 */
	@CindyScript("gsave3d")
	public void gsave3d() {
		pointAppearanceStack.push(new AppearanceState(pointAppearance));
		lineAppearanceStack.push(new AppearanceState(lineAppearance));
		polygonAppearanceStack.push(new AppearanceState(polygonAppearance));
	}

	/**
	 * Removes the top element of the appearance stack and replaces the current
	 * appearance with it
	 * @see Cindy3DPlugin#gsave3d()
	 */
	@CindyScript("grestore3d")
	public void grestore3d() {
		if (!pointAppearanceStack.isEmpty())
			pointAppearance = pointAppearanceStack.pop();
		if (!lineAppearanceStack.isEmpty())
			lineAppearance = lineAppearanceStack.pop();
		if (!polygonAppearanceStack.isEmpty())
			polygonAppearance = polygonAppearanceStack.pop();
	}
	
	@CindyScript("opacity3d")
	public void opacity3d(double opacity) {
		opacity = Math.max(0, Math.min(1, opacity));
		polygonAppearance.setOpacity(opacity);
	}

	/**
	 * Set color state
	 * @param vec Color vector
	 */
	@CindyScript("color3d")
	public void color3d(ArrayList<Double> vec) {
		setColorState(pointAppearance, vec);
		setColorState(lineAppearance, vec);
		setColorState(polygonAppearance, vec);
	}

	/**
	 * Set size state
	 * @param size Size
	 */
	@CindyScript("size3d")
	public void size3d(double size) {
		if (size <= 0)
			return;
		pointAppearance.setSize(size);
		lineAppearance.setSize(size);
		polygonAppearance.setSize(size);
	}

	/**
	 * Set point color state
	 * @param vec Color vector
	 */
	@CindyScript("pointcolor3d")
	public void pointcolor3d(ArrayList<Double> vec) {
		setColorState(pointAppearance, vec);
	}

	/**
	 * Set point size state
	 * @param size Point size
	 */
	@CindyScript("pointsize3d")
	public void pointsize3d(double size) {
		if (size <= 0)
			return;
		pointAppearance.setSize(size);
	}

	/**
	 * Set line color state
	 * @param vec Color vector
	 */
	@CindyScript("linecolor3d")
	public void linecolor3d(ArrayList<Double> vec) {
		setColorState(lineAppearance, vec);
	}
	
	/**
	 * Set polygon color state
	 * @param vec Color vector
	 */
	@CindyScript("polygoncolor3d")
	public void polygoncolor3d(ArrayList<Double> vec) {
		setColorState(polygonAppearance, vec);
	}

	/**
	 * Set line size state
	 * @param size Line size
	 */
	@CindyScript("linesize3d")
	public void linesize3d(double size) {
		if (size <= 0)
			return;
		lineAppearance.setSize(size);
	}
	
	@CindyScript("background3d")
	public void background3d(ArrayList<Double> vec) {
		if (vec.size() != 3)
			return;
		cindy3d.setBackgroundColor(new Color(
				(float)Math.max(0, Math.min(1, vec.get(0))),
				(float)Math.max(0, Math.min(1, vec.get(1))),
				(float)Math.max(0, Math.min(1, vec.get(2)))));
	}
	
	@CindyScript("depthrange3d")
	public void depthrange3d(double near, double far) {
		cindy3d.setDepthRange(near, far);
	}

	protected void setColorState(AppearanceState appearance,
			ArrayList<Double> vec) {
		if (vec.size() != 3)
			return;
		appearance.setColor(new Color(
				(float)Math.max(0, Math.min(1, vec.get(0))),
				(float)Math.max(0, Math.min(1, vec.get(1))),
				(float)Math.max(0, Math.min(1, vec.get(2)))));
	}
}