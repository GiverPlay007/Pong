package me.giverplay.pong;

import java.awt.Color;
import java.awt.Graphics;

public class Player
{
	public boolean right = false;
	public boolean left = false;
	
	public int x, y;
	public int speed = 2;
	
	public int width = 40;
	public int height = 5;
	
	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
		if(right){
			x += speed;
		} else if(left){
			x -= speed;
		}
		
		if(x + width > Game.WIDTH){
			x = Game.WIDTH - width;
		}
		else if(x < 0){
			x = 0;
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
		
	}
}
