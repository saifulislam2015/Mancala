import java.util.Random;

public class Mancala {

    public Board board;
    public Player player1;
    public Player player2;
    public boolean player1TurnNow;

    public Mancala() {

        board = new Board();

        player1 = new Player(board, true,this);
        player2 = new Player(board, false,this);


        Random r = new Random();
        int start = r.nextInt()%2;

        player1TurnNow = false;
        board.showBoard();

        if(start == 0) {
            System.out.println("Player 2 Starts");
            player2.moveAI(player2);
            player1TurnNow = true;
        }
        else {
            System.out.println("Player 1 Starts");
            player1.moveAI(player1);
            //player1TurnNow = true;
        }
        board.showBoard();
        //play();
    }

    public void play() {

        while(!board.isGameFinished()) {
            if(player1TurnNow) {
                System.out.println();
                System.out.println("Before Player 1 goes:");
                board.showBoard();
                System.out.println();
                System.out.println("Player 1 Turn");
                //player1.moveAI(player1);
                player1.move();
                //player1TurnNow = false;
                System.out.println();
                System.out.println("After Player 1 goes:");
                board.showBoard();
                if(board.isGameFinished()) {
                    //System.out.println("here");
                    player1.Finalize();
                    board.showBoard();
                }
            }
            else {
                System.out.println();
                System.out.println("Before Player 2 goes:");
                board.showBoard();
                System.out.println();
                System.out.println("Player 2 Turn");
                player2.moveAI(player2);
                //player1TurnNow = true;
                System.out.println();
                System.out.println("After Player 2 goes:");
                board.showBoard();
                if(board.isGameFinished()){
                    //System.out.println("here2");
                    player2.Finalize();
                    board.showBoard();
                }
            }
        }
        
        boolean winner = board.whoWon();
        if(winner) {
            System.out.println("Player 1 is won\nTotal stones: " + board.score1);
            //System.out.println("Human won\nTotal stones: " + board.score1);
        } else {
            System.out.println("Player 2 is won\nTotal stones: " + board.score2);
        }
    }

	public static void main(String[] args) {

		Mancala game;
        game = new Mancala();
        game.play();
    }
}
