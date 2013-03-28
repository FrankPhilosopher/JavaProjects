package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.FileUtil;
import beans.Room;

/**
 * 房间xml处理类
 * 
 * @author yinger
 * 
 */
public class RoomTool {

	private static RoomTool instance;
	private FileUtil fileUtil;

	private final String ID = "id";
	private final String NAME = "name";
	private final String NODE = "room";
	private final String NODES = "rooms";
	private final String NODEPATH = "//rooms/room";

	private RoomTool() {
		fileUtil = FileUtil.getInstance();
	}

	public static RoomTool getInstance() {
		if (instance == null) {
			instance = new RoomTool();
		}
		return instance;
	}

	/**
	 * 加载local目录下的房间
	 */
	public List<Room> loadRooms() throws Exception {
		List<Room> rooms = new ArrayList<Room>();
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			return rooms;
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			List list = document.selectNodes(NODEPATH);
			Element node;
			Room room;
			for (int i = 0; i < list.size(); i++) {
				node = (Element) list.get(i);
				room = new Room();
				room.setId(node.element(ID).getTextTrim());
				room.setName(node.element(NAME).getTextTrim());
				rooms.add(room);
			}
			return rooms;
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// tested
		}
	}

	/**
	 * 重命名设备---文件为local/room.xml
	 */
	public void rename(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个---一般肯定不会出现这种情况，如果文件不存在的话就不会出现重命名！
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
			newLocalRoomXml(room);
			return;
		}
		try {// 文件存在
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			List list = document.selectNodes(NODEPATH);
			Element node;
			int i = 0;
			for (; i < list.size(); i++) {
				node = (Element) list.get(i);
				if (node.element(ID).getTextTrim().equals(room.getId())) {
					node.element(NAME).setText(room.getName());
					break;// 一定要break
				}
			}
			if (i == list.size()) {// 如果没有的话，那么就要新增一个node
				writeNewRoom(document, room);
			}
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("写入文件" + filePath + "失败！");// 一般不会失败
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// 一般不会失败
		}
	}

	/**
	 * 删除房间---文件为local/room.xml
	 */
	public void deleteRoom(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个---一般肯定不会出现这种情况，如果文件不存在的话就不会出现重命名！
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
			return;// 这里不需要任何写入操作
		}
		try {// 文件存在
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element root = document.getRootElement();
			List list = document.selectNodes(NODEPATH);
			Element node;
			int i = 0;
			for (; i < list.size(); i++) {
				node = (Element) list.get(i);
				if (node.element(ID).getTextTrim().equals(room.getId())) {
					root.remove(node);
					break;// 一定要break
				}
			}
			// if (i == list.size()) {// 如果没有的话，那就算了
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("写入文件" + filePath + "失败！");// 一般不会失败
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// 一般不会失败
		}
	}

	/**
	 * 新建房间---目标文件为local/room.xml
	 */
	public void addRoom(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个---一般肯定不会出现这种情况，如果文件不存在的话就不会出现重命名！
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
			newLocalRoomXml(room);
			return;
		}
		try {// 文件存在
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			writeNewRoom(document, room);// 文件存在的话就添加这个room到root element下面
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("写入文件" + filePath + "失败！");// 一般不会失败
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// 一般不会失败
		}
	}

	/**
	 * 新建local目录下的room.xml文件
	 */
	public void newLocalRoomXml(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		writeNewRoom(document, room);// 添加元素
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("创建文件" + file.getName() + "失败！");// 一般不会失败
		}
	}

	/**
	 * 添加新的房间到room.xml文件中
	 */
	private void writeNewRoom(Document document, Room room) {
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		Element id = element.addElement(ID);// id和name都是element
		id.setText(room.getId());
		Element name = element.addElement(NAME);
		name.setText(room.getName());
	}

}
