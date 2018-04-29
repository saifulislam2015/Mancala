import java.util.HashMap;

public class Board {
    public static int rows = 2;
    public String[][] board;
    public HashMap<String, Pit> map;
    public int columns;
    public int initStones;
    public int score1;
    public int score2;
    public int moves;
    public int stones_captured;
    public boolean empty1;
    public boolean empty2;


    public Board() {
        columns = 8;
        board = new String[rows][columns];
        map = new HashMap<>();
        initStones = 4;
        initBoard();
    }

    public Board(Board passedBoard) {
        columns = passedBoard.columns;
        board = new String[Board.rows][columns];
        map = new HashMap<>();
        initStones = passedBoard.initStones;
        for(String key : passedBoard.map.keySet()) {
            Pit copiedPit = passedBoard.map.get(key).copy();
            this.map.put(key, copiedPit);
        }
    }

    public void initBoard() {
        //initHashMap();
        Pit temp;
        for(int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(i==1)  temp =  new Pit(initStones, i, j, false,true);
                else  temp = new Pit(initStones,i,j,false,false);
                map.put(generateKey(i,j), temp);
            }
        }
        map.put(generateKey(0, 0), new Pit(0, 0, 0, true,false));
        map.put(generateKey(1, 0), new Pit(0, 1, 0, true, true));

        map.put(generateKey(0, columns - 1), new Pit(0, 0, columns - 1, true,false));
        map.put(generateKey(1, columns - 1), new Pit(0, 1, columns - 1, true, true));
    }



    public int Heuristic_1(int isPlayer1) {
        int mancalaP1Left = map.get(generateKey(0,0)).getStones();
        int mancalaP1Right = map.get(generateKey(0,columns - 1)).getStones();
        int mancalaP2Left = map.get(generateKey(1,0)).getStones();
        int mancalaP2Right = map.get(generateKey(1,columns - 1)).getStones();

        if(isPlayer1 == 0) {
            //System.out.println("For P1: " + ((mancalaP1Left + mancalaP1Right) - (mancalaP2Left + mancalaP2Right)));
            return ((mancalaP2Left + mancalaP2Right)-(mancalaP1Left + mancalaP1Right));
        }
        else {
            //System.out.println("For p2: "+((mancalaP2Left + mancalaP2Right) - (mancalaP1Left + mancalaP1Right )));
            return ((mancalaP1Left + mancalaP1Right)-(mancalaP2Left + mancalaP2Right));
        }
    }

    public int Heuristic_2(int isPlayer1) {
        int W1 = 2,W2=10;
        int side1=0,side2=0;
        int sum = W1*Heuristic_1(isPlayer1);

        for(int i=1;i<columns-1;i++){
            side1+=map.get(generateKey(0,i)).getStones();
            side2+=map.get(generateKey(1,i)).getStones();
        }
        if(isPlayer1==1) sum+=W2*(side1-side2);
        else  sum+=W2*(side2-side1);

        return sum;
    }

    public int Heuristic_3(int isPlayer1,int m) {
        int W3 = 5;
        int h = Heuristic_2(isPlayer1);
        h+=W3*m;

        return h;
    }
    public int Heuristic_4(int isPlayer1,int val,int m) {
        //System.out.println(val);
        int W4 = 10;
        int h = Heuristic_3(isPlayer1,m);
        h+=W4*val;

        return h;
    }


    public String generateKey(int row, int column) {
        return "" + row + "," + column;
    }

    //Returns a Pit on the board
    public Pit getPit(int row, int column) {
        return map.get(generateKey(row, column));
    }

    //Prints out the board
    public void showBoard() {
        countMancala();
        System.out.println("\n");
        System.out.print(score1+" ");
        for(int j=1;j<columns-1;j++){
            if(j!=columns-2)System.out.print(getPit(0,j)+"- ");
            else System.out.print(getPit(0,j)+" ");
        }
        System.out.println();
        System.out.print("  ");
        for(int j=1;j<columns-1;j++){
            if(j!=columns-2) System.out.print(getPit(1,j)+"- ");
            else System.out.print(getPit(1,j)+" ");
        }
        System.out.print(score2);
        System.out.println();
    }

    public boolean whoWon() {
        boolean isPlayer1Winner = false;
        countMancala();
        System.out.println("p1: "+score1);
        System.out.println("p2: "+score2);
        if(score1 > score2) {
             isPlayer1Winner = true;
        }
        return isPlayer1Winner;
    }

    public void countMancala() {

        score1 = getPit(0, 0).getStones() + getPit(0, columns - 1).getStones();
        score2 = getPit(1, 0).getStones() + getPit(1, columns - 1).getStones();

    }

    public boolean isGameFinished() {

        boolean over = false;

        //Check player1 Pits
        for(int i = 1; i < columns - 1 ; i++) {
            if(getPit(0, i).isEmpty()) {
                over = true;
            }
            else {
                over = false;
                break;
            }
        }

        if(over) {
            empty1 = true;
            return over;
        }

        //Check Player2 Pits
        for(int i = 1; i < columns - 1 ; i++) {
            if(getPit(1, i).isEmpty()) {
                over = true;
            } else {
                over = false;
                break;
            }
        }
        empty2 = true;
        return over;
    }
}
