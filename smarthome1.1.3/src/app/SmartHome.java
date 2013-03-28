package app;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import util.ResouceManager;

import communication.CommunicationUtil;

/**
 * ��Ӧ�ó���<br/>
 * ����ģʽ
 * 
 * @author yinger
 */

public class SmartHome extends Application {

	private static Stage appStage;// primary stage
	private static Stage modelStage;// second model stage
	private static SmartHome application;// the only application
	private static ResourceBundle resourceBundle;// resource
	private String resourceFile = "smarthome";// ��Դ�ļ���������src��Ŀ¼�¼���

	/**
	 * �õ�ģʽstage
	 */
	public static Stage getModelStage() {
		if (modelStage == null) {
			modelStage = new Stage(StageStyle.TRANSPARENT);
			modelStage.setResizable(false);
			modelStage.initOwner(appStage);// ���������owner����ô���modelStageû�йرյĻ��Ͳ��ܲ���appStage
			modelStage.initModality(Modality.WINDOW_MODAL);// �ر���appStage����ô���modelStageҲ�ͻ���Źر�
			//modelStage.getIcons().add(ResouceManager.getImage(ResouceManager.LOGO));��仰����ȥ��
			
//			modelStage.setOnHidden(new EventHandler<WindowEvent>(){
//				@Override
//				public void handle(WindowEvent event) {
//				    System.out.println("hidden");//���Թرմ���ʱ�Ƿ����� 
//				}	
//			});
//			modelStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//				public void handle(WindowEvent arg0) {
//					System.out.println("Close");//�����Ƿ��������ڴ��йر�
//					//System.exit(0);
//				}
//			});	
		}
		return modelStage;
		
	}

	/**
	 * �õ�Ӧ��stage
	 */
	public static Stage getAppStage() {
		return appStage;
	}

	/**
	 * �õ�application
	 */
	public static SmartHome getApplication() {
		return application;
	}

	/**
	 * �õ���Դ�ļ�
	 */
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		resourceBundle = ResourceBundle.getBundle(resourceFile);
		application = this;
		appStage = primaryStage;
		appStage.setResizable(false);
		appStage.initStyle(StageStyle.TRANSPARENT);
		appStage.getIcons().add(ResouceManager.getImage(ResouceManager.LOGO));//��䲻��ȥ�����ȥ�˿�ʼ�ұߵĳ����б�������

//		appStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//			public void handle(WindowEvent arg0) {
//				//Platform.exit();
//				System.out.println("loginclose");
//			}
//		});
//		appStage.setOnHidden(new EventHandler<WindowEvent>(){
//			@Override
//			public void handle(WindowEvent event) {
//			    System.out.println("loginhidden");//���Թرմ���ʱ�Ƿ����� 	
//			}	
//		});

		gotoLoginPage();
		// gotoMainPage();
	}

	/**
	 * �����½����
	 */
	public void gotoLoginPage() {
		changeContent("Login.fxml");
	}

	/**
	 * ����������
	 */
	public void gotoMainPage() {
		changeContent("MainPage.fxml");
	}

	/**
	 * �ı�������ݣ����ز�ͬ��fxml�ļ�---appstage
	 */
	private void changeContent(String file) {
		Parent parent = null;
		try {
			parent = FXMLLoader.load(SmartHome.class.getResource(file), SmartHome.getResourceBundle());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(parent, Color.TRANSPARENT);//�����ɫ����Ҳ����ȥ����ûӰ��
		appStage.setScene(scene);
		appStage.sizeToScene();
		appStage.centerOnScreen();
		appStage.show();
		appStage.toFront();//������ȥ����ûʲôӰ��
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() throws Exception {
		try {
			CommunicationUtil.getInstance().closeSocket();// �Ͽ����Ӳ���ֹ��̨�߳�
		} catch (Exception e) {
			e.printStackTrace();
		}
		// super.stop();
		// Platform.exit();//javafx application exit,if other deamon thread exists,JVM will not exit
		System.exit(0);//make JVM exit!
	}

}
