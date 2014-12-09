package net.swordsofvalor.rpgplus.datatypes.abilities;

public final class AbilityType {
	
	public static final int LIGHT = 0;
	public static final int MEDIUM = 1;
	public static final int HEAVY = 2;
	public static final int ULTIMATE = 3;
	
	public static final String name(int type) {
		switch (type) {
		case 0: return "LIGHT";
		case 1: return "MEDIUM";
		case 2: return "HEAVY";
		case 3: return "ULTIMATE";
		}
		return null;
	}
	
	public static int convert(String name) {
		switch (name) {
		case "LIGHT": return 0;
		case "MEDIUM": return 1;
		case "HEAVY": return 2;
		case "ULTIMATE": return 3;
		}
		return -1;
	}
	
	public static String[] values() {
		return new String[] {"LIGHT","MEDIUM","HEAVY","ULTIMATE"};
	}
	
}
