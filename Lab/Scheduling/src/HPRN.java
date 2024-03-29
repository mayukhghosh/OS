import java.util.PriorityQueue;

public class HPRN {
	static PriorityQueue<Process> q = new PriorityQueue<Process>(Process.highratioComparator);
	public static void run(Process prc)
	{
		prc.c--;
	}
	public static void enqueue(Process prc)
	{
		q.add(prc);
	}
	public static Process dequeue()
	{
		return(q.remove());
	}
	public static boolean peek()
	{
		if(q.peek()!=null)
			return true;
		else
			return false;
	}

}
