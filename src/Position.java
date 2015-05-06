/** La classe position correspond au contenu d'une case de sudoku 
 * avec ses coordonnées (x, y) et sa valeur
 * @author      Philippe Leleux
 * @version     1.0
 */

public class Position {

	public int x;     //numéro de ligne
	public int y;     //numéro de colonne
	public int val;   //valeur de la case
	
	/** Constructeur de Position
	 * @param _x numéro de ligne
	 * @param _y numéro de colonne
	 * @param _val valeur de la case
	 */
	public Position(int _x, int _y, int _val) {
		this.x = _x;
		this.y = _y;
		this.val = _val;
	}
}
