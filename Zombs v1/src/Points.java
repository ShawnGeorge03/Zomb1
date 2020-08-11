import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Points extends GameObject {

	private Game game;
	private Handler handler;
	
	private BufferedImage points;
	
	public Points(int x, int y, ID id, SpriteSheet ss, Handler handler, Game game) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		
		points = ss.grabImage(8, 1, 32, 32);

		
	}

	
	public void tick() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.score +=20;
					handler.removeobject(this);
				}
			}
		}
	}


	
	public void render(Graphics g) {
		g.drawImage(points, x, y, null);

	}

	
	public Rectangle getBounds() {
		return new Rectangle (x, y, 32, 32);
	}

	

}
