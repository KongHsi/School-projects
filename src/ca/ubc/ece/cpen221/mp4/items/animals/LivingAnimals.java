package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

//This class is the superclass of all livingAnimals excluding fox and rabbit.
public class LivingAnimals implements LivingItem {

	protected ImageIcon animalImage = Util.loadImage("");
	protected int STRENGTH = 120;
	protected Location location;
	protected boolean isDead;
	protected String animalName = "";
	protected int coolDown = 0;
	@Override
	public void moveTo(Location targetLocation) {
		location = targetLocation;

	}

	@Override
	public int getMovingRange() {
		return 1;
	}

	@Override
	public ImageIcon getImage() {
		return animalImage;
	}

	@Override
	public String getName() {
		return "animalName";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public void loseEnergy(int energy) {
		isDead = true;
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public int getPlantCalories() {
		return 0;
	}

	@Override
	public int getMeatCalories() {
		return 0;
	}

	@Override
	public int getCoolDownPeriod() {
		return Util.RAND.nextInt(coolDown) + 1;
	}


	@Override
	public Command getNextAction(World world) {
		Direction dir = Util.getRandomDirection();
		Location targetLocation = new Location(this.getLocation(), dir);
		if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
			return new MoveCommand(this, targetLocation);
		}

		return new WaitCommand();
	}

	@Override
	public int getEnergy() {
		return 100;
	}

	@Override
	public LivingItem breed() {
		return null;
	}

	@Override
	public void eat(Food food) {

	}

}
