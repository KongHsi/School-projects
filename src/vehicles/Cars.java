package vehicles;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Cars extends Vehicles {
	// This is a subclass of the Vehicles class.
	public Cars(Location initialLocation) {
	
		super.location = initialLocation;
		super.vehicleImage = Util.loadImage("cars.gif");
		super.isDead = false;
		super.strength = 150;
		super.name = "Car";
	}


}
