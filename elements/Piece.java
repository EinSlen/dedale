package dedale.elements;

import java.util.ArrayList;

/**
 * Une instance de cette classe permet de représenter une pièce unitaire du labyrinthe.
 * L'agencement des différentes pièces sur le plateau permet de construire des chemins.
 * Chaque pièce peut posséder 0, 1, 2, 3 ou 4 sorties (vues de l'extèrieur ces sorties sont des entrées possibles dans le pièce).
 * Une pièce est orientée, lorsqu'on la visualise face à nous, elle possède (éventuellement) une sortie
 * vers le haut, vers la droite, vers le bas ou encore vers la gauche.
 * Une pièce peut être rotationnée dans le sens des aiguilles d'une montre.
 * Les emplacements des sorties changent après une rotation. Par exemple, si une pièce à une sortie vers le haut, après rotation, elle aura
 * une sortie vers la droite. 
 * D'autre part, une pièce peut être positionnée. Sa position peut être null lorsqu'elle est hors du plateau.
 * @author Jean-François Condotta
 * @since 2.0 (22/02/23)
 * @version 2.0 (22/02/23)
 */
public class Piece extends Positionnable {
	/**
	 * Une nombre entier pour générer aléatoirement une sortie d'une pièce.
	 */
	static private int PROB_SORTIE=60;
	
	/**
	 * Un booléen indiquant s'il est possible de sortir de la pièce par le haut.
	 */
	private boolean sortieHaut;
	/**
	 * Un booléen indiquant s'il est possible de sortir de la pièce par la droite.
	 */
	private boolean sortieDroite;
	/**
	 * Un booléen indiquant s'il est possible de sortir de la pièce par le bas.
	 */
	private boolean sortieBas;
	/**
	 * Un booléen indiquant s'il est possible de sortir de la pièce par la gauche.
	 */
	private boolean sortieGauche;

	/**
	 * Un entier compris entre 0 et 4 indiquant le nombre de sorties de la pièce.
	 */
	final private int nbSorties;


	/**
	 * Créer une nouvelle pièce avec les sorties passées en paramètres.
	 * La position de la nouvelle pièce vaudra null.
	 * @param sortieHaut true pour indiquer une sortie en haut (false sinon).
	 * @param sortieDroite true pour indiquer une sortie à droite (false sinon).
	 * @param sortieBas true pour indiquer une sortie en bas (false sinon).
	 * @param sortieGauche true pour indiquer une sortie à gauche (false sinon).
	 */
	public Piece(final boolean sortieHaut,final boolean sortieDroite,boolean sortieBas,boolean sortieGauche) {
		super();
		this.sortieHaut=sortieHaut;
		this.sortieDroite=sortieDroite;
		this.sortieBas=sortieBas;
		this.sortieGauche=sortieGauche;
		nbSorties=(sortieHaut?1:0)+(sortieDroite?1:0)+(sortieBas?1:0)+(sortieGauche?1:0);
	}

	/**
	 * Créer une nouvelle pièce avec des sorties générées aléatoirement à partir d'une probabilité qu'une sortie soit présente
	 * ou non. Cette probabilité est donnée par un nombre entier compris entre 49 et 99 (49 pour une probabilité de 0.5 et 
	 * 99 pour une probabilité de 1.0). Si la probabilité donnée n'est pas valable, la valeur de {@link Piece#PROB_SORTIE} sera utilisée.
	 * La position de la nouvelle pièce vaudra null.
	 * @param probSortie Un entier indiquant une certaine probabilité pour la génération d'une sortie (ramenée sur 99).
	 */
	public Piece(int probSortie) {
		if ((probSortie<49)||(probSortie>99))
			probSortie=PROB_SORTIE;
		this.sortieHaut=Util.genererAleatoirementEntier(99)<=probSortie;
		this.sortieDroite=Util.genererAleatoirementEntier(99)<=probSortie;
		this.sortieBas=Util.genererAleatoirementEntier(99)<=probSortie;
		this.sortieGauche=Util.genererAleatoirementEntier(99)<=probSortie;
		nbSorties=(sortieHaut?1:0)+(sortieDroite?1:0)+(sortieBas?1:0)+(sortieGauche?1:0);
	}

	/**
	 * Créer une nouvelle pièce avec des sorties générées aléatoirement à partir d'une probabilité de {@link Piece#PROB_SORTIE} (voir autre constructeur).
	 * La position de la nouvelle pièce vaudra null.
	 */
	public Piece() {
		this(PROB_SORTIE);
	}
	
