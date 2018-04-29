public class Pit {

    public int stones;
    public int row;
    public int column;
    public boolean isMancala;
    public boolean isBlue = true;


    public Pit(int stones, int row, int column, boolean isMancala, boolean makeRed) {
        this.stones = stones;
        this.row = row;
        this.column = column;
        this.isMancala = isMancala;
        if(makeRed == true) setRed();
    }

    public Pit copy() {

        Pit copiedVersion = new Pit(this.stones, this.row, this.column, this.isMancala,this.isBlue);

        if(!this.isBlue()) {
             copiedVersion.setRed();
        }

        return copiedVersion;
    }

    public void setRed() {
        isBlue = false;
    }

    public boolean isBlue() {
        return isBlue;
    }

    public int getStones() {
        return stones;
    }

    public void removeStones() {
        stones = 0;
    }

    public void addStone() {
        stones += 1;
    }

    public boolean isEmpty() {
        if(stones == 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isMancala() {
        return isMancala;
    }

    public String getKey() {
        return "" + row + "," + column;
    }

    public String toString() {
        return "" + getStones();
    }
}
