package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RootController implements Initializable{
	@FXML private Button btnHelp;
	@FXML private Button btnShare;
	@FXML private Button btnExit;
	@FXML private Button btnOption;
	@FXML private Button btnDefFol;
	@FXML private Button btnSelFol1;
	@FXML private Button btnSelFol2;
	@FXML private Button btnSelFol3;
	@FXML private Button btnSelFol4;
	@FXML private Button btnSelFol5;
	@FXML private TilePane imageList;
	@FXML private Label defFolName;
	@FXML private Label selFol1Name;
	@FXML private Label selFol2Name;
	@FXML private Label selFol3Name;
	@FXML private Label selFol4Name;
	@FXML private Label selFol5Name;
	
	@FXML private Button btnCap; // 캡처버튼
	
	File defFolPath;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	btnHelp.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			try {
				handleBtnHelpAction(event);
			} catch (Exception e) {
				
			}
		} 
	   });
	
	// btnShare.setOnAction(event->handleBtnShareAction(event));
	btnShare.setOnAction(event->{
        try {
           handleBtnShareAction(event);
        } catch (Exception e) {
           
        }
     });
	
	 btnExit.setOnAction(event->{
		try {
			handleBtnExitAction(event);
		} catch (Exception e) {
			
		}
	});
	 btnOption.setOnAction(event->{
		try {
			handleBtnOptionAction(event);
		} catch (Exception e) {
			
		}
	});
	 btnDefFol.setOnAction(event->{
		 try {
			 handleBtnDefFolAction(event);
		 } catch (Exception e) {
			 
		 }
	 });
	 
		// 캡처 기능-------------------------------------------	
	 btnCap.setOnAction(event->{
			try {
				handleBtnCaptureAction(event);
			} catch (Exception e) {
				
			}
		});
	 	 
	 
	 
	 
	 
	 
	 
	}
	
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void handleBtnHelpAction(ActionEvent e) throws Exception {
		Stage dialogExit = new Stage(StageStyle.UTILITY);
		dialogExit.initModality(Modality.WINDOW_MODAL);
		dialogExit.initOwner(primaryStage);
		dialogExit.setTitle("도움말");
		
		Parent parentHelp = FXMLLoader.load(getClass().getResource("Help_dialog.fxml"));
		Scene scene = new Scene(parentHelp);
		dialogExit.setScene(scene);
		dialogExit.setResizable(false);
		dialogExit.show();
	}
	
	public void handleBtnShareAction(ActionEvent e) throws Exception {
	      Stage dialogExit = new Stage(StageStyle.UTILITY);
	      dialogExit.initModality(Modality.WINDOW_MODAL);
	      dialogExit.initOwner(primaryStage);
	      dialogExit.setTitle("공유");
	      
	      Parent parentHelp = FXMLLoader.load(getClass().getResource("Share_dialog.fxml"));
	      Scene scene = new Scene(parentHelp);
	      dialogExit.setScene(scene);
	      dialogExit.setResizable(false);
	      dialogExit.show();
	   }
		//공유 팝업 띄우기
	
	public void handleBtnExitAction(ActionEvent e) throws Exception {
		Stage dialogExit = new Stage(StageStyle.UTILITY);
		dialogExit.initModality(Modality.WINDOW_MODAL);
		dialogExit.initOwner(primaryStage);
		dialogExit.setTitle("확인");
		
		Parent parentExit = FXMLLoader.load(getClass().getResource("Exit_dialog.fxml"));
		Button btnYes = (Button) parentExit.lookup("#btnYes");
		Button btnNo = (Button) parentExit.lookup("#btnNo");
		btnYes.setOnAction(event->Platform.exit());
		btnNo.setOnAction(event->dialogExit.close());
		
		Scene scene = new Scene(parentExit);
		dialogExit.setScene(scene);
		dialogExit.setResizable(false);
		dialogExit.show();
	}
	
	public void handleBtnCaptureAction(ActionEvent e) throws Exception {
		Capture2.ReadWriteTime();
		Capture2.HandleUp();
	}
	
	public void handleBtnOptionAction(ActionEvent e) throws Exception {
		Stage dialogOption = new Stage(StageStyle.UTILITY);
		dialogOption.initModality(Modality.WINDOW_MODAL);
		dialogOption.initOwner(primaryStage);
		dialogOption.setTitle(" ");
		
		Parent parentOption = FXMLLoader.load(getClass().getResource("Option_dialog.fxml"));
		Button btnOkOp = (Button) parentOption.lookup("#btnOkOp");
		Button btnCanOp = (Button) parentOption.lookup("#btnCanOp");
		ComboBox extenBox = (ComboBox) parentOption.lookup("#extenBox");
		ComboBox sortType1 = (ComboBox) parentOption.lookup("#sortType1");
		ComboBox sortType2 = (ComboBox) parentOption.lookup("#sortType2");
		Button btnOkSa = (Button) parentOption.lookup("#btnOkSa");
		Button btnCanSa = (Button) parentOption.lookup("#btnCanSa");
		Button btnProg1 = (Button) parentOption.lookup("#btnProg1");
		Button btnProg2 = (Button) parentOption.lookup("#btnProg2");
		Button btnProg3 = (Button) parentOption.lookup("#btnProg3");
		Button btnProg4 = (Button) parentOption.lookup("#btnProg4");
		Button btnProg5 = (Button) parentOption.lookup("#btnProg5");
		Button btnLink1 = (Button) parentOption.lookup("#btnLink1");
		Button btnLink2 = (Button) parentOption.lookup("#btnLink2");
		Button btnLink3 = (Button) parentOption.lookup("#btnLink3");
		Button btnLink4 = (Button) parentOption.lookup("#btnLink4");
		Button btnLink5 = (Button) parentOption.lookup("#btnLink5");
		Button btnDef = (Button) parentOption.lookup("#btnDef");
		TextField defaultLink = (TextField) parentOption.lookup("#defaultLink");
		TextField selectProg1 = (TextField) parentOption.lookup("#selectProg1");
		TextField selectProg2 = (TextField) parentOption.lookup("#selectProg2");
		TextField selectProg3 = (TextField) parentOption.lookup("#selectProg3");
		TextField selectProg4 = (TextField) parentOption.lookup("#selectProg4");
		TextField selectProg5 = (TextField) parentOption.lookup("#selectProg5");
		TextField selectLink1 = (TextField) parentOption.lookup("#selectLink1");
		TextField selectLink2 = (TextField) parentOption.lookup("#selectLink2");
		TextField selectLink3 = (TextField) parentOption.lookup("#selectLink3");
		TextField selectLink4 = (TextField) parentOption.lookup("#selectLink4");
		TextField selectLink5 = (TextField) parentOption.lookup("#selectLink5");
		
		
		
		btnProg1.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("실행 파일", "*.exe"));
			File prog1 = fileChooser.showOpenDialog(primaryStage);
			String prog1Name = prog1.getName();
			selectProg1.setText(prog1Name);
		});
		btnProg2.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("실행 파일", "*.exe"));
			File prog2 = fileChooser.showOpenDialog(primaryStage);
			String prog2Name = prog2.getName();
			selectProg2.setText(prog2Name);
			
		});
		btnProg3.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("실행 파일", "*.exe"));
			File prog3 = fileChooser.showOpenDialog(primaryStage);
			String prog3Name = prog3.getName();
			selectProg3.setText(prog3Name);
		});
		btnProg4.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("실행 파일", "*.exe"));
			File prog4 = fileChooser.showOpenDialog(primaryStage);
			String prog4Name = prog4.getName();
			selectProg4.setText(prog4Name);
			
		});
		btnProg5.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("실행 파일", "*.exe"));
			File prog5 = fileChooser.showOpenDialog(primaryStage);
			String prog5Name = prog5.getName();
			selectProg5.setText(prog5Name);
		});
		btnLink1.setOnAction(event->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selLink1 = directoryChooser.showDialog(primaryStage);
			String selLink1Dir = selLink1.getPath();
			selectLink1.setText(selLink1Dir);
		});
		btnLink2.setOnAction(event->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selLink2 = directoryChooser.showDialog(primaryStage);
			String selLink2Dir = selLink2.getPath();
			selectLink2.setText(selLink2Dir);
		});
		btnLink3.setOnAction(event->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selLink3 = directoryChooser.showDialog(primaryStage);
			String selLink3Dir = selLink3.getPath();
			selectLink3.setText(selLink3Dir);
		});
		btnLink4.setOnAction(event->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selLink4 = directoryChooser.showDialog(primaryStage);
			String selLink4Dir = selLink4.getPath();
			selectLink4.setText(selLink4Dir);
		});
		btnLink5.setOnAction(event->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selLink5 = directoryChooser.showDialog(primaryStage);
			String selLink5Dir = selLink5.getPath();
			selectLink5.setText(selLink5Dir);
		});
		btnDef.setOnAction(event->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File defLink = directoryChooser.showDialog(primaryStage);
			String defLinkDir  = defLink.getPath();
			defaultLink.setText(defLinkDir);
		});
		
        btnOkOp.setOnAction(event->{
			
		});
		btnCanOp.setOnAction(event->dialogOption.close());
		btnOkSa.setOnAction(event->{
			if (selectLink1 != null) {
				selFol1Name.textProperty().bind(selectProg1.textProperty());
				}
			if (selectLink2 != null) {
				selFol2Name.textProperty().bind(selectProg2.textProperty());
			}
			if (selectProg3 != null) {
				selFol3Name.textProperty().bind(selectProg3.textProperty());
			}
			if (selectProg4 != null) {
				selFol4Name.textProperty().bind(selectProg4.textProperty());
			}
			if (selectProg5 != null) {
				selFol5Name.textProperty().bind(selectProg5.textProperty());
			}
			if (defaultLink != null) {
				
			}
			
			selFol1Name.textProperty().unbind();
			selFol2Name.textProperty().unbind();
			selFol3Name.textProperty().unbind();
			selFol4Name.textProperty().unbind();
			selFol5Name.textProperty().unbind();
			dialogOption.close();
		});
		btnCanSa.setOnAction(event->dialogOption.close());
		
		Scene scene = new Scene(parentOption);
		dialogOption.setScene(scene);
		dialogOption.setResizable(false);
		dialogOption.show();
	}
	
	public void handleBtnDefFolAction(ActionEvent e) {
		if (defFolPath == null) {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			final File defaultFolDir = directoryChooser.showDialog(primaryStage);
			File[] imageFiles = defaultFolDir.listFiles(new ImageFileFilter());
			
			imageList.getChildren().clear();
			
			for(File file : imageFiles) {
				try {
					   ImageView imageView = new ImageView();
					   imageView.setFitHeight(280);
					   imageView.setFitWidth(180);
					   imageView.setImage(new Image(new FileInputStream(file)));
					   
					   imageList.getChildren().add(imageView);
				}catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
		else {
			final File defaultFolDir = defFolPath;
			File[] imageFiles = defaultFolDir.listFiles(new ImageFileFilter());
			
            imageList.getChildren().clear();
			
			for(File file : imageFiles) {
				try {
					   ImageView imageView = new ImageView();
					   imageView.setFitHeight(280);
					   imageView.setFitWidth(180);
					   imageView.setImage(new Image(new FileInputStream(file)));
					   
					   imageList.getChildren().add(imageView);
				}catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
		}
	}
  }
}

class ImageFileFilter implements FileFilter{
	private final String[] vaildFileExtension = new String[] {"jpg", "jpeg", "png", "gif"};
	@Override
	public boolean accept(File pathName) {
		for (String extension : vaildFileExtension) {
			if (pathName.getName().toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}