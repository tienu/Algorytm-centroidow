import java.awt.Color;

public class Soldier extends Point {
	int gen=1;
	
	General general=null;
	
	public Soldier(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public void setgen(General gen) {
		this.general=gen;
		this.color=this.general.getcol();
		
	}
	public void setcolor(Color color) {
		this.color = color;
	}
	public General getgen(){
		return this.general;
	}
	
	
}
