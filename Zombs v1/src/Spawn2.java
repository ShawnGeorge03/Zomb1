import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Spawn2 extends GameObject {

	private Handler handler;
	private Game game;

	private int hp = 200;
	
	private BufferedImage spawn_image;
	
	public Spawn2(int x, int y, ID id, Handler handler,Game game,SpriteSheet ss) {
		super(x, y, id, ss);	
		this.handler = handler;
		this.game = game;

		spawn_image = ss.grabImage(7, 1, 32, 32);
	}

	public void tick() {

		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeobject(tempObject);
				}
			}
			

		}
		
	}

	public void render(Graphics g) {
		g.drawImage(spawn_image, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle (x, y, 32, 32);
	}
	
	

}
