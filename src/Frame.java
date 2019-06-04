import javax.swing.JFrame;

public class Frame extends JFrame
{
	private Panel panel;
	
	public Frame() 
	{
		//Creates title bar text
		super("Space Invaders");
		
		
		
		//Exits program when close button hit
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Created an instance of the panel object, and add to frame object
		panel = new Panel();
		panel.setDoubleBuffered(true);
		this.getContentPane().add(panel);
		
		//Set size and position
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		
	}
	
	public static void main(String[] args) 
	{
		Frame frame = new Frame();
		frame.setVisible(true);
	}
	
	
}
