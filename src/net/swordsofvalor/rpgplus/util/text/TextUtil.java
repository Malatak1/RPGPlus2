package net.swordsofvalor.rpgplus.util.text;

public final class TextUtil {

	public static String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
	
}
