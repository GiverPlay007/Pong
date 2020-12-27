package me.giverplay.pong;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy
{
  private Game game;
  
  public double x, y;
  public int width, height;
  
  public Enemy(int x, int y, Game game)
  {
    this.x = x;
    this.y = y;
    this.width = 40;
    this.height = 5;
    this.game = game;
  }
  
  public void tick()
  {
    x += (game.bolinha.x - x - 6) * 0.08;
  }
  
  public void render(Graphics g)
  {
    g.setColor(Color.RED);
    g.fillRect((int) x, (int) y, width, height);
  }
}
