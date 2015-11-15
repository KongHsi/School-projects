package vehicles;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

//This is a subclass of the Vehicles class.
public class Motorcycles extends Vehicles {

	public Motorcycles(Location initialLocation) {

		super.location = initialLocation;
		super.vehicleImage = Util.loadImage("motorcycles.gif");
		super.isDead = false;
		super.strength = 110;
		super.name = "Motorcycles";
	}

}
