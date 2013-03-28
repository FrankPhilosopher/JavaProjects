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
		fxmlLoader.setResources(ResourceBundle.getBundle("ex2/PageReplacement"));// ����Ҫ�ļ���׺��������ex2/������
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent group = (Parent) fxmlLoader.load(location.openStream());
		((PageReplacementController) fxmlLoader.getController()).setStage(primaryStage);
		Scene scene = new Scene(group);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setTitle("�������ϵ�ṹ����ҳ���û��㷨��ʾ����  ���ߣ������� �ƿ�0902��");
//		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}

}
