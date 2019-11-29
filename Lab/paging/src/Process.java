
public class Process {
	double a;
	double b;
	double c;
	int next_ref_w=-1;
	int time=0;
	int evictions=0;
	int loaded=0;
	public Process(double a,double b, double c)
	{
		this.a=a;
		this.b=b;
		this.c=c;
	}

}
