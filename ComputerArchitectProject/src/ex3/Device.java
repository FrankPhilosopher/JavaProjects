package ex3;

public class Device implements Runnable {

	private Cpu cpu;// 临界资源cpu
	private int id;// 设备编号
	private String content = "";// 当前设备中的内容
	private String initContent;// 要传入到这个设备中的内容
	private boolean isStart = false;// 是否启动了
	private boolean isFinish = false;// 是否结束了
	private int index = 0;// 当前传输的字符索引

	public Device(Cpu cpu, int id, String initContent) {
		this.cpu = cpu;
		this.id = id;
		this.initContent = initContent;
	}

	public void run() {
		System.out.println("设备" + id + " 线程启动了！");

		while (!isFinish) {// 如果没有完成的话那么就一直执行
			// 注意，只有在数据传输开始和结束时发生中断！所以，注意锁的位置
			if (!isStart) {// 如果还没有启动，发送一个开始传输数据的中断请求
				synchronized (cpu) {// 锁住cpu
					while (Cpu.STATUS != Cpu.NONE) {// 一直等到cpu空闲时
						try {
							cpu.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Cpu.STATUS = Cpu.INTERRUPT;// 进入执行之前先设置为中断请求
					System.out.println("CPU被设备" + id + "中断了，该设备开始进行初始化操作！");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Cpu.STATUS = Cpu.NONE;// 执行完成之后设置为NONE
					isStart = true;
					cpu.notifyAll();// 释放对cpu的锁
				}
			} else {
				content = content + initContent.charAt(index);// content增加一个新的字符
				System.out.println("嘘，偷偷的和主存储器传送数据中，设备" + id + "中的内容是：" + content);
				index++;
				if (index == initContent.length()) {
					isFinish = true;
				}
			}
		}
		// 数据传输结束了，等待cpu空闲然后发送结束的中断请求
		synchronized (cpu) {// 锁住cpu
			while (Cpu.STATUS != Cpu.NONE) {// 一直等到cpu空闲时
				try {
					cpu.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Cpu.STATUS = Cpu.FINISH;// 进入执行之前先设置为中断请求
			System.out.println("CPU被设备" + id + "中断了，该设备的数据传输操作结束了！");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Cpu.STATUS = Cpu.NONE;// 执行完成之后设置为NONE
			cpu.notifyAll();// 释放对cpu的锁
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInitContent() {
		return initContent;
	}

	public void setInitContent(String initContent) {
		this.initContent = initContent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
