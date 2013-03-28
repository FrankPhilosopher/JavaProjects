package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import util.UiUtil;

public class TempController implements Initializable {

	private UiUtil uiUtil;

	public void initialize(URL location, ResourceBundle resources) {
		initEvents();
		initValues();
	}

	private void initEvents() {

	}

	private void initValues() {
		uiUtil = UiUtil.getInstance();

	}

}
