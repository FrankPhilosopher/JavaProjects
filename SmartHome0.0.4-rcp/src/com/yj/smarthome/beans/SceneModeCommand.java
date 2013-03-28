package com.yj.smarthome.beans;

/**
 * 情景模式下对应的命令
 * 
 * @author yinger
 * 
 */
public class SceneModeCommand {

	//下面两个重要的命令可能应该在protocolutil中定义，应为这是和协议有关的
	public static final String COMMANDON = "buttonon";
	public static final String COMMANDOFF = "buttonoff";

	private int commandCode;//设备的命令码
	//如果设备的命令码变了呢：不会有问题，下次打开的时候由于命令码不同，用户相当于没有选择	发送命令到网关时，网关会处理
	//当情景模式保存时会自动重新保存！设备的名称也是如此，如果名称换了，那么就会在保存时重新修改原来情景模式下的名称
	private String commandName;//设备的命令名称

	public SceneModeCommand(int commandCode, String commandName) {
		this.commandCode = commandCode;
		this.commandName = commandName;
	}

	@Override
	//重写这个类的equals方法，目的是为了后面的比较，因为list的contains方法的比较用的就是这个方法结果
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
