import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer
{

        // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;
    private Mark maxPlayer;
    private Mark minPlayer;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu) {
        maxPlayer = cpu;
        minPlayer = (cpu == Mark.X) ? Mark.O : Mark.X;
    }

    // Ne pas changer cette méthode
    public int getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // =========================
    // MINIMAX
    // =========================
    
    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;

        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestValue = Integer.MIN_VALUE;

        for (Move m : board.getPossibleMoves()) {
            Board next = new Board(board);
            next.play(m, maxPlayer);

            int value = minimax(next, false);

            if (value > bestValue) {
                bestValue = value;
                bestMoves.clear();
                bestMoves.add(m);
            } else if (value == bestValue) {
                bestMoves.add(m);
            }
        }
        return bestMoves;
    }

    private int minimax(Board board, boolean isMax) {
        numExploredNodes++;

        if (board.isTerminal()) {
            return board.evaluate(maxPlayer);
        }

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (Move m : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(m, maxPlayer);
                best = Math.max(best, minimax(next, false));
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Move m : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(m, minPlayer);
                best = Math.min(best, minimax(next, true));
            }
            return best;
        }
    }

    // =========================
    // ALPHA-BETA
    // =========================

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board) {
        numExploredNodes = 0;

        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestValue = Integer.MIN_VALUE;

        for (Move m : board.getPossibleMoves()) {
            Board next = new Board(board);
            next.play(m, maxPlayer);

            int value = alphaBeta(next, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (value > bestValue) {
                bestValue = value;
                bestMoves.clear();
                bestMoves.add(m);
            } else if (value == bestValue) {
                bestMoves.add(m);
            }
        }
        return bestMoves;
    }

    private int alphaBeta(Board board, boolean isMax, int alpha, int beta) {
        numExploredNodes++;

        if (board.isTerminal()) {
            return board.evaluate(maxPlayer);
        }

        if (isMax) {
            int value = Integer.MIN_VALUE;
            for (Move m : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(m, maxPlayer);
                value = Math.max(value, alphaBeta(next, false, alpha, beta));
                alpha = Math.max(alpha, value);
                if (alpha >= beta) break;
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            for (Move m : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(m, minPlayer);
                value = Math.min(value, alphaBeta(next, true, alpha, beta));
                beta = Math.min(beta, value);
                if (alpha >= beta) break;
            }
            return value;
        }
    }
}
