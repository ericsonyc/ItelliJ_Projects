package hexgame.hexBoards;

public abstract class AbstractBoard implements BoardInterface {

    protected boolean changeOccured = true;
    protected int size;
    protected String name = "Default";

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasChanged() {
        return changeOccured;
    }

    public void changeNoted() {
        this.changeOccured = false;
    }

    public int get(int x, int y) {
        // TODO Auto-generated method stub
        return 0;
    }
}
