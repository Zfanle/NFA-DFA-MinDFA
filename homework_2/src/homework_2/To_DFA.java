package homework_2;

import java.util.ArrayList;
import java.util.Stack;

import com.sun.org.apache.bcel.internal.generic.ISTORE;

import homework_2.NFA.Node;
import jdk.nashorn.internal.ir.Flags;

public class To_DFA {
		static int end;
		static int addnum = 0;
		static int begin;
		static int num0 = NFA.num;
		static boolean isend[] = new boolean[num0];
		static boolean isbegin[] = new boolean[num0];
		static Stack<Integer> stack = new Stack<Integer>();
		static StringBuilder state_str[] = new StringBuilder[26];
//		static ArrayList<String[]> LIST =  new ArrayList<String[]> (); 
		static ArrayList<String> head_LIST =  new ArrayList<String> (); 
		
		public static class DFA_NODE{
//			ArrayList<Integer> head_LIST =  new ArrayList<Integer> (); 
			String head_LIST =  new String (); 
			String state;
			String change_state =  new String(); 
			boolean isend = false;
			int statenum ;
		}
		static ArrayList <DFA_NODE> sss = new ArrayList<DFA_NODE> ();
		public static boolean get_isend(ArrayList<Integer> S ) {
			boolean is = false;
			for(int i = 0 ; i < S.size() ; i ++)
			{
//				System.out.println(S.get(i) + "  "  + S.get(i)+ " " +end);
				if((int)S.get(i) == end)
					is = true;
			}
			return is;
		}
		public static void get_list(ArrayList<Integer> list) {
			
			StringBuffer s = new StringBuffer();
			s=regular.charstateString;
			for(int j = 0 ; j < s.length() ; j ++)
			{
				DFA_NODE node = new DFA_NODE();
				node.head_LIST = list.toString();
				node.state = ""+s.charAt(j);
				node.change_state = getchang_node(node.state, list);
				node.isend = get_isend(list);
				sss.add(node);
				
			
			}
			
		}
		public static void TODFA() {
			get_head();
			System.out.println(end + "   " +begin);
			get_nulllink(begin);
//			LIST.add(state_str);
			
			
			for(int i = 0 ; i < sss.size() ; i ++)
			{
				DFA_NODE node = new DFA_NODE();
				node = sss.get(i);
				System.out.println(" " +node.head_LIST + " "  +node.state +" "+node.change_state + " " + node.isend);
			}
//			System.out.println(head_LIST.size());
//			System.out.println(head_LIST);
//			
		}
		public static void set_false() {
			for(int i = 0 ; i < NFA.num ; i++)
			{
				isbegin[i] = true;
				isend[i] = true;
			}
		}
		public static void get_head() {
			set_false();
			for(int i = 0 ; i < NFA.sss.size() ; i++)
	        {
	        	NFA.Node node = new NFA.Node();
	        	node = NFA.sss.get(i);
	        	isbegin[node.end] = false;
	        	isend[node.begin] = false;
//	        	for(int i1 = 0 ; i1 < NFA.num ; i1++)
//				{
//					isbegin[i1] = true;
//					isend[i1] = true;
//				}
	        }
			for(int i = 0 ; i < NFA.num ; i++)
			{

				if(isend[i])
					end = i;
				if(isbegin[i])
					begin = i;
					
			}
			
		} 
		public static void get_nulllink(int a ) {
			ArrayList<Integer> list =  new ArrayList<Integer> (); 
				stack.push(a);
				while(!stack.isEmpty())
				{
					int num = stack.lastElement();
					stack.pop();
					list.add(num);
//					System.out.print(num);
					for(int i = 0 ; i < NFA.sss.size() ; i++)
			        {	
						NFA.Node node = new NFA.Node();
			        	node = NFA.sss.get(i);
			        	if( num == node.begin && node.state == "¦Å")
			        	{
			        		stack.push(node.end);
			        	}
			        }
		        	
				}
				
				for(int aa = 0 ; aa < list.size() ; aa++)
				{
					for(int bb =aa+1 ; bb<list.size() ;bb++)
					{
						if(list.get(aa)> list.get(bb))
						{
							int cc=list.get(aa);
							list.set(aa, list.get(bb)) ;
							list.set(bb, cc) ;
							
						}
						if(list.get(aa)== list.get(bb))
							list.remove(bb);
					}
				}
				if(!head_LIST.contains(list.toString()) && !list.isEmpty())
				{
//					list.sort(null);

					head_LIST.add(list.toString());
					get_list(list);
					StringBuffer s = new StringBuffer();
					s=regular.charstateString;
					for(int i = 0 ; i < s.length() ; i ++)
					{
						get_charlink(s.charAt(i)+"",list);

					}
				}
				

				
			
		
		}
		public static void get_charlink(String S,ArrayList<Integer> List) {
			boolean returnstate = true;
//			System.out.println(S);
			ArrayList<Integer> list =  new ArrayList<Integer> (); 
			for(int i = 0 ; i < List.size() ; i++)
			{
					int ch = List.get(i);
				
					stack.push(ch);
					int num = stack.lastElement();
//					System.out.println(num + " --num");
					stack.pop();
					for(int j = 0 ; j < NFA.sss.size() ; j++)
			        {	
						NFA.Node node = new NFA.Node();
			        	node = NFA.sss.get(j);
//		        		System.out.println(node.begin + node.state);

			        	if( num == node.begin && node.state.equals(S))
			        	{
			        		stack.push(node.end);
			        	}
			        }
			}
					
					while(!stack.isEmpty())
					{
					    int num = stack.lastElement();
						stack.pop();
						list.add(num);
						for(int j = 0 ; j < NFA.sss.size() ; j++)
				        {	
							NFA.Node node = new NFA.Node();
				        	node = NFA.sss.get(j);
				        	if( num == node.begin && node.state == "¦Å")
				        	{
				        		stack.push(node.end);
				        	}
				        }
			        	
					}
					
					for(int aa = 0 ; aa < list.size() ; aa++)
					{
						for(int bb =aa+1 ; bb<list.size() ;bb++)
						{
							if(list.get(aa)> list.get(bb))
							{
								int cc=list.get(aa);
								list.set(aa, list.get(bb)) ;
								list.set(bb, cc) ;
								
							}
							if(list.get(aa)== list.get(bb))
								list.remove(bb);
						}
					}
					if(!head_LIST.contains(list.toString()) && !list.isEmpty())					
					{
						returnstate = false;
//						list.sort(null);
						head_LIST.add(list.toString());
						get_list(list);
						StringBuffer s = new StringBuffer();
						s=regular.charstateString;
						for(int j = 0 ; j< s.length() ; j ++)
						{
							get_charlink(s.charAt(j)+"",list);

						}
					}
					
		        	
				
			
			if(returnstate)
				return;
			
		}
		public static String getchang_node(String S , ArrayList<Integer> List)
		{
			ArrayList<Integer> list =  new ArrayList<Integer> (); 
			for(int i = 0 ; i < List.size() ; i++)
			{
					int ch ;
					ch = List.get(i);
//				System.out.println(ch);
					stack.push(ch);
					int num = stack.lastElement();
//					System.out.println(num + " --num");
					stack.pop();
					for(int j = 0 ; j < NFA.sss.size() ; j++)
			        {	
						NFA.Node node = new NFA.Node();
			        	node = NFA.sss.get(j);
//		        		System.out.println(node.begin + node.state);

			        	if( num == node.begin && node.state.equals(S))
			        	{
			        		stack.push(node.end);
			        	}
			        }
					
					while(!stack.isEmpty())
					{
					    num = stack.lastElement();
						stack.pop();
						list.add(num);
						for(int j = 0 ; j < NFA.sss.size() ; j++)
				        {	
							NFA.Node node = new NFA.Node();
				        	node = NFA.sss.get(j);
				        	if( num == node.begin && node.state == "¦Å")
				        	{
				        		stack.push(node.end);
				        	}
				        }
			        	
					}
					
			}
			for(int aa = 0 ; aa < list.size() ; aa++)
			{
				for(int bb =aa+1 ; bb<list.size() ;bb++)
				{
					if(list.get(aa)> list.get(bb))
					{
						int cc=list.get(aa);
						list.set(aa, list.get(bb)) ;
						list.set(bb, cc) ;
						
					}
					if(list.get(aa)== list.get(bb))
						list.remove(bb);
				}
			}
			return list.toString();
		}

}
