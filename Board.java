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

        //Remplir le tableau vide
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = Mark.EMPTY;
            }
        }

    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark){
        board[m.getRow()][m.getCol()] = mark;

    }

    public ArratList<Move> getAvailableMoves(){
        ArrayList<Move> availableMoves = new ArrayList<Move>();
        for(int i = 0; i < 3; i++){ //Lignes
            for(int j = 0; j < 3; j++){ //Colonnes
                if(board[i][j] == Mark.EMPTY){
                    availableMoves.add(new Move(i, j));
                }
            }
        }
        return availableMoves;
    }

    private boolean hasWon(Mark mark){
        //Vérifier les lignes
        for(int i = 0; i < 3; i++){
            if(board[i][0] == mark && board[i][1] == mark && board[i][2] == mark){
                return true;
            }
        }

        //Vérifier les colonnes
        for(int j = 0; j < 3; j++){
            if(board[0][j] == mark && board[1][j] == mark && board[2][j] == mark){
                return true;
            }
        }

        //Vérifier les diagonales
        if(board[0][0] == mark && board[1][1] == mark && board[2][2] == mark){
            return true;
        }
        if(board[0][2] == mark && board[1][1] == mark && board[2][0] == mark){
            return true;
        }

        return false;
    }

    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        if(mark == Mark.X){
            if(hasWon(Mark.X)){
                return 100;
            }
            else if(hasWon(Mark.O)){
                return -100;
            }
        }
        else{
            if(hasWon(Mark.O)){
                return 100;
            }
            else if(hasWon(Mark.X)){
                return -100;
            }
        }
        return 0;
    }
}
