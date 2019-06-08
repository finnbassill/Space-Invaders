import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Panel extends JPanel implements KeyListener, ActionListener
{
	//Constants for width and height
	private final int panelWidth = 1000;
	private final int panelHeight = 1000;
	private Timer timer = new Timer(20, this);

		
	private Ship ship;
	
	private ArrayList<Alien> alien = new ArrayList<Alien>();
	
	private ArrayList<Bomb> bomb = new ArrayList<Bomb>();
	
	private ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	
	boolean canFireBomb;
	boolean playing;
	private int score;
	private int lives;
	private int rounds;
	private int maxBombs;
	
	public Panel()
	{
		addKeyListener(this);
		
		this.setSize(panelWidth, panelHeight);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setLayout(null);
		playing = false;
		maxBombs = 5;
		timer.start();

		
		
	}
		
	private void setup()
	{	
		canFireBomb = true;
		ship = new Ship(panelWidth/2, panelHeight - 100, 8, 20, 20);
		
		int ax = 10;
		int ay = 40;
		for (int i = 0; i < 10; i++)
		{
			alien.add(new Alien(ax, ay, 3, 40, 40));
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
		
		//Show score
		g.setColor(Color.black);
		g.drawString("Score: " + score, 20, 20);
		g.drawString("Round: " + rounds, 100, 20);
		g.drawString("Lives: " + lives, 180, 20);
		//Start Screen
		if (!playing)
		{
			g.drawString("Press space to start", 450, 500);
		}
		
		//In game
		if (playing)
		{
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
			for (int i = 0; i < alien.size(); i++)
			{
				g.fillRect(alien.get(i).x, alien.get(i).y, alien.get(i).w, alien.get(i).h);
			}
			
			//Move aliens left or right
			for (int i = 0; i < alien.size(); i++)
			{
				 if (alien.get(i).moveRight)
				 {
					 alien.get(i).x += alien.get(i).s;
				 }
				 if (alien.get(i).moveLeft)
				 {
					 alien.get(i).x -= alien.get(i).s;
				 }	 
			}			 
						 
			//Move aliens down
			for (int i = 0; i < alien.size(); i++)
			{
				if (alien.get(i).moveRight)
				{
					if (alien.get(i).x + alien.get(i).w >= panelWidth)
					 {
						 for (int j = 0; j < alien.size(); j++)
						 {
							 alien.get(j).y += 100;
							 alien.get(j).moveRight = false;
							 alien.get(j).moveLeft = true;
						 }
						 i = alien.size();
					 }	
				}
				else
				{
					if (alien.get(i).x <= 0)
					 {
						 for (int j = 0; j < alien.size(); j++)
						 {
							 alien.get(j).y += 100;
							 alien.get(j).moveRight = true;
							 alien.get(j).moveLeft = false;
						 }
						 i = alien.size();
					 }	
				}
			}	
			
			//Draw bullets
			g.setColor(Color.black);
			for(Bullet i : bullet)
			{
				g.fillRect(i.x, i.y, i.w, i.h);
			}
						
			//Move bullets
			for (int i = 0; i < bullet.size(); i++)
			{
				bullet.get(i).y -= bullet.get(i).s;
				if (bullet.get(i).y <= 0)
				{
					bullet.remove(i);
				}
			}
	
			//Check if alien is hit
			
			for (int j = 0; j < bullet.size(); j++)
			{
				for (int i = 0; i < alien.size();i++)
				{
					if (alien.get(i).getHitBox().intersects(bullet.get(j).getHitBox()))
					{
						score += 10;
						bullet.remove(j);
						alien.remove(i);
						i = alien.size();
					}
				}
				if (alien.size() == 0)
				{
					score += 500;
					rounds++;
					bomb.clear();
					bullet.clear();
					alien.clear();
					setup();
				}
			}
			
			
			//Drop bomb
			if (canFireBomb)
			{
				 int i = (int)(Math.random() * alien.size());
				 bomb.add(new Bomb(alien.get(i).x + (alien.get(i).w / 2), alien.get(i).y + alien.get(i).h, 8, 2, 10));	
				 if (bomb.size() == maxBombs)
				 {
					 canFireBomb = false;
				 }
			}
			
			//Draw bomb
			g.setColor(Color.black);
			for (int i = 0; i < bomb.size(); i++)
			{
				g.fillRect(bomb.get(i).x, bomb.get(i).y, bomb.get(i).w, bomb.get(i).h);
			}
			
			//Move bomb
			for (int i = 0; i < bomb.size(); i++)
			{
				bomb.get(i).y += bomb.get(i).s;
				if (bomb.get(i).y >= panelHeight)
				{
					bomb.remove(i);
					canFireBomb = true;
				}
			}
			
			//Check if ship is hit
			for (int i = 0; i < bomb.size(); i++)
			{
				if (bomb.get(i).getHitBox().intersects(ship.getHitBox()))
				{
					lives--;
					alien.clear();
					bomb.clear();
					setup();
					if (lives < 0)
					{
						lives++;
						playing = false;
						bullet.clear();
					}
				}
			}
		}
	} 
	
	
	
	

	public boolean isHit(Rectangle a, Rectangle b)
	{
		if (a.x > b.x + b.width || b.x > a.x + a.width)
		{
			return false;
		}
		if (a.y > b.y + b.height || b.y > a.y + a.height)
		{
			return false;
		}
		return true;
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
		if (key == 38)
		{
			if (playing)
			{
				bullet.add(new Bullet(ship.x + ship.w / 2, ship.y - 5, 10, 2, 5));
			}
		}
		if (key == 32)
		{
			if (!playing)
			{
				setup();
				canFireBomb = true;
				score = 0;
				lives = 2;
				rounds = 1;	 
				playing = true;
			}
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
