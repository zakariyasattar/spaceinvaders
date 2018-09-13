import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Laser extends GameObject {

    private Image image = ImageLoader.loadCompatibleImage("sprites/playerLaser.png");
    private Image master = ImageLoader.loadCompatibleImage("sprites/alienWiggleLaser2.png");
    private int direction = -1;
    private boolean masterLaser;
    BufferedImage img;
    private int speed = 5;

	public Laser(int x, int y, int w, int h, int direction, BufferedImage img, boolean master) {
        super(x, y, w, h);
        this.direction = direction;
        this.img = img;
        
	}

	public void update() {
		if(direction == -1) {
			getBounds().y+=speed;
		}
		if(direction == 1) {
			getBounds().y-=speed;
			
		}
    }
	public int getDirection() {
		return direction;
	}
	public void setMaster(boolean b) {
		masterLaser = b;
	}
	public boolean isMaster() {
		return masterLaser;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
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