package entity;

/**
 * 用户
 * @author hjw
 *
 */
public class User {
	
	//用户角色的静态值
	public static int COMPANY_ADMIN = 0;
	public static int COMPANY_WORKER = 1;
	public static int DISTRIBUTION_ADMIN = 2;
	public static int DISTRIBUTION_WORKER = 3;
	public static int CUSTOMER = 4;

	private int id;//编号
	private String name;//用户名
	private String password;//密码
	private String realname;//真实姓名
	private String phone;//联系方式
	private int distributionId;//配送点编号
	private int companyId;//两个id会有默认值0，如果是0表示没有单位
	private int priority;//角色

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(int distributionId) {
		this.distributionId = distributionId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
