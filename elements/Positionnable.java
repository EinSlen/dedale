package dedale.elements;

import java.util.ArrayList;

/**
 * La classe Positionnable est une classe abstraite permettant de représenter un élément 
 * positionnable sur le plateau, c'est-à-dire un élément (joueur, pièce du labyrinthe ou objet) ayant une position sur le plateau (position représentée par une instance de la classe {@link dedale.elements.PositionPlateau}).
 * Un Positionnable non positionné sur le plateau a sa position qui vaut null.
 * 
 * @author Damlencourt Valentin
 * @since 1.0 (22/02/23)
 * @version 1.0 (22/02/23)
 *
 */
public abstract class Positionnable {
	/**
	 * La position de l'élément sur le plateau (la valeur null signifiera que l'élément n'est plus sur le plateau).
	 */
	private PositionPlateau position;

	/**
	 * Un constructeur permettant de créer un élément positionnable avec une position initialisée à null (hors plateau).
	 */
	protected Positionnable() {
		position=null;
	}

	/**
	 * Un constructeur permettant de créer un élément positionnable avec une position initiale (non null).
	 * Dans le cas où la position vaut null, une Exception de type IllegalArgumentExcpetion est levée.
	 * @param position La position de l'élément (non null).
	 */
	protected Positionnable(final PositionPlateau position) {
		if (position==null)
			throw new IllegalArgumentException("La position ne doit pas être null !");
		this.position=position;
	}

	/**
	 * Méthode retournant la position de l'element (possiblement null).
	 * @return La position de l'élément.
	 */
	public PositionPlateau getPosition() {
		return position;
	}

	/**
	 * Méthode permettant de changer la position de l'élément. En mettant la nouvelle position à null, l'élément est supposé sortir du plateau).
	 * @param position La nouvelle position de l'élément (cette position peut être null).
	 */
	public void setPosition(final PositionPlateau position) {
		this.position = position;
	}

	/**
	 * Méthode testant si l'élément est sur le plateau, c'est-à-dire s'il a une position non null.
	 * @return true si et seulement la position de l'élément est non null.
	 */
	public boolean estSurPlateau() {
		return position!=null;
	}

	/**
	 * Méthode permettant de tester si le positionnable est à la même position qu'un autre
	 * positionnable.
	 * @param positionnable L'autre positionnable.
	 * @return true si et seulement si l'autre positionnable est à la même position. Notez que si une des deux positions vaut null, false est retourné.
	 */
	public boolean auMemeEndroit(Positionnable positionnable) {
		if ((position==null)||(positionnable.position==null))
				return false;
		return position.equals(positionnable.position);
	}

	/**
	 * Méthode permettant de décaler la position d'une unité dans une certaine direction de chaque élément positionnable appartenant à un tableau.
	 * Chaque élément est supposé avoir une position non null.
	 * Cette méthode utilise la méthode {@link dedale.elements.PositionPlateau#decaler(Direction)} pour effectuer le traitement demandé.
	 * Si par décalage un élément "sort" du plateau, sa nouvelle position correspondra à celle se trouvant à l'autre extrémité de la ligne ou de la colonne par rapport
	 * à sa position initiale.
	 * @param positionnables Un tableau non null d'éléments positionnables ayant une position non null.
	 * @param direction La direction vers laquelle s'effectue le décalage.
	 */
	static public void decalerPositions(final ArrayList<Positionnable> positionnables, final Direction direction) {
		for (Positionnable positionnable : positionnables)
			positionnable.position.decaler(direction);
	}

	/**
	 * Méthode permettant d'extraire des éléments positionnés sur une ligne.
	 * @param positionnables Un tableau non null d'éléments positionnables pouvant avoir une position null.
	 * @param numLigne Le numéro de la ligne pour laquelle nous souhaitons extraire les éléments.
	 * @return Un tableau contenant les éléments positionnables se trouvant sur la ligne spécifiée.
	 */
	static public ArrayList<Positionnable> extraireSurLigne(final ArrayList<Positionnable> positionnables, final int numLigne) {
		ArrayList<Positionnable> elements=new ArrayList<Positionnable>();
		for (Positionnable positionnable : positionnables)
			if ((positionnable.position!=null)&&(positionnable.position.getNumLigne()==numLigne))
				elements.add(positionnable);
		return elements;
	}

	/**
	 * Méthode permettant d'extraire des éléments positionnés sur une colonne.
	 * @param positionnables Un tableau non null d'éléments positionnables pouvant avoir une position null.
	 * @param numColonne Le numéro de la colonne pour laquelle nous souhaitons extraire les éléments.
	 * @return Un tableau contenant les éléments positionnables se trouvant sur la colonne spécifiée.
	 */
	static public ArrayList<Positionnable> extraireSurColonne(final ArrayList<Positionnable> positionnables, final int numColonne) {
		ArrayList<Positionnable> elements=new ArrayList<Positionnable>();
		for (Positionnable positionnable : positionnables)
			if ((positionnable.position!=null)&&(positionnable.position.getNumColonne()==numColonne))
				elements.add(positionnable);
		return elements;
	}

	/**
	 * Méthode permettant d'extraire des éléments positionnés sur une ligne ou sur une colonne.
	 * Le choix s'effectuera en fonction d'une direction donnée en paramètre. Pour les directions HAUT et BAS, une colonne sera considérée,  tandis que pour les directions
	 * DROITE et GAUCHE, une ligne sera considérée. 
	 * @param positionnables Un tableau non null d'éléments positionnables pouvant avoir une position null.
	 * @param direction Une direction.
	 * @param numLigOuCol Un numéro correspondant à un numéro de ligne ou de colonne en fonction de la direction donnée en paramètre.
	 * @return Un tableau contenant les éléments positionnables se trouvant sur la ligne ou colonne spécifiée.
	 */
	static public ArrayList<Positionnable> extraireSurLigOuCol(final ArrayList<Positionnable> positionnables, final Direction direction,final int numLigOuCol) {
		if ((direction==Direction.HAUT)||(direction==Direction.BAS))
			return extraireSurColonne(positionnables,numLigOuCol);
		return extraireSurLigne(positionnables,numLigOuCol);
	}
	
	/**
	 * Méthode permettant d'extraire d'un tableau d'éléments un élément à une certaine position.
	 * Le premier élément trouvé se trouvant à la ligne et à la colonne données comme paramètres est retourné.
	 * Si aucun élément n'est trouvé à la position demandé, null est retourné.
	 * @param positionnables Un tableau non null d'éléments positionnables pouvant avoir une position null.
	 * @param numLigne Un numéro de ligne.
	 * @param numColonne Un numéro de colonne.
	 * @return L'élément trouvé à la ligne et colonne données comme paramètres (null si aucun élément trouvé à cette position). 
	 */
	static public Positionnable elementAt(final ArrayList<Positionnable> positionnables, final int numLigne,final int numColonne) {
		for (Positionnable positionnable : positionnables)
			if ((positionnable.position!=null)&&(positionnable.position.getNumLigne()==numLigne)&&(positionnable.position.getNumColonne()==numColonne))
				return positionnable;
		return null;
	}
}
