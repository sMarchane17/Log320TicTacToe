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

    // Le marqueur (X ou O) du joueur CPU pour se souvenir.
    private Mark cpuMark;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        this.cpuMark = cpu;
    }

    // Ne pas changer cette méthode
    public int  getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;
        
        ArrayList<Move> availableMoves = board.getAvailableMoves(); 
        
        int bestScore = Integer.MIN_VALUE;
        ArrayList<Move> bestMoves = new ArrayList<Move>();

        for(Move move : availableMoves){
            board.play(move, cpuMark);
            
            int currentScore = minMax(board, false);
            
            board.play(move, Mark.EMPTY); 

            if(currentScore > bestScore){
                bestScore = currentScore;
                bestMoves.clear();
                bestMoves.add(move);
            }
            else if(currentScore == bestScore){
                bestMoves.add(move);
            }
        }

        return bestMoves;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;

        ArrayList<Move> availableMoves = board.getAvailableMoves();
        
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        int bestScore = Integer.MIN_VALUE;
        ArrayList<Move> bestMoves = new ArrayList<Move>();

        for(Move move : availableMoves){
            board.play(move, cpuMark);

            int currentScore = alphaBeta(board, alpha, beta, false);

            board.play(move, Mark.EMPTY);

            if(currentScore > bestScore){
                bestScore = currentScore;
                
                alpha = Math.max(alpha, bestScore);

                bestMoves.clear();
                bestMoves.add(move);
            }
            else if(currentScore == bestScore){
                bestMoves.add(move);
            }
        }
        return bestMoves;
    }

    
    private int minMax(Board board, boolean isMax){
        numExploredNodes++;

        int score = board.evaluate(cpuMark);
        if(score == 100 || score == -100){
            return score;
        }

        ArrayList<Move> availableMoves = board.getAvailableMoves();
        if(availableMoves.isEmpty()){
            return 0;
        }

        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for(Move move : availableMoves){
            board.play(move, isMax ? cpuMark : (cpuMark == Mark.X ? Mark.O : Mark.X));
            int currentScore = minMax(board, !isMax);
            board.play(move, Mark.EMPTY); // Revert the move
            if(isMax){
                bestScore = Math.max(bestScore, currentScore);
            }
            else{
                bestScore = Math.min(bestScore, currentScore);
            }
        }
        return bestScore;
    }

    private int alphaBeta(Board board, int alpha, int beta, boolean isMax){
        numExploredNodes++;

        int score = board.evaluate(cpuMark);
        if(score == 100 || score == -100){
            return score;
        }

        ArrayList<Move> availableMoves = board.getAvailableMoves();
        if(availableMoves.isEmpty()){
            return 0;
        }

        if(isMax){
            int bestScore = Integer.MIN_VALUE;
            for(Move move : availableMoves){
                board.play(move, cpuMark);
                int currentScore = alphaBeta(board, alpha, beta, false);
                board.play(move, Mark.EMPTY); // Revert the move
                bestScore = Math.max(bestScore, currentScore);
                alpha = Math.max(alpha, bestScore);
                if(beta <= alpha){
                    break; // Beta cut-off
                }
            }
            return bestScore;
        }
        else{
            int bestScore = Integer.MAX_VALUE;
            for(Move move : availableMoves){
                board.play(move, (cpuMark == Mark.X ? Mark.O : Mark.X));
                int currentScore = alphaBeta(board, alpha, beta, true);
                board.play(move, Mark.EMPTY); // Revert the move
                bestScore = Math.min(bestScore, currentScore);
                beta = Math.min(beta, bestScore);
                if(beta <= alpha){
                    break; // Alpha cut-off
                }
            }
            return bestScore;
        }
    }

}
