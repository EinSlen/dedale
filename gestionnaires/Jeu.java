package dedale.gestionnaires;

import java.util.ArrayList;

import dedale.elements.CoupJeu;
import dedale.elements.Direction;
import dedale.elements.ElementsJeu;
import dedale.elements.Joueur;
import dedale.elements.JoueurHumain;
import dedale.elements.JoueurOrdi;
import dedale.elements.JoueurOrdiN1;
import dedale.elements.JoueurOrdiN2;
import dedale.elements.Objet;
import dedale.elements.Piece;
import dedale.elements.PositionPlateau;
import dedale.elements.Positionnable;
/**
 * Cette classe correspond aux gestionnaires de jeu.
 * @author Jean-François Condotta
 * @since 4.0 (26/02/23)
 * @version 5.0 (26/02/23)
 */

public class Jeu implements GestionnaireJeu {
	/**
	 * Le temps entre deux déplacements du joueur sur le plateau.
	 */
	private static final int TEMPS_DEPLACEMENT_MS=100;
	/**
	 * Le delai pour lancement d'une simulation de coup.
	 */
	private static final int DELAI_SIMULATION_MS=500;
	/**
	 * Le gestionnaire de l'interface graphique du jeu.
	 */
	private GestionnaireIG ig;
	
	/**
	 * Le coup de jeu d'un ordinateur.
	 */
	private CoupJeu coupJeuOrdi;
	
	/**
	 * Méthode retournant le joueur qui doit jouer.
	 * @return Le joueur qui doit jouer.
	 */
	private Joueur joueurDevantJouer() {
		return Joueur.joueurQuiDoitJouer(elementsJeu.getJoueurs());
	}

	/**
	 * Méthode permettant de savoir si un joueur se trouve à une certaine position.
	 * @param position La position à tester.
	 * @return true si et seulement si un joueur se trouve à la position donnée en paramètre. 
	 */
	@SuppressWarnings("unchecked")
	private boolean joueurAt(final PositionPlateau position) {
		return Positionnable.elementAt((ArrayList<Positionnable>)(ArrayList<?>)elementsJeu.getJoueurs(),position.getNumLigne(),position.getNumColonne())!=null;
	}
	/**
	 * Les éléments du jeu.
	 */
	private ElementsJeu elementsJeu;
	@Override
	public void lancerJeu(final GestionnaireIG ig) {
		this.ig=ig;
		ig.lancerVueParametrage();
	}

	@Override
	public void gererParametres(final String[] pseudos,final String[] natures,final int probSortie) {
		Piece.changerProbSortie(probSortie);
		final ArrayList<Joueur> joueurs=new ArrayList<Joueur>();
		for (int i=0;i<Joueur.NB_JOUEURS;i++) {
			Joueur joueur;
			if ((natures[i]).equals("Humain"))
				joueur=new JoueurHumain(i,pseudos[i],new PositionPlateau(0,0),i==0);
			else if ((natures[i]).equals("Ordi N1"))
				joueur=new JoueurOrdiN1(i,pseudos[i],new PositionPlateau(0,0),i==0);
			else
				joueur=new JoueurOrdiN2(i,pseudos[i],new PositionPlateau(0,0),i==0);
			joueurs.add(joueur);
		}
		elementsJeu=new ElementsJeu(joueurs);
		ig.lancerVueJeu(elementsJeu);
		final Joueur joueurEnCours=joueurDevantJouer();
		if (joueurEnCours instanceof JoueurHumain)
			ig.attendreClicRotationOuDecalage(10);
		else {
			coupJeuOrdi=((JoueurOrdi)joueurEnCours).calculerCoup(elementsJeu);
			if (coupJeuOrdi.getNombreRotations()>0) {
				coupJeuOrdi.decNombreRotations();
				ig.realiserDemandeRotation(DELAI_SIMULATION_MS);
			} else {
				ig.realiserDemandeDecalage(coupJeuOrdi.getDirection(),coupJeuOrdi.getNumLigneOuColonneDec(), DELAI_SIMULATION_MS);
			}
		}
	}

	@Override
	public void gererDemandeRotation() {
		ig.stopperTouteAttenteClic();
		final Piece piece=Piece.pieceHorsPlateau(elementsJeu.getPieces());
		piece.rotationner();
		ig.mettreAJourAffichageJeu();
		final Joueur joueurEnCours=joueurDevantJouer();
		if (joueurEnCours instanceof JoueurHumain)
			ig.attendreClicRotationOuDecalage(10);
		else {
			if (coupJeuOrdi.getNombreRotations()>0) {
				coupJeuOrdi.decNombreRotations();
				ig.realiserDemandeRotation(DELAI_SIMULATION_MS);
			}else {
				ig.realiserDemandeDecalage(coupJeuOrdi.getDirection(),coupJeuOrdi.getNumLigneOuColonneDec(), DELAI_SIMULATION_MS);
			}
		}
	}

