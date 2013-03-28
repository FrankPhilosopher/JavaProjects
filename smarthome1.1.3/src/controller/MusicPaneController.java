package controller;

import item.DoorItem;
import item.MusicItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import tool.MusicTool;
import util.UiUtil;

public class MusicPaneController implements Initializable {
	private static MusicPaneController musicController;
	private UiUtil uiUtil;
	
	@FXML
	private Text pageTitle;
	@FXML
	private ImageView ivPrev;
	@FXML
	private ImageView ivNext;
	@FXML
	private AnchorPane topPane;
	@FXML
	private AnchorPane centerPane;
	@FXML
	private TilePane tilePane;
	
	private final int COLS = 5;
	private final int ROWS = 2;

	private int PAGESIZE = COLS * ROWS;
	private int itemSum;
	private int totalPage;
	private int pageIndex;
	
	private List<MusicItem> items;
	private MusicTool tool;
	
	public static MusicPaneController getInstance(){
		return musicController;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 initEvents();
		 initValues();
	}
	private void initEvents() {
		ivPrev.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (ivPrev.getOpacity() == 0) {// 要加上这个判断，因为如果看不见的话，这个imageview是不生效的
					arg0.consume();
				} else {
					pageIndex--;
					
				}
			}
		});
		
		ivNext.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0){
				if(ivNext.getOpacity() == 1){
					pageIndex ++;
				}else{
					arg0.consume();
				}
			}
		});
	}

	private void initValues() {
		musicController = this;
		uiUtil = UiUtil.getInstance();
		tool = MusicTool.getInstance();
		ivPrev.setOpacity(0);
		ivNext.setOpacity(0);
		totalPage = 1;
		tilePane.setPrefTileWidth(DoorItem.WIDTH);
		tilePane.setPrefTileHeight(DoorItem.HEIGHT);
		tilePane.setPrefColumns(COLS);
		tilePane.setHgap(20);
		tilePane.setVgap(50);
	}


}
