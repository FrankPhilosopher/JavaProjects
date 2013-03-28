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
 * ����xml������
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
	 * ����localĿ¼�µķ���
	 */
	public List<Room> loadRooms() throws Exception {
		List<Room> rooms = new ArrayList<Room>();
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
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
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// tested
		}
	}

	/**
	 * �������豸---�ļ�Ϊlocal/room.xml
	 */
	public void rename(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��---һ��϶���������������������ļ������ڵĻ��Ͳ��������������
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
			newLocalRoomXml(room);
			return;
		}
		try {// �ļ�����
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
					break;// һ��Ҫbreak
				}
			}
			if (i == list.size()) {// ���û�еĻ�����ô��Ҫ����һ��node
				writeNewRoom(document, room);
			}
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("д���ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
		}
	}

	/**
	 * ɾ������---�ļ�Ϊlocal/room.xml
	 */
	public void deleteRoom(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��---һ��϶���������������������ļ������ڵĻ��Ͳ��������������
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
			return;// ���ﲻ��Ҫ�κ�д�����
		}
		try {// �ļ�����
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
					break;// һ��Ҫbreak
				}
			}
			// if (i == list.size()) {// ���û�еĻ����Ǿ�����
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("д���ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
		}
	}

	/**
	 * �½�����---Ŀ���ļ�Ϊlocal/room.xml
	 */
	public void addRoom(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��---һ��϶���������������������ļ������ڵĻ��Ͳ��������������
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
			newLocalRoomXml(room);
			return;
		}
		try {// �ļ�����
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			writeNewRoom(document, room);// �ļ����ڵĻ���������room��root element����
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("д���ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
		}
	}

	/**
	 * �½�localĿ¼�µ�room.xml�ļ�
	 */
	public void newLocalRoomXml(Room room) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.ROOM_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		writeNewRoom(document, room);// ���Ԫ��
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("�����ļ�" + file.getName() + "ʧ�ܣ�");// һ�㲻��ʧ��
		}
	}

	/**
	 * ����µķ��䵽room.xml�ļ���
	 */
	private void writeNewRoom(Document document, Room room) {
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		Element id = element.addElement(ID);// id��name����element
		id.setText(room.getId());
		Element name = element.addElement(NAME);
		name.setText(room.getName());
	}

}