	/**
	 * Méthode permettant de changer la valeur de {@link Piece#PROB_SORTIE} correspondant à la probabilité qu'une sortie soit générée ou non.
	 * Le nombre entier donné en paramètre doit être compris entre 49 et 99 (49 pour une probabilité de 0.5 et 
	 * 99 pour une probabilité de 1.0). Si la probabilité donnée n'est pas valable, aucun changement est effectué.
	 * @param probSortie Un nombre entier compris (au sens large) entre 49 et 99.
	 */
	static public void changerProbSortie(final int probSortie) {
		if ((probSortie>=49)&&(probSortie<=99))
			PROB_SORTIE=probSortie;
	}
	/**
	 * Cette méthode indique s'il existe une sortie dans la direction donnée en paramètre.
	 * @param direction La direction pour laquelle on souhaite savoir s'il y a une sortie.
	 * @return true si et seulement s'il existe une sortie dans la direction donnée en paramètre.
	 */
	public boolean getSortie(final Direction direction) {
		switch(direction) {
		case HAUT: return sortieHaut;
		case DROITE: return sortieDroite;
		case BAS: return sortieBas;
		case GAUCHE: return sortieGauche;
		}
		return false;
	}

	/**
	 * Méthode indiquant si la pièce est hors plateau, c'est-à-dire si sa position le nombre de sorties de la pièce.
	 * @return the nbSorties Le nombre de sorties de la pièce.
	 */
	public int getNbSorties() {
		return nbSorties;
	}

	/**
	 * Méthode permettant d'effectuer une rotation de la pièce dans le sens des aiguilles d'une montre.
	 * Cette méthode modifie en conséquence les sorties de la pièce.
	 */
	public void rotationner() {
		final boolean ancienneSortieHaut=this.sortieHaut;
		sortieHaut=sortieGauche;
		sortieGauche=sortieBas;
		sortieBas=sortieDroite;
		sortieDroite=ancienneSortieHaut;
	}

	/**
	 * Méthode permettant d'effectuer un certain nombre de rotations de la pièce dans le sens des aiguilles d'une montre.
	 * @param nbRotations Le nombre de rotations à effectuer.
	 */
	public void rotationner(final int nbRotations) {
		for (int i=0;i<nbRotations;i++)
			rotationner();
	}

	/**
	 * Méthode testant si une autre pièce est directement accessible (l'autre pièce doit être adjacente et 
	 * les sorties doivent concorder) à partir de la pièce courante.
	 * @param autrePiece Une pièce (possiblement null).
	 * @return true s'il est possible d'accéder directement à la pièce passée en paramètre.
	 */
	public boolean estDirectementAccessible(final Piece autrePiece) {
		if (autrePiece==null)
			return false;
		final PositionPlateau position=getPosition();
		final PositionPlateau autrePosition=autrePiece.getPosition();
		if (! PositionPlateau.positionsAdjacentes(position, autrePosition))
			return false;
		if ((position.getNumLigne()==autrePosition.getNumLigne()+1)&&sortieBas&&autrePiece.sortieHaut)
			return true;
		if ((position.getNumLigne()==autrePosition.getNumLigne()-1)&&sortieHaut&&autrePiece.sortieBas)
			return true;
		if ((position.getNumColonne()==autrePosition.getNumColonne()+1)&&sortieGauche&&autrePiece.sortieDroite)
			return true;
		return (position.getNumColonne()==autrePosition.getNumColonne()-1)&&sortieDroite&&autrePiece.sortieGauche;
	}
	
	/**
	 * Méthode permettant d'extraire d'un tableau de pièces toutes les pièces directement accessibles.
	 * Si aucune pièce du tableau est directement accessible, un tableau ne contenant aucune pièce sera retourné.
	 * @param pieces Un tableau non null de pièces (possiblement null). Les différentes pièces non null du tableau ont des positions différentes.
	 * @return Un nouveau tableau de pièces non null contenant les pièces directement accessibles à partir la pièce courante. 
	 */
	public ArrayList<Piece> piecesDirectementAccessibles(final ArrayList<Piece> pieces) {
		final ArrayList<Piece> res=new ArrayList<Piece>();
		for (Piece piece : pieces)
			if (estDirectementAccessible(piece))
				res.add(piece);
		return res;
	}
	
	/**
	 * Méthode retournant une nouvelle pièce correspondant à la pièce.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		final Piece piece=new Piece(sortieHaut,sortieDroite,sortieBas,sortieGauche);
		if (getPosition()!=null)
			piece.setPosition((PositionPlateau)getPosition().clone());
		return piece;
	}

	/**
	 * Méthode générant un ensemble de ({@link PositionPlateau#NB_LIGS_PLATEAU}x{@link PositionPlateau#NB_COLS_PLATEAU})+1 pièces.
	 * L'ensemble généré contient une seule pièce avec une position null et une et une seule pièce 
	 * pour chaque position possible sur le plateau. Les sorties de chaque pièce sont générées aléatoirement.
	 * 
	 * @return Un tableau contenant l'ensemble des pièces générées.
	 */
	static public ArrayList<Piece> nouvellesPieces(){
		final ArrayList<Piece> pieces=new ArrayList<Piece>();
		pieces.add(new Piece());
		Piece piece;
		for (int i=0;i<PositionPlateau.NB_LIGS_PLATEAU;i++)
			for (int j=0;j<PositionPlateau.NB_COLS_PLATEAU;j++) {
				piece=new Piece();
				piece.setPosition(new PositionPlateau(i,j));
				pieces.add(piece);
			}
		return pieces;
	}
	
