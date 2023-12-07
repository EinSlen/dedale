package dedale.elements;

import java.util.ArrayList;
import java.util.Objects;

/**
 * La classe Joueur est une classe abstraite permettant de représenter des joueurs (humains ou ordinateurs).
 * Chaque joueur possède un identifiant : l'entier 0, 1 ou 2. Cette identifiant détermine la position à laquelle doit jouer le joueur : 0 pour le premier joueur qui doit jouer,
 * 1 pour le deuxième joueur qui doit jouer et 3 pour le dernier joueur qui doit jouer.
 * L'identifiant du joueur détermine également quels sont les objets qu'il doit ramasser (voir la classe ({@link dedale.elements.Objet}).
 * <br>
 * Chaque joueur possède également un pseudo qui doit avoir au moins 3 caractères et au plus 10 caractères.
 * <br>
 * Chaque joueur a une position sur le plateau (toujours non null).
 * Deux joueurs ne peuvent jamais avoir la même position.
 * <br>
 * Lors d'un tour, un joueur joue lorsque c'est à son tour. Pour un joueur donné, un booléen permet d'indiquer si c'est à son tour de jouer.
 * 
 * @author Damlencourt Valentin
 * @since 3.0 (22/02/23)
 * @version 3.0 (22/02/23)
 */
public abstract class Joueur extends Positionnable {
	/**
	 * Le nombre de joueurs du jeu.
	 */
	public static final int NB_JOUEURS=3;
	/**
	 * Le pseudo du joueur.
	 */
	final String pseudo;
	/**
	 * L'identifiant du joueur.
	 */
	final int id;
	/**
	 * Un booléen permettant d'iniquer si c'est au tour du joueur de jouer ou non.
	 */
	private boolean estASonTourDeJouer;

	/**
	 * Constructeur permettant de créer un nouveau joueur avec un identifiant, un pseudo, une position, un booléen pour indiquer si
	 * c'est son tour de jouer et sa nature.
	 * Dans le cas où l'identifiant ou le pseudo du joueur ne sont pas valides ou dans le cas où la position vaut null ou n'est pas valide,
	 * une Exception de type IllegalArgumentExcpetion est levée.
	 * @param id L'identifiant du joueur.
	 * @param pseudo Le pseudo du joueur.
	 * @param position La position du joueur.
	 * @param estASonTourDeJouer Un booléen indiquant si c'est à son tour.
	 */
	protected Joueur(final int id,final String pseudo,final PositionPlateau position,final boolean estASonTourDeJouer) {
		super(position);
		if ((id<0)||(id>=NB_JOUEURS))
			throw new IllegalArgumentException("L'identifiant du joueur n'est pas valide !");
		if ((pseudo.length()<3)||(pseudo.length()>10))
			throw new IllegalArgumentException("Le pseudo d'un joueur doit avoir au moins 3 caractères et au plus 8 caractères !");

		this.id=id;
		this.pseudo=pseudo;
		this.estASonTourDeJouer=estASonTourDeJouer;
	}
	/**
	 * Méthode retournant l'identifiant du joueur.
	 * @return L'identifiant du joueur.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Méthode retournant le pseudo du joueur.
	 * @return Le pseudo du joueur.
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Méthode permettant de changer la position du joueur.
	 * @param numLigne Le numéro de la nouvelle ligne.
	 * @param numColonne Le numéro de la nouvelle colonne.
	 */
	public void setPosition(final int numLigne,final int numColonne) {
		getPosition().setNumColonne(numColonne);
		getPosition().setNumLigne(numLigne);
	}

	/**
	 * Change la position du joueur. Si la nouvelle position vaut null, la position du joueur n'est pas changée.
	 * @param position La nouvelle position du joueur.
	 */
	public void setPosition(PositionPlateau position) {
		if (position!=null)
			super.setPosition(position);
	}
	/**
	 * Méthode indiquant si c'est au tour du joueur de jouer ou non.
	 * @return the estASonTourDeJouer
	 */
	public boolean estASonTourDeJouer() {
		return estASonTourDeJouer;
	}

	/**
	 * Méthode mettant le joueur comme le joueur à qui c'est son tour de jouer ou non. 
	 * @param estASonTourDeJouer true pour mettre que c'est à son tour, false pour mettre que ce n'est pas/plus à son tour.
	 */
	public void setEstASonTourDeJouer(boolean estASonTourDeJouer) {
		this.estASonTourDeJouer = estASonTourDeJouer;
	}

