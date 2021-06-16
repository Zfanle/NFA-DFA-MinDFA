package homework_2;

import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.State;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class TO_minDFA {
	static ArrayList<String> head_LIST =  new ArrayList<String> (); 	 
	static int name_num = 0 ;
	static int sta_num = 0;
	public static class List
	{
		String head = new String();
		int name = 0;
		int state = 0 ;
		int NU[] = new int[regular.charstateString.length()];
		boolean isend = false;
	}
	public static class minDFA
	{
		int start;
		int end;
		String gotostateString = new String();
		boolean isend;
	}
	static ArrayList<minDFA> FinList = new  ArrayList<minDFA> ();

	static ArrayList<List> All_List = new  ArrayList<List> ();
	public static void run_tominfa() 
	{
		head_LIST = To_DFA.head_LIST;
		for(int i=0 ; i<head_LIST.size();i ++)
		{
			INIT(head_LIST.get(i));
		}
		
		for (int i = 0; i < All_List.size(); i++) {
		for(int j = 0 ; j < regular.charstateString.length();j++)
		{
			String S = new String();
			S=regular.charstateString.charAt(j)+"";
			for(int k = 0 ; k <To_DFA.sss.size();k++)
			{
				if(All_List.get(i).head.equals(To_DFA.sss.get(k).head_LIST)&& S.equals(To_DFA.sss.get(k).state))
				{
					String state_mid = new String();
					state_mid = To_DFA.sss.get(k).change_state;
					for(int p = 0 ; p < All_List.size(); p++)
					{
						if(All_List.get(p).head.equals(state_mid))
						{
							 All_List.get(i).NU[j] =  All_List.get(p).state;
						}
					}
				}
			}
		
		}
		}
		update();
		for (int i = 0; i < All_List.size(); i++) {
			System.out.println(All_List.get(i).head + "    " +sta_num);
			for(int j = 0 ; j < regular.charstateString.length() ; j++)
			{
				System.out.print(All_List.get(i).NU[j]+  " "  );
			}
		}
		show_list();
	}
	public static void INIT(String headlist) {
		
		List node = new List();
		node.head = headlist;
		node.name = name_num;
		name_num++;
		for(int j = 0 ; j < regular.charstateString.length();j++)
		{
			String S = new String();
			S=regular.charstateString.charAt(j)+"";
			for(int k = 0 ; k <To_DFA.sss.size();k++)
			{
				if(node.head.equals(To_DFA.sss.get(k).head_LIST)&& S.equals(To_DFA.sss.get(k).state)) {
					if(To_DFA.sss.get(k).isend)
					{
						node.state = 1;
						node.isend = true;
					}
					
				}
				
			}
		}
		int NU[] = new int[regular.charstateString.length()];
		for(int i = 0 ; i < regular.charstateString.length() ; i ++)
			NU[i] = -1;
		node.NU = NU;
		All_List.add(node);
		
	}
	public static void update() {
		boolean update_state = false;
		do {
			int state_num = 0;
			ArrayList<String> state = new ArrayList<String>();
			for(int i = 0 ; i < All_List.size() ; i++ )
			{
				String num = new String();
				ArrayList<Integer> numm = new ArrayList<Integer>();
				for(int k = 0 ; k <regular.charstateString.length();k++)
				{
					numm.add(All_List.get(i).NU[k]);
				}
//				System.out.println(All_List.get(i).NU);
				num = numm.toString();
				if(!state.contains(num))
				{
					state.add(num);
					System.out.println("num : "+num);
					state_num++;
				}
			}
			for (int i = 0; i < state.size(); i++) {
				System.out.println(state.get(i));
			}
			for(int i = 0 ; i < state_num ; i ++)
			{
				for(int j = 0 ; j < All_List.size() ; j ++)
				{
					if(All_List.get(j).NU.toString().equals(state.get(i)))
						All_List.get(j).state = i;
				}
			}
			if(sta_num != state_num)
				update_state = true;
			else {
				update_state = false;
			}
			sta_num = state_num;
			
			System.out.println(state_num);
			
		} while (update_state);
		for(int i = 0 ; i < All_List.size() ; i++)
		{
			for(int j = 0 ; j < All_List.size() ; j++)
			{
				if(All_List.get(i).isend)
				if(All_List.get(i).state == All_List.get(j).state)
				{
					All_List.get(j).isend = true;
				}
			}
		}
		
		
	}
//	public static boolean isend() {
//		
//		
//	}
	public static void show_list() {
		for(int i = 0 ; i < All_List.size() ; i++)
		{
			String a = All_List.get(i).head;
			int b = All_List.get(i).name;
			int c = All_List.get(i).state;
//			System.out.println(a+b+c);
//			for(int j = 0 ; j < regular.charstateString.length() ; j++)
//			{
//				System.out.println(All_List.get(i).NU[j]);
//			}
		}
		
		
		
			
			
		for(int k = 0 ; k <sta_num ; k ++)
				for(int i = 0 ; i < All_List.size() ; i++)
				{
					
					if(All_List.get(i).state == k)
					{
						for(int j = 0 ; j < regular.charstateString.length() ; j++)
						{
							minDFA nodeDfa = new minDFA();
							nodeDfa.start = k;
							nodeDfa.gotostateString = regular.charstateString.charAt(j)+"";
							nodeDfa.end = All_List.get(i).NU[j];
							nodeDfa.isend = All_List.get(i).isend;
							if(nodeDfa.end != -1)
							FinList.add(nodeDfa);
						}
						break;
					}
				}
				
			
			System.out.println(FinList.size());
		for(int i = 0 ; i < FinList.size() ; i++)
		{
			System.out.println(FinList.get(i).start);
			System.out.println(FinList.get(i).gotostateString);
			System.out.println(FinList.get(i).end);
			System.out.println(FinList.get(i).isend);


		}
		
		
	}
	
	
}
