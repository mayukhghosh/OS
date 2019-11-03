import java.util.*;

public class Process {
	Queue<String> inst=new LinkedList<String>();
	int[] claim;
	int[] alloc;
	int[] need;
	int wait=0;
	int total=0;
	boolean terminated=false;
	
	
	Process(int res)
	{
		claim=new int[res];
		alloc=new int[res];
		need=new int[res];
	}
	
	//releasing n-resources where res is type of resource
	public void release(int res, int n)
	{
		alloc[res]=alloc[res]-n;
		Main.avl[res]=Main.avl[res]+n;
	}
	
	public void initiate(int res, int n)
	{
		claim[res]=n;
	}
	public void request(int res, int n)
	{
		alloc[res]=alloc[res]+n;
		need[res]=need[res]-n;
		Main.avl[res]=Main.avl[res]-n;
	}
	
	public void terminate()
	{
		terminated=false;
	}
	
	public void parse_inst()
	{
		String line=inst.poll();
		String activity=line.split("\\+")[0];
		int process=Integer.parseInt(line.split("\\+")[1]);
		int resource=Integer.parseInt(line.split("\\+")[2]);
		int n_resources=Integer.parseInt(line.split("\\+")[3]);
		if(activity.equals("initiate"))
		{
			initiate(resource-1,n_resources);
		}
		if(activity.equals("request"))
		{
			request(resource-1,n_resources);
		}
		if(activity.equals("release"))
		{
			release(resource-1,n_resources);
		}
		if(activity.equals("terminate"))
		{
			terminate();
		}
	}
	
	

}