package ca.ubc.ece.cpen221.mp4.items.plants;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.Gardener;

/**
 * Grass will by planted by the {@link Gardener} every step at an empty location
 * if fewer than half of all locations in the world are occupied.
 */
// This is a subclass of class Plants.
public class Sunflower extends Plants {
	private final static ImageIcon sunflowerImage = Util.loadImage("Sunflower.png");

	public Sunflower(Location location) {
		super.location = location;
		super.isDead = false;
		super.plantImage=sunflowerImage;
		super.stringName = "Sunflower";
		super.strength = 20;
	}
}