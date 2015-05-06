import java.util.Random;

/** La classe RecuitSimule correspond a l'implantation de l'algorithme de recuit
 * simule et des methodes necessaires
 * @author      Philippe Leleux
 * @version     1.0
 */

public class RecuitSimule {
	
	Sudoku sudoku;  //sudoku que l'on va vider par recuit simule et backtrack
	
	/** Constructeur de RecuitSimule a partir de rien (initialisation du sudoku)
	 */
	public RecuitSimule() {
		this.sudoku = new Sudoku();
	}
	
	/** Constructeur de RecuitSimule a partir du sudoku que l'on va vider par recuit
	 * simule et backtrack
	 * @param _sudoku que l'on clone pour le vider
	 */
	public RecuitSimule(Sudoku _sudoku) {
		sudoku = new Sudoku();
		sudoku.clone(_sudoku);
	}
	
	/** Donne le nombre d'occurences d'une valeur dans un tableau d'entiers
	 * @param vecteur le tableau d'entiers
	 * @param valeur la valeur à chercher
	 * @return le nombre d'occurences de valeur dans vecteur
	 */
	public int nombreOccurences (int[] vecteur, int valeur) {
		int nombreOccurences = 0;  //résultat
		for(int i=0; i<vecteur.length; ++i) {
			if(vecteur[i]==valeur) {
				++nombreOccurences;
			}
		}
		return nombreOccurences;
	}
	
	/** Donne la position de la première occurence d'une valeur dans un tableau d'entiers
	 * @param vecteur le tableau d'entiers
	 * @param valeur la valeur à chercher
	 * @return la première position de valeur dans vecteur
	 */
	public int positionPremiereOccurence (int[] vecteur, int valeur) {
		for(int i=0; i<vecteur.length; ++i) {
			if(vecteur[i]==valeur) {
				return i+1;
			}
		}
		return -1;
	}
	
	/** Renvoie le nombre de solutions d'un sudoku
	 * @param _sudoku le sudoku à résoudre
	 * @return le nombre de solutions de _sudoku
	 * @exception GrillePasValide si une des cases n'a aucune possibilité
	 */
	public int solutionUnique(Sudoku _sudoku) {
		TableauPossibilites tableau = new TableauPossibilites(_sudoku);
		int i, j;              //parcours ligne et colonne
		int nombreOccurences;  //résultat
		boolean modif = true;  //vrai si on fait une modification
		//On remplie les cases avec une possibilité unique
		while(modif) {
			modif = false;
			for(i=0; i<9; ++i) {
				for(j=0; j<9; ++j) {
					if(_sudoku.grille[i][j]==0) {
						nombreOccurences = this.nombreOccurences(tableau.tableau[i][j], 1);
						if(nombreOccurences==0) {
							return 0;
						} else if (nombreOccurences==1) {
							_sudoku.grille[i][j] = this.positionPremiereOccurence(tableau.tableau[i][j], 1);
							tableau.initialiser(_sudoku);
							modif = true;
						}
					}
				}
			}
		}
		//Si il n'y a plus de cases vides, il y a une solution
		//Sinon on appelle bruteForce
		if(_sudoku.positionCaseVide()==null) {
			return 1;
		} else {
			return this.bruteForce(tableau, _sudoku);
		}
	}
	
	/** Calcule solutionUnique en testant le remplissage des cases vides par
	 * toutes leurs valeurs possibles
	 * @param tableau le tableau des possibilités du sudoku
	 * @param _sudoku le sudoku sur lequel on travaille
	 * @return le nombre de solutions du sudoku
	 */
	public int bruteForce(TableauPossibilites tableau, Sudoku _sudoku) {
		Position vide = _sudoku.positionCaseVide();  //la position de la première case vide
		int nombrePositions = 0;
		for(int i=0; i<9; ++i) {
			if(tableau.tableau[vide.x][vide.y][i]==1) {
				Sudoku mem = new Sudoku();
				mem.clone(_sudoku);
				_sudoku.grille[vide.x][vide.y] = i+1;
				nombrePositions += this.solutionUnique(_sudoku);
				_sudoku = mem;
			}
		}
		return nombrePositions;
	}
	
