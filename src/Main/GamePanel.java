package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private float xDir = 0.1f, yDir = 0.1f;
	private int frames = 0;
	private long lastCheck = 0;
	private Color color = new Color(150, 20, 90);
	private Random random;
	
	
	public GamePanel() {
		
		random = new Random();
		mouseInputs = new MouseInputs(this);
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	public void changeXDelta(int value) {
		this.xDelta += value;
	}
	
	public void changeYDelta(int value) {
		this.yDelta += value;
	}
	
	public void setRecPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		updateRactangle();
		g.setColor(color);
		g.fillRect((int)xDelta, (int)yDelta, 200, 50);
		

		
	}

	private void updateRactangle() {
		xDelta+= xDir;
		if(xDelta > 400 || xDelta < 0) {
			xDir *=-1;
			color = getRndColor();
		}
		
		yDelta+= yDir;
		if(yDelta > 400 || yDelta < 0) {
			yDir *=-1;
			color = getRndColor();
		}
		
	}

	private Color getRndColor() {
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return new Color(r,g,b);
	}
}
