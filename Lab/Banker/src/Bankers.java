
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Bankers {
	static Queue<Process> blocked_processes=new LinkedList<Process>();//storing blocked processes so that it is processed first
	static Queue<Process> temp=new LinkedList<Process>();
	static HashMap<Integer,Integer> just_blocked;
	static int cycle=0;
	public static void run()
	{
		
		int blocked_flag=0;
		int terminated_flag=0;
		while(true)
		{
			//System.out.println(cycle);
			cycle++;
			Main.temp=new int[Main.avl.length];
			just_blocked=new HashMap<>();
			blocked_flag=0;
			terminated_flag=0;
			//run blocked tasks first
			while(blocked_processes.size()!=0)
			{
				Process prc=blocked_processes.poll();
				if(prc.terminated==true)
					continue;
				String compute=prc.parse_inst(true);
				if(prc.blocked==true)
				{
					temp.add(prc);
					//System.out.println(prc.id+" added to blocked_processs");
				}
				if(compute.equals("")==false)
				{
					//increase_wait_time(compute);
				}
				just_blocked.put(prc.id,0);	
			}
			while(temp.size()!=0)
			{
				blocked_processes.add(temp.poll());
			}
			//run tasks which were not blocked
			for(int i=0;i<Main.list.size();i++)
			{
				if(Main.list.get(i).terminated==false && just_blocked.containsKey(Main.list.get(i).id)==false)
				{
					String compute=Main.list.get(i).parse_inst(true);
					if(Main.list.get(i).blocked==true)
					{

						blocked_processes.add(Main.list.get(i));
					}
					if(compute.equals("")==false)
					{
						//increase_wait_time(compute);
					}
				}
			}			
			

			//check if every process is terminated
			for(int i=0;i<Main.list.size();i++)
			{
				if(Main.list.get(i).terminated==true)
				{
					terminated_flag++;
				}
			}
			if(terminated_flag==Main.list.size())
			{
				break;
			}
			//making resources available in the next cycle
			for(int i=0;i<Main.avl.length;i++)
			{
				Main.avl[i]=Main.avl[i]+Main.temp[i];
			}
			//System.out.println("-------");
		}
		
		//printing statistics
		int total_wait=0;
		int total_time=0;
		System.out.println("BANKER'S");
		for(int i=0;i<Main.list.size();i++)
		{
			if(Main.list.get(i).aborted==true)
			{
				System.out.println("Task "+Main.list.get(i).id+"\t"+"aborted");
			}
			else
			{
				total_wait=total_wait+Main.list.get(i).wait;
				total_time=total_time+Main.list.get(i).total;
				double wait_ratio=Math.ceil(((double)(Main.list.get(i).wait)/(double)(Main.list.get(i).total)*100));
				System.out.println("Task "+Main.list.get(i).id+"\t"+Main.list.get(i).total+"\t"+Main.list.get(i).wait+"\t"+wait_ratio+"%");
			}
		}
		double total_wait_ratio=Math.ceil(((double)total_wait/(double)total_time*100));
		System.out.println("total "+"\t"+total_time+"\t"+total_wait+"\t"+total_wait_ratio+"%");

	}

	

}

