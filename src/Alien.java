
public class Alien extends GameObject
{
	boolean moveRight;
	boolean moveLeft;
	boolean isVisible;
	
	public Alien(int x, int y, int s, int w, int h)
	{
		super(x, y, s, w, h);
		moveLeft = false;
		moveRight = true;
		isVisible = true;
		
		
		
	}
	
	
}
