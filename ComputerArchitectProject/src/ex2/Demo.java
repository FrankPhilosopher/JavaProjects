package ex2;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL location = getClass().getResource("PageReplacement.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setResources(ResourceBundle.getBundle("ex2/PageReplacement"));// 不需要文件后缀名，而且ex2/不能少
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent group = (Parent) fxmlLoader.load(location.openStream());
		((PageReplacementController) fxmlLoader.getController()).setStage(primaryStage);
		Scene scene = new Scene(group);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setTitle("计算机体系结构——页面置换算法演示程序  作者：胡家威 计科0902班");
//		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}

}
