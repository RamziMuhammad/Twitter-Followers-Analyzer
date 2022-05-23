public class Followee {
    public int id;
    public Follower follower;
    public int nFollowers;
    public boolean mark;

    public Followee(int id, Follower follower) {
        this.id = id;
        this.follower = follower;
        this.nFollowers = 1;
        mark = false;
    }
}
