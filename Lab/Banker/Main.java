import java.io.File;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args)throws Exception
	{
		input("input-04");
	}
	
	public static void input(String str_file)throws Exception
	{
		File file = new File(str_file); 
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine())
		{
			System.out.println(sc.nextLine());
		}
	}

}
