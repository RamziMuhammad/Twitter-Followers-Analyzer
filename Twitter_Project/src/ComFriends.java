public class ComFriends implements Comparable<ComFriends> {
    public int follower;
    public int followee;

    public ComFriends(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    public String toString() {
        return "follower=" + follower + ", followee=" + followee + "\n ";
    }


    @Override
    // How they are being sorted
    public int compareTo(ComFriends o) {    // How it's sorting
        int folDiff = followee - o.followee;
        if (folDiff ==0){
            return 1;
        }
        return folDiff;
    }

}
