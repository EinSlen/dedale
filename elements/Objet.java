package dedale.elements;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Une instance de la classe Objet permet de représenter un objet à récupérer dans le jeu.
 * Un jeu concerne {@link #NB_OBJETS} objets (forcément un multiple de 3). Chaque objet possède un identifiant compris au sens large entre
 * 0 et {@link #NB_OBJETS}-1.
 * <br>
 * <ul>
 * <li>Les objets ayant un identifiant entre 0 et ({@link #NB_OBJETS}/3)-1 devront être récupérés par le premier joueur (le joueur identifié par 0),</li>
 * <li>les objets ayant un identifiant entre {@link #NB_OBJETS}/3 et ({@link #NB_OBJETS}*2/3)-1  devront être récupérés par le second joueur (le joueur identifié par 1) et</li>
 * <li>les objets ayant un identifiant entre {@link #NB_OBJETS}*2/3 et {@link #NB_OBJETS}-1 devront être récupérés par le troisième joueur (joueur identifié par 2).</li>
 * </ul>
 * Un objet est un élément positionnable sur le plateau.
 * Initialement, chaque objet a une position sur le plateau (c'est-à-dire une position non null). Une fois l'objet récupéré, sa position vaut null. Deux objets ne peuvent jamais être
 * au même endroit sur le plateau (c'est-à-dire jamais avoir des positions non null égales).
 * @author Damlencourt Valentin
 * @since 1.0 (22/02/23)
 * @version 1.0 (22/02/23)
 */
public class Objet extends Positionnable {
	/**
	 * Le nombre d'objets du jeu, la valeur de cette constante est {@value}.
	 */
	public static final int NB_OBJETS=36;

	/**
	 * L'identifiant de l'objet.
	 */
	final private int id;

	/**
	 * Constructeur permettant de créer un nouvel objet avec un identifiant et une position.
	 * Dans le cas où l'identifiant de l'objet n'est pas valide ou la position vaut null, une Exception de type IllegalArgumentExcpetion est levée.
	 * @param id L'identifiant de l'objet.
	 * @param position La position initial de l'objet sur le plateau.
	 */
	public Objet(final int id,final PositionPlateau position) {
		super(position);
		if ((id<0)||(id>=NB_OBJETS))
			throw new IllegalArgumentException("L'identifiant de l'objet n'est pas valide !");
		this.id=id;
	}

	/**
	 * Méthode permettant de mettre l'objet comme récupéré (sa position est simplement mise à null).
	 */
	public void setRecupere() {
		setPosition(null);
	}
	/**
	 * Méthode indiquant si l'objet a été récupéré ou non (c'est-à-dire si la position de l'objet est null ou non).
	 * @return true si et seulement si l'objet a été récupéré.
	 */
	public boolean estRecupere() {
		return ! super.estSurPlateau();
	}

	/**
	 * Méthode retournant l'identifiant de l'objet.
	 * @return L'identifiant de l'objet.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Méthode retournant l'identifiant du joueur propriétaire de l'objet (celui qui doit récupéré l'objet).
	 * @return L'identifiant du joueur propriétaire de l'objet (l'entier 0, 1 ou 2).
	 */
	public int getIdJoueur() {
		return id/(NB_OBJETS/3);
	}
	/**
	 * Méthode retournant true si et seulement si deux objets ont le même identifiant (ils sont considérés égaux).
	 */
	@Override
	public boolean equals(final Object obj) {
		if (! (obj instanceof Objet))
			return false;
		return id == ((Objet)obj).id;
	}


	/**
	 * Méthode retournant la représentation textuelle d'un objet.
	 */
	@Override
	public String toString() {
		return "Objet [id=" + id + ", position=" + getPosition() + ", idJoueur=" + getIdJoueur() + "]";
	}

	/**
	 * Méthode retournant le code de hachage d'un objet.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Méthode permettant d'extraire d'un tableau d'objets un objet ayant un certain identifiant.
	 * Le premier objet trouvé ayant l'identifiant donné en paramètre est retourné.
	 * La valeur null est retourné dans le cas où aucun objet avec l'identifiant demandé est trouvé.
	 * @param objets Un tableau non null d'objets (possiblement null).
	 * @param id L'identifiant de l'objet cherché.
	 * @return Un objet du tableau avec l'identifiant id ou null dans le cas où il n'y a pas un tel objet.
	 */
	static public Objet objetAvecId(final ArrayList<Objet> objets, final int id) {
		for (Objet objet : objets)
			if ((objet!=null)&&(objet.id==id))
				return objet;
		return null;
	}

	/**
	 * Méthode permettant d'extraire d'un tableau d'objets un objet non récupéré ayant le plus petit identifiant compris 
	 * (au sens large) entre une valeur minimale et une valeur maximale.
	 * @param objets Un tableau non null d'objets non null avec des identifiants différents.
	 * @param idMin La valeur minimale de l'identifiant de l'objet cherché.
	 * @param idMax La valeur maximale de l'identifiant de l'objet cherché.
	 * @return Un objet non récupéré du tableau objets ayant le plus petit identifiant compris (au sens large) entre idMin et idMax ou
	 * null si un tel objet n'existe pas.
	 */
	static public Objet objetNonRecupere(final ArrayList<Objet> objets, final int idMin,final int idMax) {
		Objet res=null;
		for (Objet objet : objets)
			if ((! objet.estRecupere())&&(objet.id>=idMin)&&(objet.id<=idMax)) {
				if (res==null)
					res=objet;
				else
					if (res.id>objet.id)
						res=objet;
			}
		return res;
	}

	/**
	 * Méthode retournant un nouveau tableau de {@link dedale.elements.Objet#NB_OBJETS} nouveaux objets avec des identifiants compris (au sens large) entre 0 et {@link dedale.elements.Objet#NB_OBJETS}-1.
	 * Deux objets sont placés à deux positions différentes.
	 * L'objet identifié par id se trouve à l'index id du tableau retourné.
	 * @return Le tableau d'objets généré.
	 */
	public static ArrayList<Objet> nouveauxObjets() {
		final int max=PositionPlateau.NB_LIGS_PLATEAU*PositionPlateau.NB_COLS_PLATEAU;
		final ArrayList<Objet> objets=new ArrayList<Objet>(NB_OBJETS);
		final boolean[] possible=new boolean[max];
		for (int i=0;i<max;i++)
			possible[i]=true;
		int pos;
		for (int i=0;i<NB_OBJETS;i++) {
			pos=Util.genererAleatoirementEntier(max-1);
			for (int j=0;j<max;j++) {
				if (possible[pos]) {
					possible[pos]=false;
					objets.add(new Objet(i,new PositionPlateau(pos/PositionPlateau.NB_COLS_PLATEAU,pos%PositionPlateau.NB_COLS_PLATEAU)));
					break;
				}else
					pos=(pos+1)%max;
			}
		}
		return objets;
	}

	/**
	 * Méthode retournant une copie de l'objet.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Objet(id,(PositionPlateau)getPosition().clone());
	}


}
