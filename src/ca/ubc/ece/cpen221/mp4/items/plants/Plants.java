package ca.ubc.ece.cpen221.mp4.items.plants;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.Item;

//This class is the superclass of all plants.
public class Plants implements Item {
	protected ImageIcon plantImage = Util.loadImage("");

	protected Location location;
	protected boolean isDead;
	protected String stringName = "";
	protected int strength = 10;
	/**
	 * Plant a Grass at <code> location </code>. The location must be valid and
	 * empty
	 *
	 * @param location
	 *            : the location where this plant will be created
	 */

	@Override
	public ImageIcon getImage() {
		return plantImage;
	}

	@Override
	public String getName() {
		return "plantName";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getPlantCalories() {
		return 10;
	}

	@Override
	public int getMeatCalories() {
		return 0;
	}

	@Override
	public void loseEnergy(int energy) {
		// Dies if loses energy.
		isDead = true;
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public int getStrength() {
		return strength;
	}

}