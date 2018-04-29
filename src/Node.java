import java.util.ArrayList;

public class Node {
    Node parent;
    public Board board;
    public ArrayList<Node> children;
    public int decisionChosen;
    public Player player;
    public Player opponent;
    public int heuristicValue;


    public Node(Node parent, Board passedBoard, Player player, Player opponent, int move) {
        this.parent = parent;
        board = new Board(passedBoard);
        children = new ArrayList<>();
        this.player = player.copy();
        this.opponent = opponent.copy();
        decisionChosen = move;
    }

    public void getChildren(boolean isMaximizingPlayer) {
        //System.out.println("In tree\n");
        ArrayList<Integer> choicesMovement = new ArrayList<>();
        if(isMaximizingPlayer) {
            for(int i = 1; i < board.columns - 1 ; i++) {
                if(!board.getPit(player.getSideOfBoard(), i).isEmpty()) {
                    choicesMovement.add(i);
                }
            }
            for(int move : choicesMovement) {
                Node childAdded = new Node(this, board, player, opponent, move);
                children.add(childAdded);
                childAdded.player.executeMove(move);
            }
        }
        else {
            for(int i = 1; i < board.columns - 1 ; i++) {
                if(!board.getPit(opponent.getSideOfBoard(), i).isEmpty()) {
                    choicesMovement.add(i);
                }
            }
            for(int move : choicesMovement) {
                Node childAdded = new Node(this, board, player, opponent, move);
                children.add(childAdded);
                childAdded.opponent.executeMove(move);
            }
        }
    }

    public int getisPlayer1Max() {
        return player.getSideOfBoard();
    }

}
