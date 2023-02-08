package Create;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	GameFrame()
	{
		this.add(new GamePanel());
		this.setTitle("Snake Game"); //title on top of window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//to close your window
		this.setResizable(false);//user can't resize window
		this.pack();//sizes the frame so that all its contents are at or above their preferred sizes
		this.setVisible(true);//makes the frame appear on screen
		this.setLocationRelativeTo(null);//window will appear in the centre of screen
	}

}
