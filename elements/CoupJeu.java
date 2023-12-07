package dedale.elements;
/**
 * La classe permet de représenter un coup de jeu (réalisé par un ordinateur).
 * @author Jean-François Condotta
 * @since 3.0 (22/02/23)
 * @version 3.0 (22/02/23)
 */
public class CoupJeu {
	/**
	 * Le nombre de rotations de la pièce hors plateau.
	 */
	private int nombreRotations;

	/**
	 * La direction du décalage.
	 */
	final private Direction direction;

	/**
	 * Le numéro de la ligne ou de la colonne pour le décalage.
	 */
	final private int numLigneOuColonneDec;

	/**
	 * Le numéro de ligne du plateau pour le déplacement voulu.
	 */
	final private int numLigneDep;

	/**
	 * Le numéro de colonne de uplateau pour le déplacement voulu.
	 */
	final private int numColonneDep;

	
	/**
	 * Constructeur permettant de construire un coup de jeu.
	 * Auncune vérification n'est faite sur les paramètres donnés.
	 * (sinon une Exception de type IllegalArgumentExcpetion est levée).
	 * @param nombreRotations Le nombre de rotations.
	 * @param direction La direction pour le décalage.
	 * @param numLigneOuColonneDec Le numéro de la ligne ou de la colonne pour le décalage.
	 * @param numLigneDep Le numéro de la ligne pour le déplacement voulu.
	 * @param numColonneDep Le numéro de la colonne pour le déplacement voulu.
	 */
	public CoupJeu(int nombreRotations, Direction direction, int numLigneOuColonneDec, int numLigneDep, int numColonneDep) {
		this.nombreRotations = nombreRotations;
		this.direction = direction;
		this.numLigneOuColonneDec = numLigneOuColonneDec;
		this.numLigneDep = numLigneDep;
		this.numColonneDep = numColonneDep;
	}


	@Override
	public String toString() {
		return "CoupJeu [nombreRotations=" + nombreRotations + ", direction=" + direction + ", numLigneOuColonneDec="
				+ numLigneOuColonneDec + ", numLigneDep=" + numLigneDep + ", numColonneDep=" + numColonneDep + "]";
	}


	/**
	 * Méthode retournant le nombre de rotations du coup de jeu.
	 * @return Le nombre de rotations du coup de jeu.
	 */
	public int getNombreRotations() {
		return nombreRotations;
	}

	/**
	 * Méthode permettant de décrémenter le nombre de rotations du coup.
	 */
	public void decNombreRotations() {
		nombreRotations--;
	}

	/**
	 * Méthode retournant la direction pour le décalage du coup de jeu.
	 * @return La direction pour le décalage.
	 */
	public Direction getDirection() {
		return direction;
	}


	/**
	 * Méthode retournant le numéro de la ligne ou de la colonne pour le décalage du coup de jeu.
	 * @return Le numéro de la ligne ou de la colonne pour le décalage.
	 */
	public int getNumLigneOuColonneDec() {
		return numLigneOuColonneDec;
	}


	/**
	 * Méthode retournant le numéro de la ligne pour le déplacement voulu.
	 * @return Le numéro de la ligne pour le déplacement voulu.
	 */
	public int getNumLigneDep() {
		return numLigneDep;
	}


	/**
	 * Méthode retournant le numéro de la colonne pour le déplacement voulu.
	 * @return Le numéro de la colonne pour le déplacement voulu.
	 */
	public int getNumColonneDep() {
		return numColonneDep;
	}

	
}
