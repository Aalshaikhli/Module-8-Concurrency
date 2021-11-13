//improtation of Random
import java.util.Random;

class Sumation extends Thread {

private int[] arry;

private int low, high, patial;

public Sumation(int[] arry, int low, int high)

{

this.arry = arry;

this.low = low;

this.high = Math.min(high, arry.length);

}

public int getPatialSum()

{

return patial;

}

public void run()

{
//finding the patial sum
patial = sum(arry, low, high);

}

public static int sum(int[] arry)

{

return sum(arry, 0, arry.length);

}

public static int sum(int[] arry, int low, int high)

{

int total = 0;

for (int k = low; k < high; k++) {

total += arry[k];

}

return total;

}

public static int paralelSum(int[] arry)

{

return paralelSum(arry, Runtime.getRuntime().availableProcessors());

}

public static int paralelSum(int[] arry, int threads)

{

int size = (int) Math.ceil(arry.length * 1.0 / threads);

Sumation[] sums = new Sumation[threads];

for (int k = 0; k < threads; k++) {

sums[k] = new Sumation(arry, k * size, (k + 1) * size);

sums[k].start();

}

try {

for (Sumation sum : sums) {

sum.join();

}

} catch (InterruptedException e) { }

int total = 0;

for (Sumation sum : sums) {

total += sum.getPatialSum();

}

return total;

}

}
//main class
public class Main {

public static void main(String[] args)

{

Random rand = new Random();
//initialize array of 200 million no.

int[] arry = new int[200000000];
//taking the numbers from 1 to 10
for (int k = 0; k < arry.length; k++) {

arry[k] = rand.nextInt(10) + 1;

}
//the outputs to be displayed

long start = System.currentTimeMillis();

System.out.println(Sumation.sum(arry));

System.out.println("Single: " + (System.currentTimeMillis() - start));

start = System.currentTimeMillis();

System.out.println(Sumation.paralelSum(arry));

System.out.println("Parallel: " + (System.currentTimeMillis() - start));

}

}