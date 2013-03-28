package com.yj.smarthome.xmlImpls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.beans.SceneModeCommand;
import com.yj.smarthome.beans.SceneModeItem;
import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * ϵͳxml�ļ��Ĳ����� ����ģʽ
 * 
 * @author yinger
 * 
 */
public class SceneModeXmlTool {

	public static SceneModeXmlTool sceneModeXmlTool;
	// ����XStream����
	private static XStream xstream = new XStream(new DomDriver());

	private SceneModeXmlTool() {

	}

	public static SceneModeXmlTool getInstance() {
		if (sceneModeXmlTool == null) {
			sceneModeXmlTool = new SceneModeXmlTool();
		}
		return sceneModeXmlTool;
	}

	//�����µ��龰ģʽxml�ļ�
	public void newSceneModeXml(SceneMode sceneMode) throws Exception {
		File file = new File(FileUtil.SCENE_DIRECTORY + sceneMode.getClientName() + ".xml");
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF8");
			xstream.toXML(sceneMode, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//�������е��龰ģʽ
	public List<SceneMode> loadAllSceneMode() throws Exception {
		List<SceneMode> sceneModes = new ArrayList<SceneMode>();
		File dir = new File(FileUtil.SCENE_DIRECTORY);
		File[] files = dir.listFiles();
		for (int i = 0, length = files.length; i < length; i++) {
			sceneModes.add(readSceneMode(files[i]));
		}
		return sceneModes;
	}

	//��ȡ�龰ģʽ�ļ�
	public SceneMode readSceneMode(File file) throws Exception {
//		File file = new File(FileUtil.Scene_Directory + sceneMode.getClientName());
		if (!file.exists()) {
			return null;//TODO:����Ӧ���Ǳ���
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			SceneMode sceneMode = (SceneMode) xstream.fromXML(fis);
			fis.close();
			return sceneMode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		SceneMode sceneMode = new SceneMode("�ҵ�ģʽ");
		SceneModeCommand command = new SceneModeCommand(131842, "��");
//		SceneModeCommand command2 = new SceneModeCommand(131842, "�ر�");
		List<SceneModeCommand> commands = Arrays.asList(command);
		SceneModeItem item = new SceneModeItem(ProtocolUtil.DEVICETYPE_LIGHT, 3, "������", commands);
		sceneMode.setItems(Arrays.asList(item));
		try {
			SceneModeXmlTool.getInstance().newSceneModeXml(sceneMode);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			List<SceneMode> sceneModes = SceneModeXmlTool.getInstance().loadAllSceneMode();
//			System.out.println(sceneModes.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	//�޸��龰ģʽ������
	public void renameSceneMode(String oldName, SceneMode sceneMode) throws Exception {
		File file = new File(FileUtil.SCENE_DIRECTORY + oldName + ".xml");
		if (!file.exists()) {
			newSceneModeXml(sceneMode);//����ļ������ڵĻ��͸����µ�ģʽ�½�һ��
		}
		//������޸��ļ�������
		file.renameTo(new File(FileUtil.SCENE_DIRECTORY + sceneMode.getClientName() + ".xml"));
	}

	//ɾ��һ���龰ģʽ��Ҳ����ɾ��ģʽ�ļ�
	public void deleteSceneMode(SceneMode sceneMode) {
		File file = new File(FileUtil.SCENE_DIRECTORY + sceneMode.getClientName() + ".xml");
		if (file.exists()) {
			file.delete();
		}
	}

}
