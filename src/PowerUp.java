import java.awt.*;
import java.awt.geom.*;

public class PowerUp extends GameObject{

	private Image image = ImageLoader.loadCompatibleImage("sprites/powerup.png");

	public PowerUp(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void update() {
		getBounds().y+=1;
	}
	
	//draw the image represented by the alien
	public void render(Graphics2D g) {

		g.drawImage(image,
				(int)getBounds().x,
				(int)getBounds().y,
				(int)getBounds().width,
				(int)getBounds().height,
				null);
	}


}