	/**
	 * Méthode permettant d'extraire d'un tableau de pièces une pièce hors plateau.
	 * Si une telle pièce n'existe pas, null est retourné.
	 * La première pièce hors du plateau trouvée est retounée.
	 * @param pieces Un tableau non null de pièces (possiblement null).
	 * @return Une pièce du tableau hors du plateau ou null dans le cas où il n'y a pas une telle pièce.
	 */
	static public Piece pieceHorsPlateau(final ArrayList<Piece> pieces) {
		for (Piece piece : pieces)
			if ((piece!=null)&&(! piece.estSurPlateau()))
				return piece;
		return null;
	}
	
	/**
	 * Méthode permettant de calculer un chemin de pièces entre deux positions à partir d'un ensemble de pièces.
	 * S'il n'existe pas un tel chemin du fait des pièces données en paramètre, null est retourné.
	 * Dans le cas contraire un tableau non null de pièces du tableau donné en paramètre est retourné.
	 * Le chemin à suivre correspond aux pièces de ce tableau à suivre dans l'ordre.
	 * La première pièce du tableau retourné est à la position initiale et la dernière à la position finale.
	 * @param positionInitiale La position initiale.
	 * @param positionFinale La position finale.
	 * @param pieces L'ensemble des pièces à partir duquel le chemin peut être construit. Il ne doit pas y avoir deux pièces à une même position non null dans l'ensemble.
	 * @return Un chemin de pièces entre la position initiale et la position finale ou si un chemin n'existe pas.
	 */
	static public ArrayList<Piece> calculerChemin(final PositionPlateau positionInitiale, final PositionPlateau positionFinale,final ArrayList<Piece> pieces) {
		@SuppressWarnings("unchecked")
		final Piece pieceFinale=(Piece)Positionnable.elementAt((ArrayList<Positionnable>)((ArrayList<?>)pieces), positionFinale.getNumLigne(),positionFinale.getNumColonne());		
		if (pieceFinale==null)
			return null;
		final int[][] longueurs=new int[PositionPlateau.NB_LIGS_PLATEAU][PositionPlateau.NB_COLS_PLATEAU];
		final Piece[][] piecesAccessibles=new Piece[PositionPlateau.NB_LIGS_PLATEAU][PositionPlateau.NB_COLS_PLATEAU];
		final PositionPlateau[][] positionsPrecedentes=new PositionPlateau[PositionPlateau.NB_LIGS_PLATEAU][PositionPlateau.NB_COLS_PLATEAU];
		for (int i=0;i<longueurs.length;i++)
			for (int j=0;j<longueurs[0].length;j++) {
				longueurs[i][j]=-1;
				piecesAccessibles[i][j]=null;
				positionsPrecedentes[i][j]=null;
			}
		PositionPlateau position=pieceFinale.getPosition();
		longueurs[position.getNumLigne()][position.getNumColonne()]=0;
		piecesAccessibles[position.getNumLigne()][position.getNumColonne()]=pieceFinale;
		int num=0;
		boolean changement=true;
		while (changement) {
			changement=false;
			for (int i=0;i<longueurs.length;i++)
				for (int j=0;j<longueurs[0].length;j++) 
					if (longueurs[i][j]==num) {
						Piece piece=piecesAccessibles[i][j];
						ArrayList<Piece> accessibles=piece.piecesDirectementAccessibles(pieces);
						for (Piece p : accessibles) {
							position=p.getPosition();
							int lig=position.getNumLigne();
							int col=position.getNumColonne();
							if (longueurs[lig][col]==-1) {
								changement=true;
								positionsPrecedentes[lig][col]=piece.getPosition();
								piecesAccessibles[lig][col]=p;
								longueurs[lig][col]=num+1;
							}
						}
				}
		num++;
		}
		num=longueurs[positionInitiale.getNumLigne()][positionInitiale.getNumColonne()];
		if (num==-1)
			return null;
		ArrayList<Piece> chemin=new ArrayList<Piece>();
		Piece piece;
		position=positionInitiale;
		do {
			piece=piecesAccessibles[position.getNumLigne()][position.getNumColonne()];
			chemin.add(piece);
			position=positionsPrecedentes[position.getNumLigne()][position.getNumColonne()];
		}while (position!=null);
		return chemin;
	}
}
