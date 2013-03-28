package ex2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.paint.Color;

public class ColorUtil {

	public static List<Color> constantColors = new ArrayList<Color>();

	static {
		// I want to get all the constant color in Color class,but I fails
//		Field[] fields = Color.class.getDeclaredFields();
//		for (int i = 0, length = fields.length; i < length; i++) {
//			if (Modifier.isFinal(fields[i].getModifiers()) && Modifier.isStatic(fields[i].getModifiers())) {
//				constantColors.add(Color.web(fields[i].getName()));//wrong method!
//			}
//		}
		Collections.addAll(constantColors, Color.LAWNGREEN, Color.ORANGE, Color.YELLOW, Color.PINK, Color.AQUA, Color.BLUE,
				Color.DODGERBLUE, Color.GREEN, Color.GREENYELLOW, Color.INDIGO, Color.THISTLE, Color.PURPLE, Color.SALMON, Color.NAVY,
				Color.MAGENTA, Color.SKYBLUE, Color.CHOCOLATE);
	}

	public static Color getColor(int index) {
		return constantColors.get(index);//every number has its own color
	}

	public static void main(String[] args) {
		for (Color color : constantColors) {
			System.out.println(color);
		}
	}

}
