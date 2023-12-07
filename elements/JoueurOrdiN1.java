package dedale.elements;

/**
 * Cette classe correspond aux joueurs ordinateurs de niveau 1.
 * Ils calculent les coups complètement aléatoirement.
 * @author Damlencourt Valentin
 * @since 3.0 (24/02/23)
 * @version 3.0 (24/02/23)
 */
public class JoueurOrdiN1 extends JoueurOrdi {
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
	public JoueurOrdiN1(final int id,final String pseudo,final PositionPlateau position,final boolean estASonTourDeJouer) {
		super(id,pseudo,position,estASonTourDeJouer);
	}

	/**
	 * Méthode qui calcule un coup complètement aléatoirement.
	 * @param elementsJeu Les éléments du jeu.
	 * @return Le coup calculé que souhaite jouer le joueur Ordinateur.
	 */
	public CoupJeu calculerCoup(ElementsJeu elementsJeu) {
		final int nbRotations=Util.genererAleatoirementEntier(3);
		final int direction=Util.genererAleatoirementEntier(3);
		Direction directionDecalage=null;
		int numLigOuColDec=-1;
		switch (direction){
		case 0:
			directionDecalage=Direction.HAUT;
			numLigOuColDec=Util.genererAleatoirementEntier(PositionPlateau.NB_COLS_PLATEAU-1);
			break;
		case 1:
			directionDecalage=Direction.BAS;
			numLigOuColDec=Util.genererAleatoirementEntier(PositionPlateau.NB_COLS_PLATEAU-1);
			break;
		case 2:
			directionDecalage=Direction.GAUCHE;
			numLigOuColDec=Util.genererAleatoirementEntier(PositionPlateau.NB_LIGS_PLATEAU-1);
			break;
		case 3: 
			directionDecalage=Direction.DROITE;
			numLigOuColDec=Util.genererAleatoirementEntier(PositionPlateau.NB_LIGS_PLATEAU-1);
			break;
		}
		final int numLigne=Util.genererAleatoirementEntier(PositionPlateau.NB_LIGS_PLATEAU-1);
		final int numColonne=Util.genererAleatoirementEntier(PositionPlateau.NB_COLS_PLATEAU-1);
		return new CoupJeu(nbRotations,directionDecalage,numLigOuColDec,numLigne,numColonne);
	}

	/**
	 * Méthode retournant une copie du joueur.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new JoueurOrdiN1(id,pseudo,(PositionPlateau)getPosition().clone(),this.estASonTourDeJouer());
	}
}
