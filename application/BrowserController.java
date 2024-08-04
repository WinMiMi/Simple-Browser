package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BrowserController implements Initializable{
	
	@FXML
	private TextField tfURL;

    @FXML
    private WebView webView;
    
    @FXML
    private ProgressBar loadingbar;
    
    @FXML
    private WebEngine webEngine;
    
    private WebHistory webHistory;
    
 

    @FXML
    void processBack(MouseEvent event) {
    	webHistory = webView.getEngine().getHistory();
    	webHistory.go(-1);
    	ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
		 int currentIndex = webHistory.getCurrentIndex();
		 tfURL.setText(entries.get(webHistory.getCurrentIndex()).getUrl());
		 
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setTitle(entries.get(currentIndex).getTitle());
		stage.show();
    }
    
    

    @FXML
    void processForward(MouseEvent event) {
    	webHistory = webView.getEngine().getHistory();
    	webHistory.go(1);
    	ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
		 int currentIndex = webHistory.getCurrentIndex();
		 tfURL.setText(entries.get(webHistory.getCurrentIndex()).getUrl());
		 
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setTitle(entries.get(currentIndex).getTitle());
		stage.show();
    }

    @FXML
    void processReload(MouseEvent event) {
    	webEngine.reload();

    }
    
    private void loadWebPage(String url, KeyEvent event) {
    	 webEngine = webView.getEngine();
		 loadingbar.setVisible(true);
		 
		 loadingbar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
		 
		 webEngine.load("https://"+url);

		 webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
			 if(newValue.equals(Worker.State.SUCCEEDED)) {
				 webHistory = webView.getEngine().getHistory();
	    		 ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
	    		 int currentIndex = webHistory.getCurrentIndex();
	    		 tfURL.setText(entries.get(webHistory.getCurrentIndex()).getUrl());
	    		
	    		 
	    		 if(event != null) {
	     		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    		stage.setTitle(entries.get(currentIndex).getTitle());

			 }
			 }
		 });
    }
    

    @FXML
    void processURL(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		 String url = tfURL.getText().trim();
    	 		 
    	} 
    }

    @FXML
    void processZoomIn(MouseEvent event) {
    	webView.setZoom(webView.getZoom()* 2.0); // 2x , 4x , 8x ...
    }

    @FXML
    void processZoomOut(MouseEvent event) {
    	webView.setZoom(webView.getZoom()/ 2.0); //2x , 4x , 8x ...
    }



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadingbar.setVisible(false);
		loadWebPage("www.google.com", null);

	}

}
