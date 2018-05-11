/**
 * 
 * @author Ayoub SANHAJI
 *
 */

public class Noeud 
{
	private int[][] matrice; // la grille de l'etat courant du jeu
	private boolean max ; // le type du joueur (true->max->machine) - (false->min->l'humain)
	private int noColonne; // le coup joue : numero de colonne joue
	private int h; // Evaluation heuritique de noeud
	
	public Noeud (Boolean max, int[][] matrice)
	{	
		this.max = max;
		this.matrice = matrice;	
	}

	public int getH() 
	{
		return h;
	}

	public void setH (int h) 
	{
		this.h = h;
	}

	public int[][] getMatrice() 
	{
		return matrice;
	}

	public void setMatrice (int[][] matriceJeu) 
	{
		this.matrice = matriceJeu;
	}

	public boolean isMax() 
	{
		return max;
	}

	public void setMax (boolean max) 
	{
		this.max = max;
	}

		
	public int getNoColonne() 
	{
		return noColonne;
	}

	public void setNoColonne (int noColonne) 
	{
		this.noColonne = noColonne;
	}
	
	@Override
	public String toString() 
	{
		System.out.println("Coup joué " + noColonne);
		System.out.println("Evaluation h = " + h);
		String retour = "----------";
		for(int i=4; i>=0; i--)
		{
			retour += "\n";
			for(int j=0; j<5; j++)
			{
				retour += matrice[i][j]+"|";
			}
		}
		retour += "\n----------\n";
		return retour;
	}

	/**
	 * Fonction d'Evaluation
	 */
	public void evaluer()
	{
		int valeur = -2*troisPionsPossiblesLigne(false);
		valeur += troisPionsPossiblesLigne(true);
		valeur += -2*troisPionsPossiblesColonne(false);
		valeur += troisPionsPossiblesColonne(true);

		int ligne = -2*troisPionsAlignesLigne(false);
		ligne += troisPionsAlignesLigne(true);
		ligne += -2*troisPionsAlignesColonne(false);
		ligne += troisPionsAlignesColonne(true);

		valeur += ligne;
	
		h = valeur;
	}
	
	/**
	 * calcule si 3 pions sont alignes en ligne
	 * @param typeJour type du joueur dont il faut verifier s'il a trois pions alignes dans une ligne
	 * @return 0 si pas d'alignement, 1000 si trois pions sont alignes
	 */
	public int troisPionsAlignesLigne (Boolean typeJoueur)
	{
		// Machine=1 / Humain=2
		int val;
		if(typeJoueur)
			val = 1;
		else
			val = 2;
		
		int pion = 0;
		for(int i=0 ; i<matrice.length ; i++)
			for(int j=0 ; j<matrice[0].length ; j++)
			{
				if(matrice[i][j] == val)
					pion++;
				else
					pion = 0;
				if(pion == 3)
					return 1000;
			}
		return 0;
	}
	
	/**
	 * calcule si 3 pions sont alignes en colonne
	 */
	public int troisPionsAlignesColonne (Boolean typeJoueur)
	{
		// Machine=1 / Humain=2
		int val;
		if(typeJoueur)
			val = 1;
		else
			val = 2;
				
		int pion = 0;
		for(int j=0 ; j<matrice[0].length ; j++)
			for(int i=0 ; i<matrice.length ; i++)
			{
				if(matrice[i][j] == val)
					pion++;
				else
					pion = 0;
				if(pion == 3)
					return 1000;
			}
		return 0;
	}
	
	/**fonction qui retourne la valeur d'une ligne
	 * si 2 pions de type typeJoueur alignes et accolles a une case vide -> val = 200 
	 * si 2 pions de type typeJoueur alignes et accolles a deux cases vides -> val = 400 
	 * si 1 pion de type typeJoueur accolle a une case vide -> val = 30 
	 * si 1 pion de type typeJoueur accolle a deux cases vides -> val = 60 
	 */
	private int troisPionsPossiblesLigne (Boolean typeJoueur)
	{
		int retour = 0;
		// Machine=1 / Humain=2
		int val;
		if(typeJoueur)
			val = 1;
		else
			val = 2;
				
		int pion=0, vide=0;
		for(int i=0 ; i<matrice.length ; i++)
			for(int j=0 ; j<matrice[0].length ; j++)
			{
				if(matrice[i][j] == val)
					pion++;
				else
				{
					if(matrice[i][j] == 0)
						vide++;
					else
					{
						pion = 0;
						vide = 0;
					}
				}
				
				if(pion==1 && vide==1)
					retour += 30;
				if(pion==1 && vide==2)
					retour += 30;
				if(pion==2 && vide==1)
					retour += 200;
				if(pion==2 && vide==2)
					retour += 200;
			}
		return retour;
	}
	
	/**fonction qui retourne la valeur d'une colonne
	 * si 2 pions de type typeJoueur alignes et accolles a une case vide -> val = 100 
	 * si 1 pion de type typeJoueur accolle a une case vide -> val = 30 
	 */
	private int troisPionsPossiblesColonne (Boolean typeJoueur)
	{
		int retour = 0;
		// Machine=1 / Humain=2
		int val;
		if(typeJoueur)
			val = 1;
		else
			val = 2;
				
		int pion=0, vide=0;
		for(int j=0 ; j<matrice[0].length ; j++)
			for(int i=matrice.length-1 ; i>=0 ; i--)	
			{
				if(matrice[i][j] == val)
					pion++;
				else
				{
					int k = (i-1>0) ? matrice[i-1][j] : -1;
					if(matrice[i][j]==0 && k!=0)
						vide++;
					else
					{
						pion = 0;
						vide = 0;
					}
				}
				
				if(pion==1 && vide==1)
					retour += 30;
				if(pion==2 && vide==1)
					retour += 70;
			}
		return retour;
	}
}