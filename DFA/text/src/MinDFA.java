import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class MinDFA extends DFA {

    public static class stateSet{
        int index;
        HashSet<Integer> set = new HashSet<Integer>();
    }

    public static HashSet<Integer>[] ss = (HashSet<Integer>[])new HashSet[200];
    //public static HashSet<HashSet> ss = new HashSet<HashSet>();

    public static DfaState[] minDfaStates = new DfaState[200];
    public static int minDfaStateNum = 0;

    public static void initss(){
        for (int i = 0; i < 200; i++) {
            ss[i] = new HashSet<Integer>();
        }
    }

    public static void initmindfaState(){
        for (int i = 0; i < 200; i++) {
            minDfaStates[i] = new DfaState();
            for (int j = 0; j < 10; j++) {
                minDfaStates[i].Edges[j] = new Edge();
            }
        }
    }

    private static int findSetNum(int count, int n) {
        for (int i = 0; i < count; i++){
            if(ss[i].contains(n)){
                return i;
            }
        }
        return -1;
    }

    public static HashSet<Integer>[] s = (HashSet<Integer>[])new HashSet[200];

    public static void inits(){
        for (int i = 0; i < 200; i++) {
            s[i] = new HashSet<Integer>();
        }
    }

    public static Dfa minDFA(Dfa dfa){

        int i,j;

        initss();
        initmindfaState();

        Dfa minDfa = new Dfa();
        minDfa.terminator = dfa.terminator;

        for (i = 0; i <200;i++){
            Arrays.fill(minDfa.trans[i],-1);
        }

        boolean endFlag = true;
        for (i = 0; i <DfaStateNum;i++){
            if (dfaStates[i].isEnd == false){

                endFlag = false;
                minDfaStateNum = 2;
                ss[1].add(dfaStates[i].index);      //
            }
            else {
                ss[0].add(dfaStates[i].index);
            }
        }

        if (endFlag) {
            minDfaStateNum=1;
        }

        boolean cutFlag = true;

        while (cutFlag){

            int cutCount = 0;

            for (i = 0; i < minDfaStateNum; i++) {

                for (Character ch : dfa.terminator) {

                    int setNum =0;
                    stateSet[] temp = new stateSet[20];

                    for (int i1 = 0; i1 < 20; i1++) {
                        temp[i1] = new stateSet();
                    }

                    for (Integer it : ss[i]) {

                        boolean epFlag = true;


                        for (j = 0; j < dfaStates[it].EdgeNum; j++) {

                            if(dfaStates[it].Edges[j].input == ch){

                                epFlag = false;

                                int transNum = findSetNum(minDfaStateNum,dfaStates[it].Edges[j].Trans);

                                int curSetNum = 0;
                                while ((temp[curSetNum].index != transNum) && (curSetNum < setNum)){
                                    curSetNum++;
                                }

                                if (curSetNum == setNum){
                                    temp[setNum].index = transNum;
                                    temp[setNum].set.add(it);

                                    setNum++;
                                }
                                else{
                                    temp[curSetNum].set.add(it);
                                }
                            }
                        }

                        if (epFlag){

                            int curSetNum = 0;
                            while ((temp[curSetNum].index != -1) && (curSetNum<setNum)){
                                curSetNum++;
                            }

                            if (curSetNum == setNum){
                                temp[setNum].index = -1;
                                temp[setNum].set.add(it);
                                setNum++;
                            }
                            else {
                                temp[curSetNum].set.add(it);
                            }
                        }
                    }

                    if (setNum>1){
                        cutCount++;

                        for (j = 0; j < setNum; j++) {
                            for (Integer t : temp[j].set) {

                                ss[i].remove(t);
                                ss[minDfaStateNum].add(t);
                            }

                            minDfaStateNum++;
                        }
                    }
                }

            }

            if (cutCount == 0){
                cutFlag = false;
            }
        }


        inits();

        int cn=0;

        for (i = 0; i < minDfaStateNum; i++){
            if(!ss[i].isEmpty()){
                s[cn++]=ss[i];
            }
        }

        minDfaStateNum = cn;

        for ( i = 0; i < cn; i++) {

            for (Integer y : s[i]) {

                if(y == dfa.startState){
                    minDfa.startState = i;
                }

                if (dfa.endStates.contains(y)){

                    minDfaStates[i].isEnd = true;
                    minDfa.endStates.add(i);
                }

                minDfaStates[i].index = i;

                for (j = 0; j < dfaStates[y].EdgeNum; j++) {

                    for (int t = 0; t < cn; t++) {
                        if (s[t].contains(dfaStates[y].Edges[j].Trans)){
                            boolean haveEdge = false;
                            for (int l = 0; l < minDfaStates[i].EdgeNum; l++) {

                                if ((minDfaStates[i].Edges[l].input == dfaStates[y].Edges[j].input) && minDfaStates[i].Edges[l].Trans == t){
                                    haveEdge = true;
                                }
                            }

                            if (!haveEdge) {
                                minDfaStates[i].Edges[minDfaStates[i].EdgeNum].input = dfaStates[y].Edges[j].input;
                                minDfaStates[i].Edges[minDfaStates[i].EdgeNum].Trans = t;

                                minDfa.trans[i][dfaStates[y].Edges[j].input - 'a'] = t;
                                minDfaStates[i].EdgeNum++;
                            }
                            break;
                        }
                    }
                }
            }
        }

        return minDfa;
    }

    public static String ShowMinDFA(Dfa dfa){
        /*System.out.println("minDFA总共有"+minDfaStateNum+"个状态，"+"初态为"+ dfa.startState);
        System.out.print("有穷字母表为｛ ");
        for (Character ch : dfa.terminator) {
            System.out.print(ch+" ");
        }
        System.out.println("}");

        System.out.println("转移函数为：");*/

        StringBuilder sb = new StringBuilder();

        String node = "node[shape=plaintext];\n"+
                dfa.startState+"[shape=circle];\n";
        sb.append(node);

        sb.append("\"\"->"+dfa.startState+"[ label = start];\n");

        node = "size = \"60, 40\";\n" +
                "rankdir = LR;\n"+
                "fontname = \"Microsoft YaHei\";\n" +
                "fontsize = 10;\n" +
                "node [shape = circle, fontname = \"Microsoft YaHei\", fontsize = 10];\n" +
                "edge [fontname = \"Microsoft YaHei\", fontsize = 10];\n";
        sb.append(node);

        for (Integer end : dfa.endStates) {
            sb.append(end + "[ shape = doublecircle ];\n");
        }


        for (int i = 0; i < minDfaStateNum; i++) {
            for (int j = 0; j < minDfaStates[i].EdgeNum; j++) {
                sb.append(minDfaStates[i].index+" -> "+minDfaStates[i].Edges[j].Trans+" [ label = \" "+minDfaStates[i].Edges[j].input+" \" ];\n");
            }
        }


        /*for (int i = 0; i < minDfaStateNum; i++) {

            for (int j = 0; j < minDfaStates[i].EdgeNum; j++) {

                if (minDfaStates[minDfaStates[i].Edges[j].Trans].isEnd==true){
                    System.out.print(minDfaStates[i].index+"-->'"+minDfaStates[i].Edges[j].input);
                    System.out.println("'--><"+minDfaStates[i].Edges[j].Trans+">");
                }

                else {
                    System.out.print(minDfaStates[i].index+"-->'"+minDfaStates[i].Edges[j].input);
                    System.out.println("'-->"+minDfaStates[i].Edges[j].Trans);
                }
            }
        }*/
        return sb.toString();
    }

    public static void StartMinDFA(String re){
        Dfa dfa = StartDFA(re);
        String formatmindfa = ShowMinDFA(minDFA(dfa));

        createDotGraph(formatmindfa, "minDFA");
    }

    /*public static void main(String[] args) {

        //String str = "ab*|c*.";
      String str = "ab.*a*b*|.ba.*.";
        Dfa dfa = StartDFA(str);
        //Dfa mindfa = minDFA(dfa);
        //System.out.println("afsdfasdf");
        String formatmindfa = printminDFA(minDFA(dfa));

        createDotGraph(formatmindfa, "minDFA");
    }*/

}
