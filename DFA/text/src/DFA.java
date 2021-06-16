import java.io.File;
import java.util.*;

public class DFA extends NFA{

    public static class Edge{

        public char input;
        public int Trans;
        public Edge() {}

    }

    public static class DfaState{

        public boolean isEnd;
        public int index;
        public HashSet<Integer> closure = new HashSet<Integer>();
        public int EdgeNum;
        public Edge[] Edges = new Edge[10];
        public DfaState() { }
    }

    public static class Dfa{

        public int startState;
        public HashSet<Integer> endStates = new HashSet<Integer>();
        public HashSet<Character> terminator = new HashSet<Character>();
        int [][] trans = new int[200][26];

        public Dfa() { }
    }

    public static DfaState[] dfaStates = new DfaState[200];

    public static int DfaStateNum = 0;

    public static void init(){
        for (int i = 0; i < 200; i++) {
            dfaStates[i] = new DfaState();
            dfaStates[i].index = i;
            dfaStates[i].isEnd = false;

            for (int j = 0; j < 10; j++) {
                dfaStates[i].Edges[j] = new Edge();
                dfaStates[i].Edges[j].input = '#';
                dfaStates[i].Edges[j].Trans = -1;
            }
        }
    }

    public static HashSet<Integer> epcloure( HashSet<Integer> s ){
        Stack<Integer> epStack = new Stack<>();

        for (Integer i : s) {
            epStack.push(i);
        }
        while (!epStack.isEmpty()){
            int temp = epStack.pop();
            for (Integer i : NfaStates[temp].epTrans) {
                if(!s.contains(i)){
                    s.add(i);
                    epStack.push(i);
                }
            }
        }
        return s;
    }

    public static boolean IsEnd(Nfa n,HashSet<Integer> s){
        for (Integer i : s) {
            if(i == n.tail.index) return true;
        }
        return false;
    }

    public static HashSet<Integer> moveEpCloure(HashSet<Integer> s,char ch){
        HashSet<Integer> temp = new HashSet<Integer>();
        for (Integer i : s) {
            if(NfaStates[i].input == ch){
                temp.add(NfaStates[i].chTrans);
            }
        }
        temp = epcloure(temp);
        return temp;

    }

    public static Dfa FormatDFA(Nfa n, String str){

        init();

        int i;
        Dfa d = new Dfa();
        HashSet<HashSet> states = new HashSet<>();

        for (int i1 = 0; i1 < 200; i1++) {
            Arrays.fill(d.trans[i1], -1);
        }

        for (i = 0; i < str.length(); i++){
            if(str.charAt(i) >='a'&&str.charAt(i)<='z'){
                d.terminator.add(str.charAt(i));
            }
        }

        d.startState = 0;

        HashSet<Integer> tempSet = new HashSet<>();
        tempSet.add(n.header.index);

        dfaStates[0].closure = epcloure(tempSet);
        dfaStates[0].isEnd = IsEnd(n,dfaStates[0].closure);
        states.add(dfaStates[0].closure);

        DfaStateNum++;

        Queue<Integer> que = new LinkedList<>();

        que.offer(d.startState);

        while (!que.isEmpty()){

            int num = que.poll();

            for (Character ch : d.terminator) {
                HashSet<Integer> temp = moveEpCloure(dfaStates[num].closure, ch);

                if (!states.contains(temp) && !temp.isEmpty()){
                    states.add(temp);
                    dfaStates[DfaStateNum].closure = temp;
                    dfaStates[num].Edges[dfaStates[num].EdgeNum].input = ch;
                    dfaStates[num].Edges[dfaStates[num].EdgeNum].Trans = DfaStateNum;
                    dfaStates[num].EdgeNum++;

                    d.trans[num][ch-'a'] = DfaStateNum;

                    dfaStates[DfaStateNum].isEnd = IsEnd(n,dfaStates[DfaStateNum].closure);

                    que.offer(DfaStateNum);
                    DfaStateNum++;
                }
                else {
                    for (i = 0; i < DfaStateNum; i++) {
                        if (temp.equals(dfaStates[i].closure)){
                            dfaStates[num].Edges[dfaStates[num].EdgeNum].input = ch;
                            dfaStates[num].Edges[dfaStates[num].EdgeNum].Trans =i;
                            dfaStates[num].EdgeNum++;

                            d.trans[num][ch-'a'] = i;
                            break;
                        }
                    }
                }
            }
        }

        for (i = 0; i < DfaStateNum; i++) {
            if(dfaStates[i].isEnd == true){
                d.endStates.add(i);
            }
        }



        return d;
    }

    public static String show(Dfa dfa){
        StringBuilder sb = new StringBuilder();

        String node = "node[shape=plaintext];\n"+
                dfa.startState +"[shape=circle];\n";
        sb.append(node);
        sb.append("\"\"->"+dfa.startState+"[ label = start];\n");

        node = "size = \"60, 40\";\n" +
                "rankdir = LR;\n"+
                "fontname = \"Microsoft YaHei\";\n" +
                "fontsize = 10;\n" +
                "node [shape = circle, fontname = \"Microsoft YaHei\", fontsize = 10];\n" +
                "edge [fontname = \"Microsoft YaHei\", fontsize = 10];\n";
        sb.append(node);

        for (Integer i1 : dfa.endStates) {
            sb.append(i1 + "[ shape = doublecircle ];\n");
        }

        for (int i = 0; i < DfaStateNum; i++) {
            for ( int j = 0; j < dfaStates[i].EdgeNum; j++) {
                sb.append(dfaStates[i].index+" -> "+dfaStates[i].Edges[j].Trans+" [ label = \" "+dfaStates[i].Edges[j].input+" \" ];\n");
            }
        }
        return sb.toString();
    }

    public static Dfa StartDFA(String re){
        Nfa nfa = StartNFA(re);
        Dfa dfa = FormatDFA(nfa,re);
        String formatdfa = show(dfa);
        createDotGraph(formatdfa, "DFA");
        return dfa;
    }

    /*public static void main(String[] args) {

        String re = "ab*|c*.";
        Nfa n = FormatNFA(re);
        Dfa dfa = FormatDFA(n,re);
        String format = show(dfa);
        createDotGraph(format, "DFA");


        *//*NFA nfa = new NFA();
        String str = "ab*|c*.";

        Nfa n = nfa.FormatNFA(str);

        Dfa dfa = FormatDFA(nfa, n, str);

        String format = show(dfa);

       // System.out.println(format);

        createDotGraph(format, "DFA");*//*

    }*/



}
