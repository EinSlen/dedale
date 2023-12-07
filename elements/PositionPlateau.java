package dedale.elements;

import java.util.Objects;

/**
 * Une instance de la classe PositionPlateau permet de représenter une position d'un élément sur le plateau de jeu.
 * Chaque position est définie par un couple d'entiers (numéro de ligne,numéro de colonne).
 * <ul>
 * <li>Un numéro de ligne valide est un entier compris (au sens large) entre 0 et {@link NB_LIGS_PLATEAU}-1.</li>
 * <li>Un numéro de colonne valide est un entier compris (au sens large) entre 0 et {@link NB_COLS_PLATEAU}-1.</li>
 * <li>Le numéro de ligne 0 correspond à la ligne la plus basse du plateau.</li>
 * <li>Le numéro de colonne 0 correspond à la colonne la plus à gauche du plateau.</li>
 * <li>Le numéro de ligne {@link NB_LIGS_PLATEAU}-1 correspond à la ligne la plus haute du plateau.</li>
 * <li>Le numéro de colonne {@link NB_COLS_PLATEAU}-1 correspond à la colonne la plus à droite du plateau.</li>
 * </ul>
 * @author Jean-François Condotta
 * @since 1.0 (22/02/23)
 * @version 1.0 (22/02/23)
 */
public class PositionPlateau {
	/**
	 * Le nombre de lignes d'un plateau, la valeur de cette constante est {@value}.
	 */
	static final public int NB_LIGS_PLATEAU=8;

	/**
	 * Le nombre de colonnes d'un plateau, la valeur de cette constante est {@value}.
	 */
	static final public int NB_COLS_PLATEAU=12;

	/**
	 * Le numéro de ligne de la position sur le plateau.
	 */
	private int numLigne; // Le numéro de ligne de la position.

	/**
	 * Le numéro de colonne de la position sur le plateau.
	 */
	private int numColonne; // Le numéro de colonne de la position.

	/**
	 * Constructeur permettant de créer une nouvelle position.
	 * Dans le cas où le numéro de ligne ou le numéro de colonne n'est pas valide une Exception de type IllegalArgumentExcpetion est levée.
	 * @param numLigne Le numéro de ligne de la position.
	 * @param numColonne Le numéro de colonne de la position.
	 */
	public PositionPlateau(final int numLigne,final int numColonne) {
		super();
		testerPosition(numLigne,numColonne);
		this.numLigne = numLigne;
		this.numColonne = numColonne;
	}

	/**
	 * Constructeur permettant de créer une nouvelle position à partir d'une position existante. 
	 * @param position Une position.
	 */
	public PositionPlateau(final PositionPlateau position) {
		this(position.numLigne,position.numColonne);
	}

	/**
	 * Retourne le numéro de ligne de la position.
	 * @return Le numéro de ligne de la position.
	 */
	public int getNumLigne() {
		return numLigne;
	}

	/**
	 * Change le numéro de ligne de la position.
	 * Dans le cas où le numéro de ligne conduit à une position non valide une Exception de type IllegalArgumentExcpetion est levée.
	 * @param numLigne Le nouveau numéro de ligne.
	 */
	public void setNumLigne(final int numLigne) {
		testerPosition(numLigne,numColonne);
		this.numLigne = numLigne;
	}

	/**
	 * Retourne le numéro de colonne de la position.
	 * @return Le numéro de colonne de la position.
	 */
	public int getNumColonne() {
		return numColonne;
	}

	/**
	 * Change le numéro de colonne de la position.
	 * Dans le cas où le numéro de colonne conduit à une position non valide une Exception de type IllegalArgumentExcpetion est levée.
	 * @param numColonne Le nouveau numéro de colonne.
	 */
	public void setNumColonne(final int numColonne) {
		testerPosition(numLigne,numColonne);
		this.numColonne = numColonne;
	}

	/**
	 * Retourne true si et seulement si la position et la position passée en paramètre représente la même position.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (! (obj instanceof PositionPlateau))
			return false;
		return (numLigne==((PositionPlateau)obj).numLigne)&&(numColonne==((PositionPlateau)obj).numColonne);
	}

	/**
	 * Redéfinition de la méthode retournant le code de hachage d'un objet.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(numColonne, numLigne);
	}

	/**
	 * Retourne un clone de la position basé sur le constructeur prenant en paramètre une position.
	 */
	@Override
	public Object clone() {
		return new PositionPlateau(this);
	}

	/**
	 * Retourne une représentation de la position de la forme [lig: L, col: C].
	 */
	@Override
	public String toString() {
		return "[lig: "+numLigne+" ,col: "+numColonne+"]";
	}

	/**
	 * Méthode permettant de tester si les numéros passés en paramètres correspondent à une position valide.
	 * Dans le cas où le numéro de ligne et/ou le numéro de colonne ne sont pas valides une Exception de type IllegalArgumentExcpetion est levée.
	 * @param numLigne Un numéro de ligne.
	 * @param numColonne Un numéro de colonne.
	 */
	private static void testerPosition(final int numLigne,final int numColonne){
		if ((numLigne<0)||(numLigne>=NB_LIGS_PLATEAU)||(numColonne<0)||(numColonne>=NB_COLS_PLATEAU))
			throw new IllegalArgumentException("Le numéro de ligne et/ou le numéro de colonne ne sont pas valides : numéro de ligne "+numLigne+" et numéro de colonne "+numColonne+" !");
	}

	/**
	 * Méthode permettant de décaler la position d'une unité dans une certaine direction.
	 * Si par décalage la position "sort" du plateau, la nouvelle position correspondra à celle se trouvant à l'autre extrémité de la ligne ou de la colonne par rapport
	 * à la position initiale.
	 * @param direction La direction vers laquelle se fait le décalage.
	 */
	public void decaler(final Direction direction) {
		switch (direction) {
		case HAUT: 
			if (++numLigne==NB_LIGS_PLATEAU)
				numLigne=0;
			break;
		case BAS: 
			if (--numLigne==-1)
				numLigne=NB_LIGS_PLATEAU-1;
			break;
		case DROITE:
			if (++numColonne==NB_COLS_PLATEAU)
				numColonne=0;
			break;
		case GAUCHE:
			if (--numColonne==-1)
				numColonne=NB_COLS_PLATEAU-1;
		}
	}


	/**
	 * Méthode permettant de tester si deux positions sont deux positions adjacentes (se trouvant à une distance de 1).
	 * @param position1 Une position (possiblement null).
	 * @param position2 Une position (possiblement null).
	 * @return true si et seulement si les deux positions sont adjacentes.
	 */
	public static boolean positionsAdjacentes(final PositionPlateau position1,final PositionPlateau position2) {
		if ((position1==null)||(position2==null))
			return false;
		if ((position1.numLigne==position2.numLigne)&&((position1.numColonne==position2.numColonne+1)||(position1.numColonne==position2.numColonne-1)))
			return true;
		if ((position1.numColonne==position2.numColonne)&&((position1.numLigne==position2.numLigne+1)||(position1.numLigne==position2.numLigne-1)))
			return true;
		return false;
	}
}
