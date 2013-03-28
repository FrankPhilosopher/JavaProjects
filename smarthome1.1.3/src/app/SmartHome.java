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
 * 主应用程序<br/>
 * 单例模式
 * 
 * @author yinger
 */

public class SmartHome extends Application {

	private static Stage appStage;// primary stage
	private static Stage modelStage;// second model stage
	private static SmartHome application;// the only application
	private static ResourceBundle resourceBundle;// resource
	private String resourceFile = "smarthome";// 资源文件名，放在src根目录下即可

	/**
	 * 得到模式stage
	 */
	public static Stage getModelStage() {
		if (modelStage == null) {
			modelStage = new Stage(StageStyle.TRANSPARENT);
			modelStage.setResizable(false);
			modelStage.initOwner(appStage);// 如果设置了owner，那么如果modelStage没有关闭的话就不能操作appStage
			modelStage.initModality(Modality.WINDOW_MODAL);// 关闭了appStage，那么这个modelStage也就会跟着关闭
			//modelStage.getIcons().add(ResouceManager.getImage(ResouceManager.LOGO));这句话可以去掉
			
//			modelStage.setOnHidden(new EventHandler<WindowEvent>(){
//				@Override
//				public void handle(WindowEvent event) {
//				    System.out.println("hidden");//测试关闭窗体时是否隐藏 
//				}	
//			});
//			modelStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//				public void handle(WindowEvent arg0) {
//					System.out.println("Close");//测试是否真正从内存中关闭
//					//System.exit(0);
//				}
//			});	
		}
		return modelStage;
		
	}

	/**
	 * 得到应用stage
	 */
	public static Stage getAppStage() {
		return appStage;
	}

	/**
	 * 得到application
	 */
	public static SmartHome getApplication() {
		return application;
	}

	/**
	 * 得到资源文件
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
		appStage.getIcons().add(ResouceManager.getImage(ResouceManager.LOGO));//这句不能去，这句去了开始右边的程序列表有问题

//		appStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//			public void handle(WindowEvent arg0) {
//				//Platform.exit();
//				System.out.println("loginclose");
//			}
//		});
//		appStage.setOnHidden(new EventHandler<WindowEvent>(){
//			@Override
//			public void handle(WindowEvent event) {
//			    System.out.println("loginhidden");//测试关闭窗体时是否隐藏 	
//			}	
//		});

		gotoLoginPage();
		// gotoMainPage();
	}

	/**
	 * 进入登陆界面
	 */
	public void gotoLoginPage() {
		changeContent("Login.fxml");
	}

	/**
	 * 进入主界面
	 */
	public void gotoMainPage() {
		changeContent("MainPage.fxml");
	}

	/**
	 * 改变界面内容，加载不同的fxml文件---appstage
	 */
	private void changeContent(String file) {
		Parent parent = null;
		try {
			parent = FXMLLoader.load(SmartHome.class.getResource(file), SmartHome.getResourceBundle());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(parent, Color.TRANSPARENT);//这句颜色参数也可以去掉，没影响
		appStage.setScene(scene);
		appStage.sizeToScene();
		appStage.centerOnScreen();
		appStage.show();
		appStage.toFront();//这句可以去掉，没什么影响
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() throws Exception {
		try {
			CommunicationUtil.getInstance().closeSocket();// 断开连接并终止后台线程
		} catch (Exception e) {
			e.printStackTrace();
		}
		// super.stop();
		// Platform.exit();//javafx application exit,if other deamon thread exists,JVM will not exit
		System.exit(0);//make JVM exit!
	}

}
