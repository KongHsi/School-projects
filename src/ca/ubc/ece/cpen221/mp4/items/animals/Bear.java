package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Bear extends LivingAnimals {

	private static final ImageIcon bearImage = Util.loadImage("bear.jpg");

	private static final int STRENGTH = 150;


	public Bear(Location initialLocation) {
		super.location = initialLocation;
		super.isDead = false;
		super.STRENGTH = STRENGTH;
		super.animalImage = bearImage;
		super.animalName = "bear";
		super.coolDown = 10;
	}




}
