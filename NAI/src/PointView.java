import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


import javax.swing.JPanel;
import javax.swing.Timer;

public class PointView extends JPanel {

	HashMap<General, ArrayList<Soldier>> generals;
	
	Graphics2D g2d;
	
	public PointView(HashMap<General, ArrayList<Soldier>> generals) {
		
		this.generals = generals;
	}
	public void updategen(HashMap<General, ArrayList<Soldier>> generals){
		this.generals = generals;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ArrayList<Soldier> list;
		g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLACK);
		
		for (General gen : generals.keySet()) {
			
			list = generals.get(gen);
			for (Soldier s : list) {
				
				g2d.setColor(s.getcol());
				g2d.drawLine(s.getx(), s.gety(), s.getx(), s.gety());

			}
			g2d.setColor(Color.BLACK);
			g2d.drawOval(gen.getx(), gen.gety(), 5, 5);

		}

	}

	

}