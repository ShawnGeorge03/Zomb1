import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy1 extends GameObject {

	private Handler handler;
	private Game game;

	Random r = new Random();
	int choose = 0;
	int hp = 100;
	
	private BufferedImage[] enemy = new BufferedImage[3];
	Animation anim;
	
	public Enemy1(int x, int y, ID id, Handler handler,Game game, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		this.game = game;
		
		enemy[0] =  ss.grabImage(4, 1, 32, 32);
		enemy[1] =  ss.grabImage(5, 1, 32, 32);
		enemy[2] =  ss.grabImage(6, 1, 32, 32);

		anim = new Animation(3,enemy[0],enemy[1] ,enemy[2]);

	}
	
	public void tick() {
		x+= velX;
		y += velY;
		
		choose = r.nextInt(10);
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBigBounds().intersects(tempObject.getBounds())) {
					x += (velX * 5) * -1;
					y += (velY * 5) * -1;
					velX *= -1;
					velY *= -1;
				}else if(choose == 0) {
					velX = (r.nextInt(4- -4) + -4);
					velY = (r.nextInt(4- -4) + -4);
					
					if(y <= 0 || y >= Game.HEIGHT - 48) velY *= -1;
					if(x <= 0 || x >= Game.WIDTH - 24) velX *= -1;
				} 
			}
			
			if(tempObject.getId() == ID.Spawn1 || tempObject.getId() == ID.Spawn2 || tempObject.getId() == ID.Spawn3 || tempObject.getId() == ID.Spawn4) {
				if(getBigBounds().intersects(tempObject.getBounds())) {
					x += (velX * 5) * -1;
					y += (velY * 5) * -1;
					velX *= -1;
					velY *= -1;
				}else if(choose == 0) {
					velX = (r.nextInt(4- -4) + -4);
					velY = (r.nextInt(4- -4) + -4);
					
					if(y <= 0 || y >= Game.HEIGHT - 48) velY *= -1;
					if(x <= 0 || x >= Game.WIDTH - 24) velX *= -1;
				} 
			}
			
			if(tempObject.getId() == ID.Bullet) {
				if(getBigBounds().intersects(tempObject.getBounds())) {
					hp -= 50;
					if(hp <= 0) {
						handler.removeobject(this);
						game.score ++;
						game.easy_enemy_count--;
						game.easy_enemy_killed++;
						game.hard_enemy_count--;
						game.hard_enemy_killed++;
					}
					handler.removeobject(tempObject);
				}
			}
		}
		
		anim.runAnimation();

	}
	
	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	public Rectangle getBigBounds() {
		return new Rectangle(x - 16, y - 16, 64, 64);
	}

	
}
