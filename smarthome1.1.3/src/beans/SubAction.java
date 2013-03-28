package beans;

/**
 * 家电设备的子命令
 * 
 * @author yinger
 * 
 */
public class SubAction {

	private int sid;// 子命令编号
	private String name;// 子命令名称
	private int code;// 命令码

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}