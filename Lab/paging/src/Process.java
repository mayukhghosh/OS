
public class Process {
	double a;
	double b;
	double c;
	int next_ref_w=-1;
	int faults=0;
	int time=0;
	int evictions=0;
	//int eviction_time=0;
	//int loaded_time=0;
	int max_num_references;
	int residency_time=0;
	boolean terminated=false;
	public Process(double a,double b, double c,int max_num_references)
	{
		this.a=a;
		this.b=b;
		this.c=c;
		this.max_num_references=max_num_references;
	}
	
	public void display()
	{
		System.out.println(a+" "+b+" "+c);
	}

}
