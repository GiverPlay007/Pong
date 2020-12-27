package me.giverplay.pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener
{
  private static final long serialVersionUID = 1L;
  
  public static final int WIDTH = 160;
  public static final int HEIGHT = 120;
  public static final int SCALE = 3;
  
  private static Game game;
  private static JFrame frame;
  
  private boolean isRunning = false;
  private Thread thread;
  
  protected Player player;
  protected Enemy enemy;
  protected Bolinha bolinha;
  
  protected int jogador = 0;
  protected int inimigo = 0;
  
  private int currentFPS = 0;
  
  private BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  
  public Game()
  {
    setupFrame();
    start();
  }
  
  private void setupFrame()
  {
    setPreferredSize(new Dimension(SCALE * WIDTH, SCALE * HEIGHT));
    this.addKeyListener(this);
    
    frame = new JFrame("Tec tec tec tec tec tec tec tec tec");
    frame.add(this);
    frame.setResizable(false);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
    try
    {
      frame.setIconImage(ImageIO.read(this.getClass().getResource("/icon.png")).getSubimage(0, 0, 32, 32));
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args)
  {
    game = new Game();
  }
  
  public void restart()
  {
    instantiate();
  }
  
  private void instantiate()
  {
    player = new Player(100, HEIGHT - 5);
    enemy = new Enemy(100, 0, this);
    bolinha = new Bolinha(100, HEIGHT / 2 - 1, this);
  }
  
  public void start()
  {
    instantiate();
    isRunning = true;
    thread = new Thread(this);
    thread.start();
  }
  
  public void stop()
  {
    isRunning = false;
    
    try
    {
      thread.join();
    }
    catch(InterruptedException ignore) { }
    
    enemy = null;
    player = null;
    bolinha = null;
  }
  
  public void tick()
  {
    player.tick();
    enemy.tick();
    bolinha.tick();
  }
  
  public void render()
  {
    BufferStrategy bs = this.getBufferStrategy();
    
    if(bs == null)
    {
      this.createBufferStrategy(3);
      return;
    }
    
    Graphics g = layer.getGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    
    player.render(g);
    enemy.render(g);
    bolinha.render(g);
    
    g = bs.getDrawGraphics();
    g.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
    
    g.setColor(Color.WHITE);
    g.setFont(new Font("arial", Font.PLAIN, 10));
    g.drawString("FPS: " + currentFPS, 0, (HEIGHT * SCALE) - 5);
    g.drawString("Sua pontuação: " + jogador, 0, 16);
    g.drawString("Inimigo: " + inimigo, 0, 28);
    
    bs.show();
  }
  
  public static void print(Object object)
  {
    System.out.println(object);
  }
  
  @Override
  public void run()
  {
    requestFocus();
    
    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    
    double ticks = 60.0D;
    double ns = 1000000000 / ticks;
    double delta = 0.0D;
    
    int fps = 0;
    
    while(isRunning)
    {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      
      if(delta >= 1)
      {
        tick();
        render();
        
        delta--;
        fps++;
      }
      
      if(System.currentTimeMillis() - timer >= 1000)
      {
        currentFPS = fps;
        fps = 0;
        timer += 1000;
      }
    }
    
    stop();
  }
  
  @Override
  public void keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      if(player != null)
        player.right = true;
    }
    
    if(e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      if(player != null)
        player.left = true;
    }
  }
  
  @Override
  public void keyReleased(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      if(player != null)
        player.right = false;
    }
    
    if(e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      if(player != null)
        player.left = false;
    }
  }
  
  @Override
  public void keyTyped(KeyEvent e)
  {
  
  }
}
