import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JFrame;

public class Game implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int WIDTH = 1000, HEIGHT = 563;
	public String title = "Zombs 1";
	
	public Thread thread;
	public boolean isRunning = false;
	
	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;

	private BufferedImage level = null;
	private BufferedImage sprite_sheet = null;
	private BufferedImage floor = null;
	
	public int time = 60;
	
	public int ammo = 100;
	public int hp = 100;
	public int score = 20;
	
	public int easy_enemy_count = 4;
	public int easy_enemy_killed = 0;
	
	public int hard_enemy_count = 4;
	public int hard_enemy_killed = 0;
	
	public boolean health = true;
	public boolean ammo_box = true;
	
	public int ticks = 0;
	public int fps = 0;
	
	public int spawnTimer = 4;
	public int frameShift = 5;
	
	JFrame game = new JFrame();

	public Game() throws InterruptedException {
		game.setSize(new Dimension(WIDTH, HEIGHT));
		game.setResizable(false);
		game.setTitle(title);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setLocationRelativeTo(null);
		game.setVisible(true);	
		
		start();
		
		handler = new Handler();
		camera = new Camera(0,0);
			
		game.addKeyListener(new KeyInput(handler));
		game.addMouseListener(new MouseInput(handler, camera, this, ss));
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/wizard_level.png");
		sprite_sheet = loader.loadImage("/3.png");
	
		ss = new SpriteSheet(sprite_sheet);
		floor = ss.grabImage(4, 2, 32, 32);
		
		loadLevel(level);		
	}
	
	private synchronized void start() {
		if(isRunning) return;
		
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	
	private synchronized void stop() {
		if(!isRunning) return;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		game.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				fps = frames;
				ticks = updates;
				frames = 0;
				updates = 0;
				if(time > 0) {
					time--;
				}
				
				if(time == 0) {
					render();			
					for(int i = frameShift; i >= 0; i-- ) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						frameShift = i;
					}
					
					if(frameShift == 0) {
						isRunning = false;
						game.dispose();
					}
				}				
			}
		}
		stop();
	}

	private void tick() {
		
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				camera.tick(handler.object.get(i));
			}
		}
		
		if(time != 0) handler.tick();
		
		if(fps > 30) {
			double rand  = Math.random();
			if(rand < 0.001) { //Least common
				handler.addobject(new Points(100,100,ID.Points,ss, handler, this));
			}else if(rand < 0.020) {
				if(easy_enemy_count == 0 && easy_enemy_killed > easy_enemy_count) {
					handler.addobject(new Enemy1(544,256,ID.Enemy1,handler,this, ss));
					handler.addobject(new Enemy1(1536,288,ID.Enemy1,handler,this, ss));
					handler.addobject(new Enemy1(544,704,ID.Enemy1,handler,this, ss));
					handler.addobject(new Enemy1(1600,704,ID.Enemy1,handler,this, ss));
					
					easy_enemy_killed = 0;
					easy_enemy_count = 4;	
				}
			} else if (rand < 0.120) {
				if(hard_enemy_count == 0 && hard_enemy_killed > hard_enemy_count) {
					handler.addobject(new Enemy2(544,256,ID.Enemy1,handler,this, ss));
					handler.addobject(new Enemy2(1536,288,ID.Enemy1,handler,this, ss));
					handler.addobject(new Enemy2(544,704,ID.Enemy1,handler,this, ss));
					handler.addobject(new Enemy2(1600,704,ID.Enemy1,handler,this, ss));
					
					hard_enemy_killed = 0;
					hard_enemy_count = 4;	
				}
			}else if(rand < 0.130) { // Most Common
				handler.addobject(new AmmoBox(900,768, ID.Health, handler, this,ss));
			}
		}
			
		
		
		if(hp < 10 && health == false) {
			handler.addobject(new Health(1024,288, ID.Health, handler, this, ss));
			health = true;
		}
				
		if(ammo < 10 && ammo_box == false) {
			handler.addobject(new AmmoBox(1024,768, ID.Health, handler, this,ss));
			ammo_box = true;
		}
		
		if(hp <= 0) {
			time = 0;
		}
	}
	
	private void render() {
		BufferStrategy bs = game.getBufferStrategy();
		if(bs == null) {
			game.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		//////////////////////////////////
		
		g.setColor(Color.red);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		for(int xx = 0; xx < 30*72; xx+=32) {
			for(int yy = 0; yy < 30*72; yy+=32) {
				g.drawImage(floor, xx, yy, null);
			}
		}
		
		handler.render(g);

		g2d.translate(camera.getX(), camera.getY());

		if(time != 0) {
			g.setColor(Color.gray);
			g.fillRect(5, 25, 200, 32);
			g.setColor(Color.green);
			g.fillRect(5, 25, hp*2, 32);
			g.setColor(Color.black);
			g.drawRect(5, 25, 200, 32);
			
			g.setColor(Color.white);
			g.setFont(new Font("Century Gothic", Font.PLAIN,16));
			g.drawString("Ammo : " + ammo, 5, 70);
			g.drawString("Score : " + score, 5, 90);
			
			g.drawString(String.valueOf(fps), WIDTH - 30, 32);
			g.drawString(String.valueOf(ticks), WIDTH - 30 , 62);
			
			if(time > 10) {
				String s = String.valueOf(time);
				int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				g.drawString(s, ( WIDTH - length)/2, 50);
			} else {
				g.setColor(Color.red);
				g.setFont(new Font("Century Gothic", Font.PLAIN,20));
				String s = String.valueOf(time);
				int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				g.drawString(s, ( WIDTH - length)/2, 50);
			}
		}
	
		if(time == 0 ) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.PLAIN,16));
			String s = "G A M E   O V E R";
			int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
			g.drawString(s, ( WIDTH - length)/2, HEIGHT/2);
			s = "Final Score  : " + score;
			length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
			g.drawString(s, ( WIDTH - length)/2, HEIGHT/2 + 30);
		}
		
		//////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < w; xx++ ) {
			for(int yy = 0; yy < h; yy++ ) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 && green == 0 && blue == 0) 
					handler.addobject(new Block(xx*32, yy*32, ID.Block,ss));
				
				if(red == 0 &&  green == 0 && blue == 255)
					handler.addobject(new Player(xx*32, yy*32, ID.Player, handler, this, ss));
				
				if(red == 0 &&  green == 255 && blue == 0)
					handler.addobject(new Enemy1(xx*32, yy*32, ID.Enemy1, handler,this, ss));
				
				if(red == 255 &&  green == 255 && blue == 0)
					handler.addobject(new Health(xx*32, yy*32, ID.Health, handler, this, ss));
				
				if(red == 128 &&  green == 128 && blue == 128)
					handler.addobject(new AmmoBox(xx*32, yy*32, ID.Ammo_Crate, handler,this,ss));
				
				if(red == 255 &&  green == 165 && blue == 0)
					handler.addobject(new Spawn1(xx*32, yy*32, ID.Spawn1, handler,this, ss));
				
				if(red == 140 &&  green == 0 && blue == 130)
					handler.addobject(new Spawn2(xx*32, yy*32, ID.Spawn2, handler, this, ss));
				
				if(red == 238 &&  green == 130 && blue == 238)
					handler.addobject(new Spawn3(xx*32, yy*32, ID.Spawn3, handler, this, ss));
			
				if(red == 0 &&  green == 255 && blue == 255)
					handler.addobject(new Spawn4(xx*32, yy*32, ID.Spawn4, handler, this, ss));
			}
		}
	}
}
