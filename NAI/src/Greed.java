import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Greed {

	ArrayList<Soldier> list;// = new ArrayList<Soldier>();
	HashMap<General, ArrayList<Soldier>> generals;// = new HashMap<General,
	PointView points; // ArrayList>();
	Random generator = new Random();
	boolean changed = true;
	int position = 2;
	int distposition = 1;

	public Greed() {
		newSoldier();

		generals = new HashMap<General, ArrayList<Soldier>>();
		int[] coords;
		coords = genxy();

		General newgen = new General(-5, -5, Color.BLACK);

		generals.put(newgen, list);
		String gen[] = { "2 general", "3 general", "4 general", "5 general", "6 general", "7 general", "8 general",
				"9 general" };
		String dist[] = {  "Manhatan","Euklides", "Akritean" };
		JComboBox cb = new JComboBox(gen);
		JComboBox cb2 = new JComboBox(dist);

		// newGenerals(5);
		// setgen();
		// scalegen();

		points = new PointView(generals);

		JButton start = new JButton("Start");
		JFrame frame = new JFrame("K-Srednie");
		JPanel north = new JPanel();

		GridLayout gl = new GridLayout(0, 3);

		// cb.setBounds(50, 50, 90, 20);
		north.setLayout(gl);
		north.add(start);
		north.add(cb);
		north.add(cb2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(points, BorderLayout.CENTER);
		frame.add(north, BorderLayout.NORTH);
		frame.setSize(400, 450);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		/*
		 * newGenerals(5); setgen(); scalegen(); points.updategen(generals);
		 * points.repaint(); newstart();
		 */
		Runnable ref = new Runnable() {

			@Override
			public void run() {
				refresh();
			}
		};
		Runnable play = new Runnable() {

			@Override
			public void run() {
				changed = true;
				newGenerals();
				setgen();
				scalegen();
				points.updategen(generals);
				points.repaint();
				newstart();

			}
		};

		// Thread paint = new Thread(play);

		Thread paint2 = new Thread(ref);
		// paint.start();
		// paint2.start();

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread paint = new Thread(play);
				paint.start();

			}
		});

		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				position = cb.getSelectedIndex() + 2;

			}
		});
		cb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				distposition = cb2.getSelectedIndex() + 1;

			}
		});
	}

	

	void refresh() {
		points.repaint();
	}

	void scalegen() {

		int sumx = 0;
		int sumy = 0;
		int num = 0;

		int oldX;
		int oldY;

		for (General g : generals.keySet()) {

			sumx = 0;
			sumy = 0;
			num = 0;
			oldX = g.getx();
			oldY = g.gety();
			if (generals.get(g).size() > 0) {
				for (Soldier s : generals.get(g)) {
					sumx += s.getx();
					sumy += s.gety();
					num++;
				}

				System.out.println("sumx " + sumx + "sumy" + sumy + "num" + num);
				g.setxy(sumx / generals.get(g).size() + 1, sumy / generals.get(g).size() + 1);

				// g.setxy(sumx / num, sumy / num);
				if (g.getx() == oldX && g.gety() == oldY) {
					this.changed = false;
				}

			}
		}

	}

	void setgen() {
		int countchange = 0;
		//
		for (Soldier sold : list) {
			

			for (General gen : generals.keySet()) {
				int mindist = distance(sold, sold.getgen());
				int dist = distance(sold, gen);

				if (dist < mindist) {
					sold.setgen(gen);

				}
			}

		}
		updatelistofsold();

	}

	void updatelistofsold() {

		for (General gen : generals.keySet()) {
			ArrayList<Soldier> genlist = new ArrayList<Soldier>();
			for (Soldier s : list) {
				if (s.getgen().equals(gen)) {
					genlist.add(s);
				}
			}
			generals.put(gen, genlist);

		}

	}

	int distanceeuclides(Soldier sold, General gen) {
		return (int) (Math.hypot(gen.getx() - sold.getx(), gen.gety() - sold.gety()));

	}

	int distancecity(Soldier sold, General gen) {
		return (int) (Math.abs(gen.getx() - sold.getx()) + Math.abs(gen.gety() - sold.gety()));

	}
	int distanceakritean(Soldier sold, General gen,int a){
		
		return distanceeuclides(sold, gen)*(1-a)+distancecity(sold, gen)*a;
	}

	int distance(Soldier sold, General gen) {
		if (this.distposition == 1) {
			return distancecity(sold, gen);
		} else if (this.distposition == 2) {
			return distanceeuclides(sold, gen);
		} else if (this.distposition == 3) {
			return distanceakritean(sold, gen,3);
		} else
			return 0;
	}

	void newstart() {
	

		while (this.changed) {
			setgen();
			scalegen();
			points.repaint();

			try {

				Thread.sleep(1000);
			} catch (InterruptedException e) { // TODO Auto-generated catch
												// block
				e.printStackTrace();
			}
			System.out.println("loop");
		}

		System.out.println("end loop");
	}

	void newSoldier() {
		int randpoint = 0;
		list = new ArrayList<Soldier>();
		int length = 0;
		for (int i = 1; i < 400; i += 2) {
			for (int j = 1; j < 400; j += 2) {

				randpoint = generator.nextInt(2);

				if (randpoint == 1) {
					list.add(new Soldier(i, j));
					length++;
				}
			}

		}
		System.out.println(length);
	}

	void newGenerals() {
		generals = new HashMap<General, ArrayList<Soldier>>();
		int[] coords;

		for (int i = 0; i < this.position; i++) {
			coords = genxy();
			General newgen = new General(coords[0], coords[1], getcol(i + 1));
			int newdistance = 0;

			generals.put(newgen, new ArrayList<Soldier>());
			for (Soldier sold : list) {
				sold.setgen(newgen);
			}
		}

	}

	int[] genxy() {

		int x = generator.nextInt(400);
		int y = generator.nextInt(400);
		int[] coords = { x, y };
		return (coords);
	}

	Color getcol(int no) {
		Color col = null;

		switch (no) {
		case 1:
			col = Color.BLUE;
			break;

		case 2:
			col = Color.RED;
			break;
		case 3:
			col = Color.GREEN;
			break;
		case 4:
			col = Color.CYAN;
			break;
		case 5:
			col = Color.ORANGE;
			break;
		case 6:
			col = Color.PINK;
			break;
		case 7:
			col = Color.GRAY;
			break;
		case 8:
			col = Color.YELLOW;
			break;
		case 9:
			col = Color.DARK_GRAY;
			break;

		}
		return col;
	}

}
