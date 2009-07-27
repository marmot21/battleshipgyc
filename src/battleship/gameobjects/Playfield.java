package battleship.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import battleship.EventManager;

public class Playfield extends GameObject
{
	private Dimension gridSize;
	private int[][] grid;
	private Point xy = new Point(-1, -1);
	private MouseEvent mouse;
	private boolean mousePressed = false;
	
	public Playfield(Point p, Dimension d, Dimension g)
	{
		super(new Rectangle(p.x, p.y, d.width*g.width+1, d.height*g.height+1));
		gridSize = g;
		grid = new int[d.width][d.height];
		
		//lol
		grid[1][1] = 1;
		grid[2][1] = 1;
		grid[3][1] = 1;
		grid[2][2] = 1;
		grid[2][3] = 1;
		grid[1][4] = 1;
		grid[2][4] = 1;
		grid[3][4] = 1;
		grid[5][1] = 1;
		grid[5][2] = 1;
		grid[5][3] = 1;
		grid[5][4] = 1;
		grid[6][2] = 1;
		grid[7][3] = 1;
		grid[8][1] = 1;
		grid[8][2] = 1;
		grid[8][3] = 1;
		grid[8][4] = 1;
		grid[10][1] = 1;
		grid[11][1] = 1;
		grid[12][1] = 1;
		grid[11][2] = 1;
		grid[11][3] = 1;
		grid[11][4] = 1;
		grid[14][1] = 1;
		grid[14][2] = 1;
		grid[14][3] = 1;
		grid[14][4] = 1;
		grid[15][1] = 1;
		grid[15][3] = 1;
		grid[16][1] = 1;
		grid[16][2] = 1;
		grid[16][3] = 1;
		
		render();
	}


	@Override
	public void update()
	{
		if(mousePressed && xy.x >= 0 && xy.x < r.width/gridSize.width && xy.y >= 0 && xy.y < r.width/gridSize.height)
		{
			if(mouse.getButton() == MouseEvent.BUTTON1)
				grid[xy.x][xy.y]++;
			else if(mouse.getButton() == MouseEvent.BUTTON3)
				grid[xy.x][xy.y]--;
			render();
		}
	}
	
	@Override
	public void render()
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, r.width, r.height);
		g.setColor(Color.BLACK);
		for(int i = 0; i < r.width/gridSize.width+1; i++)
		{
			g.drawLine(i*gridSize.width, 0, i*gridSize.width, r.height);
		}
		for(int i = 0; i < r.height/gridSize.height+1; i++)
		{
			g.drawLine(0, i*gridSize.height, r.width, i*gridSize.height);
		}
		for(int i = 0; i < r.width/gridSize.width; i++)
		{
			for(int j = 0; j < r.height/gridSize.height; j++)
			{
				if(grid[i][j] > 0)
				{
					g.setColor(Color.PINK);
					g.fillRect(i*gridSize.width+1, j*gridSize.height+1, gridSize.width-1, gridSize.height-1);
					g.setColor(Color.BLACK);
					g.drawString(""+grid[i][j], i*gridSize.width+1, j*gridSize.height+10);
				}
			}
		}
		if(xy.x >= 0)
		{
			g.setColor(new Color(255, 0, 0, 128));
			g.fillRect(xy.x*gridSize.width, 0, gridSize.width, r.height);
		}
		if(xy.y >= 0)
		{
			g.setColor(new Color(0, 0, 255, 128));
			g.fillRect(0, xy.y*gridSize.height, r.width, gridSize.height);
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);
	}

	@Override
	public EventManager getEvents()
	{
		return new EventManager();
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).event.startsWith("mouse"))
			{
				MouseEvent me = (MouseEvent) em.get(i).param;
				if(em.get(i).event.equals("mouseMoved") || em.get(i).event.equals("mouseDragged"))
				{
					if(r.contains(me.getPoint()))
					{
						xy.x = me.getX()/gridSize.width - r.x/gridSize.width;
						xy.y = me.getY()/gridSize.height - r.y/gridSize.height;
					}
					else
					{
						xy.x = -1;
						xy.y = -1;
					}
					render();
				}
				else if(em.get(i).event.equals("mousePressed"))
				{
					mousePressed = true;
					mouse = me;
				}
				else if(em.get(i).event.equals("mouseReleased"))
				{
					mousePressed = false;
				}
				else if(em.get(i).event.equals("mouseClicked"))
				{
					
				}
			}
		}
	}
}
