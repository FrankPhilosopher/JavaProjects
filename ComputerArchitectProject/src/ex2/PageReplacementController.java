package ex2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PageReplacementController implements Initializable {

	private Stage stage;

	@FXML
	private TextField tfPageFrames;
	@FXML
	private TextField tfReferenceString;
	@FXML
	private Label labelMessage;
	@FXML
	private Button btnInit;
	@FXML
	private Button btnStep;
	@FXML
	private Button btnStart;
	@FXML
	private GridPane rsGridPane;
	@FXML
	private GridPane replaceGridPane;
	@FXML
	private ToggleGroup chooseAlgorithm;// I do not know how to get the value via ToggleGroup
	@FXML
	private RadioButton rbFifo;
	@FXML
	private RadioButton rbLru;
	@FXML
	private RadioButton rbOpt;

	private int frames;
	private Alogrithm alogrithm;
	private int currentStep;// record the current step
	private int currentFrame;// frames used!
	private Block currentBlock;// current block to enter
	private Block[] rsBlocks;// rs blocks user data!
	private Block[][] replaceBlocks;// re blocks user data!

	public void initialize(URL arg0, ResourceBundle arg1) {
//		chooseAlgorithm.selectedToggleProperty().getValue().//the selected radiobutton
		initValues();
		replaceGridPane.setGridLinesVisible(true);
		rsGridPane.setGridLinesVisible(true);// notice:if you set hgap,then the gridpane will show it as a new clumn
		// but it is not actually existing!
	}

	private void initValues() {
		labelMessage.setText("");
		rsGridPane.layoutXProperty().bindBidirectional(replaceGridPane.layoutXProperty());// alignment left
		tfPageFrames.setText("4");
		tfReferenceString.setText("1 1 2 4 3 5 2 1 6 7 1 3");
	}

	@FXML
	private void handleHand(ActionEvent event) {
		tfPageFrames.setEditable(true);
		tfReferenceString.setEditable(true);
	}

	@FXML
	private void handleFile(ActionEvent event) {
		tfPageFrames.setEditable(false);
		tfReferenceString.setEditable(false);
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Data file...");
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File file = fc.showOpenDialog(stage.getOwner());
		if (file == null) {
			return;
		} else {
			readDataFromFile(file);
		}
	}

	private void readDataFromFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			tfPageFrames.setText(reader.readLine().trim());
			tfReferenceString.setText(reader.readLine().trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleFifo(ActionEvent event) {
		alogrithm = new FifoAlogorithm();
	}

	@FXML
	private void handleLru(ActionEvent event) {
		alogrithm = new LruAlogorithm();
	}

	@FXML
	private void handleOpt(ActionEvent event) {
		alogrithm = new OptAlogorithm();
	}

	@FXML
	private void handleInit(ActionEvent event) {
		currentStep = -1;// init!
		currentFrame = 0;
		rsGridPane.getChildren().clear();
		replaceGridPane.getChildren().clear();
		// set reference string
		String[] rs = tfReferenceString.getText().trim().split(" ");
		rsBlocks = new Block[rs.length];
		Block block;
		for (int i = 0, length = rs.length; i < length; i++) {
			block = new Block(Integer.valueOf(rs[i]), ColorUtil.getColor(Integer.valueOf(rs[i])));
			rsGridPane.add(block, i, 0);
			rsBlocks[i] = block;
		}
		// set page frames
		frames = Integer.parseInt(tfPageFrames.getText().trim());// reBlocks frames
		replaceBlocks = new Block[frames][rs.length];
		for (int i = 0; i < frames; i++) {// row
			for (int j = 0; j < rs.length; j++) {// column
				block = Block.getBlankBlock();// this kind of block is used to hold the cell space
				replaceGridPane.add(block, j, i);
				replaceBlocks[i][j] = block;
			}
		}
		// set algorithm
		if (rbFifo.isSelected()) {
			alogrithm = new FifoAlogorithm();
		} else if (rbLru.isSelected()) {
			alogrithm = new LruAlogorithm();
		} else if (rbOpt.isSelected()) {
			alogrithm = new OptAlogorithm();
		}
	}

	@FXML
	private void handleStart(ActionEvent event) {
		new Thread(new Runnable() {
			public void run() {
				while (currentStep < rsBlocks.length - 1) {
					handleStep(null);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@FXML
	private void handleStep(ActionEvent event) {
		if (currentStep >= (rsBlocks.length - 1)) {// can not be bigger than (rsBlocks.length - 1)
			return;
		}
		currentStep++;
		currentBlock = rsBlocks[currentStep];
		alogrithm.doNextStep();
	}

	// interface
	abstract class Alogrithm {
		// general method to do the next step!
		public void doNextStep() {
			int index = -1;
			if (currentStep > 0) {// copy from leftto right
				for (int i = 0; i < currentFrame; i++) {
					Block.cloneBlockTimePlus(replaceBlocks[i][currentStep], replaceBlocks[i][currentStep - 1]);
					if (replaceBlocks[i][currentStep].getNumber() == currentBlock.getNumber()) {
						index = i;// exists
					}
				}
			}
			if (index >= 0) {// hit
				Block.makeBlank(currentBlock);
				replaceBlocks[index][currentStep].setTime(0);// hit one time return to 0
			} else {
				if (currentFrame < frames) {// load
					Block.cloneBlock(replaceBlocks[currentFrame][currentStep], currentBlock);
					Block.makeBlank(currentBlock);
					currentFrame++;
				} else {// replace
					int replaceIndex = getReplaceIndex();// TODO:time largest!
					Block.cloneBlock(replaceBlocks[replaceIndex][currentStep], currentBlock);// copy
					Block.makeBlank(currentBlock);// notice the order!
					replaceBlocks[replaceIndex][currentStep].setTime(0);
				}
			}
		}

		public abstract int getReplaceIndex();
	}

	// for inner class,they can see the outter class's attributes
	// FIFO:no need to use "time" and do not take care of hit
	class FifoAlogorithm extends Alogrithm {
		private int fifoIndex = 0;

		public int getReplaceIndex() {
			return (fifoIndex++) % frames;
		}
	}

	// LRU
	// least recently used
	class LruAlogorithm extends Alogrithm {
		public int getReplaceIndex() {
			int maxIndex = 0;
			int maxTime = replaceBlocks[0][currentStep].getTime();
			for (int i = 0; i < frames; i++) {// replace
				if (maxTime < replaceBlocks[i][currentStep].getTime()) {
					maxIndex = i;
					maxTime = replaceBlocks[i][currentStep].getTime();
				}
			}
			return maxIndex;
		}
	}

	// OPT
	// optimal
	class OptAlogorithm extends Alogrithm {
		public int getReplaceIndex() {
			int index = 0;
			int[] periods = new int[frames];
			for (int i = 0; i < frames; i++) {
				periods[i] = getPeriod(replaceBlocks[i][currentStep].getNumber());
				if (periods[i] == -1) {
					return i;// not use in the future,so return
				}
			}
			return index;
		}

		private int getPeriod(int number) {
			for (int i = currentStep + 1; i < rsBlocks.length; i++) {
				if (rsBlocks[i].getNumber() == number) {
					return i;
				}
			}
			return -1;// not use in the future
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
