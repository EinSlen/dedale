package dedale.elements;

import java.util.ArrayList;

/**
 * Une instance de cette classe représente un joueur humain.
 * @author Jean-François Condotta
 * @since 3.0 (22/02/23)
 * @version 3.0 (22/02/23)
 */
public class JoueurHumain extends Joueur {
	/**
	 * Constructeur permettant de créer un nouveau joueur humain avec un identifiant, un pseudo, une position et un booléen pour indiquer si
	 * c'est son tour de jouer.
	 * Dans le cas où l'identifiant ou le pseudo du joueur ne sont pas valides ou dans le cas où la position vaut null,
	 * une Exception de type IllegalArgumentExcpetion est levée.
	 * Si l'identifiant du joueur est 0 alors le booléen indiquant que c'est son tour est mis à true, dans le cas contraire il est mis à false.
	 * @param id L'identifiant du joueur.
	 * @param pseudo Le pseudo du joueur.
	 * @param position La position du joueur.
	 * @param estASonTourDeJouer Un booléen indiquant si c'est à son tour.
	 */
	public JoueurHumain(final int id,final String pseudo,final PositionPlateau position,final boolean estASonTourDeJouer) {
		super(id,pseudo,position,estASonTourDeJouer);
	}

	/**
	 * Méthode retournant un nouveau tableau de {@link dedale.elements.Joueur#NB_JOUEURS} nouveaux joueurs humains avec des identifiants différents (de 0 à ({@link dedale.elements.Joueur#NB_JOUEURS})-1).
	 * Seul le joueur identifié par 0 aura son booléen indiquant que c'est son tour à jouer à true.
	 * Les pseudos des différents joueurs sont donnés par un tableau donné en paramètre.
	 * Chaque joueur est placé aléatoirement à une position de une des pièces (données en paramètre).
	 * La position choisie correspondra toujours à la position d'une pièce contenant au moins une sortie.
	 * D'autre part, deux joueurs sont toujours placés à deux positions différentes.
	 * L'appelant doit s'assurer que les pièces passées en paramètre permettent un tel placement des joueurs.
	 * Le joueur identifié par id se trouve à l'index id du tableau retourné.
	 * @param pseudos Un tableau contenant les pseudos des joueurs.
	 * @param pieces L'ensemble des pièces à partir desquelles sont extraites les positions utilisées pour le placement des joueurs.
	 * @return Un nouveau tableau de joueurs.
	 */
	public static ArrayList<Joueur> nouveauxJoueursHumains(final String[] pseudos,final ArrayList<Piece> pieces) {
		final ArrayList<Joueur> joueurs=new ArrayList<Joueur>(NB_JOUEURS);
		for (int i=0;i<NB_JOUEURS;i++)
			joueurs.add(new JoueurHumain(i,pseudos[i],new PositionPlateau(0,0),i==0));
		Joueur.positionnerJoueurs(joueurs, pieces);
		return joueurs;
	}

	/**
	 * Méthode retournant une copie du joueur.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new JoueurHumain(id,pseudo,(PositionPlateau)getPosition().clone(),this.estASonTourDeJouer());
	}


}
