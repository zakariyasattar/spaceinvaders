import java.awt.*;
import java.awt.geom.*;

public class Barrier extends GameObject {

	private Image image = ImageLoader.loadCompatibleImage("sprites/barrier.png");
	boolean remove = false;
	boolean edge = false;
	private int pos = 1;

	public Barrier(int x, int y, int w, int h, int pos) {
		super(x, y, w, h);
		this.pos = pos;
	}

	public void update() {


	}

	public void setImage(Image img) {
		image = img;
	}

	public void remove() {
		remove = true;
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