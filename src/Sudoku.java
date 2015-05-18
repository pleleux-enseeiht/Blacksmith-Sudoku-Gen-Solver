import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/** La classe Sudoku correspond à l'implantation de la grille, avec un tableau
 * de vecteurs colonnes, et des méthodes associées
 * @author      Philippe Leleux
 * @version     1.0
 */

public class Sudoku {
	
	public int[][] grille;	//grille de sudoku
	
	/** Constructeur de Sudoku en lisant son contenu dans un fichier
	 * @param fichier le chemin relatif vers le fichier
	 */
	public Sudoku (String fichier) {
		try{
			grille = new int[9][9];  //grille de sudoku
			String line;             //parcours par ligne du fichier
			int i, j;		 //numéros de ligne et colonne
			int count = 0;
			int k;
			BufferedReader in  = new BufferedReader(new FileReader(fichier));
			for(i=0; i<9; ++i) {
				line = in.readLine();
				count = 0;
				for(j=0; j<9; ++j) {
					k = j + count;
					if (line.substring(k, k+1).equals(" ")) {
						count++;
					}
					k = j + count;
					grille[i][j] = Integer.parseInt(line.substring(k, k+1));
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Constructeur de Sudoku à partir de rien : grille vide (de 0)
	 */
	public Sudoku () {
		this.grille = new int[9][9];
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				this.grille[i][j] = 0;
			}
		}
	}
	
	/** Constructeur de Sudoku en clonant un autre sudoku
	 * @param sudoku le Sudoku à cloner
	 */
	public Sudoku (Sudoku _sudoku) {
		this.grille = new int[9][9];
		this.clone(_sudoku);
	}
	
	/** vérifie si une valeur est possible, c'est à dire qu'on peut remplir une
	 * case tout en respectant les règles du jeu
	 * @param x numéro de la ligne
	 * @param y numéro de la colonne
	 * @param valeur valeur testée pour la case
	 * @return un booléen vrai si on mettre valeur dans la case (x, y), faux sinon
	 */
	public boolean valeurPossible (int x, int y, int valeur) {
		int i, j;	//parcours ligne et colonne
		
		//Respect de : un seul exemplaire de valeur par ligne
		for(j=0; j<9; ++j) {
			if(grille[x][j]==valeur) {
				return false;
			}
		}
		//Respect de : un seul exemplaire de valeur par colonne		
		for(i=0; i<9; ++i) {
			if(grille[i][y]==valeur) {
				return false;
			}
		}
		//Respect de : un seul exemplaire de valeur par région		
		for(i=0; i<3; ++i) {
			for(j=0; j<3; ++j) {
				if(grille[i+((x/3)*3)][j+((y/3)*3)]==valeur) {
					return false;
				}
			}
		}
		return true;
	}
	
	/** Recherche la position de la premiere case vide
	 * @return la position de la premiere case vide
	 */
	public Position positionCaseVide() {
		int i, j;	//parcours ligne et colonne
		for(i=0; i<9; ++i) {
			for(j=0; j<9; ++j) {
				if(grille[i][j]==0) {
					return new Position(i, j, 0);
				}
			}
		}
		return null;
	}
	
	/** Calcule le critere d'un sudoku : nombre d'exemplaires surnuméraires 
	 * de tous les chiffres en sommant les critereSimple de chaque chiffre
	 * @return le critere du sudoku
	 */
	public int critere () {
		int k;            //parcours chiffre
		int critere = 0;  //résultat	
		for(k=1; k<10; ++k) {
			critere +=critereSimple(k);
		}
		return critere;
	}

	/** Calcule le critere pour une case : nombre d'exemplaires surnuméraires
	 * du chiffre k
	 * @param k chiffre pour lequel on calcule le critere
	 * @return le critere du sudoku pour le chiffre k
	 */
	public int critereSimple (int k) {
		int i, j, r;      //parcours ligne, colonne et région
		int critere = 0;  //résultat
		boolean premier;  //vrai si c'est la première occurence de k
		//Calcul du nombre d'exemplaires surnuméraires de k en ligne
		for(i=0; i<9; ++i) {
			premier = true;
			for(j=0; j<9; ++j) {
				if(grille[i][j]==k&&!premier) {
					++critere;
				} else if(grille[i][j]==k) {
					premier = false;
				}
			}
		}
		//Calcul du nombre d'exemplaires surnuméraires de k en colonne
		for(j=0; j<9; ++j) {
			premier = true;
			for(i=0; i<9; ++i) {
				if(grille[i][j]==k&&!premier) {
					++critere;
				} else if(grille[i][j]==k) {
					premier = false;
				}
			}
		}
		//Calcul du nombre d'exemplaires surnuméraires de k en région
		for(i=0; i<3; ++i) {
			for(j=0; j<3; ++j) {
				premier = true;
				for(r=0; r<9; ++r) {
					if(grille[i*3+r/3][j*3+r%3]==k&&!premier) {
						++critere;
					} else if(grille[i*3+r/3][j*3+r%3]==k) {
						premier = false;
					}
				}
			}
		}
		return critere;
	}

	/** Calcule le critere de génération d'un sudoku : nombre d'occurences
	 * grille[i][j] dans l'union de la ligne, de la colonne et de la région 
	 * associée à la case (i, j) sans elle, en sommant pour toutes les cases
	 * @return le critere de génération du sudoku
	 */
	public int critereGeneration () {
		int i, j;         //parcours ligne et colonne
		int critere = 0;  //résultat
		for(i=0; i<9; ++i) {
			for(j=0; j<9; ++j) {
			critere +=critereGenerationSimple(grille[i][j], i, j);
			}
		}
		return critere;
	}
	
	/** Calcule le critere de génération d'une case de sudoku : nombre 
	 * d'occurences de grille[i][j] (k) dans l'union de la ligne, de la 
	 * colonne et de la région associée à la case (i, j) sans elle
	 * @return le critere de génération du sudoku
	 */
	public int critereGenerationSimple (int k, int x, int y) {
		int i, j, r;      //parcours de ligne, colonne et région
		int critere = 0;  //résultat
		//Calcul du nombre d'occurences dans la ligne privée de la case
		for(j=0; j<9; ++j) {
			if(grille[x][j]==k&&j!=y) {
				++critere;
			}
		}
		//Calcul du nombre d'occurences dans la colonne privée de la case
		for(i=0; i<9; ++i) {
			if(grille[i][y]==k&&i!=x) {
				++critere;
			}
		}
		//Calcul du nombre d'occurences dans la région privée de la case
		for(i=0; i<3; ++i) {
			for(j=0; j<3; ++j) {
				if(grille[i*3+3*(x/3)][j*3+3*(y/3)]==k&&(i!=x||j!=y)) {
					++critere;
				}
			}
		}
		return critere;
	}
	
	/** Génère le sudoku par permutations aléatoires en améliorant le critere
	 */
	public void genererSudoku() {
		int k;                       //parcours chiffres
		int i, j;                    //parcours ligne et numéro
	                                     //case aléatoire pour permutations
		int m, n;                    //case aléatoire pour permutations
		int pos;                     //position de placement aléatoire de départ
		int critere;                 //critere calculé
		int temp;		             //sudoku temporaire pour le calcul
		Random rand = new Random();  //fonction aléatoire
		int[] credit = new int[9];   //réserve de chiffres pour le placement
		int count = 0;		     //nombre d'itérations totales
		//credit de base de chaque chiffre
		for(k=0; k<9; ++k) {
			credit[k] = 9;
		}
		//lancement du recuit
		//remplissage de la grille aleatoirement avec les credits
		for (i = 0; i < 9; i++){
			for (j = 0; j < 9; j++){
				pos = rand.nextInt(9);
				while (credit[pos]==0) {
					pos = rand.nextInt(9);
				}
				credit[pos]--;
				grille[i][j] = pos+1;
			}
		}
		//permutations aléatoires jusqu'à obtention d'une grille valide
		critere = this.critere();
		while (critere!=0) {
			count++;
			i = 0;
			j = 0;
			m = 0;
			n = 0;
			//case (i, j) aleatoire 
				i = rand.nextInt(9);
				j = rand.nextInt(9);
			//case (m, n) aleatoire
				m = rand.nextInt(9);
				n = rand.nextInt(9);
			//permutation des cases (i, j) et (m, n) si le critere est ameliore
			temp = grille[i][j];
			grille[i][j] = grille[m][n];
			grille[m][n] = temp;
			if (this.critere() > critere) {
				temp = grille[i][j];
				grille[i][j] = grille[m][n];
				grille[m][n] = temp;
			}
			critere = this.critere();
			if (count%10000==0) {
				System.out.println(critere);
			}
		}
	}
	
	/** Génère le sudoku par recuit simulé
	 */
	public void genererSudokuRecuit() {
		double delta = 0.1;            //paramètre delta pour la dégénérescence de T
		int ep = 810;                  //paramètre ep pour la dégénérescence de T
		double T = 1.0;                //Température
		Random random = new Random();  //fonction aléatoire
		ArrayList<Position> casesFixes = new ArrayList<Position>();
                                       //liste des cases fixes de départ du sudoku
		int i=0, j=0;                  //parcours ligne et colonne
                                       //case aléatoire pour le changement
		int k;                         //compteur de paliers
		int mem;                       //memoire temporaire de grille[i][j]
		//critere de génération calculé
		int c, cprime;                 //pour le sudoku actuel puis c-c1+c2
		int c1, c2;                    //avant et après changement pour la case     
		int count;                     //nombre d'itérations
		double aleatoire;              //double compris entre 0 et 1
		boolean fin = false;           //vrai si on a trouvé un résultat exact
		boolean choisi;                //vrai si on a une case aléatoire non fixe
		//initialisation des cases fixes
		for(i=0; i<9; ++i) {
			for(j=0; j<9; ++j) {
				if(grille[i][j]!=0) {
					casesFixes.add(new Position(i, j, 0));
				}
			}
		}
		//lancement du recuit
		c = this.critereGeneration();
		count = 0;
		while(T>=0.00273852) {
			k = 0;
			//paliers successifs
			while(k<81&&!fin) {
				//choix d'une case non fixe aleatoirement
				choisi = false;
				while(!choisi) {
					choisi = true;
					i = random.nextInt(9);
					j = random.nextInt(9);
					for(int l=0; l<casesFixes.size(); ++l) {
						if((casesFixes.get(l).x==i)&&(casesFixes.get(l).y==j)) {
							choisi = false;
						}
					}
				}
				//changement de la valeur de la case (i,j)
				mem = grille[i][j];
				c1 = this.critereGenerationSimple(grille[i][j], i, j);
				while(grille[i][j]==mem/*|| !this.valeurPossible(i, j, grille[i][j])*/) {
					grille[i][j] = (random.nextInt(9)+1);
				}
				c2 = this.critereGenerationSimple(grille[i][j], i, j);
				cprime = c - c1 + c2;
				//on change le sudoku si on a une probabilité acceptable
				aleatoire = random.nextDouble()/10;
				if(aleatoire<=Math.exp(-(((double) (cprime-c))/ (2.0*T)))) {
					c = cprime;
				} else {
					grille[i][j] = mem;
				}
				++k;
			}
			//Dégénérescence de la température
			T = T/(1.0+((Math.log(1.0 + delta)/(ep+1.0))*T));
			if(count%10000==0) {
				System.out.println(this + "\n" + T + "\n");
			}
			++count;
		}
		System.out.println("Le résultat final est : \n" + this);
	}
	
	/** Retourne le nombre de cases non vides du sudoku
	 * @return le nombre de cases pleines du sudoku
	 */
	public int nombreCasesPleines() {
		int result = 0; //résultat
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				if(grille[i][j]!=0) {
					++result;
				}
			}
		}
		return result;
	}
	
	/** Renvoie la représentation d'un entier en binaire sous forme de liste
	 * @param n entier à traduire en bianaire
	 * @return la représentation en liste de n en binaire
	 */
	public int[] nextInt (int[] actuel) {
		int somme;
		int retenue = 1;
		for(int i=0; i<82; ++i) {
			somme = actuel[i]+retenue;
			actuel[i] = somme%2;
			retenue = somme/2;
		}
		return actuel;
	}
	
	/** Clone un Sudoko en copiant les valeurs de sa grille dans la grille actuelle
	 * @param _sudoku le sudoku à cloner
	 */
	public void clone (Sudoku _sudoku) {
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				this.grille[i][j] = _sudoku.grille[i][j];
			}
		}
	}
	
	/** Renvoie le contenu du sudoku sous forme de chaine de caracteres
	 * @return le sudoku en String
	 * ex :
            002370900
            007568402
            080090000
            100040800
            204000706
            006020001
            000050010
            501932600
            003086200
	 */
	public String toString() {
		String result = "";  //résultat
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				result += grille[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
	
}
