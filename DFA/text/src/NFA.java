import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.locks.StampedLock;

public class NFA {
    public static class NfaState{
        public int index;
        public char input;
        public int chTrans;
        public HashSet<Integer> epTrans = new HashSet<Integer>();

        public NfaState() {
        }

        public NfaState(int index, char input, int chTrans, HashSet<Integer> epTrans) {
            this.index = index;
            this.input = input;
            this.chTrans = chTrans;
            this.epTrans = epTrans;
        }
    }

    public static class Nfa{
        public NfaState header = new NfaState();
        public NfaState tail = new NfaState();

        public Nfa() {
        }
    }

    public static NfaState[] NfaStates =new NfaState[200];
    public static int StatesNum=0;

    public static void CreatNfa(Nfa n){
        n.header = NfaStates[StatesNum++];
        n.tail = NfaStates[StatesNum++];
    }

    private static void init() {
        for (int i = 0; i < 200; i++) {
            NfaStates[i] = new NfaState();
            NfaStates[i].index = i;
            NfaStates[i].input = '#';
            NfaStates[i].chTrans = -1;
        }
    }

    public static void add(NfaState n1, NfaState n2, char ch){
        n1.input=ch;
        n1.chTrans = n2.index;
    }

    public static void add(NfaState n1, NfaState n2){
        n1.epTrans.add(n2.index);
    }

    public static Nfa FormatNFA(String re){
        Stack<Nfa> NfaStack = new Stack<Nfa>();
        init();
        for (int i = 0; i <re.length();i++){
            if (re.charAt(i) >='a'&&re.charAt(i)<='z'){
                Nfa n= new Nfa();
                CreatNfa(n);

                add(n.header,n.tail,re.charAt(i));

                NfaStack.push(n);
            }
            else if (re.charAt(i)=='*'){
                Nfa n1 = new Nfa();
                CreatNfa(n1);

                Nfa n2 = new Nfa();
                n2 = NfaStack.pop();

                add(n2.tail, n1.header);
                add(n2.tail, n1.tail);
                add(n1.header, n2.header);
                add(n1.header, n1.tail);

                NfaStack.push(n1);
            }
            else if (re.charAt(i) == '|'){
                Nfa n1 = new Nfa();
                Nfa n2 = new Nfa();

                n2 = NfaStack.pop();
                n1 = NfaStack.pop();

                Nfa n = new Nfa();
                CreatNfa(n);

                add(n.header, n1.header);				/*n的头通过ε指向n1的头*/
                add(n.header, n2.header);				/*n的头通过ε指向n2的头*/
                add(n1.tail, n.tail);				/*n1的尾通过ε指向n的尾*/
                add(n2.tail, n.tail);

                NfaStack.push(n);
            }

            else if (re.charAt(i) == '.') {
                Nfa n1 = new Nfa();
                Nfa n2 = new Nfa();
                n2 = NfaStack.pop();
                n1 = NfaStack.pop();
                add(n1.tail,n2.header);

                Nfa n = new Nfa();
                n.header=n1.header;
                n.tail=n2.tail;
                NfaStack.push(n);
            }

        }

        return NfaStack.peek();
    }

    public static String ShowNfa(Nfa nfa) {
        StringBuilder sb = new StringBuilder();

        String node = "node[shape=plaintext];\n"+
                      nfa.header.index+"[shape=circle];\n";
        sb.append(node);
        sb.append("\"\"->"+nfa.header.index+"[ label = start];\n");

        node = "size = \"60, 40\";\n" +
                "rankdir = LR;\n"+
                "fontname = \"Microsoft YaHei\";\n" +
                "fontsize = 10;\n" +
                "node [shape = circle, fontname = \"Microsoft YaHei\", fontsize = 10];\n" +
                "edge [fontname = \"Microsoft YaHei\", fontsize = 10];\n";
        sb.append(node);

        //sb.append(nfa.header.index+"[ shape = doublecircle ];\n");
        sb.append(nfa.tail.index + "[ shape = doublecircle ];\n");
        for (int i = 0; i < StatesNum;i++){
            if (NfaStates[i].input!='#'){
                sb.append(NfaStates[i].index+" -> "+NfaStates[i].chTrans+" [ label = \" "+NfaStates[i].input+"\" ];\n");
            }
            Iterator<Integer> iter = NfaStates[i].epTrans.iterator();
            while (iter.hasNext()){
                sb.append(NfaStates[i].index + " -> " + iter.next()+" [label = \" "+"ε"+"\" ];\n");
            }
        }
        return sb.toString();
    }

    public static void createDotGraph(String dotFormat,String fileName) {
        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        String type = "jpg";
        gv.increaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }

    public static Nfa StartNFA(String re){
        Nfa nfa = FormatNFA(re);
        String formatnfa = ShowNfa(nfa);
        createDotGraph(formatnfa, "NFA");
        return nfa;
    }

    /*public static void main(String[] args) {
        //String str = "ab*|c*.";
        String str = "ab.*a*b*|.ba.*.";
        StartNFA(str);
    }*/

}

