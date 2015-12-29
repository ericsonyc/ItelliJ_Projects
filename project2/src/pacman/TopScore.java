package pacman;

/**
 * Created by ericson on 2015/12/28 0028.
 */
public class TopScore {
    private String name = "";
    private int score = 0;

    public TopScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TopScore) {
            TopScore top = (TopScore) obj;
            return this.getName().equals(top.getName());
        }
        return false;
    }
}
