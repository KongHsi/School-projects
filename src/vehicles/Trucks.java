package vehicles;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

//This is a subclass of the Vehicles class.
public class Trucks extends Vehicles {

	public Trucks(Location initialLocation) {

		super.location = initialLocation;
		super.vehicleImage = Util.loadImage("trucks.gif");
		super.isDead = false;
		super.strength = 250;
		super.name = "Truck";
	}

}
