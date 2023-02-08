package Create;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener
{
	static final int sc_wd=600; //screen_width
	static final int sc_ht=600; //screen_height
	static final int unit_size=30; //how big the objects in game should be
	static final int units=(sc_wd*sc_ht)/unit_size; //no of objects that can be fit in screen
	static final int delay=90; //timer to delay
	final int x[]=new int[units]; //x coordinates of snake's position
	final int y[]=new int[units]; //y coordinates of snake's position
	int bodyparts=6; //no of body parts of snake
	int fruiteaten; 
	int fruitX; //x coordinate of fruit
	int fruitY; //y coordinate of fruit
	char direction='R'; //direction snake goes in-start with right
	boolean running=false;
	Timer timer;
	Random random;

	public GamePanel() 
	{
		random =new Random();
		this.setPreferredSize(new Dimension(sc_wd,sc_ht)); //resize the component to the specified size
		this.setBackground(Color.black); //set background colour
		this.setFocusable(true); //indicates whether a component can gain the focus if it is requested to do so
		this.addKeyListener(new MyKeyAdapter()); //keyboard event is generated
		start(); //call start method
	}
	
	public void start()
	{
		addfruit();
		running=true;
		timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		if(running) 
		{
			/*
			 * //draw grid lines on screen
			for(int i=0;i<sc_ht/unit_size;i++)
			{
				g.drawLine(i*unit_size,0,i*unit_size,sc_ht); //x grid line
				g.drawLine(0,i*unit_size,sc_wd,i*unit_size); //y grid line
			}
			*/
			//fruit color
			g.setColor(Color.red); 
			g.fillOval(fruitX,fruitY,unit_size,unit_size);
		
			//snake color
			for(int i=0;i<bodyparts;i++)
			{
				if(i==0)
				{
					g.setColor(Color.green);
					g.fillRect(x[i],y[i],unit_size,unit_size);
				}
				else
				{
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i],y[i],unit_size,unit_size);
				}
			}
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("Serif",Font.BOLD,20));
		FontMetrics fm=getFontMetrics(g.getFont());
		g.drawString("Score: "+fruiteaten,(sc_wd-fm.stringWidth("Score: ")+fruiteaten)/2,g.getFont().getSize());
		}
		else
		{
			gameover(g);
		}
	}
	
	public void addfruit()
	{
		fruitX=random.nextInt((int)(sc_wd/unit_size))*unit_size;
		fruitY=random.nextInt((int)(sc_wd/unit_size))*unit_size;
	}
	
	public void move()
	{
		for(int i=bodyparts;i>0;i--) //shift all body parts of snake by 1
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) 
		{
		case 'U':
			y[0] = y[0] - unit_size;
			break;
		case 'D':
			y[0] = y[0] + unit_size;
			break;
		case 'L':
			x[0] = x[0] - unit_size;
			break;
		case 'R':
			x[0] = x[0] + unit_size;
			break;
		}
			
	}
	
	public void eatfruit()
	{
		if((x[0]==fruitX) &&(y[0]==fruitY))
		{
			bodyparts++;
			fruiteaten++;
			addfruit();
		}
		
	}
	
	public void checkcollisions()
	{
		//see if snake touches fruit
		for(int i=bodyparts;i>0;i--)
		{
			if((x[0]==x[i])&& (y[0]==y[i]))
				running=false;
		}
		
		//see if snake touches left border
		if(x[0]<0)
			running=false;
		//see if snake touches right border
		if(x[0]>sc_wd)
			running=false;
		//see if snake touches top border
		if(y[0]<0)
			running=false;
		//see if snake touches lower border
		if(y[0]>sc_ht)
			running=false;
		
		if(!running)
			timer.stop();
	}
	
	public void gameover(Graphics g)
	{
		//print score
		g.setColor(Color.BLUE);
		g.setFont(new Font("Serif",Font.BOLD,50));
		FontMetrics fm1=getFontMetrics(g.getFont());
		g.drawString("Score: "+fruiteaten,(sc_wd-fm1.stringWidth("Score: ")+fruiteaten)/2,g.getFont().getSize());
		
		//print text
		g.setColor(Color.red);
		g.setFont(new Font("Serif",Font.BOLD,75));
		FontMetrics fm=getFontMetrics(g.getFont());
		g.drawString("Game Over!",(sc_wd-fm.stringWidth("Game Over!"))/2,sc_ht/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(running)
		{
			move();
			eatfruit();
			checkcollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			switch(e.getKeyCode()) 
			{
			case KeyEvent.VK_LEFT:
				if(direction != 'R') 
				{
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') 
				{
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') 
				{
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') 
				{
					direction = 'D';
				}
				break;
			}
		}
	}
}
