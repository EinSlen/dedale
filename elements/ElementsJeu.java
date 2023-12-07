package dedale.elements;

import java.util.ArrayList;

/**
 * Une instance de cette classe permet de stocker l'ensemble des éléments du jeu lors de son déroulement.
  <ul>
 * <li>Les pièces du jeu.
 * <li>Les objets du jeu.
 * <li>Les joueurs du jeu.
 * </ul>
 * @author Damlencourt Valentin
 * @since 3.0 (24/02/23)
 * @version 3.0 (24/02/23)
 */
public class ElementsJeu {
	/**
	 * Les pièces du jeu.
	 */
	final private ArrayList<Piece> pieces;

	/**
	 * Les objets du jeu.
	 */
	final private ArrayList<Objet> objets;

	/**
	 * Les joueurs du jeu.
	 */
	final private ArrayList<Joueur> joueurs;

	/**
	 * Constructeur permettant de construire l'ensemble des éléments du jeu à partir de chacun d'eux.
	 * 
	 * @param pieces Toutes les pièces du jeu (celles positionnées sur le plateau et celle hors plateau).
	 * @param objets Tous les objets du jeu (aucun déjà récupéré).
	 * @param joueurs L'ensemble des joueurs (avec un et un seul à qui c'est le tour et tous positionnés).
	 */
	public ElementsJeu(ArrayList<Piece> pieces,ArrayList<Objet> objets,ArrayList<Joueur> joueurs) {
		this.pieces=pieces;
		this.objets=objets;
		this.joueurs=joueurs;
	}

	/**
	 * Constructeur permettant de générer (en partie aléatoirement) les pièces et les objets.
	 * Seul doit être donné en paramètre l'ensemble des joueurs. Les joueurs seront postionnés aléatoirement
	 * (les positions initiales des joueurs n'a donc pas d'importance).
	 * 
	 * @param joueurs L'ensemble des joueurs (avec un et un seul à qui c'est le tour).
	 */
	public ElementsJeu(ArrayList<Joueur> joueurs) {
		ArrayList<Piece> pieces=null;
        int nb;
        do {
        	nb=0;
        	pieces=Piece.nouvellesPieces();
        	for (Piece piece : pieces)
        		if (piece.getNbSorties()>0)
        			nb++;
        }while (nb<Joueur.NB_JOUEURS);
		this.pieces=pieces;
		objets=Objet.nouveauxObjets();
		Joueur.positionnerJoueurs(joueurs, pieces);
		this.joueurs=joueurs;
	}

	/**
	 * Constructeur permettant de générer (en partie aléatoirement) les pièces et les objets.
	 * Les joueurs sont également générés automatiquement et ce sont des joueurs humains.
	 */
	public ElementsJeu() {
		ArrayList<Piece> pieces=null;
        int nb;
        do {
        	nb=0;
        	pieces=Piece.nouvellesPieces();
        	for (Piece piece : pieces)
        		nb+=piece.getNbSorties();
        }while (nb<Joueur.NB_JOUEURS);
		this.pieces=pieces;
		objets=Objet.nouveauxObjets();
		final String[] noms= {"Cyclope 1","Cyclope 2","Cyclope 3"}; 
		joueurs=JoueurHumain.nouveauxJoueursHumains(noms, pieces);
	}

	/**
	 * Méthode retournant les pièces du jeu.
	 * @return Les pièces du jeu.
	 */
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	/**
	 * Méthode retournant les objets du jeu.
	 * @return the objets
	 */
	public ArrayList<Objet> getObjets() {
		return objets;
	}

	/**
	 * Méthode retournant les joueurs du jeu.
	 * @return the joueurs
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Méthode effectuant l'insertion de la pièce hors plateau sur une ligne ou d'une colonne du plateau selon une direction.
	 * Les pièces, les objets et les joueurs sont décalés selon les règles du jeu.
	 * La pièce sortie devient la nouvelle pièce hors plateau (position null).
	 * @param direction La direction du décalage.
	 * @param numLigOuCol Le numéro de la ligne ou de la colonne dans laquelle se fait l'insertion de la pièce hors plateau.
	 */
	public void effectuerInsertionPieceHorsPlateau(final Direction direction,final int numLigOuCol) {
		@SuppressWarnings("unchecked")
		final ArrayList<Positionnable> objetsSel=Positionnable.extraireSurLigOuCol((ArrayList<Positionnable>)(ArrayList<?>)objets,direction,numLigOuCol);
		Positionnable.decalerPositions(objetsSel, direction);
		@SuppressWarnings("unchecked")
		final ArrayList<Positionnable> piecesSel=Positionnable.extraireSurLigOuCol((ArrayList<Positionnable>)(ArrayList<?>)pieces,direction,numLigOuCol);
		Positionnable.decalerPositions(piecesSel, direction);
		@SuppressWarnings("unchecked")
		final ArrayList<Positionnable> joueursSel=Positionnable.extraireSurLigOuCol((ArrayList<Positionnable>)(ArrayList<?>)joueurs,direction,numLigOuCol);
		Positionnable.decalerPositions(joueursSel, direction);
		final Piece pieceHorsPlateau=Piece.pieceHorsPlateau(pieces);
		Piece nouvellePieceHorsPlateau=null;
		switch (direction) {
		case HAUT : 
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(piecesSel,0, numLigOuCol);
			break;
		case BAS : 
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(piecesSel,PositionPlateau.NB_LIGS_PLATEAU-1, numLigOuCol);
			break;
		case DROITE : 
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(piecesSel,numLigOuCol,0);
			break;
		case GAUCHE :
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(piecesSel,numLigOuCol,PositionPlateau.NB_COLS_PLATEAU-1);
			break;
		}
		pieceHorsPlateau.setPosition(nouvellePieceHorsPlateau.getPosition());
		nouvellePieceHorsPlateau.setPosition(null);
	}
	
	/**
	 * Methode retournant une copie des éléments du jeu.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		final ArrayList<Piece> pieces=new ArrayList<Piece>();
		final ArrayList<Objet> objets=new ArrayList<Objet>();
		final ArrayList<Joueur> joueurs=new ArrayList<Joueur>();
		for (Piece piece : this.pieces)
			pieces.add((Piece)piece.clone());
		for (Objet objet : this.objets)
			objets.add((Objet)objet.clone());
		for (Joueur joueur : this.joueurs)
			joueurs.add((Joueur)joueur.clone());
		return new ElementsJeu(pieces,objets,joueurs);
	}
	
	
}
