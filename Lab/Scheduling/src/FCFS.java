import java.util.LinkedList;
import java.util.Queue;

public class FCFS {
	
	static Queue<Process> q=new LinkedList<>();
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
