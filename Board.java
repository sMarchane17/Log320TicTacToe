import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class Board 
{
    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        board = new Mark[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    public Board(Board other) {
        board = new Mark[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = other.board[i][j];
            }
        }
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark) {
        board[m.getRow()][m.getCol()] = mark;
    }

    // Génère les coups possibles (gauche → droite, haut → bas)
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Mark.EMPTY) {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }

    // Vérifie si victoire
    private boolean isWinner(Mark m) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == m && board[i][1] == m && board[i][2] == m)
                return true;
            if (board[0][i] == m && board[1][i] == m && board[2][i] == m)
                return true;
        }

        if (board[0][0] == m && board[1][1] == m && board[2][2] == m)
            return true;
        if (board[0][2] == m && board[1][1] == m && board[2][0] == m)
            return true;

        return false;
    }

    // Vérifie si partie terminée
    public boolean isTerminal() {
        return isWinner(Mark.X) || isWinner(Mark.O) || getPossibleMoves().isEmpty();
    }

    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark max) {
        Mark min = (max == Mark.X) ? Mark.O : Mark.X;

        if (isWinner(max)) return 100;
        if (isWinner(min)) return -100;

        return 0;
    }

    public void printBoard() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (board[i][j]) {
                    case X -> System.out.print(" X ");
                    case O -> System.out.print(" O ");
                    case EMPTY -> System.out.print(" . ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public Mark getWinner() {
        if (isWinner(Mark.X)) return Mark.X;
        if (isWinner(Mark.O)) return Mark.O;
        return Mark.EMPTY;
    }
}