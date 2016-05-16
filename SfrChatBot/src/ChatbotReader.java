import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class ChatbotReader {

	public static void main(String[] args) {

		/* INTRODUCTION , ACCUEIL DU CLIENT */

		String chatterbotIntro[] = { "merci", "bonjour", "je vais bien","parfait", "bien" };
		String pasOk[] = { "pas très bien", "pas top", "malade", "pas terrible" };

		// dans un premier temps récuperer ce qui est tapé

		String intro = Input.input("Bonjour mon nom est alain comment allez vous?");

		/* thread pour donner l'illusion que la reponse n'est pas preparee */

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < chatterbotIntro.length; i++) {
			if (intro.contains(chatterbotIntro[i])) {
				System.out.println("je suis ravi d'entendre que vous allez bien");

			}
		}

		for (int i = 0; i < pasOk.length; i++) {
			if (intro.contains(pasOk[i])) {
				System.out.println("j'espère que vous irez mieux.");

			}
		}

		/* thread pour donner l'illusion que je tape quelque chose au clavier */

		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* DEUXIEME QUESTION */

		String forfaitOk[] = { "oui", "tout à fait", "oui parfait", "parfait","j'en suis ravi", "tout est ok", "ca va" };
		String forfaitPasOk[] = { "pas du tout", "non", "absolument pas","pas trop", "je n'en suis pas satisfait" };

		String nextIntro = Input.input("Avant toute chose, pourriez vous m'indiquer si vous êtes satisfait de votre forfait? ");

		/* thread pour donner l'illusion que la reponse n'est pas preparee */

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < forfaitOk.length; i++) {
			if (nextIntro.contains(forfaitOk[i])) {
				System.out.println("je suis ravi d'entendre que nos services vous conviennent");

			}
		}

		for (int i = 0; i < forfaitPasOk.length; i++) {
			if (nextIntro.contains(forfaitPasOk[i])) {
				System.out.println("j'espère que je parviendrai à trouver une solution pendant cet appel afin de vous satisfaire");

			}
		}

		/* thread pour donner l'illusion que je tape quelque chose au clavier */

		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* TROISIEME QUESTION AVANT D'ENTRER DANS LE VIF DU SUJET */

		String Ok[] = { "oui", "bien sûr", "aucun souci", "avec joie","j'en serai ravi" };
		String nonOk[] = { "non", "non merci", "désolé non","je n'ai pas de temps pour ca" };

		String thirdIntro = Input.input("A la fin de notre conversation m'autorisez-vous à vous envoyer une enquête de satisfaction? ");

		/* thread pour donner l'illusion que la reponse n'est pas préparée */

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < Ok.length; i++) {
			if (thirdIntro.contains(Ok[i])) {
				System.out.println("je vous enverrai cela sur votre boite e-mail.");

			}
		}

		for (int i = 0; i < nonOk.length; i++) {
			if (thirdIntro.contains(nonOk[i])) {
				System.out.println("Comme vous le désirez, aucun problème. ");

			}
		}

		/* thread pour donner l'illusion que je tape quelque chose au clavier */

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* ON ENTRE DANS LE VIF DU SUJET */

		/*
		 * je commence par demander quel est le sujet de l'appel et récupère
		 * l'entrée clavier
		 */

		System.out.println("quel est le sujet que vous souhaitez aborder?");
		Scanner scan = new Scanner(System.in);

		/*
		 * je laisse un temps mort de 10 secondes pour que le client tape au
		 * clavier le theme qu'il souhaite aborder
		 */

		try {
			Thread.sleep(18000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<MenuItem> result = new ArrayList<MenuItem>(); // j'initialise une liste pour les éléments xml

		/* ETAPE DE "PARSAGE" DU DOCUMENT DE REPONSES XML: chatbotData.xml */
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // on instancie la classe pour le xml

			DocumentBuilder builder = factory.newDocumentBuilder(); // permet de parser le xml
			Document doc = builder.parse("src/chatbotData.xml");

			NodeList list = doc.getElementsByTagName("menuItem"); // on recupère le tag via un noeud qui joue le role de tableau
			// on parcourt chaque menuitem du fichier
			for (int i = 0; i < list.getLength(); i++) {
				// recupere le menuItem
				Element item = (Element) list.item(i);
				MenuItem menuItem = new MenuItem(); // on instancie l'objet de la classe MenuItem
				NodeList childs = item.getChildNodes();
				for (int count = 0; count < childs.getLength(); count++) {

					Node anItem = childs.item(count); // on fait une boucle sur le total de tags "menuItem"

					if (anItem.getNodeName().equals("value")) {
						// on "set" comme valeur dans l'objet menuItem la valeur du node anItem.
						menuItem.setValue(anItem.getFirstChild().getNodeValue());

					}
					if (anItem.getNodeName().equals("keywords")) { // si je tombe sur keywords je recupere les tags "enfants" -> keyword

						// reboucle car il y a plusieurs sous nodes de anItem
						// qui seront les mots clés associés
						// pour chaques sous noeud (mot clé -> créer un objet
						// keyword et lajouter à la liste des mots clés du
						// MenuItem créé plus haut.
						for (int j = 0; j < anItem.getChildNodes().getLength(); j++) {
							Node aKeyword = anItem.getChildNodes().item(j);
							menuItem.addKeyword(new Keyword(aKeyword.getTextContent())); // j'utilise la methode "addKeyword" de l'objet "menuItem" pour ajouter le sous noeud "keyword"
						}
					}

				}
				result.add(menuItem);// et on ajoute a la liste déclarée plus haut, l'objet menuItem

			}

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}

		/*
		 * PUIS JE COMMENCE LE TRAITEMENT XML POUR PROPOSER AU CLIENT DES
		 * REPONSES
		 */

		ChatBot bot = new ChatBot(result);

		while (true) {

			List<String> reponses = bot.answer(scan.nextLine());
			System.out.println("les domaines concernés par votre réponse, sont : "+ reponses + ".\n Donc quel est le domaine qui vous intéresse ? ");
		}
		
		/* voir pour rajouter un test conditionnel pour differencier une seule reponse de plusieurs */ 

	}



}
