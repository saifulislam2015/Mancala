import java.util.*;


public class Player {
    public Board board;
    public Scanner s;
    public boolean isPlayer1;
    public int sideOfBoard;
    public ArrayList<Pit> playerView;
    public HashMap<Integer, Pit> pitHashmap;
    public Mancala mancala;
    public int depth=10;


    public Player(Board board, boolean isPlayer1,Mancala m) {
        this.board = board;
        //this.isAI = isAI;
        this.isPlayer1 = isPlayer1;
        if(isPlayer1) {
            sideOfBoard = 0;
        } else {
            sideOfBoard = 1;
        }
        s = new Scanner(System.in);
        this.mancala = m;
        playerView = new ArrayList<>();
        pitHashmap = new HashMap<>();
        initplayerView();


    }

    public Player copy() {
        //return new Player(new Board(board), isPlayer1, isAI);
        return new Player(new Board(board), isPlayer1,mancala);
    }

    public int getSideOfBoard() {
        return sideOfBoard;
    }


    public void initplayerView() {
        int counter = 0;
        if(isPlayer1) {

            for(int i = board.columns - 1 ; i >= 0 ; i--) {
                Pit PitToAdd = board.getPit(0,i);
                playerView.add(PitToAdd);
                pitHashmap.put(counter, PitToAdd);
                counter++;
            }

            for(int i = 0; i < board.columns; i++) {
                Pit PitToAdd = board.getPit(1,i);
                playerView.add(PitToAdd);
                pitHashmap.put(counter, PitToAdd);
                counter++;
            }
        } else {
            for(int i = 0; i < board.columns; i++) {
                Pit PitToAdd = board.getPit(1,i);
                playerView.add(PitToAdd);
                pitHashmap.put(counter, PitToAdd);
                counter++;
            }

            for(int i = board.columns - 1 ; i >= 0 ; i--) {
                Pit PitToAdd = board.getPit(0,i);
                playerView.add(PitToAdd);
                pitHashmap.put(counter, PitToAdd);
                counter++;
            }
        }
    }

    public void Finalize(){
        for(int i=8;i<15;i++){
            int add = pitHashmap.get(i).stones;
            pitHashmap.get(i).removeStones();
            pitHashmap.get(8).stones+=add;
        }
    }


    public void move() {
        //System.out.println("Choose the pit to take stones from:");
        //System.out.println("Options are: ");
        ArrayList<Integer> choicesMovement = new ArrayList<>();
        for(int i = 1; i < board.columns - 1 ; i++) {
            if(!board.getPit(sideOfBoard, i).isEmpty()) {
                choicesMovement.add(i);
                System.out.print(i + " ");
            }
        }
        if(choicesMovement.size()!=0){
            Random r = new Random();
            int idx = r.nextInt(choicesMovement.size());
            int choice = choicesMovement.get(idx);
            System.out.println(choice);

            executeMove(choice);
        }
    }

