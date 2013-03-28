package com.yj.smarthome.beans;

/**
 * �龰ģʽ�¶�Ӧ������
 * 
 * @author yinger
 * 
 */
public class SceneModeCommand {

	//����������Ҫ���������Ӧ����protocolutil�ж��壬ӦΪ���Ǻ�Э���йص�
	public static final String COMMANDON = "buttonon";
	public static final String COMMANDOFF = "buttonoff";

	private int commandCode;//�豸��������
	//����豸������������أ����������⣬�´δ򿪵�ʱ�����������벻ͬ���û��൱��û��ѡ��	�����������ʱ�����ػᴦ��
	//���龰ģʽ����ʱ���Զ����±��棡�豸������Ҳ����ˣ�������ƻ��ˣ���ô�ͻ��ڱ���ʱ�����޸�ԭ���龰ģʽ�µ�����
	private String commandName;//�豸����������

	public SceneModeCommand(int commandCode, String commandName) {
		this.commandCode = commandCode;
		this.commandName = commandName;
	}

	@Override
	//��д������equals������Ŀ����Ϊ�˺���ıȽϣ���Ϊlist��contains�����ıȽ��õľ�������������
	public boolean equals(Object obj) {
		if (obj instanceof SceneModeCommand) {
			SceneModeCommand command = (SceneModeCommand) obj;
			if (command.commandCode == this.commandCode) {
				return true;
			}
		}
		return false;
	}

	public int getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(int commandCode) {
		this.commandCode = commandCode;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

}
