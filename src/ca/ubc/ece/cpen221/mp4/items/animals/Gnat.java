package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Gnat extends LivingAnimals {

	private static final ImageIcon gnatImage = Util.loadImage("gnat.gif");

	private static final int STRENGTH = 30;


	public Gnat(Location initialLocation) {
		super.location = initialLocation;
		super.isDead = false;
		super.STRENGTH = STRENGTH;
		super.animalImage = gnatImage;
		super.animalName = "gnat";
		super.coolDown = 10;
	}





}
