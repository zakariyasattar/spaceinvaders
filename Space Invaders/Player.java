/*
    Class that represents a player in the game
*/

import java.awt.*;
import java.awt.geom.*;

public class Player extends GameObject {

    //the image used for the player
    private Image image = ImageLoader.loadCompatibleImage("sprites/player.png");
    
    //keeps track of whether the player is moving to the left
    private boolean movingLeft = false;

	public Player(int x, int y, int w, int h) {
        super(x, y, w, h);
	}

	public void update() {
        //if we're moving to the left, decrease the player's boundary x by 1
        if(movingLeft)
            getBounds().x--;
    }
    
    //tell the player if they should be moving left or right
    public void setMovingLeft(boolean ml) {
        movingLeft = ml;
    }
    
    //draw the player with the player's image
	public void render(Graphics2D g) {

		g.drawImage(image,
                    (int)getBounds().x,
                    (int)getBounds().y,
                    (int)getBounds().width,
                    (int)getBounds().height,
                    null);

	}

}