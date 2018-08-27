import java.awt.*;
import java.awt.geom.*;

public class MasterLaser extends GameObject {

    private Image master = ImageLoader.loadCompatibleImage("sprites/alienWiggleLaser2.png");
    private int direction = -1;
    Image img;

	public MasterLaser(int x, int y, int w, int h, int direction, Image img) {
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

		g.drawImage(master,
                    (int)getBounds().x,
                    (int)getBounds().y,
                    (int)getBounds().width,
                    (int)getBounds().height,
                    null);

	}

}