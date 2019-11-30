import java.io.File;
import java.util.Scanner;

public class Main {
	
	static int m;
	static int p;
	static int s;
	static int j;
	static int n;
	static String r;
	static Process[] list;
	static frame_node[] frame_table;
	static int[] random_numbers;
	static int random_index=0;
	static int global_time=0;
	static PRA obj;
	public static void main(String[] args)throws Exception {
		m=Integer.parseInt(args[0]);p=Integer.parseInt(args[1]);s=Integer.parseInt(args[2]);j=Integer.parseInt(args[3]);
		n=Integer.parseInt(args[4]);r=args[5];frame_table=new frame_node[m/p];
		
		File file = new File("random-numbers.txt"); 
		Scanner sc = new Scanner(file);
		int count=0;
		while(sc.hasNext())
		{
			sc.next();
			count++;
		}
		random_numbers=new int[count];
		sc = new Scanner(file);
		int i=0;
		while(i<count)
		{
			random_numbers[i]=sc.nextInt();
			i++;
		}
		
		initialize_processes(n);
		obj=new PRA(r);
		
		
		

	}
	
	public static void run()
	{
		for(int i=0;;i++)
		{
			int cnt=i%list.length;
			if(list[cnt].terminated==true)
				continue;
			//check if all processes are terminated
			if(check_termination()==true)
				break;
			for(int j=0;j<3;j++)
			{
				global_time++;
				if(list[cnt].time==0)
				{
					int curr_w=(111*(cnt+1))%s;
					int curr_ref_page=curr_w%p;
					use_free_frame(cnt+1,curr_ref_page);
					list[cnt].loaded_time=global_time;
					list[cnt].next_ref_w=next_ref(curr_w,cnt);
					list[cnt].time++;
				}
				else
				{
					int curr_w=list[cnt].next_ref_w;
					int curr_ref_page=curr_w%p;
					list[cnt].time++;
					if(check_frame_table(cnt+1,curr_ref_page)==-1)
					{
						//implement PRA
						Main.list[cnt].eviction_time=global_time;
						list[cnt].evictions++;
						list[cnt].residency_time=list[cnt].residency_time+(list[cnt].eviction_time-list[cnt].loaded_time);
						obj.execute(cnt+1, curr_ref_page);
						
					}
					else
					{
						frame_table[check_frame_table(cnt+1,curr_ref_page)].last_used=global_time;
					}
					list[cnt].next_ref_w=next_ref(curr_w,cnt);
					if(list[cnt].time==list[cnt].max_num_references)
					{
						list[cnt].terminated=true;
						break;
					}
				}
			}
		}
		for(int i=0;i<list.length;i++)
		{
			System.out.println("Process "+(i+1)+" had "+list[i].evictions+" faults and "+(list[i].residency_time/list[i].evictions)+" average residency");
		}


	}

	public static boolean check_termination()
	{
		for(int i=0;i<list.length;i++)
		{
			if(list[i].terminated==false)
				return false;
		}
		return true;
	}
	
	public static void use_free_frame(int process,int page)
	{
		for(int i=frame_table.length;i>=0;i--)
		{
			if(frame_table[i].free==true)
			{
				frame_table[i].process_num=process;
				frame_table[i].page=page;
				frame_table[i].free=false;
				break;
			}
		}
		
	}
	
	
	public static int check_frame_table(int process,int page)
	{
		for(int i=0;i<frame_table.length;i++)
		{
			if(frame_table[i].process_num==process && frame_table[i].page==page)
			{
				return i;
			}
		}
		return -1;
	}
	
	public static int next_ref(int word,int process)
	{
		
		double y=gen_random();
		
		double a=list[process].a;
		double b=list[process].b;
		double c=list[process].c;
		double res=0;
		if(y<a)
			return (word+1)%s;
		else if(y<(a+b))
			return (word-5)%s;
		else if(y<(a+b+c))
			return (word+4)%s;
		else if(y>=a+b+c)
		{
			res=gen_random();
			return 5;
		}
		return -1;
		
	}

	public static double gen_random()
	{
		int num=random_numbers[random_index];
		random_index++;
		double y=num/(Integer.MAX_VALUE+1d);

		return y;
	}

	
	public static void initialize_processes(int n)
	{
		if(j==1)
		{
			list=new Process[1];
			list[0]=new Process(1,0,0,n);
		}
		else if(j==2)
		{
			list=new Process[4];
			list[0]=new Process(1,0,0,n);
			list[1]=new Process(1,0,0,n);
			list[2]=new Process(1,0,0,n);
			list[3]=new Process(1,0,0,n);
		}
		else if(j==3)
		{
			list=new Process[4];
			list[0]=new Process(0,0,0,n);
			list[1]=new Process(0,0,0,n);
			list[2]=new Process(0,0,0,n);
			list[3]=new Process(0,0,0,n);
		}
		else if(j==4)
		{
			list=new Process[4];
			list[0]=new Process(0.75,0.25,0,n);
			list[1]=new Process(0.75,0,0.25,n);
			list[2]=new Process(0.75,0.125,0.125,n);
			list[3]=new Process(0.5,0.125,0.125,n);
		}
	}

}
