import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code {

	public static void main(String[] args) throws IOException {

		String file1 = "";
		String file2 = "";

		if (args.length < 2) {
			System.out.println("Error: Please enter file1 and file2 paths.");
			System.exit(0);
		} else {
			file1 = args[0];
			file2 = args[1];
		}

		heapDriver(file1);
		queueDriver(file2);

	}

	private static void heapDriver(String path) {
		// Creating empty Priority Queue
		PriorityQueue<String> pQueue = new PriorityQueue<String>();
		pQueue = toHeapFileReader(path);
		int size = pQueue.size();

		long start = System.nanoTime();
		String[] names = sort_pQueue(pQueue);
		long end = System.nanoTime();

		double elapsedTime = (end - start) / 1000000;

		String timeMessage1 = "Heap(PriorityQueue) Sort: " + "Number of Names = " + size + ", Elapsed Time = "
				+ (elapsedTime) + " milliSeconds";
		String timeMessage2 = "Heap(PriorityQueue) Sort: " + "Number of Names = " + size + ", Elapsed Time = "
				+ (elapsedTime) / 1000 + " Seconds";

		System.out.println("\n");
		System.out.println(timeMessage1);
		System.out.println(timeMessage2);

		writeToFile(names);
	}

	private static void writeToFile(String[] data) {
		File file = new File("sortedFile1.txt");
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			for (int i = 0; i < data.length; i++) {
				br.write(data[i]);
				br.newLine();
			}

		} catch (IOException e) {
			System.out.println("Couldn't find file");
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String[] sort_pQueue(PriorityQueue<String> pQueue) {
		// Takes priority queue and return sorted data in array
		// each poll O(logn) * n item = O(nlogn)
		// poll : retrieves and removes the head of this queue
		int size = pQueue.size();
		String[] data = new String[size];
		for (int i = 0; i < size; i++) {
			data[i] = pQueue.poll();
		}
		return data;
	}

	private static void queueDriver(String path) {
		Queue<String> q = new LinkedList<String>();
		q = toQueueFileReader(path);
		int size = q.size();
		long startTime;
		long endTime;
		double elapsedTime;
		String timeMessage1;
		String timeMessage2;

		if (size > 850) {
			startTime = System.nanoTime();
			sortQueue_Inplace(q);
			endTime = System.nanoTime();
			elapsedTime = (endTime - startTime) / 1000000;
			timeMessage1 = "Inplace Queue Sort: " + "Number of Names = " + q.size() + ", Elapsed Time = "
					+ (elapsedTime) + " milliSeconds";
			timeMessage2 = "Inplace Queue Sort: " + "Number of Names = " + q.size() + ", Elapsed Time = "
					+ (elapsedTime) / 1000 + " Seconds";

			System.out.println("\n");
			System.out.println(timeMessage1);
			System.out.println(timeMessage2);
		} else {
			startTime = System.nanoTime();
			sortQueue_Rec(q);
			endTime = System.nanoTime();
			elapsedTime = (endTime - startTime) / 1000000;

			timeMessage1 = "Recursion Queue Sort: " + "Number of Names = " + q.size() + ", Elapsed Time = "
					+ (elapsedTime) + " milliSeconds";
			timeMessage2 = "Recursion Queue Sort: " + "Number of Names = " + q.size() + ", Elapsed Time = "
					+ (elapsedTime) / 1000 + " Seconds";
			System.out.println("\n");
			System.out.println(timeMessage1);
			System.out.println(timeMessage2);
		}

		writeToFile(q);
	}

	private static void writeToFile(Queue<String> data) {
		File file = new File("sortedFile2.txt");
		int size = data.size();
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			for (int i = 0; i < size; i++) {
				br.write(data.poll());
				br.newLine();
			}

		} catch (IOException e) {
			System.out.println("Couldn't find file");
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static Queue<String> toQueueFileReader(String path) {

		// Creating empty Queue
		Queue<String> q = new LinkedList<String>();

		File file = new File(path);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line;
			// each add O(1) * n item = O(n)
			while ((line = br.readLine()) != null) {
				q.add(line);
			}
		} catch (IOException e) {
			System.out.println("Please enter file2 path...");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return q;

	}

	private static PriorityQueue<String> toHeapFileReader(String path) {
		// Creating empty priority queue
		PriorityQueue<String> pQueue = new PriorityQueue<String>();

		File file = new File(path);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				// each add O(logn) * n item = O(nlogn)
				pQueue.add(line);
			}
		} catch (IOException e) {
			System.out.println("Please enter file1 path...");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return pQueue;

	
	
	}

	
	
// ---------------------------------------------------Inplace Sort Queue Implementation----------------------------------------------------------------------
		// This function returns
		// index of minimum element from front to
		// sortIndex
	private static int minIndex(Queue<String> q, int sortIndex) {
		int min_index = -1;
		String min_value = "zzzzzzzzzzz";
		int s = q.size();
		for (int i = 0; i < s; i++) {
			String current = q.peek();

			// This is dequeue() in Java STL
			q.poll();

			// we add the condition i <= sortIndex
			// because we don't want to traverse
			// on the sorted part of the queue,
			// which is the right part.
			if (current.compareTo(min_value) <= 0 && i <= sortIndex) {
				min_index = i;
				min_value = current;
			}
			q.add(current);
		}
		return min_index;
	}

	// Moves given minimum element
	// to rear of queue
	private static void insertMinToRear(Queue<String> list, int min_index) {
		String min_value = "";
		int s = list.size();
		for (int i = 0; i < s; i++) {
			String current = list.peek();
			list.poll();
			if (i != min_index)
				list.add(current);
			else
				min_value = current;
		}
		list.add(min_value);
	}

	public static void sortQueue_Inplace(Queue<String> list) {
		for (int i = 1; i <= list.size(); i++) {
			int min_index = minIndex(list, list.size() - i);
			insertMinToRear(list, min_index);
		}
	}
 // -------------------------------------------------------------------------------------------------------------------------
	
	
// -----------------------------------------------Recursion Sort Queue Implementation------------------------------------------------------------------------------	
	
	// Function to push element in last by
		// popping from front until size becomes 0
		private static void FrontToLast(Queue<String> q, int qsize) {
			// Base condition
			if (qsize <= 0)
				return;

			// pop front element and push
			// this last in a queue
			q.add(q.peek());
			q.remove();

			// Recursive call for pushing element
			FrontToLast(q, qsize - 1);
		}

		// Function to push an element in the queue
		// while maintaining the sorted order
		private static void pushInQueue(Queue<String> q, String temp, int qsize) {

			// Base condition
			if (q.isEmpty() || qsize == 0) {
				q.add(temp);
				return;
			}

			// If current element is less than
			// the element at the front
			else if (temp.compareTo(q.peek()) <= 0) {

				// Call stack with front of queue
				q.add(temp);

				// Recursive call for inserting a front
				// element of the queue to the last
				FrontToLast(q, qsize);
			} else {

				// Push front element into
				// last in a queue
				q.add(q.peek());
				q.remove();

				// Recursive call for pushing
				// element in a queue
				pushInQueue(q, temp, qsize - 1);
			}
		}

		// Function to sort the given
		// queue using recursion
		public static void sortQueue_Rec(Queue<String> q) {

			// Return if queue is empty
			if (q.isEmpty())
				return;

			// Get the front element which will
			// be stored in this variable
			// throughout the recursion stack
			String temp = q.peek();

			// Remove the front element
			q.remove();

			// Recursive call
			sortQueue_Rec(q);

			// Push the current element into the queue
			// according to the sorting order
			pushInQueue(q, temp, q.size());
		}
//-----------------------------------------------------------------------------------------------------------------------------------
}
