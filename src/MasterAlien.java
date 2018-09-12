import java.awt.*;
import java.awt.geom.*;

public class MasterAlien extends Alien{
	/*
    This class represents a basic alien in the game
	 */
	private Image image = ImageLoader.loadCompatibleImage("sprites/mysteryShip.png");
	private int updateCounter = 0;
	private int shootCountDown = 20;
	boolean edge = false;
	private boolean master = true;

	public MasterAlien(int x, int y, int w, int h, boolean master) {
		super(x, y, w, h, master, false);
	}

	public void update() {
		
		if(getBounds().x < 755 && edge == false) {
			getBounds().x += 0.5;
			if(getBounds().x == 755) {
				edge = true;
				getBounds().y += getBounds().height*1.125;
			}
		}
		else if(getBounds().x > 0 && edge == true) {
			getBounds().x -= 0.5;
			if(getBounds().x == 0) {
				edge = false;
				getBounds().y += getBounds().height*1.125;
			}
		}




		if(shootCountDown > 0) {
			shootCountDown--;
		}

	}
	public boolean isMaster() {
		return true;
	}
	public Laser shoot() {
		if(shootCountDown <= 0) {
			shootCountDown = 400;
			return new Laser((int)getBounds().x--, (int)(getBounds().height+getBounds().y), 6, 12, -1, ImageLoader.loadCompatibleImage("sprites/bossLaser.png"), true);
		}
		else
			return null;
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

