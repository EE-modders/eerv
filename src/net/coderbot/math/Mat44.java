package net.coderbot.math;

public class Mat44 
{
	//[R][C] formatted matrix data
	public double[][] elems;
	
	public Mat44()
	{
		elems = new double[4][4];
	}
	
	public void add(Mat44 other)
	{
		elems[0][0]+=other.elems[0][0];
		elems[0][1]+=other.elems[0][1];
		elems[0][2]+=other.elems[0][2];
		elems[0][3]+=other.elems[0][3];
		elems[1][0]+=other.elems[1][0];
		elems[1][1]+=other.elems[1][1];
		elems[1][2]+=other.elems[1][2];
		elems[1][3]+=other.elems[1][3];
		elems[2][0]+=other.elems[2][0];
		elems[2][1]+=other.elems[2][1];
		elems[2][2]+=other.elems[2][2];
		elems[2][3]+=other.elems[2][3];
		elems[3][0]+=other.elems[3][0];
		elems[3][1]+=other.elems[3][1];
		elems[3][2]+=other.elems[3][2];
		elems[3][3]+=other.elems[3][3];
	}
	
	public void sub(Mat44 other)
	{
		elems[0][0]-=other.elems[0][0];
		elems[0][1]-=other.elems[0][1];
		elems[0][2]-=other.elems[0][2];
		elems[0][3]-=other.elems[0][3];
		elems[1][0]-=other.elems[1][0];
		elems[1][1]-=other.elems[1][1];
		elems[1][2]-=other.elems[1][2];
		elems[1][3]-=other.elems[1][3];
		elems[2][0]-=other.elems[2][0];
		elems[2][1]-=other.elems[2][1];
		elems[2][2]-=other.elems[2][2];
		elems[2][3]-=other.elems[2][3];
		elems[3][0]-=other.elems[3][0];
		elems[3][1]-=other.elems[3][1];
		elems[3][2]-=other.elems[3][2];
		elems[3][3]-=other.elems[3][3];
	}
	
	/**
	 * Multiplies the vector, storing the result in <code>res</code>, according to the following formula:<br>
	 * <br><code>
	 * 
	 * [a b c d] *  [x] = [a*x + b*y + c*z + d*w]<br>
	 * [e f g h] *  [y] = [e*x + f*y + g*z + h*w]<br>
	 * [i j k l] *  [z] = [i*x + j*y + k*z + l*w]<br>
	 * [m n o p] *  [w] = [m*x + n*y + o*z + p*w]<br>
	 * 
	 * </code>
	 * 
	 * @param vec Input vector
	 * @param res Output storage
	 */
	public void mul(Vector4 vec, Vector4 res)
	{
		res.x = (vec.x*elems[0][0])+(vec.y*elems[0][1]+(vec.z*elems[0][2])+(vec.w*elems[0][3]));
		res.y = (vec.x*elems[1][0])+(vec.y*elems[1][1]+(vec.z*elems[1][2])+(vec.w*elems[1][3]));
		res.z = (vec.x*elems[2][0])+(vec.y*elems[2][1]+(vec.z*elems[2][2])+(vec.w*elems[2][3]));
		res.w = (vec.x*elems[3][0])+(vec.y*elems[3][1]+(vec.z*elems[3][2])+(vec.w*elems[3][3]));
	}
	
	public void mul(Vector3 vec, Vector3 res)
	{
		res.x = (vec.x*elems[0][0]) + (vec.y*elems[0][1] + (vec.z*elems[0][2]) + elems[0][3]);
		res.y = (vec.x*elems[1][0]) + (vec.y*elems[1][1] + (vec.z*elems[1][2]) + elems[1][3]);
		res.z = (vec.x*elems[2][0]) + (vec.y*elems[2][1] + (vec.z*elems[2][2]) + elems[2][3]);
	}
	
