import java.awt.*;
import java.awt.geom.*;

public class PowerUp extends GameObject{

	private Image life = ImageLoader.loadCompatibleImage("sprites/powerup.png");
	private Image barrier = ImageLoader.loadCompatibleImage("sprites/barrier.png");
	private String powerup = "";

	public PowerUp(int x, int y, int w, int h, String powerup) {
		super(x, y, w, h);
		this.powerup = powerup;
	}
	public String getType () {
		return powerup;
	}

	public void update() {
		getBounds().y+=3;
	}

	//draw the image represented by the alien
	public void render(Graphics2D g) {
		if(powerup.equals("life")) {
			g.drawImage(life,
					(int)getBounds().x,
					(int)getBounds().y,
					(int)getBounds().width,
					(int)getBounds().height,
					null);
		}
		else if(powerup.equals("barrier")){
			g.drawImage(barrier,
					(int)getBounds().x,
					(int)getBounds().y,
					(int)getBounds().width,
					(int)getBounds().height,
					null);
		}
	}


}
