import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Health extends GameObject{

	private Game game;
	private Handler handler;
	
	private BufferedImage health;

	
	public Health(int x, int y, ID id, Handler handler, Game game,SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		this.game = game;
		
		health = ss.grabImage(7, 2, 32, 32);
	}

	public void tick() {
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
		
			if(tempObject.getId() == ID.Player) {
				if(getBounds().intersects(tempObject.getBounds())) {
					if(game.hp < 20) {
						game.hp += (100 - game.hp);
						game.health = false;
						handler.removeobject(this);
					} else if(game.hp < 40) {
						game.hp += 30;
						game.health = false;
						handler.removeobject(this);
					} else if (game.hp < 80) {
						game.hp += 10;
						game.health = false;
						handler.removeobject(this);
					} else if (game.hp < 90) {
						game.hp += 0;
					}
				}
			}
		
		}
		
	}

	public void render(Graphics g) {
		g.drawImage(health, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
} 


