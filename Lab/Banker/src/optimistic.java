import java.util.*;
//Logic for the optimistic algorithm
public class optimistic {
	static Queue<Process> blocked_processes=new LinkedList<Process>();//storing blocked processes so that it is processed first
	static Queue<Process> temp=new LinkedList<Process>();
	static HashMap<Integer,Integer> just_blocked;
	public static void run()
	{
		int blocked_flag=0;
		int terminated_flag=0;
		while(true)
		{
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
				String compute=prc.parse_inst(false);
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
					String compute=Main.list.get(i).parse_inst(false);
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
			//make resources available next cycle
			for(int i=0;i<Main.avl.length;i++)
			{
				Main.avl[i]=Main.avl[i]+Main.temp[i];
			}
			//check if every process is blocked
			for(int i=0;i<Main.list.size();i++)
			{
				if(Main.list.get(i).terminated==false && Main.list.get(i).blocked==true)
				{
					blocked_flag++;
				}
			}
			//abort some tasks
			if(blocked_flag==Main.list.size())
			{
				check_abort_multiple1();
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
			
			//System.out.println("-------------");
		}

		int total_wait=0;
		int total_time=0;
		System.out.println("FIFO");
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
		System.out.println();

	}

	public static void check_abort()
	{
		int flag=0;
		for(int i=0;i<Main.list.size();i++)
		{
			System.out.println("---------");
			Main.list.get(i).release_all();
			Main.list.get(i).aborted=true;
			Main.list.get(i).terminated=true;
			Main.list.get(i).blocked=false;
			for(int j=i+1;j<Main.list.size();j++)
			{
				if(Main.avl[0]>=Main.list.get(j).get_req_res())
				{
					flag=1;
					break;
				}
			}
			if(flag==1)
				break;
		}
	}

	
	public static void check_abort_multiple1()
	{
		int flag=0;
		for(int i=0;i<Main.list.size();i++)
		{
			//System.out.println("---------");
			for(int p=0;p<Main.list.get(i).alloc.length;p++)
			{
				Main.avl[p]=Main.avl[p]+Main.list.get(i).alloc[p];
				Main.list.get(i).alloc[p]=0;
			}
			Main.list.get(i).aborted=true;
			Main.list.get(i).terminated=true;
			Main.list.get(i).blocked=false;
			//System.out.println("Aborted: "+Main.list.get(i).id);
			for(int j=i+1;j<Main.list.size();j++)
			{
				for(int k=0;k<Main.avl.length;k++)
				{
					if(Main.avl[k]<Main.list.get(j).get_req_res(k))
					{
						break;
					}
					else
						flag=1;
				}
				if(flag==1)
					break;
			}
			if(flag==1)
				break;
		}

	}
}
