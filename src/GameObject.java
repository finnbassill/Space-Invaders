import java.awt.Rectangle;

public class GameObject 
{
	int x;
	int y;
	int s;
	int w;
	int h;
	
	Rectangle hitBox;
	
	public GameObject(int x, int y, int s, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.s = s;
		this.w = w;
		this.h = h;
		
		hitBox = new Rectangle(x, y, w, h);
	}
	
	public Rectangle getHitBox()
	{
		return hitBox;
	}
	
}
