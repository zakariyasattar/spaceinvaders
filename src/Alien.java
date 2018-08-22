/*
    This class represents a basic alien in the game
*/

import java.awt.*;
import java.awt.geom.*;

public class Alien extends GameObject {

    private Image image = ImageLoader.loadCompatibleImage("sprites/alienA1.png");
    private int updateCounter = 0;
    private int shootCountDown = (int) ((Math.random() + 0) * 240);
    boolean edge = false;
    private boolean master = false;
    
	public Alien(int x, int y, int w, int h, boolean master) {
        super(x, y, w, h);
        master = false;
	}
	public boolean isMaster() {
		return master;
	}

	public void update() {
    
        //keep track of how many times this Alien has been updated
        updateCounter++;
        
        //every 120th update, move the bounds of the alien half it's width to the right
        
        if(getBounds().x < 755 && edge == false) {
        	getBounds().x += 0.5;
        	if(getBounds().x == 755) {
        		edge = true;
        		getBounds().y += getBounds().height*2;
        	}
        }
        else if(getBounds().x > 0 && edge == true) {
        	getBounds().x -= 0.5;
        	if(getBounds().x == 0) {
        		edge = false;
        		getBounds().y += getBounds().height*2;
        	}
        }
        
        
        if(shootCountDown > 0) {
        	shootCountDown--;
        }
    
    }
	public Laser shoot() {
		if(shootCountDown <= 0) {
			shootCountDown = (int) ((Math.random() + 0) * 240);
			return new Laser((int)getBounds().x, (int)(getBounds().height+getBounds().y), 4, 12, -1);
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