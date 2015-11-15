package ca.ubc.ece.cpen221.mp4.ai;

import java.util.LinkedHashSet;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {

	int MIN_BREEDING_DIST = 2;
	int MAX_ANIMAL_DENSITY = 3;
	double EAT_BREED_ENERGY_RATIO = 0.8;
	String FOOD = "Rabbit";
	String ANIMAL_TYPE = "Fox";

	public FoxAI() {
	}

	/**
	 * Requires: animal is in world Effects: returns an EatCommand, MoveCommand,
	 * BreedCommand or a WaitCommand
	 * 
	 * @param world
	 * @param animal
	 * @return an EatCommand, MoveCommand, BreedCommand or a WaitCommand
	 */
	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

		Location loc = animal.getLocation();
		Location toLoc = null;
		Location foodLoc = null;
		Item food = null;

		// smallest impossible distance
		int foodDist = world.getHeight() + world.getWidth() - 1;
		food = closestChosenItem(world, animal, FOOD);

		if ((food != null) && (animal.getEnergy() < animal.getMaxEnergy() * EAT_BREED_ENERGY_RATIO)) {
			foodLoc = food.getLocation();
			foodDist = foodLoc.getDistance(loc);

			if (foodDist == 1)
				return new EatCommand(animal, food);

			Direction toFood = Util.getDirectionTowards(loc, foodLoc);
			toLoc = new Location(loc, toFood);

			if (isLocationEmpty(world, animal, toLoc))
				return new MoveCommand(animal, toLoc);
		}

		if (toLoc == null && loc.getX() % 2 == 0) {
			toLoc = new Location(loc, Direction.SOUTH);

			for (Item adjChosenItem : getAdjacentChosenItems(world, animal, FOOD)) {
				if (adjChosenItem.getLocation() == toLoc && adjChosenItem.getName() == FOOD)
					return new EatCommand(animal, adjChosenItem);
			}
		} else if (toLoc == null && (loc.getX() % 2 == 1)) {
			toLoc = new Location(loc, Direction.NORTH);

			for (Item adjChosenItem : getAdjacentChosenItems(world, animal, FOOD)) {
				if (adjChosenItem.getLocation() == toLoc && adjChosenItem.getName() == FOOD)
					return new EatCommand(animal, adjChosenItem);
			}
		} else if (toLoc == null && (loc.getY() % 2 == 0)) {
			toLoc = new Location(loc, Direction.WEST);

			for (Item adjChosenItem : getAdjacentChosenItems(world, animal, FOOD)) {
				if (adjChosenItem.getLocation() == toLoc && adjChosenItem.getName() == FOOD)
					return new EatCommand(animal, adjChosenItem);
			}
		} else if (toLoc == null && (loc.getY() % 2 == 1)) {
			toLoc = new Location(loc, Direction.EAST);

			for (Item adjChosenItem : getAdjacentChosenItems(world, animal, FOOD)) {
				if (adjChosenItem.getLocation() == toLoc && adjChosenItem.getName() == FOOD)
					return new EatCommand(animal, adjChosenItem);
			}
		}

		if ((toLoc != null && Util.isLocationEmpty((World) world, toLoc))
				&& (animal.getEnergy() >= animal.getMaxEnergy() * EAT_BREED_ENERGY_RATIO))
			return new BreedCommand(animal, toLoc);

		if (!Util.isLocationEmpty((World) world, toLoc)) {
			int[] itemCount = countChosenItemEitherSide(world, animal, ANIMAL_TYPE);

			if (itemCount[0] + itemCount[1] == 0) {
				toLoc = new Location(loc, Direction.WEST);
			} else if (itemCount[1] > itemCount[0]) {
				toLoc = new Location(loc, Direction.WEST);
			} else if (itemCount[0] > itemCount[1]) {
				toLoc = new Location(loc, Direction.EAST);
			} else if (!Util.isLocationEmpty((World) world, toLoc)) {
				toLoc = new Location(loc, Direction.NORTH);
			} else if (!Util.isLocationEmpty((World) world, toLoc)) {
				toLoc = new Location(loc, Direction.SOUTH);
			}
		}

		if (toLoc != null && Util.isLocationEmpty((World) world, toLoc))
			return new MoveCommand(animal, toLoc);

		return new WaitCommand();
	}

	/**
	 * Requires: animal is in world Effects: returns the closest item of given
	 * name to the animal. Returns null if item of given name is not visible to
	 * animal.
	 * 
	 * @param world
	 * @param animal
	 * @param name
	 *            String name of chosen item
	 * @return the closest item of given name to the animal. Returns null if
	 *         item of given name is not visible to animal.
	 */
	private Item closestChosenItem(ArenaWorld world, ArenaAnimal animal, String name) {
		Location loc = animal.getLocation();
		Item item = null;
		// smallest impossible distance
		int shortestDist = world.getHeight() + world.getWidth() - 1;
		int distFromAnimal = shortestDist; // smallest impossible distance

		Set<Item> nearItems = world.searchSurroundings(animal);

		for (Item itemFound : nearItems) {
			if (itemFound.getName() == name) {
				distFromAnimal = itemFound.getLocation().getDistance(loc);

				if (distFromAnimal <= shortestDist) {
					shortestDist = distFromAnimal;
					item = itemFound;
				}
			}
		}

		return item;
	}

	/**
	 * Requires: animal is in world Effects: returns a set of items of the given
	 * name adjacent (north, south, east, west of) to animal
	 * 
	 * @param world
	 * @param animal
	 * @param name
	 *            String name of chosen item
	 * @return a set of items of the given name adjacent (north, south, east,
	 *         west of) to animal
	 */
	private Set<Item> getAdjacentChosenItems(ArenaWorld world, ArenaAnimal animal, String name) {
		Set<Item> adjacentItems = getAdjacentItems(world, animal);
		Set<Item> adjacentChosenItems = new LinkedHashSet<Item>();

		for (Item adjItem : adjacentItems) {
			if (adjItem.getName() == FOOD) {
				adjacentChosenItems.add(adjItem);
			}
		}

		return adjacentChosenItems;
	}

	/**
	 * Requires: animal is in world Effects: returns a set of items of distance
	 * 1 from the animal
	 * 
	 * @param world
	 * @param animal
	 * @return a set of items of distance 1 from the animal
	 */
	private Set<Item> getAdjacentItems(ArenaWorld world, ArenaAnimal animal) {
		Set<Item> adjacentItems = new LinkedHashSet<Item>();
		Set<Item> nearItems = world.searchSurroundings(animal);

		for (Item item : nearItems) {
			if (animal.getLocation().getDistance(item.getLocation()) == 1) {
				adjacentItems.add(item);
			}
		}

		return adjacentItems;
	}

	/**
	 * Requires: animal is in world Effects: returns array of integers where
	 * first entry is number of items of given name to the left of the animal
	 * and the second entry is number of items of given name to the right of the
	 * animal
	 * 
	 * @param world
	 * @param animal
	 * @param name
	 *            String name of type of animal
	 * @return array of integers where first entry is number of items of given
	 *         name to the left of the animal and the second entry is number of
	 *         items of given name to the right of the animal
	 */
	private int[] countChosenItemEitherSide(ArenaWorld world, ArenaAnimal animal, String name) {
		int numChosenItemsOnLeft = 0;
		int numChosenItemsOnRight = 0;
		int[] numItemsEitherSide = new int[2];

		Set<Item> nearItems = world.searchSurroundings(animal);

		for (Item itemFound : nearItems) {
			if (itemFound.getName() == name) {
				if (itemFound.getLocation().getX() > animal.getLocation().getX())
					numChosenItemsOnRight++;
				if (itemFound.getLocation().getX() < animal.getLocation().getX())
					numChosenItemsOnLeft++;
			}

		}

		numItemsEitherSide[0] = numChosenItemsOnLeft;
		numItemsEitherSide[1] = numChosenItemsOnRight;

		return numItemsEitherSide;
	}

}
