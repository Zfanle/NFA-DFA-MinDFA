package homework_2;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.*;
public class regular {
	
	//a*(abb)*b(b|a)* 
	public static	String S_in = new String();
//	a*|a|b*aab*
//	a*a|b*a·a·b
	static StringBuilder RE ;
	static StringBuffer charstateString = new StringBuffer(); 
	static StringBuffer RPN =new  StringBuffer();
	public static boolean isch(char ch)
	{
		if(ch<'a'||ch>'z')
			return false;
		else
			return true;		
	}
	public static boolean compare(char ch1 , char ch2)
	{
		if(ch1 == '·')
		{
			if(ch2 == '·' || ch2 =='|')
				return true;
			else return false;
		}
		if(ch1 == '*')
		{
			return true;
		}
		if(ch1=='|')
		{
			if(ch2 == '|')
				return true;
			else return false;
		}
		
		return false;
	
		
	}
	public static void GET_RE()
	{
		RE = new StringBuilder(S_in);
		int i = 0 ,j = 0;
		int len = RE.length();
		for(i= 1 ; i < len ; i++)
		{
			j = i - 1 ;
			if(isch(RE.charAt(i))&&(isch(RE.charAt(j)) || RE.charAt(j) == '*' || RE.charAt(j) == ')'))
					{
				RE.insert(i, "·");
				i++;
				len++;
					}
			if(RE.charAt(i) == '(' && (isch(RE.charAt(j)) ||  RE.charAt(j) == '*'|| RE.charAt(j) == ')'))
			{
				RE.insert(i, "·");
				i++;
				len++;
			}
					
		}
		
		System.out.println(RE);
	}
	public static void GET_RPN()
	{
		RPN =  new StringBuffer();
		Stack<Character> OPTR = new Stack<Character>();
		
		for(int i = 0 ; i < RE.length() ; i++)
		{
			//字母直接添加！
			if(isch(RE.charAt(i)))
			{
				char ch = RE.charAt(i);
				boolean istrue = true;
			for(int j = 0 ; j < charstateString.length() ; j ++)
			{
				if(charstateString.charAt(j) == ch)
					istrue = false;
			}
			if(istrue)
				charstateString.append(ch);
				RPN.append( ch) ;
				continue;
			}
			//符号添加
			else
			{
				
				//栈空或者为左括号直接入
				if(OPTR.isEmpty()||RE.charAt(i)=='(')
				{
					OPTR.push(RE.charAt(i));
					continue;

				}
				else
				{
					//右括号寻找左括号
					if(RE.charAt(i)==')')
					{
						while(true)
						{
							
							if(OPTR.lastElement() == '(')
							{
								OPTR.pop();
								break;
							}
							RPN.append( OPTR.lastElement()) ;								//p++;
							OPTR.pop();
							
						}
						continue;
					}
					
//					进行比较，若top优先级大，出栈，继续比较
					else {
						char ch1 = OPTR.lastElement();
						char ch2 = RE.charAt(i);
						if(compare(ch1,ch2))
						{
							
						while(compare(ch1,ch2))
						{
							RPN.append(ch1) ;
							OPTR.pop();							
						 {
								if(OPTR.isEmpty()||!compare(ch1,ch2))
									{
									OPTR.push(ch2);
									break;
									}
								else
								{
								
									{
									
										ch1 = OPTR.lastElement();
									}
//									catch(NoSuchElementException e)
//									{
										
//									}
								}
							}
							
							
							
						}
						if(!OPTR.isEmpty()&&!compare(ch1,ch2))
						{
						OPTR.push(ch2);
						
						}
						
						
						}
						else
						{
							
							OPTR.push(ch2);
							continue;
						}
						
					}
					
						
					}
				}
			}
		
		//栈内剩余出栈
//		System.out.println(OPTR);
		while(!OPTR.isEmpty())
		{
			RPN.append(OPTR.lastElement());
			OPTR.pop();		
		}
			
}

	public static void Show() 
	{
		Font font;
		font = new Font("楷体",Font.PLAIN,20);
		Frame frame = new Frame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		TextField text = new TextField(100);
		Label message = new Label("请输入正则表达式");
		Label message2 = new Label("逆波兰表达式：");
		Panel centerPanel = new Panel();
		Panel southPanel = new Panel();
		Button enter = new Button("测试");
		enter.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				S_in = text.getText();
				GET_RE();
				GET_RPN();
				NFA.GET_NFA();
				System.out.println(charstateString);
					
				To_DFA.TODFA();
				NFA_RUN.NFARUN();
				TO_minDFA.run_tominfa();
				DFA_RUN.DFARUN();
				message.setText("正则表达式为："+ RE.toString());
				message2.setText("逆波兰表达式为："+RPN.toString());
				
				
//				System.out.println("RE"+RE);
//				System.out.println("RPN"+RPN);
			}
		});
		
		Button enter0 = new Button("显示");
		
		enter0.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFrame frame_nfa = new JFrame();
				frame_nfa .setTitle("NFA");
				JFrame frame_dfa = new JFrame();
				frame_dfa .setTitle("minDFA");
				ImageIcon nfaimg = new ImageIcon("E:\\compiling\\homework_2\\src\\homework_2\\NFA.png");
				ImageIcon dfaimg = new ImageIcon("E:\\compiling\\homework_2\\src\\homework_2\\minDFA.png");

				JLabel imgLabel = new JLabel(nfaimg);
				JLabel imgLabel2 = new JLabel(dfaimg);
				frame_dfa.add(imgLabel2);
				frame_nfa.add(imgLabel);
				frame_nfa.setSize(1500, 500);
				frame_dfa.setSize(1500, 500);
				frame_nfa.setVisible(true);
				frame_dfa.setVisible(true);
			}
		});
				
		
		centerPanel.add(text);
		centerPanel.add(enter);
		centerPanel.add(enter0);
		centerPanel.setFont(font);
		message2.setFont(font);
		message.setFont(font);
//		southPanel.add(message1);
		southPanel.add(message2);
		frame.add(centerPanel,BorderLayout.CENTER);
		frame.add(message,BorderLayout.NORTH);
		frame.add(message2,BorderLayout.SOUTH);
		frame.setSize(1500, 500);
		frame.setTitle("正则表达式输入窗口");
		frame.setVisible(true);
	}
	
	public static void main(String[]args)
	{
		Show();
	
		//GraphVizTest.run();
	}
}
