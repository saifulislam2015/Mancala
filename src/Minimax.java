public class Minimax {
    public Node root;
    public int depth;
    public boolean isMaximizingPlayer;

    public Minimax(Node n,int d,boolean im){
        this.root =  n;
        this.depth = d;
        this.isMaximizingPlayer = im;
    }

    //public int miniMax(Node root,int depth,boolean isMaximizingPlayer){
    public int miniMax(){
        if(depth==0 || root==null)
            return root.board.Heuristic_1(root.getisPlayer1Max());

        if(isMaximizingPlayer){
            root.getChildren(true);
            int best = Integer.MAX_VALUE;
            int val;

            for(Node n:root.children){
                Minimax m = new Minimax(n,depth-1,false);
                val = m.miniMax();
                best = Math.max(val,best);
            }

            return best;
        }

        else {
            root.getChildren(false);
            int best = Integer.MIN_VALUE;
            int val;

            for(Node n:root.children){
                Minimax m = new Minimax(n,depth-1,true);
                val = m.miniMax();
                best = Math.min(val,best);
            }

            return best;
        }
    }


}
