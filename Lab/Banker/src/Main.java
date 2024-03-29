import java.io.*;
import java.util.*;

public class Main {
	
	static int[] avl;
	static int[] temp;
	static ArrayList<Process> list;
	public static void main(String[] args)throws Exception
	{
		String input_file="input-07";
		list=new ArrayList<Process>();
		input(input_file);
		optimistic.run();
		list=new ArrayList<Process>();
		input(input_file);
		Bankers.run();
	}
	
	public static void input(String str_file)throws Exception
	{
		File file=new File(str_file);
		Scanner sc=new Scanner(file);
		String init=sc.nextLine();
		int n_processes=Integer.parseInt(init.split(" ")[0]);
		int n_resources=Integer.parseInt(init.split(" ")[1]);
		avl=new int[n_resources];
		//initiating available resources
		for(int i=0;i<n_resources;i++)
		{
			avl[i]=Integer.parseInt(init.split(" ")[i+2]);
		}
		/*System.out.println(n_processes);
		System.out.println(n_resources);
		System.out.println(Arrays.toString(avl));*/
		
		//creating processes(tasks)
		for(int i=0;i<n_processes;i++)
		{
			list.add(new Process(n_resources,i+1));
		}
		int flag=0;	
		//inserting instructions for each task
		while(sc.hasNextLine())
		{
			String line=sc.nextLine();
			if(line.equals(""))
				continue;
			int process=Integer.parseInt(line.split("\\s+")[1]);
			//System.out.println(process);
			list.get(process-1).inst.add(line);
			//System.out.println(line);
		}
		
	}
	
	
	
	

}
