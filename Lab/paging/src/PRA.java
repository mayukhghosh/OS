public class PRA{

    public String alg="";
    public PRA(String alg)
    {
        this.alg=alg;
    }

    public int LRU()
    {
        int min=Integer.MAX_VALUE;
        int min_index=-1;
        for(int i=0;i<Main.frame_table.length;i++)
        {
            if(Main.frame_table[i].last_used<min)
            {
                min=Main.frame_table[i].last_used;
                min_index=i;
            }
        }
        System.out.println("Evicting page "+Main.frame_table[min_index].page+" of "+Main.frame_table[min_index].process_num+" from frame "+(min_index));
        return min_index;
    }
    public int LIFO()
    {
        System.out.println("Evicting page "+Main.frame_table[0].page+" of "+Main.frame_table[0].process_num+" from frame "+(0));
    	return 0;
    }
    public int random()
    {
    	int num=Main.gen_random();
        System.out.println("Evicting page "+Main.frame_table[num%Main.frame_table.length].page+" of "+Main.frame_table[num%Main.frame_table.length].process_num+" from frame "+(num%Main.frame_table.length));
    	return (num%Main.frame_table.length);
    }

    public void execute(int process,int page)
    {
        int index=-1;
        if(alg.equals("lru"))
        {
            index=LRU();
        }
        else if(alg.equals("lifo"))
        {
        	index=LIFO();
        }
        else if(alg.equals("random"))
        {
        	index=random();
        }
        //System.out.println(Main.frame_table[index].process_num);
        Main.list[Main.frame_table[index].process_num-1].eviction_time=Main.global_time;
		Main.list[Main.frame_table[index].process_num-1].evictions++;
		Main.list[Main.frame_table[index].process_num-1].residency_time=Main.list[Main.frame_table[index].process_num-1].residency_time+(Main.list[Main.frame_table[index].process_num-1].eviction_time-Main.list[Main.frame_table[index].process_num-1].loaded_time);
        System.out.println("Eviction Time: "+Main.list[Main.frame_table[index].process_num-1].eviction_time+" Loaded time: "+Main.list[Main.frame_table[index].process_num-1].loaded_time);
		System.out.println("Residency time: "+Main.frame_table[index].process_num+" : "+Main.list[Main.frame_table[index].process_num-1].residency_time);
		Main.frame_table[index].process_num=process;
        Main.frame_table[index].page=page;
        Main.frame_table[index].last_used=Main.global_time;
        Main.list[process-1].loaded_time=Main.global_time;
        Main.list[process-1].faults++;
        

    }
}