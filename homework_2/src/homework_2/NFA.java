package homework_2;

import java.util.*;

import com.sun.javafx.css.StyleClassSet;


public class NFA {
	static int num = 0;
	public static class Node
	{
		int begin;
		String state;
		int end;
		boolean isnode = true;
	}
	static Stack<Node> stack = new Stack<Node>();
	static Stack<Node> queue = new Stack<Node>();
	static StringBuffer S = new StringBuffer();
	static Node SS = new Node() ;
	static ArrayList <  Node> sss = new ArrayList<Node> ();
	
	public static void GET_NFA()
	{
		
	StringBuffer STR = new StringBuffer();
	STR = regular.RPN;
	{
			for(int i = 0 ; i < STR.length() ; i++)
			{
				if(regular.isch(STR.charAt(i)))
				{
					Node node = new Node();
					node.begin = num;
					num++;
					node.state =""+ STR.charAt(i);
					node.end = num;
					num++;
					node.isnode = true;
					stack.push(node);
					queue.push(node);
//					System.out.println("¡¤:" +node.begin +"  " + node.end);
					
				}
				if(STR.charAt(i)=='¡¤')
				{
					Node node1 = new Node();
					Node node2 = new Node();
					node1 = stack.lastElement();
					stack.pop();
					node2 = stack.lastElement();
					stack.pop();
					
					Node node3 = new Node();
					node3.begin = node2.begin;
					node3.end = node1.end;
					node3.state="¦Å";
					node3.isnode = false;
					
					Node node4 = new Node();
					node4.begin = node2.end;
					node4.end = node1.begin;
					node4.state="¦Å";
					node4.isnode = true;
					
					queue.push(node4);
					stack.push(node3);
//					System.out.println("¡¤:" +node3.begin +"  " + node3.end);
					
				}
				if(STR.charAt(i)=='*')
				{
					Node node1 = new Node();
					Node node2 = new Node();
					Node node3 = new Node();
					Node node4 = new Node();
					Node node5 = new Node();
					Node node = new Node();
					node1 = stack.lastElement();
					stack.pop();
					node2.begin = node1.end;
					node2.end = node1.begin;
					node2.isnode = true;
					node2.state = "¦Å";
					
					node3.begin = node1.end;
					node3.end = num;
					num++;
					node3.isnode = true;
					node3.state = "¦Å";
					
					node4.begin = num;
					num++;
					node4.end = node1.begin;
					node4.isnode = true;
					node4.state = "¦Å";
					
					node5.begin = node4.begin;
					node5.end = node3.end;
					node5.isnode = true;
					node5.state = "¦Å";
					
					node.begin = node4.begin;
					node.end = node3.end;
					node.isnode = false;
					node.state = "¦Å";
					
					queue.push(node2);
					queue.push(node3);
					queue.push(node4);
					queue.push(node5);
					
					stack.push(node);
//					System.out.println("*:" +node.begin +"  " + node.end);
				}
				if(STR.charAt(i)=='|')
				{
					Node node1 = new Node();
					Node node2 = new Node();
					Node node3 = new Node();
					Node node4 = new Node();
					Node node5 = new Node();
					Node node6 = new Node();
					Node node  = new Node();
					
					node2 = stack.lastElement();
					stack.pop();
					node1 = stack.lastElement();
					stack.pop();
					
					node3.begin = node1.end;
					node3.end = num;
					num++;
					node3.isnode = true;
					node3.state = "¦Å";
					
					node4.begin = node2.end;
					node4.end = node3.end;
					node4.isnode = true;
					node4.state = "¦Å";
					
					node5.begin = num;
					num++;
					node5.end = node1.begin;
					node5.isnode = true;
					node5.state = "¦Å";
					
					node6.begin = node5.begin;
					node6.end = node2.begin;
					node6.isnode = true;
					node6.state = "¦Å";
					
					node.begin = node5.begin;
					node.end = node3.end;
					node.isnode = false;
					node.state = "¦Å";
					
					queue.push(node3);
					queue.push(node4);
					queue.push(node5);
					queue.push(node6);
		
					stack.push(node);
				}
			}
			
			
			while(!queue.isEmpty())
			{
				Node node = new Node();
				node = queue.lastElement();
				sss.add(node);
//				System.out.println("node.begin:" + node.begin + "    node .end:" +node.end);
				queue.pop();
			}
			
			
			
			
			
			
	}
	
		
	}
	}
