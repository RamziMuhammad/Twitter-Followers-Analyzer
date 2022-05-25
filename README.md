#                                       Twitter-Followers-Project

In this project, you are given a comma separated file to represent some twitter users and the users they follow. The file name is “twitter.csv”. Each row represents a certain user’s id and the id of another user he/she follows.



## Project features

- Reading and sorting **CSV files**, as well as removing any duplicate data

- Counting **how many** accounts are available

- Get any amount of **top-influencers**, even if you wish to include all accounts.

- Get the number of **followers** of a particular person by his **ID**

- Get the number of **followees** of a particular person by his **ID**

- Get **recommendations** for individuals to follow.

  

##  Here's a virtual representation for the project so you don't get lost

<p align="center">
  <img src="https://github.com/RamziMohamad/Twitter-Followers-Project/blob/main/Assets/Project-Representation.png"/>
</p>




------

## The various methods and the complexity analysis

So, we can evaluate the complexity of each project function.

First, there's the function that **reads the CSV file and stores the data after sorting and removing any duplicate data in a Tree Set**.

We have a complexity equals **O(E)**

​	Where **E** equals **no. of Edges** (**no. of rows in the CSV file**).

```java
public static Set<Followees_Set> followeesReader(String filePath) throws FileNotFoundException {
        int previous = 0;
        int preFollower;
        Set<Followees_Set> data = new TreeSet<>();					// A huge buffer for the whole data.
        Set<Buffer_Set> buffer = new TreeSet<>();					// A small buffer for a segment of data.
        // The buffer for storing sorted and distinct data.
    	Set<Followees_Set> followeesSet = new TreeSet<>();

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Complexity -> O(E).
            while ((line = reader.readLine()) != null) {            // Reading the file line by line.

                String[] row = line.split(",");               		// Divide the line into two elements
                // Storing every line in "data" TreeSet
                data.add(new Followees_Set(Integer.parseInt(row[0]), Integer.parseInt(row[1])));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Complexity -> O(1).
        for (Followees_Set acc : data) {
            previous = acc.followee;			// Initializing previous with the first followee.
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
                buffer.clear();					// Clear "buffer" TreeSet for a new followee data.
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
```

------

The second function is one that **counts the number of accounts we have**.

​	with a complexity equals **O(E)**

​		Where **E** equals **no. of Edges** (**no. of rows in the CSV file**).

```java
public static int nFollowees(Set<Followees_Set> followeesSet) {
    int nFollowees = 0;                                   // Counter.
    int preID = -1;                                       // Previous ID.
    // Complexity -> O(E).
    for (Followees_Set acc : followeesSet) {
        if (acc.followee != preID) {                      // If the read followee is not the previous one.
            nFollowees++;                                 // Increment the counter.
            preID = acc.followee;                         // Make previous pointing to the current followee.
        }
    }
    return nFollowees;
}
```

------

The third function is one that **inserts all data into adjacency list**.

So, we will have an **array of lists** to contain each account and its followers.

​	We iterate on the hole set so we have a complexity equals **O(E)**.

```java
public static Followee[] insertFollowees(Set<Followees_Set> followeesSet, int nFollowees) {
        Followee[] followees = new Followee[nFollowees];            // Declare an array of Followees.
        int i = 0;

        // Complexity -> O(E).
        for (Followees_Set account : followeesSet) {
            Follower newFollower = new Follower(account.follower);  // Create a new follower.

            if (followees[0] == null) {                             // If the array is empty.

                followees[0] = new Followee(account.followee, newFollower);
                
			// If the read follower belongs to the same followee.
            } else if (account.followee == followees[i].id) {       

                newFollower.nextFollower = followees[i].follower;   // Change references.
                followees[i].follower = newFollower;
                // Increment number of followers for the current followee.
                followees[i].nFollowers++;

            // If the read follower doesn't belong to the same followee.
            } else if (followees[i].id != account.followee) {       
                i++;
                followees[i] = new Followee(account.followee, newFollower);
            }
        }
        return followees;
    }
```

------

The forth function is one that **gets the index of a specific account ID in out array**.

We applied what we learned about the **Divide & Conquer Algorithm**.

So, with a complexity of **O(log(V))**, we've completed our index search.

​	Where **V** equals **no. of accounts**

```java
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
```

------

The fifth function is one that **gets any amount of top-influencers, even if you wish to include all accounts**.

​	with a complexity equals **O(V^2)**

```java
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
            // If the current followee has followers greater than top-influencer & not marked.
            if ((followees[j].nFollowers >= topInfluencer.nFollowers) && (!followees[j].mark)) {
                topInfluencer = followees[j];
            }
        }
        topInfluencer.mark = true;                              // Mark the top-influencer
        System.out.println(i + ") " + topInfluencer.id + " with " + topInfluencer.nFollowers + " followers");
    }
}
```

