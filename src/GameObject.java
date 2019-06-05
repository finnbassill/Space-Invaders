import java.awt.Rectangle;

public class GameObject 
{
	int x;
	int y;
	int s;
	int w;
	int h;
	
	public GameObject(int x, int y, int s, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.s = s;
		this.w = w;
		this.h = h;
	}
	
	public Rectangle getHitBox()
	{
		Rectangle hitbox = new Rectangle(x, y, w, h);
		return hitbox;
	}
	
}