	/** Renvoie une solution d'un sudoku
	 * @param _sudoku le sudoku à résoudre
	 * @return le sudoku solution
	 * @exception GrillePasValide si une des cases n'a aucune possibilité
	 */
	public Sudoku solver(Sudoku _sudoku) {
		TableauPossibilites tableau = new TableauPossibilites(_sudoku);
		int i, j;              //parcours ligne et colonne
		int nombreOccurences;  //résultat
		boolean modif = true;  //vrai si on fait une modification
		//On remplie les cases avec une possibilité unique
		while(modif) {
			modif = false;
			for(i=0; i<9; ++i) {
				for(j=0; j<9; ++j) {
					if(_sudoku.grille[i][j]==0) {
						nombreOccurences = this.nombreOccurences(tableau.tableau[i][j], 1);
						if(nombreOccurences==0) {
							return null;
						} else if (nombreOccurences==1) {
							_sudoku.grille[i][j] = this.positionPremiereOccurence(tableau.tableau[i][j], 1);
							tableau.initialiser(_sudoku);
							modif = true;
						}
					}
				}
			}
		}
		//Si il n'y a plus de cases vides, il y a une solution
		//Sinon on appelle bruteForce
		if(_sudoku.positionCaseVide()==null) {
			return _sudoku;
		} else {
			return this.solverBruteForce(tableau, _sudoku);
		}
	}
	
	/** Calcule solutionUnique en testant le remplissage des cases vides par
	 * toutes leurs valeurs possibles
	 * @param tableau le tableau des possibilités du sudoku
	 * @param _sudoku le sudoku sur lequel on travaille
	 * @return le nombre de solutions du sudoku
	 */
	public Sudoku solverBruteForce(TableauPossibilites tableau, Sudoku _sudoku) {
		Position vide = _sudoku.positionCaseVide();  //la position de la première case vide
		for(int i=0; i<9; ++i) {
			if(tableau.tableau[vide.x][vide.y][i]==1) {
				Sudoku mem = new Sudoku();
				Sudoku temp = new Sudoku();
				mem.clone(_sudoku);
				_sudoku.grille[vide.x][vide.y] = i+1;
				temp = this.solver(_sudoku);
				if (temp!=null) {
					return temp;
				}
				_sudoku = mem;
			}
		}
		return null;
	}
	
