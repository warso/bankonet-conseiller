package fr.pizzeria.console;

import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.digest.DigestUtils;

import fr.pizzeria.model.Client;

public class PizzeriaClient {

	public static void main(String[] args) {

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emfact = Persistence.createEntityManagerFactory("pizzeria-uni");

		int choixMenuClient = 0;
		while (choixMenuClient != 99) {
			System.out.println("*****Pizzeria Client*****");
			System.out.println("1. S'inscrire");
			System.out.println("2. Se connecter");
			System.out.println("99. Sortir");
			Scanner questionUser = new Scanner(System.in);
			questionUser.useLocale(Locale.US);
			choixMenuClient = questionUser.nextInt(); // Le choix de
														// l'utilisateur
			/** Inscription du client */

			if (choixMenuClient == 1) {
				System.out.println("Veuillez saisir un nom");
				String nom = questionUser.next(); // Choix d'un nom
				System.out.println("Veuillez saisir un prenom");
				String prenom = questionUser.next(); // Choix d'un prenom
				System.out.println("Veuillez saisir un mail");
				String mail = questionUser.next(); // Choix d'un mail
				System.out.println("Veuillez saisir un mot de passe");
				String mdp = questionUser.next(); // Choix d'un mot de passe

				EntityManager em = emfact.createEntityManager();
				EntityTransaction et = em.getTransaction();
				et.begin();
				Client c = new Client(nom, prenom, mail,mdp);
				em.persist(c);
				et.commit();
				em.close();
//				DigestUtils.sha1Hex(mdp) pour hacher le mot de passe le remplacer a mdp

			}

			/** Connexion du client */

			if (choixMenuClient == 2) {
				System.out.println("Veuillez saisir votre mail");
				String mail = questionUser.next(); // Inserer le mail
				System.out.println("Veuillez saisir votre mot de passe");
				String mdp = questionUser.next(); // Inserer le mot de passe

				EntityManager em = emfact.createEntityManager();
				TypedQuery<Client> query = em.createQuery("select c from Client c where c.mail=:mail AND c.mdp=:mdp",
						Client.class);
				query.setParameter("mail", mail);
				query.setParameter("mdp",mdp);

				if (query.getResultList().isEmpty()) {
					System.out.println("Ses identifiants n'existent pas !");
				} 
				
				else {
					Client c1 = query.getResultList().get(0);
					EntityTransaction et = em.getTransaction();
					et.begin();

					System.out.println("*****Pizzeria Client*****");
					System.out.println("1. Commander une pizza");
					System.out.println("2. Lister ses commandes");
					System.out.println("99. Sortir");

				}

			}

		}
		if (choixMenuClient == 99) {
			System.out.println("Au revoir \u1F614");
		}
	}

}
