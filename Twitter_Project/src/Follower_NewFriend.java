public class Follower_NewFriend {
    public int id;
    public Followee_NewFriend followee;
    public int nFollowees;

    public Follower_NewFriend(int id, Followee_NewFriend followee) {
        this.id = id;
        this.followee = followee;
        this.nFollowees = 1;
    }

}
