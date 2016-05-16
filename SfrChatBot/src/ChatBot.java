import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author youssef
 *
 */
public class ChatBot {

    private List<MenuItem> data;

    ChatBot() {

    }

    ChatBot(List<MenuItem> data) {
	this.data = data;
    }

    public List<String> answer(String question) {

	Set<String> reponses = new HashSet<String>();

	BigDecimal bestDistance = BigDecimal.valueOf(0.1); // la meilleur distance attendue sinon on augmente le spectre de recherches
	BigDecimal distanceMaxRequired = BigDecimal.valueOf(0.5); 

	while (bestDistance.compareTo(distanceMaxRequired) <= 0) { //tant qu'on atteint distanceMaxRequired

	    for (MenuItem anItem : data) { // on cherche si des anItem correspondent

		BigDecimal distance = editDistanceNormalyzed(anItem.getValue(),question);
		if (distance.compareTo(bestDistance) < 0) { // si inférieur à 0 donc : distance < 0.5
		    reponses.add(anItem.getValue());
		} else {

		    loopkeywords: /* on initialise le debut de la boucle */
		    for (Keyword keyword : anItem.getKeyword()) { // sinon on a recourt aux mots clés

			StringTokenizer st = new StringTokenizer(question, " ");

			while (st.hasMoreTokens()) {
			    String token = st.nextToken();

			    BigDecimal keyDistance = editDistanceNormalyzed(keyword.getValue(), token); // mais on exige une forte ressemblance tout de meme

			    if (keyDistance.compareTo(BigDecimal.valueOf(0.1)) < 0) {
				reponses.add(anItem.getValue());
				break loopkeywords; /* et ca permet de faire un break en dehors du test */
			    }
			}
		    }

		}

	    }
	    bestDistance = bestDistance.add(BigDecimal.valueOf(0.1)); //on rajoute 0.1 à bestDistance dans le while
	}
	return new ArrayList<String>(reponses);

    }

    /*-----------------------------------------------------------------*/

    public int editDistance(String s1, String s2) {
	int finalEd = 0;

	if (s1.isEmpty() || s2.isEmpty()) {
	    System.err.println("erreur dans le(s) mot(s)");
	} else {
	    int[][] ed = new int[s1.length() + 1][s2.length() + 1]; // pr le tab avec 0
	    // partie d'initialisation
	    for (int i = 0; i < ed.length; i++) {
		   for (int j = 0; j < ed[i].length; j++) {
		    if (i == 0) {
			    ed[i][j] = j;
		    } else {
			if (j == 0) {
			    ed[i][j] = i;
			} else {
			    ed[i][j] = 0;
			}
		    }

		    // traitement
		    if (i > 0 && j > 0) {
			if (s1.charAt(i - 1) == s2.charAt(j - 1)) { // pr recup la  pos du char avec dc -1 (sans tab dc)
			    ed[i][j] = ed[i - 1][j - 1];
			} else {
			    ed[i][j] = Math.min(ed[i - 1][j] + 1, Math.min(ed[i][j - 1] + 1, ed[i - 1][j - 1] + 1));
			}
			finalEd = ed[ed.length - 1][ed[0].length - 1]; //pr recup la val à la bonne place du tab(dc avec -1)

		    }

		}
	    }
	}

	return finalEd;

    }

    /**
     * Calculer la distance entre s1 et S2 sous forme de pourcentage (sur base 1). 
     * 1 signifie différence totale
     * 0 signifie aucune différence donc similarité totale.
     * 
     * Pour ce faire, on calcule la distance entre s1 et s2 = on obtient un  nombre : N
     * N est nécessairement inférieur à la longueur au plus long des deux mots s1 et s2.
     * 
     * On calcul le rapport de N sur le plus maxLongeur(s1,S2)
     * 
     * 
     * @param s1
     * @param s2
     * @return
     */
    public BigDecimal editDistanceNormalyzed(String s1, String s2) {

	Integer distance = editDistance(s1, s2);
	Integer maxLength = (s1.length() > s2.length()) ? s1.length() : s2.length();
	return BigDecimal.valueOf(distance.longValue()).divide(
		BigDecimal.valueOf(maxLength.longValue()),RoundingMode.HALF_DOWN);
    }

}
