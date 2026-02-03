import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Initialisation
        Board board = new Board();
        // L'IA joue les O
        CPUPlayer ai = new CPUPlayer(Mark.O); 
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bienvenue au Tic-Tac-Toe !");
        System.out.println("Vous êtes X, l'IA est O.");
        
        // 2. Boucle de jeu
        while (true) {
            // --- TOUR DU JOUEUR (X) ---
            board.print();
            System.out.println("Votre tour ! Entrez ligne (0-2) et colonne (0-2) séparées par un espace :");
            
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            
            // On joue le coup du joueur
            // (Note: dans un vrai jeu, il faudrait vérifier si la case est vide avant !)
            board.play(new Move(row, col), Mark.X);
            
            // Vérification fin de partie après coup Joueur
            if (checkEndGame(board)) break;

            // --- TOUR DE L'IA (O) ---
            System.out.println("L'IA réfléchit...");
            
            // On demande le meilleur coup à l'IA (AlphaBeta)
            ArrayList<Move> bestMoves = ai.getNextMoveAB(board);
            
            // L'IA prend le premier coup de sa liste de meilleurs coups
            Move aiMove = bestMoves.get(0);
            
            board.play(aiMove, Mark.O);
            System.out.println("L'IA a joué en : " + aiMove.getRow() + ", " + aiMove.getCol());
            System.out.println("Noeuds explorés : " + ai.getNumOfExploredNodes());

            // Vérification fin de partie après coup IA
            if (checkEndGame(board)) break;
        }
        
        scanner.close();
    }

    // Petite méthode utilitaire pour vérifier si le jeu est fini
    public static boolean checkEndGame(Board board) {
        int result = board.evaluate(Mark.X); // On regarde du point de vue de X
        
        if (result == 100) {
            board.print();
            System.out.println("VICTOIRE ! Vous avez gagné !");
            return true;
        } else if (result == -100) {
            board.print();
            System.out.println("DÉFAITE... L'IA a gagné.");
            return true;
        }
        
        // Vérifier match nul (plus de coups possibles)
        if (board.getAvailableMoves().isEmpty()) {
            board.print();
            System.out.println("MATCH NUL !");
            return true;
        }
        
        return false;
    }
}