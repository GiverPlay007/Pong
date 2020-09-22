package me.giverplay.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Bolinha
{
  
  public double x, y;
  public int width, height;
  
  public double dx, dy;
  public double speed = 2;
  
  private Game game;
  
  public Bolinha(int x, int y, Game game)
  {
    this.game = game;
    
    this.x = x;
    this.y = y;
    this.width = 5;
    this.height = 5;
    
    int angle = new Random().nextInt(120 - 45) + 45 + 1;
    dx = Math.cos(Math.toRadians(angle));
    dy = Math.sin(Math.toRadians(angle));
  }
  
  public void tick()
  {
    
    if(x + (dx * speed) + width >= Game.WIDTH)
    {
      dx *= -1;
    } else if(x + (dx * speed) < 0)
    {
      dx *= -1;
    }
    
    if(y >= Game.HEIGHT)
    {
      game.inimigo++;
      game.restart();
      
    }
    else if(y < 0)
    {
      game.jogador++;
      game.restart();
    }
    
    Rectangle bounds = new Rectangle((int) (x + (dx * speed)), (int) (y + (dy * speed)), width, height);
    
    Rectangle boundsPlayer = new Rectangle(game.player.x, game.player.y, game.player.width, game.player.height);
    Rectangle boundsEnemy = new Rectangle((int) game.enemy.x, (int) game.enemy.y, game.enemy.width, game.enemy.height);
    
    if(bounds.intersects(boundsPlayer))
    {
      int angle = new Random().nextInt(120 - 45) + 45 + 1;
      dx = Math.cos(Math.toRadians(angle));
      dy = Math.sin(Math.toRadians(angle));
      
      if(dy > 0)
        dy *= -1;
      
    }
    else if(bounds.intersects(boundsEnemy))
    {
      
      int angle = new Random().nextInt(120 - 45) + 45 + 1;
      dx = Math.cos(Math.toRadians(angle));
      dy = Math.sin(Math.toRadians(angle));
      
      if(dy < 0)
        dy *= -1;
    }
    
    x += dx * speed;
    y += dy * speed;
  }
  
  public void render(Graphics g)
  {
    g.setColor(Color.yellow);
    g.drawOval((int) x, (int) y, width, height);
  }
}