	/**
	 * Méthode retournant le prochain objet par le joueur parmi un ensemble d'objets.
	 * Si il n'existe pas un tel objet, null est retourné.
	 * @param objets Un tableau non null d'objets non null avec des identifiants différents.
	 * @return Le prochain objet à récupérer.
	 */
	public Objet prochainObjetARecuperer(final ArrayList<Objet> objets) {
		return Objet.objetNonRecupere(objets,id*(Objet.NB_OBJETS/3),(id+1)*(Objet.NB_OBJETS/3)-1);
	}
	/**
	 * Retourne une représentation textuelle du joueur.
	 */
	@Override
	public String toString() {
		return "Joueur [pseudo=" + pseudo + ", id=" + id + ", estASonTourDeJouer=" + estASonTourDeJouer + "]";
	}
	/**
	 * Méthode redéfinissant l'égalité de deux joueurs en comparant l'égalité de leur identifiant.
	 */
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Joueur))
			return false;
		return id == ((Joueur)obj).id;
	}

	/**
	 * Méthode retournant la clé de hachage d'un joueur.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}	


	/**
	 * Méthode permettant d'extraire d'un tableau de joueurs un joueur ayant un certain identifiant.
	 * Le premier joueur trouvé ayant l'identifiant donné en paramètre est retourné.
	 * La valeur null est retourné dans le cas où aucun joueur avec l'identifiant demandé est trouvé.
	 * @param joueurs Un tableau non null de joueurs (possiblement null).
	 * @param id L'identifiant du joueur cherché.
	 * @return Un joueur du tableau avec l'identifiant id ou null dans le cas où il n'y a pas un tel joueur.
	 */
	static public Joueur joueurAvecId(final ArrayList<Joueur> joueurs, final int id) {
		for (Joueur joueur : joueurs)
			if ((joueur!=null)&&(joueur.id==id))
				return joueur;
		return null;
	}

	/**
	 * Méthode permettant d'extraire d'un tableau de joueurs un joueur à qui c'est la tour de jouer.
	 * Le premier joueur trouvé à qui c'est le tour de jouer est retourné.
	 * La valeur null est retourné dans le cas où aucun joueur à qui c'est le tour de jouer est trouvé.
	 * @param joueurs Un tableau non null de joueurs (possiblement null).
	 * @return Un joueur du tableau à qui c'est le tour de jouer ou null dans le cas où il n'y a pas un tel joueur.
	 */
	static public Joueur joueurQuiDoitJouer(final ArrayList<Joueur> joueurs) {
		for (Joueur joueur : joueurs)
			if ((joueur!=null)&&(joueur.estASonTourDeJouer))
				return joueur;
		return null;
	}
	/**
	 * Afin d'obliger les sous-classes concrètes à la définir.
	 */
	@Override
	abstract protected Object clone() throws CloneNotSupportedException;

	/**
	 * Méthode permettant de positionner aléatoirement des joueurs sur des pièces.
	 * Chaque joueur est placé aléatoirement à une position de une des pièces (données en paramètre).
	 * La position choisie correspondra toujours à la position d'une pièce contenant au moins une sortie.
	 * D'autre part, deux joueurs sont toujours placés à deux positions différentes.
	 * L'appelant doit s'assurer que les pièces passées en paramètre permettent un tel placement des joueurs.
	 * Le joueur identifié par id se trouve à l'index id du tableau retourné.
	 * @param joueurs Les joueurs à positionner.
	 * @param pieces L'ensemble des pièces à partir desquelles sont extraites les positions utilisées pour le placement des joueurs. Un pièce peut avoir une position null.
	 */
	public static void positionnerJoueurs(final ArrayList<Joueur> joueurs,final ArrayList<Piece> pieces) {
		final int max=PositionPlateau.NB_LIGS_PLATEAU*PositionPlateau.NB_COLS_PLATEAU;
		final boolean[] possible=new boolean[max];
		for (int i=0;i<max;i++)
			possible[i]=true;
		for (Piece piece : pieces)
			if ((piece.getPosition()!=null)&&(piece.getNbSorties())==0)
				possible[piece.getPosition().getNumLigne()*PositionPlateau.NB_COLS_PLATEAU+piece.getPosition().getNumColonne()]=false;
		for (Joueur joueur:joueurs) {
			int pos=Util.genererAleatoirementEntier(max-1);
			for (int j=0;j<max;j++) {
				if (possible[pos]) {
					possible[pos]=false;
					joueur.setPosition(new PositionPlateau(pos/PositionPlateau.NB_COLS_PLATEAU,pos%PositionPlateau.NB_COLS_PLATEAU));
					break;
				}else
					pos=(pos+1)%max;
			}
		}
	}

}
