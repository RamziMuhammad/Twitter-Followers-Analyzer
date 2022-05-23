public class TopInfluencers_set implements Comparable<TopInfluencers_set> {
    public int rank;
    public int id;
    public int followers;

    public TopInfluencers_set(int rank, int id, int followers) {
        this.rank = rank;
        this.id = id;
        this.followers = followers;
    }

    @Override
    public String toString() {
        return "** Number " + rank + " with ID=" + id + " has " + followers + " followers **\n";
    }

    @Override
    public int compareTo(TopInfluencers_set o) {    // How it's sorting
        int folDiff = followers - o.followers;
        if (folDiff ==0){
            return 1;
        }
        return folDiff;
    }

}
