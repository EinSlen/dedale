package dedale;

import dedale.gestionnaires.GestionnaireIG;
import dedale.gestionnaires.GestionnaireJeu;
import dedale.gestionnaires.Jeu;
import dedaleig.ig.IG;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * La première version de l'application Dédale ;-).
 * @author Damlencourt Valentin
 * @since 5.0 (26/02/23)
 * @version 5.0 (26/02/23)
 */
public class DedaleV1 extends Application {

    @Override
    public void start(Stage stage) {
    	stage.initStyle(StageStyle.UNDECORATED);
    	stage.xProperty().addListener((obs, oldVal, newVal) -> stage.centerOnScreen());
    	stage.yProperty().addListener((obs, oldVal, newVal) -> stage.centerOnScreen());
    	final GestionnaireJeu jeu=new Jeu();
    	final GestionnaireIG ig=new IG(stage,jeu);
        jeu.lancerJeu(ig);
    	
         final Pane root= new Pane();
    	final Scene scene = new Scene(root,IG3.LARGEUR_TOTALE,IG3.HAUTEUR_TOTALE, Color.BLACK);
    	final IG3 ig=new IG3(root);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
        ig.initialiserElementsJeu(new ElementsJeu());
        ig.lancerAttenteCoup();
        
    }
    
    public static void main(String[] args) {
        launch();
    }
}