package magic_book;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class Main extends Application {
    private String mode;
    
    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        FlowPane root = new FlowPane();
        //Button button = new Button("Bouton");
        //Label l= new Label("0");
        
        //button.setOnAction(value ->  {
        //   l.setText("1");
        //});
        
        //root.getChildren().add(button);
        //root.getChildren().add(l);
        mode = "0";
        Scene scene = new Scene(root,600,600);
        
        Button add = new Button("Ajouter");
        Button del = new Button("Supprimer");
        Button mod = new Button("Modifier");
        
        add.setOnAction(value ->  {
           mode = "add";
           System.out.println("clic bouton");
        });
        del.setOnAction(value ->  {
           mode = "del";
           System.out.println("clic bouton");
        });
        mod.setOnAction((ActionEvent value) ->  {
           mode = "mod";
           System.out.println("clic bouton");
        });
        
        root.addEventFilter(MouseEvent.MOUSE_PRESSED,(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clic fenêtre avec mode "+mode);
                }
            }));
        
        root.getChildren().addAll(add,del,mod);
        
        
        stage.setScene(scene);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

}
