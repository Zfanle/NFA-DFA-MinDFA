package homework_2;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import homework_2.NFA.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class NFA_RUN {
    public static void NFARUN() {
        GraphViz gViz=new GraphViz("E:\\compiling\\homework_2\\src\\homework_2", "C:\\Program Files\\Graphviz\\bin\\dot.exe");
        
        
        gViz.start_graph();
        
        for(int i = 0 ; i < NFA.sss.size() ; i++)
        {
        	NFA.Node node = new NFA.Node();
        	node = NFA.sss.get(i);
        	 gViz.addln(node.begin+"->"+node.end+"[label = " + node.state + "];");
//             System.out.println(A + "  " +B);
        	 gViz.addln(To_DFA.end + "[peripheries=2]");
        }
        gViz.end_graph();
        
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






class  GraphViz{
    private static String runPath = "";
    private static String dotPath = ""; 
    private static String runOrder="";
    private static String dotCodeFile="NFAcode.txt";
    private static String resultpng="NFA";
    private static  StringBuilder graph = new StringBuilder();

    static Runtime runtime=Runtime.getRuntime();

    public static void run() {
        File file=new File(runPath);
        file.mkdirs();
        writeGraphToFile(graph.toString(), runPath);
        creatOrder();
        try {
            runtime.exec(runOrder);
//            JFrame frame_nfa = new JFrame();
//			frame_nfa .setTitle("NFA");
//		//	JFrame frame_dfa = new JFrame();
//		//	frame_dfa .setTitle("minDFA");
//			ImageIcon nfaimg = new ImageIcon("E:\\compiling\\homework_2\\src\\homework_2\\NFA.png");
//			//ImageIcon dfaimg = new ImageIcon("E:\\compiling\\homework_2\\src\\homework_2\\minDFA.png");
//
//			JLabel imgLabel = new JLabel(nfaimg);
//		//	JLabel imgLabel2 = new JLabel(dfaimg);
//		//	frame_dfa.add(imgLabel2);
//			frame_nfa.add(imgLabel);
//			frame_nfa.setSize(1500, 500);
//		//	frame_dfa.setSize(1500, 500);
//			frame_nfa.setVisible(true);
//		//	frame_dfa.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void creatOrder(){
        runOrder+=dotPath+" ";
        runOrder+=runPath;
        runOrder+="\\"+dotCodeFile+" ";
        runOrder+="-T png ";
        runOrder+="-o ";
        runOrder+=runPath;
        runOrder+="\\"+resultpng+".png";

        System.out.println(runOrder);
    }

    public static void writeGraphToFile(String dotcode, String filename) {
        try {
            File file = new File(filename+"\\"+dotCodeFile);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(dotcode.getBytes("UTF-8"));
            fos.close();
        } catch (java.io.IOException ioe) { 
            ioe.printStackTrace();
        }
     }  

    public GraphViz(String runPath,String dotPath) {
        this.runPath=runPath;
        this.dotPath=dotPath;
    }

    public void add(String line) {
        graph.append("\t"+line);
    }

    public void addln(String line) {
        graph.append("\t"+line + "\n");
    }

    public void addln() {
        graph.append('\n');
    }

    public void start_graph() {
        graph.append("digraph G {\n") ;
        graph.append("rankdir = LR \n") ;

    }

    public void end_graph() {
        graph.append("}") ;
    }   
} 