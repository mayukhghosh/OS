import java.util.LinkedList;
import java.util.Queue;
//take care of case when two jobs become ready at the same time
public class RR {
	static int qnt=2;
	static int curr_qnt=qnt;//current quantum state
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
	public static void decrease_qnt()
	{
		curr_qnt--;
	}
	public static void reset_qnt()
	{
		curr_qnt=qnt;
	}

}
