package cn.edu.csu.dbhospital.pojo;

// default package

/**
 * TRegister entity. @author MyEclipse Persistence Tools
 */

public class TRegister implements java.io.Serializable {

	public static final int TYPE_YUYUE = 0;
	public static final int TYPE_GUAHAO = 1;

	public static final int VALIDATE_NO = 0;
	public static final int VALIDATE_YES = 1;

	// Fields

	private int id;
	private int userId;
	private int arrangementId;
	private String number;
	private int type;
	private int validate;

	// Constructors

	/** default constructor */
	public TRegister() {
	}

	// Property accessors

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getArrangementId() {
		return arrangementId;
	}

	public void setArrangementId(int arrangementId) {
		this.arrangementId = arrangementId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValidate() {
		return validate;
	}

	public void setValidate(int validate) {
		this.validate = validate;
	}

}