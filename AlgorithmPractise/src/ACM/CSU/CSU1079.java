package ACM.CSU;
//AC
import java.util.ArrayList;
import java.util.Scanner;

public class CSU1079 {
	public static ArrayList<Student> stus = new ArrayList<Student>();

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		while (sin.hasNext()) {
			int i;
			int t = sin.nextInt();
			Student stu = null;
			if (t != 0) {
				stus.clear();
				for (i = 0; i < t; i++) {
					String name = sin.next();
					String height = sin.next();
					String weight = sin.next();
					float h = Float.parseFloat(height.substring(0, height
							.indexOf("m")));
					float w = Float.parseFloat(weight.substring(0, weight
							.indexOf("kg")));
					stu = new Student(name, h, w,height,weight);
					stus.add(stu);
				}
				sort();
				for (i = 0; i < stus.size(); i++) {
					stus.get(i).printStudent();
					if(i<stus.size()-1){
						System.out.println();
					}
				}
			} else {
				return;
			}
		}
	}

	public static void sort() {
		int i, j, size = stus.size();
		for (i = 0; i < size; i++) {
			for (j = i + 1; j < size; j++) {
				if (stus.get(j).height == stus.get(i).height) {
					if (stus.get(j).weight > stus.get(i).weight) {
						swap(i, j);
					}
				} else if (stus.get(j).height > stus.get(i).height) {
					swap(i, j);
				}
			}
		}
	}

	public static void swap(int i, int j) {
		Student stu = stus.get(i);
		stus.set(i, stus.get(j));
		stus.set(j, stu);
	}
}

class Student {
	String name;
	float height;
	float weight;
	String hs;
	String ws;

	public Student() {

	}

	public Student(String name, float height, float weight,String hs,String ws) {
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.hs=hs;
		this.ws=ws;
	}

	public void printStudent() {
		System.out.println(this.name + " " + this.hs + " " + this.ws);
	}
}
