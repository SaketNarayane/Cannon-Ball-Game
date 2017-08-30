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
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

class CannonGame extends Frame {

    public static void main (String [ ] args)
    {
	CannonGame world = new CannonGame ();
	
    }

    public static final int FrameWidth = 600;
    public static final int FrameHeight = 400;

    private int angle = 45;
    private String message = "Angle: " + angle;
    private CannonBall cannonBall = null;
    private Scrollbar slider;

    private class FireButtonListener implements ActionListener {
	public void actionPerformed (ActionEvent e) {
	    double radianAngle = angle * Math.PI / 180.0;
	    double sinAngle = Math.sin(radianAngle);
	    double cosAngle = Math.cos(radianAngle);
	    // create the cannon ball
	    cannonBall = new CannonBall (
					 20 + (int) (30 * cosAngle), dy(5+(int) (30 * sinAngle)),
					 5, 12 * cosAngle, -12 * sinAngle);
	    repaint(1);
	}
    }

    private class ScrollBarListener implements AdjustmentListener {
	public void adjustmentValueChanged (AdjustmentEvent e) {
	    angle = slider.getMaximum() - slider.getValue();
	    message = "Angle: " + angle;
	    repaint(1);
	}
    }

    public CannonGame () {
	setSize (FrameWidth, FrameHeight);
	setTitle ("Cannon Game");

	// add the fire button
	Button fire = new Button("fire");
	fire.addActionListener(new FireButtonListener());
	add ("North", fire);
	// add the scroll bar
	slider = new Scrollbar(Scrollbar.VERTICAL, angle, 5, 0, 90);
	slider.addAdjustmentListener(new ScrollBarListener());
	add ("East", slider);
        setVisible(true);
    }

    public static int dy (int y) {	return FrameHeight - y; }

    public void paint (Graphics g)
    {
	int x = 20;	// location of cannon
	int y = 15;
	double radianAngle = angle * Math.PI / 180.0;
	int lv = (int) (30 * Math.sin(radianAngle));
	int lh = (int) (30 * Math.cos(radianAngle));
	int sv = (int) (10 * Math.sin(radianAngle + Math.PI/2));
	int sh = (int) (10 * Math.cos(radianAngle + Math.PI/2));
	// draw cannon
	g.setColor(Color.green);
	g.drawLine(x, dy(y), x+lh, dy(y+lv));
	g.drawLine(x+lh, dy(y+lv), x+lh+sh, dy(y+lv+sv));
	g.drawLine(x+lh+sh, dy(y+lv+sv), x+sh, dy(y+sv));
	g.drawLine(x+sh, dy(y+sv), x, dy(y));
	g.drawOval(x-8, dy(y+10), 12, 12);
	// draw target
	g.setColor(Color.red);
	g.fillRoundRect(FrameWidth-100, dy(12), 50, 10, 6, 6);
	// draw cannonball
	if (cannonBall != null) { 
	    cannonBall.move();
	    cannonBall.paint(g);
	    try {
				Thread.sleep(20);
				} catch(InterruptedException e) { }
	    if (dy(cannonBall.y()) > 0) 
		repaint(1);
	    else {
		int targetX = FrameWidth - 100;
		if ((cannonBall.x() > targetX) && (cannonBall.x() < (targetX + 50)))
		    message = "You Hit It!";
		else
		    message = "Missed!";
		cannonBall = null;
	    }
	}
	g.drawString(message, FrameWidth/2, FrameHeight/2);
    }
}
