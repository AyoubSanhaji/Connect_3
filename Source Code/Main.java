import java.util.Scanner;

/**
 * 
 * @author Ayoub SANHAJI
 *
 */

public class Main 
{
	private static Scanner sc;

	public static void main(String[] args)
	{
		Puissance3 jeu = new Puissance3();
		sc = new Scanner(System.in);
		int colonne;
		boolean b = true;
		int alpha=Integer.MIN_VALUE, beta=Integer.MAX_VALUE;
		while(true)
		{
			// Tour de l'IA
			if(b)
			{
				System.out.println("IA: ");
				Noeud n = new Noeud(true, jeu.getMatriceJeu());
				Coup c = jeu.alpha_beta(n, alpha, beta, jeu.PROFONDEUR_DE_JEU);
				colonne = c.getNoColonne();

				jeu.jouer(true, colonne, jeu.getMatriceJeu());
				n = new Noeud(true, jeu.getMatriceJeu());
				n.setNoColonne(colonne);
				n.evaluer();
				System.out.println(n);
				if(jeu.estFinJeu(b, jeu.getMatriceJeu()))
				{
					System.out.println("IA win !");
					System.exit(0);
				}
				b = false;
			}
			// Tour de l'humain
			else
			{
				System.out.println("Humain: ");
				System.out.print("Type the column index (1-5) : ");
				colonne = sc.nextInt()-1;
				jeu.jouer(false, colonne, jeu.getMatriceJeu());
				Noeud n = new Noeud(false, jeu.getMatriceJeu());
				n.setNoColonne(colonne);
				n.evaluer();
				System.out.println(n);
				if(jeu.estFinJeu(b, jeu.getMatriceJeu()))
				{
					System.out.println("Player win !");
					System.exit(0);
				}
				b = true;
			}
			
		}
		
	}
}