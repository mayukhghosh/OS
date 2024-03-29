import java.util.*;
//
public class Process {
	Queue<String> inst=new LinkedList<String>();//instructions for a task is stored which is executed in a FIFO manner
	Queue<String> comp=new LinkedList<String>();//Storing 'compute' instruction
	int id;
	int[] claim;
	int[] alloc;
	int[] need;
	int wait=0;
	int total=0;
	boolean aborted=false;
	boolean terminated=false;
	boolean blocked=false;
	
	
	Process(int res,int id)
	{
		claim=new int[res];
		alloc=new int[res];
		need=new int[res];
		this.id=id;
	}
	
	//releasing n-resources where res is type of resource
	public void release(int res, int n)
	{
		alloc[res]=alloc[res]-n;
		need[res]=need[res]+n;
		//Main.avl[res]=Main.avl[res]+n;
		Main.temp[res]=Main.temp[res]+n;
	}
	//releasing all resouorces
	public void release_all()
	{
		for(int i=0;i<alloc.length;i++)
		{
			//Main.avl[i]=Main.avl[i]+alloc[i];
			Main.temp[i]=Main.temp[i]+alloc[i];
			alloc[i]=0;
		}
	}
	// setting claim for each task(process)
	public void initiate(int res, int n)
	{
		claim[res]=n;
	}
	
	//some different checks when running bankers algorithm
	public void initiate_bankers(int res, int n)
	{
		if(n>Main.avl[res])
		{
			terminated=true;
			aborted=true;
			blocked=false;
			return;
		}
		else
		{
			claim[res]=n;
			need[res]=n;
		}
	}
	
	//requesting n-resources where res is type of resource
	public void request(int res, int n)
	{
		if(Main.avl[res]-n>=0)//checking to see if the requested resources are available
		{
			alloc[res]=alloc[res]+n;
			need[res]=need[res]-n;
			Main.avl[res]=Main.avl[res]-n;
			blocked=false;
		}
		else//block it
		{
			//System.out.println("Blocked: "+this.id);
			blocked=true;
			this.wait++;
		}
	}
	
	//some different checks when running bankers algorithm
	public void request_bankers(int res, int n)
	{
		if(n>claim[res] || n>need[res])
		{
			terminated=true;
			aborted=true;
			blocked=false;
			//System.out.println("Aborted: "+this.id);
			release_all();
			return;
		}
		if(Main.avl[res]-n>=0 && Main.avl[res]>=need[res])//checking to see if the requested resources are available
		{
			alloc[res]=alloc[res]+n;
			need[res]=need[res]-n;
			Main.avl[res]=Main.avl[res]-n;
			blocked=false;
		}
		/*else if(Main.avl[res]-n>=0 && Main.avl[res]<need[res])
		{
			terminated=true;
			aborted=true;
			blocked=false;
			release_all();
		}*/
		else//block it
		{
			//System.out.println("Blocked: "+this.id);
			blocked=true;
			this.wait++;
		}
	}
	//compute function 
	public void compute(int process, int cycles)
	{
		if(comp.isEmpty()==false)
		{
			comp.poll();
		}
		else
		{
			for(int i=0;i<cycles-1;i++)
			{
				comp.add("compute  "+process+" "+"0"+" 0");
			}
		}
		if(comp.isEmpty())
			inst.poll();
	}
	
	public void terminate()
	{
		terminated=true;
	}
	// parsing instructions in the 'inst' queue
	public String parse_inst(boolean bankers)
	{
		this.total++;
		String line=inst.peek();
		//System.out.println(line);
		//System.out.println(Arrays.toString(line.split("\\s+")));
		String activity=line.split("\\s+")[0];
		int process=Integer.parseInt(line.split("\\s+")[1]);
		int resource=Integer.parseInt(line.split("\\s+")[2]);
		int n_resources=Integer.parseInt(line.split("\\s+")[3]);
		
		
		if(activity.equals("initiate"))
		{
			if(bankers==true)
				initiate_bankers(resource-1,n_resources);
			else
				initiate(resource-1,n_resources);
			inst.poll();
		}
		if(activity.equals("request"))
		{
			if(bankers==true)
				request_bankers(resource-1,n_resources);
			else
				request(resource-1,n_resources);
			if(this.blocked==false)
			{
				inst.poll();
			}
		}
		if(activity.equals("release"))
		{
			release(resource-1,n_resources);
			inst.poll();
		}
		if(activity.equals("compute"))
		{
			compute(process,resource);
			return this.id+" "+resource;
		}
		if(activity.equals("terminate"))
		{
			this.total--;
			terminate();
			release_all();
			inst.poll();
		}
		return "";
	}
	
	public int get_req_res()
	{
		
		String line=inst.peek();
		String activity=line.split("\\s+")[0];
		int process=Integer.parseInt(line.split("\\s+")[1]);
		int resource=Integer.parseInt(line.split("\\s+")[2]);
		int n_resources=Integer.parseInt(line.split("\\s+")[3]);
		//System.out.println("line: "+line);
		//System.out.println("n_resources: "+n_resources);
		return n_resources;
	}
	public int get_req_res(int k)
	{
		
		String line=inst.peek();
		String activity=line.split("\\s+")[0];
		int process=Integer.parseInt(line.split("\\s+")[1]);
		int resource=Integer.parseInt(line.split("\\s+")[2]);
		int n_resources=Integer.parseInt(line.split("\\s+")[resource+2]);
		//System.out.println("line: "+line);
		//System.out.println("n_resources: "+n_resources);
		return n_resources;
	}
	
	

}
