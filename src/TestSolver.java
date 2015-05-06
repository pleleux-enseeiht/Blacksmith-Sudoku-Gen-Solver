/** Classe de test de génération de sudoku par Permutations Aleatoires et Recuit Simulé
 * Pour lancer l'application : java MainSolver [fichier]
 * Paramètres : fichier le chemin relatif du fichier à utiliser
 * @author      Philippe Leleux
 * @version     1.0
 */

public class MainSolver {

	public static void main(String[] args) {
		try {
			long t0;
			long t1;
			Sudoku sudoku = new Sudoku();
			boolean grilleVide = true;
			if (args.length==0) {
			} else if (args.length==1) {
				sudoku = new Sudoku(args[0]);
				grilleVide = false;
			} else {
				throw new ChoixEnEntreeInvalide();
			}
			Sudoku temp1 = new Sudoku(sudoku);
			Sudoku temp2 = new Sudoku(sudoku);
			System.out.println("La grille de départ est : \n" + sudoku);
			
			if(!grilleVide) {
				RecuitSimule recuit = new RecuitSimule(temp1);
				t0=System.currentTimeMillis();
				System.out.println("Résolution par brute force");
				temp1 = recuit.solver(temp1);
				t1=System.currentTimeMillis();
				System.out.println("Résultat : \n" + temp1);
				System.out.println("Durée d'exécution : "+(t1-t0)+"ms");
			} else {
				t0=System.currentTimeMillis();
				System.out.println("Résolution par permutations aléatoires avec amélioration du critère [nombre de surnuméraires] en cours...");
				temp1.genererSudoku();
				t1=System.currentTimeMillis();
				System.out.println("Résultat : \n" + temp1);
				System.out.println("Durée d'exécution : "+(t1-t0)+"ms");
			}
			
		
			t0=System.currentTimeMillis();
			System.out.println("Résolution par recuit simulé avec avec amélioration du critère [cij] en cours...");
			temp2.genererSudokuRecuit();
			t1=System.currentTimeMillis();
			System.out.println("Résultat : \n" + temp2);
			System.out.println("Durée d'exécution : "+(t1-t0)+"ms");
		} catch (ChoixEnEntreeInvalide e) {
			System.out.println("Pour lancer l'application taper : java MainGenerer [fichier]\nL'argument fichier est facultatif, cela dépend si vous voulez partir d'une grille existante et la remplir ou générer une grille à partir de rien");
		}
	}
}
