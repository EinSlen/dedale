package dedale.gestionnaires;

import java.util.ArrayList;

import dedale.elements.Direction;
import dedale.elements.ElementsJeu;
import dedale.elements.PositionPlateau;
/**
 * Interface spécifiant les différentes méthodes attendues d'un gestionnaire gérant l'interface graphique.
 * @author Jean-François Condotta
 * @since 4.0 (26/02/23)
 * @version 4.0 (26/02/23)
 */
public interface GestionnaireIG {
	/**
	 * Méthode permettant d'afficher la vue afin que les paramètres des joueurs puissent être saisis.
	 */
	public void lancerVueParametrage();

	/**
	 * Méthode permettant d'afficher la vue correspondant au jeu.
	 * @param elementsJeu Les éléments du jeu à visualiser.
	 */
	public void lancerVueJeu(final ElementsJeu elementsJeu);

	/**
	 * Méthode permettant de mettre à jour l'affichage du jeu (c'est-à-dire la vue du jeu). 
	 */
	public void mettreAJourAffichageJeu();
	
	
	/**
	 * Méthode permettant d'arrêter toute prise en compte de clics de la part de l'utilisateur pour rotationner
	 * la pièce hors plateau, décaler une ligne ou une colonne ou une séléction d'une pièce sur le plateau.
	 */
	public void stopperTouteAttenteClic();
	
	/**
	 * Méthode permettant de mettre la vue du jeu dans l'attente d'un clic pour rotationner
	 * la pièce hors plateau ou décaler une ligne ou une colonne.
	 * @param delai Un délai en millisecondes à partir duquel sera mise en place l'attente. 
	 * Ce délai doit être strictement supérieure à 0.
	 */
	public void attendreClicRotationOuDecalage(final int delai);
	
	/**
	 * Méthode permettant de mettre la vue du jeu dans l'attente d'un clic sur le plateau de la part de l'utilisateur
	 * pour sélectionner une position.
	 * @param delai Un délai en millisecondes à partir duquel sera mise en place l'attente. 
	 * Ce délai doit être strictement supérieure à 0.
	 */
	public void attendreClicPositionPlateau(final int delai);
	
	/**
	 * Méthode permettant de définir un chemin qui sera affiché sur la plateau par la vue du jeu.
	 * Un seul chemin à la fois peut être défini et affiché par la vue du jeu.
	 * Une mise à jour de l'affichage est systématiquement réalisée.
	 * 
	 * @param chemin Le chemin à définir et affiché.
	 * @param delai Un délai après lequel la méthode GestionnaireJeu.gererDeplacementChemin 
	 * sera appelée avec chemin comme paramètre. Ce délai doit être strictement supérieure à 0.
	 */
	public void definirEtAfficherChemin(final ArrayList<PositionPlateau> chemin,final int delai);
	
	/**
	 * Méthode enlevant le chemin affiché et une sélection de ligne ou de colonne.
	 * Une mise à jour de l'affichage du jeu est systématiquement réalisée.
	 */
	public void enleverCheminEtSelection();
	
	/**
	 * Méthode permettant d'afficher le joueur courant comme le joueur gagant.
	 */
	public void afficherFinAvecGagnant();
	
	/**
	 * Méthode demandant à l'interface graphique de simuler/faire une demande de rotation après un certain délai.
	 * @param delai Le délai après lequel la demande sera faite. Ce délai doit être strictement supérieure à 0.
	 */
	public void realiserDemandeRotation(final int delai);
	
	/**
	 * Méthode demandant à l'interface graphique de simuler/faire une demande de décalage après un certain délai.
	 * @param direction La direction du décalage demandé.
	 * @param numLigOuCol Le numéro de la ligne ou de la colonne du décalage demandé.
	 * @param delai Le délai après lequel la demande sera faite. Ce délai doit être strictement supérieure à 0.
	 */
	public void realiserDemandeDecalage(final Direction direction,final int numLigOuCol,final int delai);
	
	/**
	 * Méthode demandant à l'interface graphique de simuler/faire une demande de déplacement après un certain délai.
	 * @param numLig Le numéro de la ligne pour le déplacement demandé.
	 * @param numCol Le numéro de la colonne pour le déplacement demandé.
	 * @param delai Le délai après lequel la demande sera faite. Ce délai doit être strictement supérieure à 0.
	 */
	public void realiserDemandeDeplacement(final int numLig,final int numCol,final int delai);
	
	/**
	 * Méthode demandant à la vue de jouer un son.
	 * La vue de jeu joue déjà des sons. Seul la récupération d'un objet n'entraîne
	 * pas de son. Le son 3 pourrait correspondre à la récupération d'un objet.
	 * @param numSon Un numéro compris entre 0 et 8.
	 */
	public void jouerSon(int numSon);
}
