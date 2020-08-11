import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class AmmoBox extends GameObject{

	private Game game;
	private Handler handler;
	
	private BufferedImage crate;
	
	public AmmoBox(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		
		crate = ss.grabImage(6, 2, 32, 32);

	}
	
	public void tick() {
				
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.ammo += 20;
					game.ammo_box = false;
					handler.removeobject(this);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(crate, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
} 


