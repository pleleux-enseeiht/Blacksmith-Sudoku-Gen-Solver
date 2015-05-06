/** Classe de test de vidage de sudoku par Recuit Simulé et backtracking
 * Pour lancer l'application : java MainGen ([File])
 * Paramètres : température initiale du recuit simulé
 *              température finale du recuit simulé
 *              le pas de décrémentation de l'algorithme
 * @author      Philippe Leleux
 * @version     1.0
 */ 

public class MainGen {

	public static void main(String[] args) {
		try {
			int verbose = 0;
			double Ti = 1;
			double Tf = 0.0004;
			double pas = 0.00001;

			Sudoku temp = new Sudoku();
			RecuitSimule recuit = new RecuitSimule();
			if(args.length == 0) {
				recuit.sudoku.genererSudoku();
			} else if (args.length > 0) {
				recuit = new RecuitSimule(new Sudoku(args[0]));
				recuit.sudoku = recuit.solver(recuit.sudoku);
			}
			System.out.println("La grille de départ est : \n" + recuit.sudoku);
			long t0=System.currentTimeMillis();
			
			System.out.println("Vidage de la grille par recuit simulé en cours...");
			recuit.recuitSimule(verbose, Ti, Tf, pas);
			long t1=System.currentTimeMillis();
			System.out.println("Durée d'exécution : "+(t1-t0)+"ms");
			
			long nbOperations = (long) recuit.sudoku.nombreCasesPleines();
			nbOperations = ((long) Math.pow(2, nbOperations)) - 1;
		} catch (GrillePasValide e) {
			System.out.println("La grille n'est pas valide");
		}
	}
	
}
