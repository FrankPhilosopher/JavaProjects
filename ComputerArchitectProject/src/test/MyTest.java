package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MyTest {
	public static void main(String[] args) {
		MyTest pp = new MyTest();
		pp.doClass("javafx.scene.paint.Color");
	}

	protected void doClass(String className) {
		try {
			Class<?> c = Class.forName(className);
			System.out.println(Modifier.toString(c.getModifiers()) + ' ' + c + " {");
			// 通过类的metadata属性信息，获取类的域字段，但是不包括超类中的字段
			// 这其中getFields()方法将仅仅返回当前类的public域字段
			Field fields[] = c.getDeclaredFields();
			for (Field f : fields) {
				if (Modifier.isPrivate(f.getModifiers())) {
					System.out.println("Field '" + f.getName() + "' is private.");
				} else if (Modifier.isProtected(f.getModifiers())) {
					System.out.println("Field '" + f.getName() + "' is protected.");
				} else if (Modifier.isPublic(f.getModifiers())) {
					System.out.println("Field '" + f.getName() + "' is public.");
				} else if (Modifier.isFinal(f.getModifiers())) {
					System.out.println("Field '" + f.getName() + "' is final.");
				} else if (Modifier.isStatic(f.getModifiers())) {
					System.out.println("Field '" + f.getName() + "' is static.");
				}
			}
			// 获取类的构造函数，getConstructors仅仅返回该类的public构造函数。
			// 如果希望获得全部的构造函数，调用getDeclaredConstructors
			Constructor<?>[] constructors = c.getConstructors();
			for (Constructor<?> ctor : constructors) {
				if (Modifier.isProtected(ctor.getModifiers())) {
					System.out.println("Constructor '" + ctor.getName() + "' is protected.");
				} else if (Modifier.isPrivate(ctor.getModifiers())) {
					System.out.println("Constructor '" + ctor.getName() + "' is private.");
				} else if (Modifier.isPublic(ctor.getModifiers())) {
					System.out.println("Constructor '" + ctor.getName() + "' is public.");
				}
			}
			// 获取类中声明的域方法，但是不包含超类中的方法。
			// 这其中getMethods()方法将仅仅返回当前类的public域方法
			Method methods[] = c.getDeclaredMethods();
			for (Method m : methods) {
				if (Modifier.isProtected(m.getModifiers())) {
					System.out.println("Method '" + m.getName() + "' is protected.");
				} else if (Modifier.isPrivate(m.getModifiers())) {
					System.out.println("Method '" + m.getName() + "' is private.");
				} else if (Modifier.isPublic(m.getModifiers())) {
					System.out.println("Method '" + m.getName() + "' is public.");
				} else if (Modifier.isStatic(m.getModifiers())) {
					System.out.println("Method '" + m.getName() + "' is static.");
				} else if (Modifier.isNative(m.getModifiers())) {
					System.out.println("Method '" + m.getName() + "' is native.");
				} else if (Modifier.isAbstract(m.getModifiers())) {
					System.out.println("Method '" + m.getName() + "' is abstract.");
				} else if (Modifier.isFinal(m.getModifiers())) {
					System.out.println("Method '" + m.getName() + "' is final.");
				}
			}
			System.out.println("}");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: Class " + className + " not found!");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}