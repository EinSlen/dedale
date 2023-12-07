package dedale.elements;

import java.util.Random;

public class Util {
	static private final Random generateurAleatoire=new Random(0);
	/**
	 * Méthode retournant un nombre entier généré aléatoirement.
	 * @param valMax La valeur maximale du nombre entier généré.
	 * @return Un nombre entier compris au sens large entre 0 et valMax.
	 */
	static public int genererAleatoirementEntier(final int valMax) {
		return generateurAleatoire.nextInt(valMax+1);
	}
}