	public void mul(Mat44 other, Mat44 res)
	{
		res.elems[0][0] = (elems[0][0]*other.elems[0][0]) + (elems[0][1]*other.elems[1][0]) + (elems[0][2]*other.elems[2][0]) + (elems[0][3]*other.elems[3][0]);
		res.elems[0][1] = (elems[0][0]*other.elems[0][1]) + (elems[0][1]*other.elems[1][1]) + (elems[0][2]*other.elems[2][1]) + (elems[0][3]*other.elems[3][1]);
		res.elems[0][2] = (elems[0][0]*other.elems[0][2]) + (elems[0][1]*other.elems[1][2]) + (elems[0][2]*other.elems[2][2]) + (elems[0][3]*other.elems[3][2]);
		res.elems[0][3] = (elems[0][0]*other.elems[0][3]) + (elems[0][1]*other.elems[1][3]) + (elems[0][2]*other.elems[2][3]) + (elems[0][3]*other.elems[3][3]);
		
		res.elems[1][0] = (elems[1][0]*other.elems[0][0]) + (elems[1][1]*other.elems[1][0]) + (elems[1][2]*other.elems[2][0]) + (elems[1][3]*other.elems[3][0]);
		res.elems[1][1] = (elems[1][0]*other.elems[0][1]) + (elems[1][1]*other.elems[1][1]) + (elems[1][2]*other.elems[2][1]) + (elems[1][3]*other.elems[3][1]);
		res.elems[1][2] = (elems[1][0]*other.elems[0][2]) + (elems[1][1]*other.elems[1][2]) + (elems[1][2]*other.elems[2][2]) + (elems[1][3]*other.elems[3][2]);
		res.elems[1][3] = (elems[1][0]*other.elems[0][3]) + (elems[1][1]*other.elems[1][3]) + (elems[1][2]*other.elems[2][3]) + (elems[1][3]*other.elems[3][3]);
		
		res.elems[2][0] = (elems[2][0]*other.elems[0][0]) + (elems[2][1]*other.elems[1][0]) + (elems[2][2]*other.elems[2][0]) + (elems[2][3]*other.elems[3][0]);
		res.elems[2][1] = (elems[2][0]*other.elems[0][1]) + (elems[2][1]*other.elems[1][1]) + (elems[2][2]*other.elems[2][1]) + (elems[2][3]*other.elems[3][1]);
		res.elems[2][2] = (elems[2][0]*other.elems[0][2]) + (elems[2][1]*other.elems[1][2]) + (elems[2][2]*other.elems[2][2]) + (elems[2][3]*other.elems[3][2]);
		res.elems[2][3] = (elems[2][0]*other.elems[0][3]) + (elems[2][1]*other.elems[1][3]) + (elems[2][2]*other.elems[2][3]) + (elems[2][3]*other.elems[3][3]);
		
		res.elems[3][0] = (elems[3][0]*other.elems[0][0]) + (elems[3][1]*other.elems[1][0]) + (elems[3][2]*other.elems[2][0]) + (elems[3][3]*other.elems[3][0]);
		res.elems[3][1] = (elems[3][0]*other.elems[0][1]) + (elems[3][1]*other.elems[1][1]) + (elems[3][2]*other.elems[2][1]) + (elems[3][3]*other.elems[3][1]);
		res.elems[3][2] = (elems[3][0]*other.elems[0][2]) + (elems[3][1]*other.elems[1][2]) + (elems[3][2]*other.elems[2][2]) + (elems[3][3]*other.elems[3][2]);
		res.elems[3][3] = (elems[3][0]*other.elems[0][3]) + (elems[3][1]*other.elems[1][3]) + (elems[3][2]*other.elems[2][3]) + (elems[3][3]*other.elems[3][3]);
	}
	
	public void loadIdentity()
	{
		elems[0][0] = 1;
		elems[0][1] = 0;
		elems[0][2] = 0;
		elems[0][3] = 0;
		elems[1][0] = 0;
		elems[1][1] = 1;
		elems[1][2] = 0;
		elems[1][3] = 0;
		elems[2][0] = 0;
		elems[2][1] = 0;
		elems[2][2] = 1;
		elems[2][3] = 0;
		elems[3][0] = 0;
		elems[3][1] = 0;
		elems[3][2] = 0;
		elems[3][3] = 1;
	}
	
	public void loadZero()
	{
		elems[0][0] = 0;
		elems[0][1] = 0;
		elems[0][2] = 0;
		elems[0][3] = 0;
		elems[1][0] = 0;
		elems[1][1] = 0;
		elems[1][2] = 0;
		elems[1][3] = 0;
		elems[2][0] = 0;
		elems[2][1] = 0;
		elems[2][2] = 0;
		elems[2][3] = 0;
		elems[3][0] = 0;
		elems[3][1] = 0;
		elems[3][2] = 0;
		elems[3][3] = 0;
	}
	
	public void loadOrtho(double left, double right, double bottom, double top, double near, double far)
	{
		loadZero();
		elems[0][0]=2/(right-left);
		elems[1][1]=2/(top-bottom);
		elems[2][2]=-2/(far-near);
		elems[0][3]=(right+left)/(right-left);
		elems[1][3]=(top+bottom)/(top-bottom);
		elems[2][3]=(far+near)/(far-near);
	}
	
	/**
	 * Sets the relavent values for collumn transform. See loadTransformCollumn
	 * 
	 * @param x X value
	 * @param y Y value
	 * @param z Z value
	 */
	public void setTransformCollumn(double x, double y, double z)
	{
		elems[0][3] = x;
		elems[1][3] = y;
		elems[2][3] = z;
	}
	
