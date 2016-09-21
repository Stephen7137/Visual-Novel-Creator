package cpp.TextAdvEditor.Model;

import java.io.Serializable;

/**
 * A simple class to keep track of points
 * and have some calculation with other points.
 * Can be saved  to a file.
 * 
 * @author Stephen Jackson
 *
 */
public class SimpPoint2D implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -578926032391541865L;

	
	double x;
	double y;
	
	public SimpPoint2D(double x, double y){
		setPoint(x,y);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setPoint(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Finds the distance between itself and the
	 * provided points.
	 * @param x2
	 * @param y2
	 * @return returns the distance.
	 */
	public double distance(double x2, double y2) {
		x2 -= x;
		y2 -= y;
		x2 *= x2;
		y2 *= y2;
		return Math.sqrt(x2 + y2);
	}
}
