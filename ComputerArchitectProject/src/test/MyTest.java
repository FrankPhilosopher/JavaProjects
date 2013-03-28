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
			// ͨ�����metadata������Ϣ����ȡ������ֶΣ����ǲ����������е��ֶ�
			// ������getFields()�������������ص�ǰ���public���ֶ�
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
			// ��ȡ��Ĺ��캯����getConstructors�������ظ����public���캯����
			// ���ϣ�����ȫ���Ĺ��캯��������getDeclaredConstructors
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
			// ��ȡ�����������򷽷������ǲ����������еķ�����
			// ������getMethods()�������������ص�ǰ���public�򷽷�
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