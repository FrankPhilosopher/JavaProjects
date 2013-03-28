package ex3;

public class Cpu implements Runnable {

	public static final int NONE = 0;
	public static final int INTERRUPT = 1;
	public static final int FINISH = 2;

	public static int STATUS = NONE;

	public void run() {
		System.out.println("CPU 线程启动了！");
		
		while (Cpu.STATUS == NONE) {
			synchronized (this) {
				System.out.println("CPU 执行用户程序中。。。");
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
