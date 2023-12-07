package dedale.elements;

/**
 * Cette classe correspond aux joueurs ordinateurs.
 * @author Jean-François Condotta
 * @since 3.0 (24/02/23)
 * @version 3.0 (24/02/23)
 */
abstract public class JoueurOrdi extends Joueur {
	/**
	 * Constructeur permettant de créer un nouveau joueur ordinateur avec un identifiant, un pseudo, une position et un booléen pour indiquer si
	 * c'est son tour de jouer.
	 * Dans le cas où l'identifiant ou le pseudo du joueur ne sont pas valides ou dans le cas où la position vaut null,
	 * une Exception de type IllegalArgumentExcpetion est levée.
	 * Si l'identifiant du joueur est 0 alors le booléen indiquant que c'est son tour est mis à true, dans le cas contraire il est mis à false.
	 * @param id L'identifiant du joueur.
	 * @param pseudo Le pseudo du joueur.
	 * @param position La position du joueur.
	 * @param estASonTourDeJouer Un booléen indiquant si c'est à son tour.
	 */
	protected JoueurOrdi(final int id,final String pseudo,final PositionPlateau position,final boolean estASonTourDeJouer) {
		super(id,pseudo,position,estASonTourDeJouer);
	}

	/**
	 * Méthode qui doit calculer le coup à jouer en fonction des éléments du jeu.
	 *
	 * @param elementsJeu Les éléments du jeu.
	 * @return Le coup calculé que souhaite jouer le joueur Ordinateur.
	 */
	abstract public CoupJeu calculerCoup(ElementsJeu elementsJeu);


}
