package analysis;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import twitter4j.JSONException;
import graph.Graph;
import graph.User;

public class TwitterAnalysis {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Graph graph = null;

		if (args.length == 0) {
			System.out.println("Please enter at least 1 json file");
			System.exit(1);
		}
		System.out.println("Setting up graph...");
		try {
			graph = new Graph(args);
		} catch (IOException e) {
			System.out.println("Error: file failed to open");
			System.out.println(e.getMessage());
			System.exit(2);
		} catch (JSONException e) {
			System.out.println("JSON Error: " + e.getMessage());
			System.exit(3);
		}

		while (true) {
			System.out.println("\n***********************");
			System.out.println("Which of the following "
					+ "methods would you like to run?");
			System.out.println("A. Number of users in dataset");
			System.out.println("B. Number of triangles in graph (Count strong "
			        + "relationships only)");
			System.out.println("C. Number of triangles in graph (Include weak "
                    + "relationships too)");
			System.out.println("D. Friend recommendations "
					+ "for a user (strong ties**)");
			System.out.println("E. Friend recommendations "
					+ "for a user (weak ties**)");
			System.out.println("F. Follow recommendations "
			        + "for a user");

			System.out.println("\n\nstrong ties: only friendships where both "
					+ "users follow each other are counted");
			System.out.println("weak ties: friendships where either or both "
					+ "users follow each other are counted");

			String answer = in.nextLine().trim();
			if (answer.equals("quit")) {
				System.out.println("Are you sure you want to leave? :(");
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					//do nothing
				}
				System.out.println("Bye");
				System.exit(0);
			}
			else if (answer.equals("a") || answer.equals("A")) {
				System.out.println("There are " + graph.getGraph().size()
						+ " users in the dataset");
			}
			else if (answer.equals("b") || answer.equals("B")) {
				int triang = TriadicClosure.triangleNumber(graph.getGraph(), 
				        true);
				System.out.println("There are " + triang 
						+ " strongly-related triangles in the dataset");
			}
			else if (answer.equals("c") || answer.equals("C")) {
			    int triang = TriadicClosure.triangleNumber(graph.getGraph(), 
			            false);
			    System.out.println("There are " + triang 
			            + " weakly-related triangles in the dataset");
			}
			else if (answer.equals("d") || answer.equals("D")) {
				System.out.println("Please enter User ID");
				System.out.println("(For random user, type 0)");
				String ans = in.nextLine();
				System.out.println("How many friends would you "
						+ "like recommended?");
				int no = Integer.parseInt(in.nextLine().trim());

				if (ans.equals("0")) {
					Object[] arr = graph.getGraph().toArray();
					int random = (int) (Math.random() * arr.length);
					User user = (User) arr[random];
					TriadicClosure.friendRecommendation(graph.getGraph(), 
							user.getID(), true, no);
				}
				else {
					long id = Long.parseLong(ans);
					TriadicClosure.friendRecommendation(graph.getGraph(), 
							id, true, no);
				}
			}
			else if (answer.equals("e") || answer.equals("E")) {
				System.out.println("Please enter User ID");
				System.out.println("(For random user, type 0)");
				String ans = in.nextLine();

				System.out.println("How many friends would you "
						+ "like recommended?");
				int no = Integer.parseInt(in.nextLine().trim());


				if (ans.equals("0")) {
					Object[] arr = graph.getGraph().toArray();
					int random = (int) (Math.random() * arr.length);
					User user = (User) arr[random];
					TriadicClosure.friendRecommendation(graph.getGraph(), 
							user.getID(), false, no);
				}
				else {
					long id = Long.parseLong(ans);
					TriadicClosure.friendRecommendation(graph.getGraph(), 
							id, false, no);
				}
			} else if (answer.equals("f") || answer.equals("F")) {
                System.out.println("Please enter User ID");
                System.out.println("(For random user, type 0)");
                String ans = in.nextLine();

                System.out.println("How many users would you "
                        + "like recommended?");
                int no = Integer.parseInt(in.nextLine().trim());

                if (ans.equals("0")) {
                    Object[] arr = graph.getGraph().toArray();
                    int random = (int) (Math.random() * arr.length);
                    User user = (User) arr[random];
                    TriadicClosure.followRecommendations(graph.getGraph(), 
                            user.getID(), no);
                }
                else {
                    long id = Long.parseLong(ans);
                    TriadicClosure.followRecommendations(graph.getGraph(), 
                            id, no);
                }
            } else {
			    System.out.println("Sorry. Invalid input. Please try again.\n"
			            + "Type quit to exit the program");
			}
			//pause before prompting for next answer
			try {
				TimeUnit.MILLISECONDS.sleep(2000);
			} catch (InterruptedException e) {
				//do nothing
			}
		}
	}
}
