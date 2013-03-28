package ex3;

public class Demo {

	public static void main(String[] args) {

		Cpu cpu = new Cpu();

		Device device1 = new Device(cpu, 1, "JavaSE JavaEE JavaME");
		Device device2 = new Device(cpu, 2, "Spring Struts Hibernate");
		Device device3 = new Device(cpu, 3, "JavaFx OsGi RCP");

		new Thread(cpu).start();
		new Thread(device1).start();
		new Thread(device2).start();
		new Thread(device3).start();
		
	}

}
