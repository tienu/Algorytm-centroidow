import java.awt.Color;

public abstract class Point {
	int x = 0;
	int y = 0;
Color color=Color.BLACK;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;

	}


	public int[] getxy(){
		int[] xy=new int[2];
		xy[0]=this.x;
		xy[1]=this.y;
		return(xy);
	}
	public int getx(){
		
		return(this.x);
	}
	public int gety(){
		return(this.y);
	}
	
	public Color getcol(){
	return this.color;	
	}

}
