package ex3;

public class Cpu implements Runnable {

	public static final int NONE = 0;
	public static final int INTERRUPT = 1;
	public static final int FINISH = 2;

	public static int STATUS = NONE;

	public void run() {
		System.out.println("CPU �߳������ˣ�");
		
		while (Cpu.STATUS == NONE) {
			synchronized (this) {
				System.out.println("CPU ִ���û������С�����");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.notifyAll();
			}
		}
	}

}
