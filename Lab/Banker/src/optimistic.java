
public class optimistic {
	
	public void run()
	{
		while(true)
		{
			for(int i=0;i<Main.list.size();i++)
			{
				if(Main.list.get(i).terminated==false)
				{
					Main.list.get(i).parse_inst();
				}
			}
		}
	}

}