    public void moveAI(Player p){
        //System.out.println("In move\n");
        ArrayList<Integer> choicesMovement = new ArrayList<>();
        for(int i = 1; i < board.columns - 1 ; i++) {
            if(!board.getPit(sideOfBoard, i).isEmpty()) {
                choicesMovement.add(i);
                System.out.print(i + " ");
            }
        }
        //System.out.println();
        depth=4;
        Node root = new Node(null,board, this, p,0);
        root.getChildren(true);
        int max = -99999999;



        for(Node n : root.children) {
            //Minimax mini = new Minimax(n,4,false);
            n.heuristicValue = AlphaBeta(n, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            //n.heuristicValue = miniMax(n, 4, false);
            //n.heuristicValue = mini.miniMax();
            if(n.heuristicValue >= max) {
                max = n.decisionChosen;
            }
        }

        int choice = max;
        System.out.println("\nPit chosen by AlphaBeta was : " + choice);
        //System.out.println("calling execute\n");
        executeMove(choice);
    }

    public void executeMove(int choice) {
        //System.out.println("In execute\n");
        //in++;
        Pit chosenPit = board.getPit(sideOfBoard, choice);
        int numStones = chosenPit.getStones();
        chosenPit.removeStones();
        if(isPlayer1) {
            choice = board.columns - 1 - choice;
            //System.out.println("choice move: "+choice);
        }
        int counter = 1;
        int stoneIncrementer = 1;
        int init = pitHashmap.get(0).stones+pitHashmap.get(7).stones;
        board.moves = 0;

        while(stoneIncrementer <= numStones) {
            if(choice + counter > (board.columns)*2 - 1) {
                counter = 0;
                choice = 0;
                //if(in<=1) System.out.println("counter: "+counter);
            }
            Pit currentPit = pitHashmap.get(choice + counter);

            if(isPlayer1) {
                if(currentPit.isMancala() && !currentPit.isBlue()) {
                    counter++;
                    mancala.player1TurnNow = false;
                }
                else if(stoneIncrementer==numStones && currentPit.isMancala() && currentPit.isBlue){
                    //System.out.println("here");
                    currentPit.addStone();
                    counter++;
                    stoneIncrementer++;
                    board.moves+=1;
                    mancala.player1TurnNow = true;
                    //mancala.play();
                }

                else if(stoneIncrementer==numStones && currentPit.stones==0 && (choice+counter)<7 && pitHashmap.get(15-(choice+counter)).stones!=0){
                    currentPit.addStone();
                    int index = 15-(counter+choice);
                    int total = currentPit.stones+pitHashmap.get(index).stones;
                    currentPit.removeStones();
                    pitHashmap.get(index).removeStones();
                    pitHashmap.get(7).stones+=total;
                    counter++;
                    stoneIncrementer++;
                    mancala.player1TurnNow = false;
                }

                else {
                    pitHashmap.get(choice + counter).addStone();
                    counter++;
                    stoneIncrementer++;
                    mancala.player1TurnNow = false;
                    //if(in<=1) System.out.println("counter: "+counter);
                }
                //mancala.player1TurnNow = true;

            }
            else {
                if(currentPit.isMancala() && currentPit.isBlue()) {
                    counter++;
                    mancala.player1TurnNow = true;
                    //if(in<=1) System.out.println("counter: "+counter);
                }
                else if(stoneIncrementer==numStones && currentPit.isMancala() && !currentPit.isBlue){
                    currentPit.addStone();
                    counter++;
                    stoneIncrementer++;
                    board.moves++;
                    //System.out.println(board.moves);
                    mancala.player1TurnNow = false;
                }
                else if(stoneIncrementer==numStones && currentPit.stones==0 && (choice+counter)<7 && pitHashmap.get(15-(choice+counter)).stones!=0){
                    currentPit.addStone();
                    int index = 15-(counter+choice);
                    int total = currentPit.stones+pitHashmap.get(index).stones;
                    currentPit.removeStones();
                    pitHashmap.get(index).removeStones();
                    pitHashmap.get(7).stones+=total;
                    counter++;
                    stoneIncrementer++;
                    mancala.player1TurnNow = true;
                }
                else {
                    pitHashmap.get(choice + counter).addStone();
                    counter++;
                    stoneIncrementer++;
                    mancala.player1TurnNow = true;
                    //if(in<=1) System.out.println("counter: "+counter);
                }

            }
        }
        //if(extraMoves>=1) System.out.println(extraMoves);
        int fin  = pitHashmap.get(0).stones+pitHashmap.get(7).stones;
        board.stones_captured = fin-init;
        //System.out.println(board.stones_captured);
    }

    public int miniMax(Node root, int depth, boolean isMaximizingPlayer) {
        //System.out.println("Node number : " + root.getNodeCount() + " opened.");
        if(depth == 0 || root == null) {
            //Check if the game is done
            //return root.getBoard().Heuristic_1(root.getisPlayer1Max());
            return root.board.Heuristic_1(root.getisPlayer1Max());
        }

        if(isMaximizingPlayer) {
            root.getChildren(true);
            int bestValue = Integer.MIN_VALUE;
            int value;
            for(Node aNode : root.children) {
                value = miniMax(aNode, depth - 1, false);
                bestValue = Math.max(bestValue, value);
            }
            return bestValue;
        } else {
            root.getChildren(false);
            int bestValue = Integer.MAX_VALUE;
            int value;
            for(Node aNode : root.children) {
                value = miniMax(aNode, depth - 1, true);
                bestValue = Math.min(bestValue, value);
            }
            return bestValue;
            }
    }

    public int AlphaBeta(Node root, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        //System.out.println("Node number alphaBeta2: " + root.getNodeCount() + " opened.");
        if(depth == 0 || root == null) {
            if(isPlayer1)
                return root.board.Heuristic_1(root.getisPlayer1Max());
            return root.board.Heuristic_2(root.getisPlayer1Max());
        }

        if(isMaximizingPlayer) {
            root.getChildren(true);
            int value;
            for(Node aNode : root.children) {
                value = AlphaBeta(aNode, depth - 1, alpha, beta, false);
                //value = AlphaBeta(aNode, depth + 1, alpha, beta, false);
                alpha = Math.max(alpha, value);
                if(alpha >= beta) {
                    break;
                }
            }
            return alpha;
        }
        else {
            root.getChildren(false);
            int value;
            //for(Node aNode : root.getChildren()) {
            for(Node aNode : root.children) {
                value = AlphaBeta(aNode, depth - 1, alpha, beta, true);
                //value = AlphaBeta(aNode, depth + 1, alpha, beta, true);
                beta = Math.min(beta, value);
                if(alpha >= beta) {
                    break;
                }
            }
            return beta;
        }
    }
}
