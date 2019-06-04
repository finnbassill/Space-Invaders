import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements KeyListener, ActionListener
{
	//Constants for width and height
	private final int panelWidth = 1000;
	private final int panelHeight = 1000;
	private Timer timer = new Timer(20, this);
	
	private Ship ship;
	private Alien[] alien = new Alien[10];
	
	public Panel()
	{
		addKeyListener(this);
		
		this.setSize(panelWidth, panelHeight);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setLayout(null);
		
		timer.start();
		
		ship = new Ship(panelWidth/2, panelHeight - 100, 8, 20, 20);
		
		int ax = 10;
		int ay = 10;
		for (int i = 0; i < alien.length; i++)
		{
			alien[i] = new Alien(ax, ay, 3, 40, 40);
			ax += 100;
			if (i == 4)
			{
				ay += 100;
				ax = 10;
			}
		}
		
		
		
		
	}
		
		
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		//Clear the screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, panelWidth, panelHeight);
		
		//Drawing ship
		g.setColor(Color.RED);
		g.fillRect(ship.x, ship.y, ship.w, ship.h);
		
		//Move ship
		if (ship.moveLeft)
		{
			if (ship.x - ship.s >= - 3)
			{
				ship.x -= ship.s;
			}
		}
		if (ship.moveRight)
		{
			if (ship.x + ship.s <= panelWidth - 10)
			{
				ship.x += ship.s;
			}
		}

		
		
		
		
		
		
		
		
		
		
		
		
		//Draw aliens
		g.setColor(Color.BLUE);
		for (int i = 0; i < alien.length; i++)
		{
			g.fillRect(alien[i].x, alien[i].y, alien[i].w, alien[i].h);
		}
		
		//Move aliens left or right
		for (int i = 0; i < alien.length; i++)
		{
			 if (alien[i].moveRight)
			 {
				 alien[i].x += alien[i].s;
			 }
			 if (alien[i].moveLeft)
			 {
				 alien[i].x -= alien[i].s;
			 }	 
		}			 
					 
		//Move aliens down
		for (int i = 0; i < alien.length; i++)
		{
			if (alien[i].moveRight)
			{
				if (alien[i].x + alien[i].w >= panelWidth)
				 {
					 for (int j = 0; j < alien.length; j++)
					 {
						 alien[j].y += 100;
						 alien[j].moveRight = false;
						 alien[j].moveLeft = true;
					 }
					 i = alien.length;
				 }	
			}
			else
			{
				if (alien[i].x <= 0)
				 {
					 for (int j = 0; j < alien.length; j++)
					 {
						 alien[j].y += 100;
						 alien[j].moveRight = true;
						 alien[j].moveLeft = false;
					 }
					 i = alien.length;
				 }	
			}
		}	
		
					 
					 
		
		
		
		
		
					 
				 
					
			 
		
	}

	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == 37)
		{
			ship.moveLeft = true;
			ship.moveRight = false;
		}
		if (key == 39)
		{
			ship.moveRight = true;
			ship.moveLeft = false;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == 37)
		{
			ship.moveLeft = false;
		}
		if (key == 39)
		{
			ship.moveRight = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == timer)
		{
			repaint();
		}
	}
	
	
	
	
}
