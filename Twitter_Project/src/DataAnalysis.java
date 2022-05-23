import java.io.*;
import java.util.*;

public class DataAnalysis {
    public static Scanner sc = new Scanner(System.in);

    public static void Welcome() {
        System.out.format("""
                ⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙
                ⁛ 👉 Welcome to our program ❤   ⁛
                ⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙
                """);
    }

    public static void intro() {
        System.out.format("""
                1) Get the number of accounts
                2) Get the number of followers of a specific ID
                3) Get the top influences
                4) Get the number of followees of a specific ID
                5) Get recommended new friends
                6) Exit the program
                """);
    }

    public static void helloAgain() {
        System.out.format("""
                ⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙
                ⁛      👇 Welcome again 😍       ⁛
                ⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙
                """);
    }

    public static void outro() {
        System.out.format("""
                ⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙
                ⁛         💓 Goodbye 🤗          ⁛
                ⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙⁙
                """);
    }

    public static void goToHome() {
        while (true) {
            System.out.println("""
                    1 -> Back to home
                    2 -> Exit
                    """);
            String choice = sc.next();
            if (Objects.equals(choice, "1")) {
                helloAgain();
                break;
            } else if (Objects.equals(choice, "2")) {
                System.exit(0);
            } else {
                System.out.println("Not valid Entry!");
            }
        }
    }

    // Complexity -> O(E).
    public static Set<Followees_Set> followeesReader(String filePath) throws FileNotFoundException {
        int previous = 0;
        int preFollower;
        Set<Followees_Set> data = new TreeSet<>();                  // A huge buffer for the hole data.
        Set<Buffer_Set> buffer = new TreeSet<>();                   // A small buffer for a segment of data.
        Set<Followees_Set> followeesSet = new TreeSet<>();          // The buffer for storing sorted and distinct data.

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Complexity -> O(E).
            while ((line = reader.readLine()) != null) {            // Reading the file line by line.

                String[] row = line.split(",");               // Divide the line into two elements
                // Storing every line in "data" TreeSet
                data.add(new Followees_Set(Integer.parseInt(row[0]), Integer.parseInt(row[1])));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Complexity -> O(1).
        for (Followees_Set acc : data) {
            previous = acc.followee;                                // Initializing previous with the first followee.
            break;
        }
        // Complexity -> T(E) = 2*E = O(E).
        for (Followees_Set acc : data) {
            if (acc.followee == previous) {
                // Storing all rows related to the same followee into "buffer" TreeSet.
                buffer.add(new Buffer_Set(acc.follower, acc.followee));
            } else {
                previous = acc.followee;
                preFollower = 0;
                for (Buffer_Set following : buffer){
                    if(following.follower != preFollower) {
                        // Storing only distinct data into "followeeSet" TreeSet.
                        followeesSet.add(new Followees_Set(following.follower, following.followee));
                        preFollower = following.follower;
                    }
                }
                buffer.clear();                                     // Clear "buffer" TreeSet for a new followee data.
                buffer.add(new Buffer_Set(acc.follower, acc.followee));
            }
        }
        for (Buffer_Set following : buffer){
            followeesSet.add(new Followees_Set(following.follower, following.followee));
        }

        File csvFile = new File("Sorted_Followees.csv");   // Storing the sorted data into CSV file.
        PrintWriter out = new PrintWriter(csvFile);
        // Complexity -> T(E) = E - Duplicated data = O(E).
        for (Followees_Set acc : followeesSet) {
            out.println(acc.follower + ", " + acc.followee);
        }
        out.close();

        return followeesSet;
    }

    // Complexity -> O(E).
    public static int nFollowees(Set<Followees_Set> followeesSet) {
        int nFollowees = 0;                                         // Counter.
        int preID = -1;                                             // Previous ID.
        // Complexity -> O(E).
        for (Followees_Set acc : followeesSet) {
            if (acc.followee != preID) {                            // If the read followee is not the previous one.
                nFollowees++;                                       // Increment the counter.
                preID = acc.followee;                               // Make previous pointing to the current followee.
            }
        }
        return nFollowees;
    }

    // Complexity -> O(E).
    public static Followee[] insertFollowees(Set<Followees_Set> followeesSet, int nFollowees) {
        Followee[] followees = new Followee[nFollowees];            // Declare an array of Followees.
        int i = 0;

        // Complexity -> O(E).
        for (Followees_Set account : followeesSet) {
            Follower newFollower = new Follower(account.follower);  // Create a new follower.

            if (followees[0] == null) {                             // If the array is empty.

                followees[0] = new Followee(account.followee, newFollower);

            } else if (account.followee == followees[i].id) {       // If the read follower belongs to the same followee.

                newFollower.nextFollower = followees[i].follower;   // Change references.
                followees[i].follower = newFollower;
                followees[i].nFollowers++;                          // Increment number of followers for the current followee.

            } else if (followees[i].id != account.followee) {       // If the read follower doesn't belong to the same followee.
                i++;
                followees[i] = new Followee(account.followee, newFollower);
            }
        }
        return followees;
    }

    // Complexity -> O(log(V)).
    public static int getFolloweeIndex(Followee[] followees, int id, int low, int high) {
        // Using D&C Algorithm (Binary Search).
        int mid = (low + high) / 2;

        if (low > high) {
            System.out.println("Sorry, not found!");
            return -1;
        }
        if (followees[mid].id == id) {                              // The followee is found.
            return mid;
        } else if (followees[mid].id < id) {
            return getFolloweeIndex(followees, id, mid + 1, high);
        } else {
            return getFolloweeIndex(followees, id, low, mid - 1);
        }
    }

    // Complexity -> O(V^2).
    public static void topInfluencers(Followee[] followees, int nFollowees, int num) {

        // Complexity -> O(V).
        for (int j = 0; j < nFollowees; j++) {                      // Remove all marks from followees.
            if (followees[j].mark)
                followees[j].mark = false;
        }

        // Complexity -> O(V^2).
        for (int i = 1; i <= num; i++) {
            Followee topInfluencer = new Followee(0, null);

            // Complexity -> O(V).
            for (int j = 0; j < nFollowees; j++) {
                // If the current followee has followers greater than top-influencer & not taken before (not marked).
                if ((followees[j].nFollowers >= topInfluencer.nFollowers) && (!followees[j].mark)) {
                    topInfluencer = followees[j];
                }
            }
            topInfluencer.mark = true;                              // Mark the top-influencer
            System.out.println(i + ") " + topInfluencer.id + " with " + topInfluencer.nFollowers + " followers");
        }
    }

}
