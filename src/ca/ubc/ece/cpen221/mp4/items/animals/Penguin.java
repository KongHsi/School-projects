package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Penguin extends LivingAnimals {

	private static final ImageIcon penguinImage = Util.loadImage("penguin.gif");

	private static final int STRENGTH = 110;


	public Penguin(Location initialLocation) {
		super.location = initialLocation;
		super.isDead = false;
		super.STRENGTH = STRENGTH;
		super.animalImage = penguinImage;
		super.animalName = "penguin";
		super.coolDown = 15;
	}




}
