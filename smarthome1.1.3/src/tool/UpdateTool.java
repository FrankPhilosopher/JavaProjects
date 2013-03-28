package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import util.FileUtil;

/**
 * 
 * 软件更新的类
 * 
 * @author yinger
 * 
 */
public class UpdateTool {

	private static UpdateTool instance;

	private String versionCode;
	private String url = "http://www.iever.cn/YJdown/YongJingSmartHome.exe";
	private String description = "";

	// xml elements
	private final String VERSION_NODE = "apkversion";
	private final String VERSIONCODE = "versioncode";
	private final String URL = "apkurl";
	private final String DESCRIPTION = "description";

	private UpdateTool() {
		versionCode = SystemTool.getInstance().getVersion();// 给个默认值
	}

	public static UpdateTool getInstance() {
		if (instance == null) {
			instance = new UpdateTool();
		}
		return instance;
	}

	/**
	 * 读取软件更新xml文件
	 */
	public void readVersionXml() throws Exception {
		File file = new File(FileUtil.DOWN_DIRECTORY + FileUtil.PCVERSION_XML);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在// 文件不存在表示还没有系统配置信息，那么就创建新的配置文件
			return;
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");// 用指定的编码方式读取文件
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element node = document.getRootElement().element(VERSION_NODE);
			versionCode = node.elementTextTrim(VERSIONCODE);
			url = node.elementTextTrim(URL);
			description = node.elementText(DESCRIPTION);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取版本更新文件失败！");
		}
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
