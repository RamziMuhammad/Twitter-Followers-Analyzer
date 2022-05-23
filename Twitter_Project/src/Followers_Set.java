public class Followers_Set implements Comparable<Followers_Set> {
    public int follower;
    public int followee;

    public Followers_Set(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    public String toString() {
        return "follower=" + follower + ", followee=" + followee + "\n ";
    }

    @Override
    // How they are being sorted
    public int compareTo(Followers_Set o) {
        int folDiff = follower - o.follower;
        if (folDiff == 0){
            return 1;
        }
        return folDiff;
    }

}
