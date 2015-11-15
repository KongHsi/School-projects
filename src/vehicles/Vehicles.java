package vehicles;

import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

//This is the superclass of all vehicles.
public class Vehicles implements MoveableItem, Actor, ArenaAnimal {
	protected Location location;
	protected ImageIcon vehicleImage = Util.loadImage("");

	protected boolean isDead = false;
	protected int strength = 100;
	protected String name = "";

	private int currentCoolDown = 10;
	private boolean acceleratingFlag = true;
	private Direction currentDirection = Direction.EAST;

	@Override
	public ImageIcon getImage() {
		return vehicleImage;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getStrength() {
		return strength;
	}

	@Override
	public void loseEnergy(int energy) {
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
	public void moveTo(Location targetLocation) {
		location = targetLocation;

	}

	@Override
	public int getMovingRange() {
		return 1;
	}

	@Override
	public int getCoolDownPeriod() {
		return currentCoolDown;
	}

	// This method decides the next action of the vehicle
	// requires: world not null
	// effects: return a command of next action
	// modifies: currentCoolDown, acceleratingFlag, isDead
	@Override
	public Command getNextAction(World world) {

		Location toLoc = location;

		// First accelerate then decelerate
		if ((currentCoolDown > 2) && (acceleratingFlag == true))
			currentCoolDown--;
		else if (currentCoolDown == 2)
			acceleratingFlag = false;
		if ((currentCoolDown < 10) && (acceleratingFlag == false))
			currentCoolDown++;
		else if (currentCoolDown == 10)
			acceleratingFlag = true;
		// turns randomly only when it moves slowly
		if (currentCoolDown > 8) {
			double random = Math.random();
			if (currentDirection == Direction.NORTH){
				if (random < 0.3)
					currentDirection = Direction.WEST;
				else if (random < 0.7)
					currentDirection = Direction.EAST;
			}
			else if (currentDirection == Direction.SOUTH){
				if (random < 0.3)
					currentDirection = Direction.WEST;
					else if (random < 0.7)
					currentDirection = Direction.EAST;
			}
			else if (currentDirection == Direction.EAST){
				if (random < 0.3)
					currentDirection = Direction.NORTH;
						else if (random < 0.7)
					currentDirection = Direction.SOUTH;
			}
			else if (currentDirection == Direction.WEST){
				if (random < 0.3)
					currentDirection = Direction.NORTH;
							else if (random < 0.7)
					currentDirection = Direction.SOUTH;
		}
		}

		toLoc = new Location(this.getLocation(), currentDirection);
		// check if there is any other item blocks the way of the vehicle
		if (!Util.isLocationEmpty(world, toLoc)) {
			Set<Item> surroundingSet = world.searchSurroundings(toLoc, 0);
			// check which item or vehicle itself will be destroyed
			for (Item x : surroundingSet) {
				Item sameLoc = x;
				if (sameLoc.getStrength() < this.strength)
					sameLoc.loseEnergy(200);
				else if ((sameLoc.getStrength() >= this.strength))
					isDead = true;
			

			if (sameLoc.getStrength() < this.strength)
				sameLoc.loseEnergy(200);
			else if (sameLoc.getStrength() >= this.strength)
				isDead=true;
			}
		}
		// vehicle will be destroyed if it goes out of the bound
		if (!Util.isValidLocation(world, toLoc))
			isDead = true;
		return new MoveCommand(this, toLoc);
	}

	@Override
	public int getEnergy() {
		return 0;
	}

	@Override
	public LivingItem breed() {
		return null;
	}

	@Override
	public void eat(Food food) {

	}

	@Override
	public int getMaxEnergy() {

		return 0;
	}

	@Override
	public int getViewRange() {

		return 0;
	}

	@Override
	public int getMinimumBreedingEnergy() {

		return 0;
	}


}
