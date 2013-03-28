package MathCharPriority;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class DemoCalLast {

	public static void main(String[] args) throws Exception {
		ArrayList<Expression> expressions = readData();
		MathCharPriority mathCharPriority = new MathCharPriority(expressions);
		mathCharPriority.calLastVt();
	}

	private static ArrayList<Expression> readData() throws Exception {
		ArrayList<Expression> expressions = new ArrayList<Expression>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(DemoCalLast.class.getResourceAsStream("grammer.txt")));
		String line = null;
		Scanner scanner = null;
		Expression expression = null;
		while ((line = reader.readLine()) != null) {
			scanner = new Scanner(line);
			expression = new Expression(scanner.next().charAt(0), scanner.next());
			expressions.add(expression);
		}
		return expressions;
	}

}
