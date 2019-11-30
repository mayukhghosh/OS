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
        return min_index;
    }

    public void execute(int process,int page)
    {
        int index=-1;
        if(alg.equals("lru"))
        {
            index=LRU();
        }
        Main.frame_table[index].process_num=process;
        Main.frame_table[index].page=page;
        Main.frame_table[index].last_used=Main.global_time;
        Main.list[process-1].loaded_time=Main.global_time;

    }
}