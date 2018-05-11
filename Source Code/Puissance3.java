/**
 * 
 * @author Ayoub SANHAJI
 *
 */

public class Puissance3 {

	private int[][] matriceJeu;
	public  final int WIDTH = 5;
	public  final int HEIGHT = 5;
	public final int PROFONDEUR_DE_JEU = 4;

	public Puissance3()
	{
		matriceJeu = new int[HEIGHT][WIDTH];
	}

	/**
	 * jouer la colonne j de la matrice passe en prametre selon le type du joueur : 
	 * jeton = 1 si MAX et jeton = 2 si MIN 
	 */
	public boolean jouer(Boolean typeJoueur, int j, int[][] matrice)
	{
		// Machine=1 / Humain=2
		int val;
		if(typeJoueur)
			val = 1;
		else
			val = 2;
		
		// Placer la valeur 'val' dans la premiere case vide de la colonne 'j'
		for(int i=0 ; i<HEIGHT ; i++)
			if((matrice[i][j]==0) && (!estFinJeu(false, matrice) && (!estFinJeu(true, matrice))))
			{
				matrice[i][j] = val;
				return true;
			}
		return false;
	}
	
	/**
	 * retourne true si c'est la fin du jeu : grille pleine, MAX a gagne ou MIN a gagne
	 */
	public boolean estFinJeu(Boolean typeJoueur, int [][] matriceJeu)
	{
		// Tester si la grille est pleine
		boolean full = true;
		for(int j=0 ; j<WIDTH ; j++)
			if(matriceJeu[HEIGHT-1][j] == 0)
				full = false;
		
		// Tester si qlq'un a gange
		boolean victoire = false;
		Noeud n = new Noeud(typeJoueur, matriceJeu);
		if(n.troisPionsAlignesColonne(typeJoueur)==1000 || n.troisPionsAlignesLigne(typeJoueur)==1000)
			victoire = true;
		return full || victoire;
	}

	/**
	 * l'algorithme alpha beta : il retourne un Objet Coup (val + noColonne) 
	 * il s'agit de l'evaluation du meilleur successeur avec le meilleur coup a jouer
	 */
	public Coup alpha_beta(Noeud n , int alpha, int beta, int profondeur) 
	{
		if(profondeur==1 || estFinJeu(n.isMax(), n.getMatrice()))
		{
			n.evaluer();
			return new Coup(n.getH(), n.getNoColonne());
		}
		
		Coup eval = null;
		int [][] newGrille = new int[HEIGHT][WIDTH];
		copieMatrice(n.getMatrice(), newGrille);
		n.evaluer();
		int col = 0;
		if(n.isMax())
		{
			for(int j=0 ; j<WIDTH ; j++)
				if(jouer(true, j, newGrille))
				{
					Noeud succ = new Noeud (true, newGrille);
					succ.setNoColonne(j);
					succ.evaluer();
					eval = alpha_beta(succ, alpha, beta, profondeur-1);
					if(eval.getVal() > alpha)
					{
						alpha = eval.getVal();
						col = j;
					}
					if(alpha >= beta)
						return new Coup(alpha, col);
				}
		}
		else
		{
			for(int j=0 ; j<WIDTH ; j++)
				if(jouer(false, j, newGrille))
				{
					Noeud succ = new Noeud (false, newGrille);
					succ.setNoColonne(j);
					succ.evaluer();
					eval = alpha_beta(succ, alpha, beta, profondeur-1);
					if(eval.getVal() < beta)
					{
						beta = eval.getVal();
						col = j;
					}
					if(beta <= alpha)
						return new Coup(beta, col);
				}
		}
		return eval;
	}
	
	private void copieMatrice (int [][]from, int[][]to)
	{
		for(int i=0; i<HEIGHT; i++)
			System.arraycopy(from[i], 0, to[i], 0, WIDTH);
	}
	
	public int[][] getMatriceJeu() 
	{
		return matriceJeu;
	}
	
	@Override
	public String toString()
	{
		String retour = "----------";
		for(int i=4; i>=0; i--)
		{
			retour += "\n";
			for(int j=0; j<5; j++)
			{
				retour += matriceJeu[i][j]+"|";
			}
		}
		retour += "\n----------\n";	
		return retour;
	}
}