	/** Vide un sudoku en utilisant le recuit simulé
	 * @param Ti température initiale
	 * @param Tf température finale
	 * @param pas le pas de décrémentation de l'algorithme
	 */
	public void recuitSimule (int verbose, double Ti, double Tf, double pas) throws GrillePasValide {
		int E = 81;                      //Nombre de cases non vides
		double T = Ti;                   //Température de l'algorithme
		double aleatoire;                //Nombre aléatoire compris entre 0 et 1
		int Ef = 17;                     //Nombre de cases non vides minimum
		Random rand = new Random();      //fonction aléatoire
		int i, j;                        //case aléatoire
		Sudoku copieSolutionUniqueSudoku = new Sudoku();
		                  //copie du sudoku utilisée pour le calcul de solutionUnique
		Sudoku meilleur = new Sudoku();  //Initialisation de la meilleure grille
		int Emeilleur = E;               //Initialisation du meilleur résultat pour E
		RecuitSimule temp = new RecuitSimule(this.sudoku);
		                                 //RecuitSimule temporaire pour les calculs
		int cd = 0;                      //nombre de dégradations
		int cnd = 0;                     //nombre de dégradations potentielles
		int count = 0;                   //nombre d'itérations 
		while((T>Tf) && (E>Ef)) {
			if(count%10000==0 && verbose == 1) {
				System.out.println("T =" + T + "\nE =" + E + "\n");
			}
			aleatoire = Math.random()/10;
			i = rand.nextInt(9);
			j = rand.nextInt(9);
			//Si la case est vide et que la proba est adequate on degrade
			if (temp.sudoku.grille[i][j]==0) {
				++cnd;
				if(count%10000==0 && verbose == 1) {
					System.out.println("degrade si prob:\ni = " + i +" ,j = " + j +"\naleatoire = " + aleatoire + "\n");
				}
				if (aleatoire<this.loiProbabilite(1, T)) {
						++cd;
						temp.sudoku.grille[i][j] = this.sudoku.grille[i][j];
						E++;
						}
				}
			//sinon on la vide
			else {
				temp.sudoku.grille[i][j] = 0;
				E--;
				copieSolutionUniqueSudoku.clone(temp.sudoku);
				if (temp.solutionUnique(copieSolutionUniqueSudoku)>1) {
					temp.sudoku.grille[i][j] = this.sudoku.grille[i][j];
					E++;
				}
				if(count%10000==0 && verbose == 1) {
					System.out.println(temp.sudoku);
					System.out.println("amelioration critere:\ni = " + i + ",j = " + j + "\nE = " + E + "\n");
				}
			}
			if(Emeilleur>E) {
				Emeilleur = E;
				meilleur.clone(temp.sudoku);
			}
			T-=pas;
		}
		sudoku = meilleur;
		if (verbose == 1) {
			System.out.println("Grille vidée : " + temp.sudoku);
			System.out.println("nombre de dégradations effectuées : " + (cnd-cd));
			System.out.println("E = " + E + ", T = " + T + "\n");
			System.out.println("La meilleure solution trouvée est : \n" + meilleur + "avec E = " + Emeilleur);
		}
	}
	
	/** Vidage du sudoku par bruteForce
	 * @return le sudoku vidé
	 */
	public Sudoku vidageBruteForce() throws GrillePasValide {
		Sudoku meilleur = new Sudoku(sudoku);       //sudoku le plus vidé
		int Emeilleur = 81;                         //nombre de cases pleines du sudoku le plus vidé
		RecuitSimule temp = new RecuitSimule();     //recuit simulé temporaire pour les calculs
		Sudoku copieSolutionUnique = new Sudoku();  //sudoku temporaire pour le calcul du nombre de solutions
		int[] binaire = new int[82];                //nombre binaire pour avoir toutes les permutations
		boolean changement;                         //vrai a chaque changement de meilleure grille
		//initialisation du nombre binaire
		for(int i=0; i<82; ++i) {
			binaire[i] = 0;
		}
		//fin lorsque l'on atteint 2^81
		while (binaire[81]==0) {
			changement =false;
			temp.sudoku.clone(sudoku);
			binaire = sudoku.nextInt(binaire);
			//Un 1 dans le nombre binaire correspond à une case vidée
			for(int i=0; i<81; ++i) {
				if(binaire[i]==1) {
					temp.sudoku.grille[i/9][i%9] = 0;
				}
			}
			copieSolutionUnique.clone(temp.sudoku);
			//cette grille est-elle meilleure ?
			if(temp.solutionUnique(copieSolutionUnique) == 1) {
				if(temp.sudoku.nombreCasesPleines() < Emeilleur) {
					Emeilleur = temp.sudoku.nombreCasesPleines();
					meilleur.clone(temp.sudoku);
					changement = true;
				}
				if(changement) {
					System.out.println("meilleur : " + Emeilleur + "\n" + meilleur);
				}
			}
		}
		return meilleur;
	}
	
	/** Clone un RecuitSimule en clonant le sudoku
	 * @param recuit le RecuitSimule à cloner
	 */
	public void clone (RecuitSimule recuit) {
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				this.sudoku.grille[i][j] = recuit.sudoku.grille[i][j];
			}
		}
	}
	
	/** Calcule la loi de probabilité exp(-de/T)
	 * @param de le paramètre delta
	 * @param T la température
	 * @return la valeur de la loi de probabilité
	 */
	public double loiProbabilite (int de, double T) {
		return Math.exp(-((double) de/ T));
	}

}