	/**
	 * Loads a transform matrix, with the following format:<br>
	 * <br>
	 * <code>
	 * [1 0 0 X]<br>
	 * [0 1 0 Y]<br>
	 * [0 0 1 Z]<br>
	 * [0 0 0 1]<br>
	 * </code>
	 * 
	 * @param x X value
	 * @param y Y value
	 * @param z Z value
	 */
	public void loadTransformCollumn(double x, double y, double z)
	{
		loadIdentity();
		setTransformCollumn(x,y,z);
	}
	
	/**
	 * Sets the relavent values for row transform. See loadTransformRow
	 * 
	 * @param x X value
	 * @param y Y value
	 * @param z Z value
	 */
	public void setTransformRow(double x, double y, double z)
	{
		elems[0][3] = x;
		elems[1][3] = y;
		elems[2][3] = z;
	}
	
	
	/**
	 * Sets the relavent values for row transform. See loadTransformRow
	 * 
	 * @param v Vector
	 */
	public void setTransformRow(Vector3 v)
	{
		setTransformRow(v.x,v.y,v.z);
	}
	
	/**
	 * Loads a transform matrix, with the following format:<br>
	 * <br>
	 * <code>
	 * [1 0 0 0]<br>
	 * [0 1 0 0]<br>
	 * [0 0 1 0]<br>
	 * [X Y Z 1]<br>
	 * </code>
	 * 
	 * @param x X value
	 * @param y Y value
	 * @param z Z value
	 */
	public void loadTransformRow(double x, double y, double z)
	{
		loadIdentity();
		setTransformRow(x,y,z);
	}
	
	/**
	 * Loads a transform matrix, with the following format:<br>
	 * <br>
	 * <code>
	 * [1 0 0 0]<br>
	 * [0 1 0 0]<br>
	 * [0 0 1 0]<br>
	 * [X Y Z 1]<br>
	 * </code>
	 * 
	 * @param v Vector containing x,y,and z
	 */
	public void loadTransformRow(Vector3 v)
	{
		loadTransformRow(v.x,v.y,v.z);
	}
	
	/**
	 * Loads a scale matrix with the following format:<br>
	 * <br><code>
	 * [X 0 0 0]<br>
	 * [0 Y 0 0]<br>
	 * [0 0 Z 0]<br>
	 * [0 0 0 1]<br>
	 * </code>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void loadScale(double x, double y, double z)
	{
		loadIdentity();
		elems[0][0] = x;
		elems[0][1] = y;
		elems[0][2] = z;
	}
	
	public void flipRows()
	{
		Mat44 tmp = new Mat44();
		tmp.add(this);
		
		elems[0][0] = tmp.elems[0][0];
		elems[0][1] = tmp.elems[1][0];
		elems[0][2] = tmp.elems[2][0];
		elems[0][3] = tmp.elems[3][0];
		elems[1][0] = tmp.elems[0][1];
		elems[1][1] = tmp.elems[1][1];
		elems[1][2] = tmp.elems[2][1];
		elems[1][3] = tmp.elems[3][1];
		elems[2][0] = tmp.elems[0][2];
		elems[2][1] = tmp.elems[1][2];
		elems[2][2] = tmp.elems[2][2];
		elems[2][3] = tmp.elems[3][2];
		elems[3][0] = tmp.elems[0][3];
		elems[3][1] = tmp.elems[1][3];
		elems[3][2] = tmp.elems[2][3];
		elems[3][3] = tmp.elems[3][3];
	}
	
	//TODO: rotation
	
	public Mat44 clone()
	{
		Mat44 other = new Mat44();
		other.elems[0][0] = elems[0][0];
		other.elems[0][1] = elems[0][1];
		other.elems[0][2] = elems[0][2];
		other.elems[0][3] = elems[0][3];
		other.elems[1][0] = elems[1][0];
		other.elems[1][1] = elems[1][1];
		other.elems[1][2] = elems[1][2];
		other.elems[1][3] = elems[1][3];
		other.elems[2][0] = elems[2][0];
		other.elems[2][1] = elems[2][1];
		other.elems[2][2] = elems[2][2];
		other.elems[2][3] = elems[2][3];
		other.elems[3][0] = elems[3][0];
		other.elems[3][1] = elems[3][1];
		other.elems[3][2] = elems[3][2];
		other.elems[3][3] = elems[3][3];
		return other;
	}
	
	public String toString()
	{
		return
				 "["+elems[0][0]+" "+elems[0][1]+" "+elems[0][2]+" "+elems[0][3]+"]\n"
				+"["+elems[1][0]+" "+elems[1][1]+" "+elems[1][2]+" "+elems[1][3]+"]\n"
				+"["+elems[2][0]+" "+elems[2][1]+" "+elems[2][2]+" "+elems[2][3]+"]\n"
				+"["+elems[3][0]+" "+elems[3][1]+" "+elems[3][2]+" "+elems[3][3]+"]\n";
	}
}
