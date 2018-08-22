import java.awt.*;
import java.awt.geom.*;

public class Laser extends GameObject {

    private Image image = ImageLoader.loadCompatibleImage("sprites/playerLaser.png");
    private int direction = -1;

	public Laser(int x, int y, int w, int h, int direction) {
        super(x, y, w, h);
        this.direction = direction;
	}

	public void update() {
		if(direction == -1) {
			getBounds().y++;
		}
		if(direction == 1) {
			getBounds().y--;
			
		}
    }
	public int getDirection() {
		return direction;
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