public class Buffer_Set implements Comparable<Buffer_Set> {
    public int follower;
    public int followee;

    public Buffer_Set(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    public String toString() {
        return "follower=" + follower + ", followee=" + followee + "\n ";
    }

    @Override
    public int compareTo(Buffer_Set o) {    // How it's sorting
        int folDiff = follower- o.follower;
        if (folDiff ==0){
            return 1;
        }
        return folDiff;
    }

}
