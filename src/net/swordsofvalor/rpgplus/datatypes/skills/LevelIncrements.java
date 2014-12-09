package net.swordsofvalor.rpgplus.datatypes.skills;

public final class LevelIncrements {

	public static int getIncrementAt(int level) {
		return (int) Math.round(90 * Math.pow(1.1, level - 1));
	}

}