------

The sixth one gets **recommendations** for individuals to follow with complexity equals **O(v^2)**.

```java
public static List<Integer> suggestFriends(Follower_NewFriend[] followers, int	 nFollowers, int id, int thresholdNum){
        Set<ComFriends> allFollowees = new TreeSet<>();    // A buffer contains followees of both followers.
        List<Integer> sugFriends = new ArrayList<>();      // Array List contains the suggested friends.

        // Get the index of the entered ID.
        int index = getFollowerIndex(followers, id, 0, nFollowers);

        // Complexity -> O(v^2).
        for (int i = 0 ; i < nFollowers; i++) {            // Iterate on all accounts.

            if (followers[i].id != id) {

                // Adding the (id)'s followees into allFollowees
                Followee_NewFriend current = followers[index].followee;
                // Complexity -> O(v).
                for (int j = 0; j < followers[index].nFollowees; j++){
                    allFollowees.add(new ComFriends(id, current.id));
                    current = current.nextFollowee;
                }

                // Adding the (i)'s followees into allFollowees
                current = followers[i].followee;
                // Complexity -> O(v).
                for (int k = 0; k < followers[i].nFollowees; k++) {
                    allFollowees.add(new ComFriends(followers[i].id, current.id));
                    current = current.nextFollowee;
                }

                // If the mutual followees >= Threshold number, add the current id to the Suggestion List.
                if (isRecommended(allFollowees, followers[i].id, thresholdNum)){
                    sugFriends.add(followers[i].id);
                }

                // Clear the buffer for a new possible suggested friend.
                allFollowees.clear();
            }
        }
        return sugFriends;
    }
```



------

## The output simulation



First, the intro is presented, which allows you to select one of our project's amazing features.

<p align="center">
  <img src="https://github.com/RamziMohamad/Twitter-Followers-Project/blob/main/Assets/Intro.png" style="width:60%;"/>
</p>




1. If you select the first option to display the **number of available accounts**, the function will be instantly called and the right number will be presented.

   <p align="center">
     <img src="https://github.com/RamziMohamad/Twitter-Followers-Project/blob/main/Assets/Number-Accounts-Method.png" style="width:60%;"/>
   </p>
   
   
2. If you select the second option to see the **number of followers for a specific account**, you will be **prompted to input its ID**, after which the accurate number of followers will be displayed.

   <p align="center">
     <img src="https://github.com/RamziMohamad/Twitter-Followers-Project/blob/main/Assets/Number-Followers-Method.png" style="width:60%;"/>
   </p>
   
   
3. For the third feature to display the **Top-Influencers**, you will enter the number you want and we will do the rest.

   <p align="center">
     <img src="https://github.com/RamziMohamad/Twitter-Followers-Project/blob/main/Assets/Top-Influencers-Method.png" style="width:60%;"/>
   </p>
   
   
4. The forth feature is about **getting the number of followees** of a specific ID, you will be **prompted to input its ID**, after which the accurate number of followers will be displayed.

   <p align="center">
     <img src="https://github.com/RamziMohamad/Twitter-Followers-Project/blob/main/Assets/Number-Followees-Method.png" style="width:60%;"/>
   </p>
   
   
5. The fifth and final function allows you to receive a list of **near people to follow**; all you have to do is provide us your ID and we'll take care of the rest.

   <p align="center">
     <img src="https://github.com/RamziMohamad/Twitter-Followers-Project/blob/main/Assets/Suggestion-Friends-Method.png" style="width:60%;"/>
   </p>
   
   
6. Only use the last option if you wish to **exit** the application as well as after every function completion.

   **Note that**: We ask you if you want to **continue** using the app or leave it when each function is completed.

   

------

### Team Members

This Project was created due to the efforts of all the team members and their indescribable hard work.

<table>
  <tr>
    <td align="center"><a href="https://github.com/RamziMohamad"><img src="https://avatars.githubusercontent.com/u/66510024?v=4" width="250px;" alt=""/><br /><sub><b><center>Ramzi Muhammed</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/Abdelrahman-Atef-Elsayed"><img src="https://avatars.githubusercontent.com/u/66162676?v=4" width="250px;" alt=""/><br /><sub><b><center>Abd El-Rahman Atef</b></sub></a><br /></td>
   <td align="center"><a href="https://github.com/dohakhaled33"><img src="https://avatars.githubusercontent.com/u/66404704?v=4" width="250px;" alt=""/><br /><sub><b><center>Doha Khaled</b></sub></a><br /></td>
    </tr>
</table>
