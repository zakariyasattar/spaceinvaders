import java.awt.*;
import java.awt.geom.*;

public class Barrier extends GameObject {

	private Image image = ImageLoader.loadCompatibleImage("sprites/barrier.png");
	private int timer = 0;
	boolean edge = false;

	public Barrier(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void update() {
		if(getBounds().x < 755 && edge == false) {
			getBounds().x += 1;
			if(getBounds().x == 755) {
				edge = true;
				getBounds().x -= 1;
			}
		}
		else if(getBounds().x > 0 && edge == true) {
			getBounds().x -= 1;
			if(getBounds().x == 0) {
				edge = false;
				getBounds().x -= 1;
			}
		}
	}
	
	public void setImage(Image img) {
		image = img;
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