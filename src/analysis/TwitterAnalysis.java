package analysis;

import java.io.IOException;
import java.util.Scanner;

import twitter4j.JSONException;
import graph.Graph;
import graph.User;

public class TwitterAnalysis {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Graph graph = null;

		try {
			switch (args.length) {
			case 1:
				graph = new Graph(args[0], null);
				break;
			case 2:
				graph = new Graph(args[0], args[1]);
				break;
			default:
				System.out.println("Wrong number of arguments:");
				System.out.println("Please enter 1 or 2 json files");
				System.exit(-1);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		while (true) {
			System.out.println("\n***********************");
			System.out.println("Which of the following "
					+ "methods would you like to run?");
			System.out.println("A. Number of users in dataset");
			System.out.println("B. Number of triangles "
					+ "in graph (strong ties)**");
			System.out.println("C. Friend recommendations "
					+ "for a user (strong ties**)");
			System.out.println("D. Friend recommendations "
					+ "for a user (strong ties**)");
			System.out.println("\n\nstrong ties: edges have to be "
					+ "bidirectional for a recommendation to be made");
			System.out.println("weak ties: there is an existing "
					+ "edge between users");
			
			String answer = in.nextLine().trim();
			if (answer.equals("quit")) {
				System.exit(0);
				break;
			}
			else if (answer.equals("a") || answer.equals("A")) {
				System.out.println("There are " + graph.getGraph().size()
						+ " users in the dataset");
			}
			else if (answer.equals("b") || answer.equals("B")) {
				int triang = TriadicClosure.triangleNumber(graph.getGraph());
				System.out.println("There are " + triang 
						+ " users in the dataset");
			}
			else if (answer.equals("c") || answer.equals("C")) {
				System.out.println("Please enter User ID");
				System.out.println("(For random user, type 0)");
				String ans = in.nextLine();
				if (ans.equals("0")) {
					Object[] arr = graph.getGraph().toArray();
					int random = (int) (Math.random() * arr.length);
					User user = (User) arr[random];
					TriadicClosure.friendRecommendation(graph.getGraph(), 
							user.getID(), true);
				}
				else {
					long id = Long.parseLong(ans);
					if (graph.getGraph().contains(new User(id))) {
						TriadicClosure.friendRecommendation(graph.getGraph(), 
								id, true);
					}
				}
			}
			else if (answer.equals("d") || answer.equals("D")) {
				System.out.println("Please enter User ID");
				System.out.println("(For random user, type 0)");
				String ans = in.nextLine();
				if (ans.equals("0")) {
					Object[] arr = graph.getGraph().toArray();
					int random = (int) (Math.random() * arr.length);
					User user = (User) arr[random];
					TriadicClosure.friendRecommendation(graph.getGraph(), 
							user.getID(), false);
				}
				else {
					long id = Long.parseLong(ans);
					if (graph.getGraph().contains(new User(id))) {
						TriadicClosure.friendRecommendation(graph.getGraph(), 
								id, false);
					}
				}
			}
			

		}


	}

}
