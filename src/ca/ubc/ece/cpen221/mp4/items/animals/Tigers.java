package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Tigers extends LivingAnimals {

	private static final ImageIcon tigerImage = Util.loadImage("tiger.gif");

	private static final int STRENGTH = 120;


	public Tigers(Location initialLocation) {
		super.location = initialLocation;
		super.isDead = false;
		super.STRENGTH = STRENGTH;
		super.animalImage = tigerImage;
		super.animalName = "tiger";
		super.coolDown = 5;
	}




}
