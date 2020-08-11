
public class Camera {
private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object) {
		
		//Tweaning Algorithm
		x += ((object.getX() -x) - 1000/2) * 0.05f;
		y += ((object.getY() -y) - 563/2) * 0.05f;

		//Limiting the camera to the world
		if(x <= 0) x = 1;
		if(x >= 1055) x = 1055;
		if(y <= 0) y = 0;
		if(y >= 595) y = 595;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
