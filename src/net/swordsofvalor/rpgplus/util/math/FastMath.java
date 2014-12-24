package net.swordsofvalor.rpgplus.util.math;

import org.bukkit.Location;

public final class FastMath {
	
	/* 
	 * A square-root algorithm utilizing minimal overhead and process
	 * time to efficiently calculate square roots.
	 * @param x the number to calculate
	 * @return the approximate square-root of a number <code>x</code>
	 */
	public static float sqrt(float x) {
	    float xhalf = 0.5f*x;
	    int i = Float.floatToIntBits(x);
	    i = 0x5f3759df - (i>>1);
	    x = Float.intBitsToFloat(i);
	    x = x*(1.5f - xhalf*x*x);
	    return 1/x;
	}
	
	public static float distanceBetween(Location l1, Location l2) {
		return sqrt((float) (Math.pow(l1.getX()-l2.getX(),2) 
				+ Math.pow(l1.getY()-l2.getY(),2) 
				+ Math.pow(l1.getZ()-l2.getZ(),2)));
	}
	
}
