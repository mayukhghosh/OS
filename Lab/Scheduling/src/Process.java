
import java.util.Comparator;
public class Process {
	int a;//arrival time
	int c;//Total CPU time
	int x;//storing copy of CPU time
	int b;//next CPU burst time
	int m;//used for calculating next I/O time
	int finish_time;
	int turnaround;//finish_time-a
	int io_time;//time in blocked time
	int wait_time;//time in ready state
	int blocked_burst;//time remaining in current blocked state

	boolean in_queue;//For FCFS and RR
	int cpu_left=0;//For RR
	int position;//Keeping track of position of position in list
	float T=0;//time the the process has been in the system,used in HPRN
	float t=0;//running time of process to date,used in HPRN
	float r;//penalty ratio, used in HPRN

	String state;

	Process(int a,int c,int b,int m)
	{
		this.a=a;
		this.c=c;
		this.b=b;
		this.m=m;
		this.x=c;
		this.state="unstarted";
	}
	/*public int compareTo(Process o) {
		if (this.a>o.a)
			return 1;
		else if(this.a<o.a) 
			return -1;
		else
			return 0;
	}*/

	public void calc_penalty()
	{
		r=T/Math.max(1,t);
	}

	public static Comparator<Process> ArrivalComparator = new Comparator<Process>() {

		public int compare(Process p1, Process p2) {
			if(p1.a>p2.a)
				return 1;
			else if(p1.a<p2.a)
				return -1;
			else
				return 0;
		}};

		public static Comparator<Process> remCPUComparator = new Comparator<Process>() {

			public int compare(Process p1, Process p2) {
				
					if(p1.c>=p2.c)
						return 1;
					else
						return -1;
					
				
			}};

			public static Comparator<Process> highratioComparator = new Comparator<Process>() {

				public int compare(Process p1, Process p2) {
					if(p1.r==p2.r)
					{
						if(p1.a!=p2.a)
						{
							if(p1.a>p2.a)
								return 1;
							else
								return -1;
						}
						else
						{
							if(p1.position>p2.position)
								return 1;
							else
								return -1;
						}
					}
					else
					{
						if(p1.r<p2.r)
							return 1;
						else
							return -1;
					}
				}};
				public static Comparator<Process> listposComparator = new Comparator<Process>() {

					public int compare(Process p1, Process p2) {
						if(p1.position<p2.position)
							return 1;
						else
							return -1;
					}};

					public String toString() {
						return a+" "+b+" "+c+" "+m;
					}
					public String toString1() {
						return a+" "+b+" "+x+" "+m;
					}
					public String toString2() {
						return a+" "+b+" "+x+" "+m+" "+position;
					}

}