	@Override
	public void gererDemandeDecalage(final Direction direction,final int numLigOuCol) {
		ig.stopperTouteAttenteClic();
		elementsJeu.effectuerInsertionPieceHorsPlateau(direction, numLigOuCol);
		/*
		final ArrayList<Positionnable> objets=Positionnable.extraireSurLigOuCol((ArrayList<Positionnable>)(ArrayList<?>)elementsJeu.getObjets(),direction,numLigOuCol);
		Positionnable.decalerPositions(objets, direction);
		final ArrayList<Positionnable> pieces=Positionnable.extraireSurLigOuCol((ArrayList<Positionnable>)(ArrayList<?>)elementsJeu.getPieces(),direction,numLigOuCol);
		Positionnable.decalerPositions(pieces, direction);
		final ArrayList<Positionnable> joueurs=Positionnable.extraireSurLigOuCol((ArrayList<Positionnable>)(ArrayList<?>)elementsJeu.getJoueurs(),direction,numLigOuCol);
		Positionnable.decalerPositions(joueurs, direction);
		final Piece pieceHorsPlateau=Piece.pieceHorsPlateau(elementsJeu.getPieces());
		Piece nouvellePieceHorsPlateau=null;
		switch (direction) {
		case HAUT : 
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(pieces,0, numLigOuCol);
			break;
		case BAS : 
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(pieces,PositionPlateau.NB_LIGS_PLATEAU-1, numLigOuCol);
			break;
		case DROITE : 
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(pieces,numLigOuCol,0);
			break;
		case GAUCHE :
			nouvellePieceHorsPlateau=(Piece)Positionnable.elementAt(pieces,numLigOuCol,PositionPlateau.NB_COLS_PLATEAU-1);
			break;
		}
		pieceHorsPlateau.setPosition(nouvellePieceHorsPlateau.getPosition());
		nouvellePieceHorsPlateau.setPosition(null);
		*/
		ig.mettreAJourAffichageJeu();
		if (joueurDevantJouer() instanceof JoueurHumain)
			ig.attendreClicPositionPlateau(10);
		final Joueur joueurEnCours=joueurDevantJouer();
		if (joueurEnCours instanceof JoueurHumain)
			ig.attendreClicPositionPlateau(10);
		else 
			ig.realiserDemandeDeplacement(coupJeuOrdi.getNumLigneDep(), coupJeuOrdi.getNumColonneDep(),DELAI_SIMULATION_MS);
	}

	@Override
	public void gererDemandeDeplacement(final int numLig,final int numCol) {
		ig.stopperTouteAttenteClic();
		final Joueur joueurEnCours=joueurDevantJouer();
		final PositionPlateau positionActuelle=joueurEnCours.getPosition();
		final PositionPlateau positionDestination=new PositionPlateau(numLig,numCol);
		ArrayList<Piece> cheminPieces=Piece.calculerChemin(positionActuelle,positionDestination,elementsJeu.getPieces());
		if (cheminPieces==null) {
			positionDestination.setNumLigne(positionActuelle.getNumLigne());
			positionDestination.setNumColonne(positionActuelle.getNumColonne());
		}
		if (joueurAt(positionDestination))
			for (Piece piece : elementsJeu.getPieces())
				
				if ((piece.getPosition()!=null)&&(! piece.auMemeEndroit(joueurEnCours))) {
					if ((!joueurAt(piece.getPosition()))&&(Piece.calculerChemin(positionActuelle, piece.getPosition(), elementsJeu.getPieces())!=null)) {
						positionDestination.setNumLigne(piece.getPosition().getNumLigne());
						positionDestination.setNumColonne(piece.getPosition().getNumColonne());
						break;
					}
				}
		cheminPieces=Piece.calculerChemin(positionActuelle,positionDestination,elementsJeu.getPieces());
		final ArrayList<PositionPlateau> cheminPositions=new ArrayList<PositionPlateau>();
		for (Piece piece : cheminPieces)
			cheminPositions.add((PositionPlateau)piece.getPosition().clone());
		ig.definirEtAfficherChemin(cheminPositions,TEMPS_DEPLACEMENT_MS);
	}


	@Override
	public void gererDeplacementSurChemin(ArrayList<PositionPlateau> chemin) {
		final Joueur joueurEnCours=joueurDevantJouer();
		if (chemin.size()==0) {
			Objet objet=joueurEnCours.prochainObjetARecuperer(elementsJeu.getObjets());
			if (joueurEnCours.auMemeEndroit(objet)) {
				objet.setRecupere();
				ig.jouerSon(3);
			}
			ig.enleverCheminEtSelection();
			if (joueurEnCours.prochainObjetARecuperer(elementsJeu.getObjets())==null) {
				ig.afficherFinAvecGagnant();
				return;
			}
			joueurEnCours.setEstASonTourDeJouer(false);
			Joueur joueurSuivant=Joueur.joueurAvecId(elementsJeu.getJoueurs(), (joueurEnCours.getId()+1)%Joueur.NB_JOUEURS);
			joueurSuivant.setEstASonTourDeJouer(true);
			ig.mettreAJourAffichageJeu();
			if (joueurSuivant instanceof JoueurHumain) {
				coupJeuOrdi=null;
				ig.attendreClicRotationOuDecalage(10);
			}
			else {
				coupJeuOrdi=((JoueurOrdi)joueurSuivant).calculerCoup(elementsJeu);
				if (coupJeuOrdi.getNombreRotations()>0) {
					coupJeuOrdi.decNombreRotations();
					ig.realiserDemandeRotation(DELAI_SIMULATION_MS);
				}else {
					ig.realiserDemandeDecalage(coupJeuOrdi.getDirection(),coupJeuOrdi.getNumLigneOuColonneDec(), DELAI_SIMULATION_MS);
				}
			}
		}else {
			PositionPlateau premierePosition=chemin.remove(0);
			joueurEnCours.setPosition(premierePosition);
			ig.definirEtAfficherChemin(chemin,TEMPS_DEPLACEMENT_MS);
		}

	}



}
