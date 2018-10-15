import java.awt.Color;

public class General extends Point{

	public General(int x, int y,Color color) {
		super(x, y);
		this.color=color;
	}
	
	public void setxy(int x,int y){
		this.x=x;
		this.y=y;
	}

}
