package cop2805;

//Buddy Skinner
//April 3rd 2023
//CEN 3024C


import java.util.Random;

class Multithreading extends Thread {

private int[] arr;

private int low, high, partial;

public Multithreading(int[] arr, int low, int high)

{

	this.arr = arr;

	this.low = low;

	this.high = Math.min(high, arr.length);

}

public int getPartialSum()

{

	return partial;

}

public void run()

{

	partial = sum(arr, low, high);

}

public static int sum(int[] arr)

{

	return sum(arr, 0, arr.length);

}

public static int sum(int[] arr, int low, int high)

{

	int total = 0;

	for (int i = low; i < high; i++) {

		total += arr[i];

}

	return total;

}

public static int parallelSum(int[] arr)

{

	return parallelSum(arr, Runtime.getRuntime().availableProcessors());

}

public static int parallelSum(int[] arr, int threads)

{

	int size = (int) Math.ceil(arr.length * 1.0 / threads);

	Multithreading[] sums = new Multithreading[threads];

	for (int i = 0; i < threads; i++) {

		sums[i] = new Multithreading(arr, i * size, (i + 1) * size);

		sums[i].start();

}

try {

	for (Multithreading sum : sums) {

		sum.join();

}

} catch (InterruptedException e) { }

	int total = 0;

	for (Multithreading sum : sums) {

		total += sum.getPartialSum();

}

	return total;

}

}

public class Multithread_Test {

public static void main(String[] args)

{

	Random rand = new Random();

	int[] arr = new int[200000000];

	for (int i = 0; i < arr.length; i++) {

		arr[i] = rand.nextInt(100) + 1;

}

	long start = System.nanoTime();

	System.out.println("Sum: " + Multithreading.sum(arr));

	System.out.println("Single Thread in nanoseconds: " + (System.nanoTime() - start));

	start = System.nanoTime();

	System.out.println("Sum: " + Multithreading.parallelSum(arr));

	System.out.println("Parallel Thread in nanoseconds: " + (System.nanoTime() - start));

}

}
