import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Laser extends GameObject {

    private Image image = ImageLoader.loadCompatibleImage("sprites/playerLaser.png");
    private Image master = ImageLoader.loadCompatibleImage("sprites/alienWiggleLaser2.png");
    private int direction = -1;
    BufferedImage img;

	public Laser(int x, int y, int w, int h, int direction, BufferedImage img) {
        super(x, y, w, h);
        this.direction = direction;
        this.img = img;
	}

	public void update() {
		if(direction == -1) {
			getBounds().y+=5;
		}
		if(direction == 1) {
			getBounds().y-=5;
			
		}
    }
	public int getDirection() {
		return direction;
	}
    //draw the image represented by the alien
	public void render(Graphics2D g) {

		g.drawImage(img,
                    (int)getBounds().x,
                    (int)getBounds().y,
                    (int)getBounds().width,
                    (int)getBounds().height,
                    null);

	}

}