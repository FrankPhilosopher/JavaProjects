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
 * ������µ���
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
		versionCode = SystemTool.getInstance().getVersion();// ����Ĭ��ֵ
	}

	public static UpdateTool getInstance() {
		if (instance == null) {
			instance = new UpdateTool();
		}
		return instance;
	}

	/**
	 * ��ȡ�������xml�ļ�
	 */
	public void readVersionXml() throws Exception {
		File file = new File(FileUtil.DOWN_DIRECTORY + FileUtil.PCVERSION_XML);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������// �ļ������ڱ�ʾ��û��ϵͳ������Ϣ����ô�ʹ����µ������ļ�
			return;
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");// ��ָ���ı��뷽ʽ��ȡ�ļ�
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element node = document.getRootElement().element(VERSION_NODE);
			versionCode = node.elementTextTrim(VERSIONCODE);
			url = node.elementTextTrim(URL);
			description = node.elementText(DESCRIPTION);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�汾�����ļ�ʧ�ܣ�");
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
