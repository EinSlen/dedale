package dedale.gestionnaires;

import java.util.ArrayList;

import dedale.elements.Direction;
import dedale.elements.PositionPlateau;

public interface GestionnaireJeu {
	/**
	 * Toute première méthode appelée lors du démarrage de l'application.
	 * Les pseudos et la nature des joueurs ne sont pas encore paramétrés.
	 * @param ig Le gestionnaire gérant l'interface graphique du jeu. 
	 */
	public void lancerJeu(final GestionnaireIG ig);

	/**
	 * Méthode appelée après que les pseudos et les natures des joueurs aient été saisies ainsi que l'entier correspondant 
	 * à la probabilité de génération aléatoire d'une sortie d'une pièce.
	 * Les éléments du jeu seront créés par cette méthode.
	 * @param pseudos Les pseudos des joueurs (ces pseudos sont valides).
	 * @param natures La nature de chacun des joueurs ("Humain", "Ordi N1", "Ordi N2", "Ordi N3" ou "Ordi N4").
	 * @param probSortie Un nombre entier (50,55,60,65,70,75,80,85,90 ou 95) correspondant la probabilité de génération aléatoire d'une sortie d'une pièce.
	 */
	public void gererParametres(final String[] pseudos,final String[] natures,final int probSortie);
	
	/**
	 * Méthode devant gérer une demande de rotation de la pièce hors plateau.
	 */
	public void gererDemandeRotation();
	
	/**
	 * Méthode devant gérer une demande de décalage.
	 * @param direction Une direction.
	 * @param numLigOuCol Un numéro correspondant à un numéro de ligne ou de colonne en fonction de la direction donnée en paramètre.
	 */
	public void gererDemandeDecalage(final Direction direction,final int numLigOuCol);
	
	/**
	 * Méthode devant gérer une demande de déplacement sur le plateau.
	 * 
	 * @param numLig Le numéro de la ligne sur laquelle veut aller le joueur.
	 * @param numCol Le numéro de la colonne sur laquelle veut aller le joueur.
	 */
	public void gererDemandeDeplacement(final int numLig,final int numCol);
	
	/**
	 * Méthode qui sera epplée systématiquement lorsque la définition et l'affichage d'un chemin a été demandé auprès de la vude du jeu.
	 * @param chemin Le chemin qui a été auparavant demandé à être défini et affiché.
	 */
	public void gererDeplacementSurChemin(ArrayList<PositionPlateau> chemin);
		
}
