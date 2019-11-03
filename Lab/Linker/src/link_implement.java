import java.io.*;
import java.util.*;

public class link_implement {
	static int num_module;
	static int[] base;
	static String[] line_tot= {};
	static Map<String,Integer> sym=new HashMap<String,Integer>();
	static Map<String,Boolean> sym_use=new HashMap<String,Boolean>();
	static ArrayList<String> warn=new ArrayList<String>();
	static ArrayList<String> err=new ArrayList<String>();
	static BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args)throws Exception {

		String text=input_2();
		base=new int[num_module];
		//System.out.println("num: "+num_module);
		text=pass_1(text);
		System.out.println("\n\n\n");
		pass_2(text);
		
		
		
		
		System.out.println("\nSymbol Table");
		for (Object key: sym.keySet()) 
		{
			String key1=key.toString();
			System.out.println(key1+"="+sym.get(key1)); 
		}
		for (Object key: sym_use.keySet()) 
		{
			String key1=key.toString();
			if(sym_use.get(key1)==false)
			{
				int module=0;
				for(int j=0;j<base.length;j++)
					if(sym.get(key1)>base[j])
						module=j;
				warn.add("Warning: "+(key1)+" is defined in module "+(module+1)+" but never used.");
			}
		}	
		System.out.println();
		for(int i=0;i<warn.size();i++)
			System.out.println(warn.get(i));
		for(int i=0;i<err.size();i++)
			System.out.println(err.get(i));

	}


	public static String input_1()throws Exception
	{
		String text="";int count=0;num_module=0;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while (true)
		{
			line=input.readLine();
			if(line.equals(""))
				continue;
			if(line.charAt(0)==' ')
			{
				line=line.substring(1,line.length());
			}
			if(count==0)
			{
				num_module=Integer.parseInt(line.substring(0,1));
				count++;
				continue;
			}
			//System.out.println("*****"+line);
			text=text+line+'\n';
			count++;
			if((num_module*3)+1==(count))
			{
				break;
			}
		}		
		System.out.println("----------");
		System.out.println(text);
		return text;
	}

	public static String pass_1(String text)throws Exception
	{

		while(true)
		{
			sym=new HashMap<String,Integer>();
			sym_use=new HashMap<String,Boolean>();
			err=new ArrayList<String>();
			
			int flag=0;
			try 
			{
				base[0]=0;
				Scanner sc=new Scanner(text);
				Map<String,Integer> module_sym=new HashMap<String,Integer>();
				for(int i=0;i<num_module*3;i++)
				{
					int module_no=i/3;
					String line=sc.nextLine();
					//System.out.println("line: "+line);
					if((i%3)==0 && Integer.parseInt(line.substring(0,1))!=0)
					{
						int pairs=Integer.parseInt(line.substring(0,1));
						module_sym=new HashMap<String,Integer>();
						String[] symbols=line.substring(2,line.length()).split(" ");
						//System.out.println(Arrays.toString(symbols));
						for(int j=0;j<pairs*2;j=j+2)
						{
							if(sym.containsKey(symbols[j]))
								err.add("Error: "+symbols[j]+" is defined in multiple places; first value used.");
							else
							{
								module_sym.put(symbols[j], Integer.parseInt(symbols[j+1]));
								sym.put(symbols[j], base[module_no]+Integer.parseInt(symbols[j+1]));
								sym_use.put(symbols[j], false);
							
							}
						}
					}
					else if((i%3)==0 && Integer.parseInt(line.substring(0,1))==0)
					{
						module_sym=new HashMap<String,Integer>();
					}
					else if((i%3)==2)
					{
						if(!module_sym.isEmpty())
						{
							for (Object key: module_sym.keySet()) 
							{
								String key1=key.toString();
								if(module_sym.get(key1)>=Integer.parseInt(line.split(" ")[0]))
								{
									sym.put(key1, base[module_no]+0);
									//System.out.println("********"+line);
									//System.out.println("********"+key1+": "+module_sym.get(key1));
									err.add("Error: The definition of "+key1+" is outside "+module_no+"; zero(relative) is used.");
								}
							}
						}
						if(module_no+1>=num_module)
							continue;
						base[module_no+1]=base[module_no]+Integer.parseInt(line.split(" ")[0]);
					}
				}
			}
			catch(Exception e)
			{
				flag=1;
				//System.out.println("Error in pass_1()");
				text=input_2();
			}
			if(flag==0)
				break;
		}


		/*for (Object key: sym.keySet()) 
		{
			String key1=key.toString();
			System.out.println("key : "+key1+" Value in Dictionary : " + sym.get(key1)); 
		}		
		System.out.println(Arrays.toString(base));*/
		return text;

	}
	
	public static void pass_2(String text)
	{
		System.out.println("Memory Map");
		Scanner sc=new Scanner(text);
		String line="";String[] add_list= {};String[] use_list= {};
		for(int i=0;i<num_module*3;i++)
		{
			line=sc.nextLine();
			int module_no=i/3;
			if(i%3==1)
			{
				if(Integer.parseInt(line.split(" ")[0])!=0)
				{
					use_list=line.substring(line.indexOf(" "),line.length()).trim().split(" ");
					//System.out.println("use_list: "+Arrays.toString(use_list));
				}
				else
				{
					use_list= new String[0];
				}
			}
			if(i%3==2)
			{
				add_list=line.substring(line.indexOf(" "),line.length()).trim().split(" ");
				//System.out.println("add_list: "+Arrays.toString(add_list));
			}
			if(i%3==2)
				resolve(use_list,add_list,module_no);
		}
	}

	
	public static void resolve(String[] use_list,String[] add_list,int module_no)
	{
		boolean[] ext_use=new boolean[add_list.length];
		int[] abs_add=new int[add_list.length];
		for(int i=0;i<use_list.length;i=i+2)
		{
			String symbol=use_list[i];
			int rel_add=Integer.parseInt(use_list[i+1]);
			while(true)
			{
				if(Integer.parseInt(add_list[rel_add].substring(4,5))==1)
				{
					err.add("Error: Immediate address on use list for "+symbol+" in module "+(module_no+1)+"; treated as External.");
				}
				ext_use[rel_add]=true;
				if(sym.containsKey(symbol))
				{
					abs_add[rel_add]=(Integer.parseInt(add_list[rel_add])/10000)*1000+sym.get(symbol);
					sym_use.put(symbol, true);
				}
				else
				{
					abs_add[rel_add]=(Integer.parseInt(add_list[rel_add])/10000)*1000;
					err.add("Error: "+symbol+" was used but not defined, zero used.");
				}
				rel_add=Integer.parseInt(add_list[rel_add].substring(1,4));
				if(rel_add==777)
					break;				
			}
			
		}
		for(int j=0;j<add_list.length;j++)
		{
			if(Integer.parseInt(add_list[j])%10==4 && ext_use[j]==true)
				continue;
			else if(Integer.parseInt(add_list[j])%10==4 && ext_use[j]==false)
			{
				err.add("Error: E type address not on use chain; treated as I type in module "+(module_no+1));
				abs_add[j]=Integer.parseInt(add_list[j])/10;
			}
			else if(Integer.parseInt(add_list[j])%10==3)
			{
				abs_add[j]=(Integer.parseInt(add_list[j])/10)+base[module_no];
			}
			else if(Integer.parseInt(add_list[j])%10==2 || Integer.parseInt(add_list[j])%10==1)
			{
				abs_add[j]=Integer.parseInt(add_list[j])/10;
			}
		}
		//System.out.println("Absolute address: "+Arrays.toString(abs_add));

		for(int i=0;i<abs_add.length;i++)
			System.out.println(base[module_no]+i+": "+abs_add[i]);
	}
	
	public static String input_2()throws Exception
	{
		
	
		String curr="";String[] line;String text="";
		while(true)
		{
			curr=input.readLine();
			if(curr.equals(""))
				continue;
			curr=curr.trim();
			line=curr.split("\\s+");
			line_tot=combine(line_tot,line);
			//System.out.println(Arrays.toString(line_tot));
			num_module=Integer.parseInt(line_tot[0]);
			text=line_tot_parse(line_tot);	
			//System.out.println(text);
			//System.out.println("-------------");
			if(!text.equals(""))
			{
				//System.out.println("Final: "+text);
				return text;
			}
		}
	}

	public static String line_tot_parse(String[] tot)
	{
		String text="";int position=1;
		while(position<tot.length)
		{
			try 
			{
				text=text+var_define_use(tot,position)+"\n";
				//System.out.println("text1- "+text);
				position=position+Integer.parseInt(tot[position])*2+1;
				//System.out.println("position1- "+position);
				text=text+var_define_use(tot,position)+"\n";
				//System.out.println("text2- "+text);
				position=position+Integer.parseInt(tot[position])*2+1;
				//System.out.println("position2- "+position);
				text=text+var_use(tot,position)+"\n";
				//System.out.println("text3- "+text);
				position=position+Integer.parseInt(tot[position])+1;
				//System.out.println("position3- "+position);
			}
			catch(Exception e)
			{
				return "";
			}
		}
		return text;
	}
	public static String var_define_use(String[] line_tot, int start)
	{
		String text="";

		int end=start+Integer.parseInt(line_tot[start])*2;
		for(int i=start;i<=end;i++)
		{
			text=text+" "+line_tot[i];
		}

		return text.trim();
	}
	public static String var_use(String[] line_tot,int start)
	{
		String text="";

		int end=start+Integer.parseInt(line_tot[start]);
		for(int i=start;i<=end;i++)
		{
			text=text+" "+line_tot[i];
		}

		return text.trim();
	}
	public static String[] combine(String[] a, String[] b){
		int length = a.length + b.length;
		String[] result = new String[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
}
