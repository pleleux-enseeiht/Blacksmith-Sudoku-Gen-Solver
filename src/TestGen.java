/** Classe de test de vidage de sudoku par Recuit Simulé et backtracking
 * Pour lancer l'application : java MainGen [Ti] [Tf] [pas de décrémentation]
 * Paramètres : température initiale du recuit simulé
 *              température finale du recuit simulé
 *              le pas de décrémentation de l'algorithme
 * @author      Philippe Leleux
 * @version     1.0
 */ 

public class MainGen {

	public static void main(String[] args) {
		try {
			Sudoku temp = new Sudoku();
			RecuitSimule recuit = new RecuitSimule();
			if(args.length==3) {
				recuit.sudoku.genererSudoku();
			} else if (args.length==4) {
				recuit = new RecuitSimule(new Sudoku(args[3]));
				recuit.sudoku = recuit.solver(recuit.sudoku);
			} else {
				throw new ChoixEnEntreeInvalide();
			}
			System.out.println("La grille de départ est : \n" + recuit.sudoku);
			long t0=System.currentTimeMillis();
			
			System.out.println("Vidage de la grille par recuit simulé en cours...");
			recuit.recuitSimule(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
			long t1=System.currentTimeMillis();
			System.out.println("Durée d'exécution : "+(t1-t0)+"ms");
			
			long nbOperations = (long) recuit.sudoku.nombreCasesPleines();
			nbOperations = ((long) Math.pow(2, nbOperations)) - 1;
			t0=System.currentTimeMillis();
			System.out.println("Vidage de la grille par backtracking en cours...");
			System.out.println("Le nombre d'opérations totale sera de : " + nbOperations);
			temp = recuit.vidageBruteForce();
			System.out.println("La meilleure solution est : \n" + temp + "avec E = " + temp.nombreCasesPleines());
			t1=System.currentTimeMillis();
			System.out.println("Durée d'exécution : "+(t1-t0)+"ms");
		} catch (ChoixEnEntreeInvalide e) {
			System.out.println("Pour lancer l'application taper : java Main [Ti] [Tf] [pas de décrémentation] [fichier]\nL'argument fichier est facultatif, cela dépend si vous voulez partir d'une grille existante et la remplir ou générer une grille à partir de rien");
		} catch (GrillePasValide e) {
			System.out.println("La grille n'est pas valide");
		}
	}
	
}
