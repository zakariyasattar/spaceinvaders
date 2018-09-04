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
		if(pos == 1) {
			if(getBounds().x < 730 && edge == false) {
				getBounds().x += 1;
				if(getBounds().x == 730 ) {
					edge = true;
				}
			}
			else if(getBounds().x > 600 && edge == true) {
				getBounds().x -= 1;
				if(getBounds().x == 600) {
					edge = false;
					getBounds().x -= 1;
				}
			}
		}
		
		else if(pos == 2) {
			if(getBounds().x < 530 && edge == false) {
				getBounds().x += 1;
				if(getBounds().x == 530 ) {
					edge = true;
				}
			}
			else if(getBounds().x > 400 && edge == true) {
				getBounds().x -= 1;
				if(getBounds().x == 400) {
					edge = false;
					getBounds().x -= 1;
				}
			}
		}
		
		else if(pos == 3) {
			if(getBounds().x < 330 && edge == false) {
				getBounds().x += 1;
				if(getBounds().x == 330 ) {
					edge = true;
					getBounds().x -= 1;
				}
			}
			else if(getBounds().x > 200 && edge == true) {
				getBounds().x -= 1;
				if(getBounds().x == 200) {
					edge = false;
					getBounds().x -= 1;
				}
			}
		}
		else if(pos == 4) {
			if(getBounds().x < 130 && edge == false) {
				getBounds().x += 1;
				if(getBounds().x == 130 ) {
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