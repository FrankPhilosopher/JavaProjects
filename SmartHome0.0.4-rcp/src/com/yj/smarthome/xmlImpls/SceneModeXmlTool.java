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
 * 系统xml文件的操作类 单例模式
 * 
 * @author yinger
 * 
 */
public class SceneModeXmlTool {

	public static SceneModeXmlTool sceneModeXmlTool;
	// 创建XStream对象
	private static XStream xstream = new XStream(new DomDriver());

	private SceneModeXmlTool() {

	}

	public static SceneModeXmlTool getInstance() {
		if (sceneModeXmlTool == null) {
			sceneModeXmlTool = new SceneModeXmlTool();
		}
		return sceneModeXmlTool;
	}

	//创建新的情景模式xml文件
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

	//加载所有的情景模式
	public List<SceneMode> loadAllSceneMode() throws Exception {
		List<SceneMode> sceneModes = new ArrayList<SceneMode>();
		File dir = new File(FileUtil.SCENE_DIRECTORY);
		File[] files = dir.listFiles();
		for (int i = 0, length = files.length; i < length; i++) {
			sceneModes.add(readSceneMode(files[i]));
		}
		return sceneModes;
	}

	//读取情景模式文件
	public SceneMode readSceneMode(File file) throws Exception {
//		File file = new File(FileUtil.Scene_Directory + sceneMode.getClientName());
		if (!file.exists()) {
			return null;//TODO:这里应该是报错！
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
		SceneMode sceneMode = new SceneMode("我的模式");
		SceneModeCommand command = new SceneModeCommand(131842, "打开");
//		SceneModeCommand command2 = new SceneModeCommand(131842, "关闭");
		List<SceneModeCommand> commands = Arrays.asList(command);
		SceneModeItem item = new SceneModeItem(ProtocolUtil.DEVICETYPE_LIGHT, 3, "客厅灯", commands);
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

	//修改情景模式的名称
	public void renameSceneMode(String oldName, SceneMode sceneMode) throws Exception {
		File file = new File(FileUtil.SCENE_DIRECTORY + oldName + ".xml");
		if (!file.exists()) {
			newSceneModeXml(sceneMode);//如果文件不存在的话就根据新的模式新建一个
		}
		//否则就修改文件的名称
		file.renameTo(new File(FileUtil.SCENE_DIRECTORY + sceneMode.getClientName() + ".xml"));
	}

	//删除一个情景模式，也就是删除模式文件
	public void deleteSceneMode(SceneMode sceneMode) {
		File file = new File(FileUtil.SCENE_DIRECTORY + sceneMode.getClientName() + ".xml");
		if (file.exists()) {
			file.delete();
		}
	}

}
