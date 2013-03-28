package ex3;

public class Device implements Runnable {

	private Cpu cpu;// �ٽ���Դcpu
	private int id;// �豸���
	private String content = "";// ��ǰ�豸�е�����
	private String initContent;// Ҫ���뵽����豸�е�����
	private boolean isStart = false;// �Ƿ�������
	private boolean isFinish = false;// �Ƿ������
	private int index = 0;// ��ǰ������ַ�����

	public Device(Cpu cpu, int id, String initContent) {
		this.cpu = cpu;
		this.id = id;
		this.initContent = initContent;
	}

	public void run() {
		System.out.println("�豸" + id + " �߳������ˣ�");

		while (!isFinish) {// ���û����ɵĻ���ô��һֱִ��
			// ע�⣬ֻ�������ݴ��俪ʼ�ͽ���ʱ�����жϣ����ԣ�ע������λ��
			if (!isStart) {// �����û������������һ����ʼ�������ݵ��ж�����
				synchronized (cpu) {// ��סcpu
					while (Cpu.STATUS != Cpu.NONE) {// һֱ�ȵ�cpu����ʱ
						try {
							cpu.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Cpu.STATUS = Cpu.INTERRUPT;// ����ִ��֮ǰ������Ϊ�ж�����
					System.out.println("CPU���豸" + id + "�ж��ˣ����豸��ʼ���г�ʼ��������");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Cpu.STATUS = Cpu.NONE;// ִ�����֮������ΪNONE
					isStart = true;
					cpu.notifyAll();// �ͷŶ�cpu����
				}
			} else {
				content = content + initContent.charAt(index);// content����һ���µ��ַ�
				System.out.println("�꣬͵͵�ĺ����洢�����������У��豸" + id + "�е������ǣ�" + content);
				index++;
				if (index == initContent.length()) {
					isFinish = true;
				}
			}
		}
		// ���ݴ�������ˣ��ȴ�cpu����Ȼ���ͽ������ж�����
		synchronized (cpu) {// ��סcpu
			while (Cpu.STATUS != Cpu.NONE) {// һֱ�ȵ�cpu����ʱ
				try {
					cpu.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Cpu.STATUS = Cpu.FINISH;// ����ִ��֮ǰ������Ϊ�ж�����
			System.out.println("CPU���豸" + id + "�ж��ˣ����豸�����ݴ�����������ˣ�");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Cpu.STATUS = Cpu.NONE;// ִ�����֮������ΪNONE
			cpu.notifyAll();// �ͷŶ�cpu����
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
