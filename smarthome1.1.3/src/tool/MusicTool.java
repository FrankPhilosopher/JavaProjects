package tool;

import util.FileUtil;
import util.UiUtil;

public class MusicTool {
	private static MusicTool musicTool;
	private FileUtil fileUtil;
	
	private final String ID = "id";
	private final String NAME = "name";
	private final String BUTTONPLAY= "play";
	private final String BUTTONSTOP = "stop";
	private final String BUTTONPAUSE = "pause";
	private final String NODE = "music";
	private final String NODES = "musics";
	private final String MUSIC = "music";
	private final String NODEPATH = "//musics/music";
	
	private MusicTool(){
		fileUtil = FileUtil.getInstance();
	}
	
	public static MusicTool getInstance() {
		// TODO Auto-generated method stub
		if(musicTool == null){
			musicTool  = new MusicTool();
		}
		return musicTool;
	}

}
