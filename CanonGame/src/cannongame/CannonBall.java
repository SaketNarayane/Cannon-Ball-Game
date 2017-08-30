/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cannongame;

/**
 *
 * @author nageshbhattu
 */
public class CannonBall extends Ball {
	
    public CannonBall (int sx, int sy, int r, double dx, double dy)
    {
	super(sx, sy, r);
	setMotion (dx, dy);
    }

    public void move ()
    {
	dy = dy + 0.3; // effect of gravity
	super.move();	// update the ball position
    }
}
