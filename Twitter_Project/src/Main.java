import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc= new Scanner(System.in);
        Set<Followees_Set> followeesSet = DataAnalysis.followeesReader("twitter.csv");
        int nFollowees = DataAnalysis.nFollowees(followeesSet);
        Followee[] followees = DataAnalysis.insertFollowees(followeesSet, nFollowees);

        Set<Followers_Set> followersSet = DataAnalysis.followersReader(followeesSet);
        int nFollowers = DataAnalysis.nFollowers(followersSet);
        Follower_NewFriend[] followers = DataAnalysis.insertFollowers(followersSet, nFollowers);

        DataAnalysis.Welcome();

        while(true){
            DataAnalysis.intro();

            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    System.out.println(nFollowees + " Accounts");
                    DataAnalysis.goToHome();
                }
                case "2" -> {
                    System.out.println("Enter the account ID: ");
                    int id = sc.nextInt();
                    int index = DataAnalysis.getFolloweeIndex(followees, id, 0, nFollowees);
                    if(index != -1) {
                        System.out.println(followees[index].nFollowers);
                    }
                    DataAnalysis.goToHome();
                }
                case "3" -> {
                    System.out.println("Enter the number of the Top Influencers you would like to display: ");
                    int num = sc.nextInt();
                    DataAnalysis.topInfluencers(followees, nFollowees, num);
                    DataAnalysis.goToHome();
                }
                case "4" -> {
                    System.out.println("Enter the account ID: ");
                    int id = sc.nextInt();
                    int index = DataAnalysis.getFollowerIndex(followers, id, 0, nFollowers);
                    if(index != -1) {
                        System.out.println(followers[index].nFollowees);
                    }
                    DataAnalysis.goToHome();
                }
                case "5" -> {
                    System.out.println("Enter your account ID: ");
                    int id = sc.nextInt();
                    System.out.println("Enter the threshlod number: ");
                    int thresholdNum = sc.nextInt();
                    List<Integer> sugFriends = DataAnalysis.suggestFriends(followers, nFollowers, id, thresholdNum);
                    System.out.println("You have " + sugFriends.size() + " suggested friends:");
                    System.out.println(sugFriends);
                    DataAnalysis.goToHome();
                }
                case "6" -> {
                    DataAnalysis.outro();
                    System.exit(0);
                }
                default -> System.out.println("Not valid entry!");
            }
        }
    }

}
