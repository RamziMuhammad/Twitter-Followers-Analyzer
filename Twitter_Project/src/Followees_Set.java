public class Followees_Set implements Comparable<Followees_Set> {
    public int follower;
    public int followee;

    // Constructor
    public Followees_Set(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    public String toString() {
        return "follower=" + follower + ", followee=" + followee + "\n ";
    }

    @Override
    public int compareTo(Followees_Set o) {    // How it's sorting
        int folDiff = followee - o.followee;
        if (folDiff == 0){
            return 1;
        }
        return folDiff;
    }

}
