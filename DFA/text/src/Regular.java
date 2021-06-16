import java.util.Stack;

public class Regular {

    String re;

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public Regular() {
    }

    public Regular(String re) {
        this.re = re;
    }

    public static Boolean  Isop(char op){
        return (op=='|'||op=='('||op==')'||op=='.');
    }

    public static boolean Isch(char ch){
        if(ch<'a'||ch>'z')
            return false;
        else
            return true;
    }

    public int Priority(char ch){
        if (ch =='*') return 3;
        if (ch == '.') return 2;
        if (ch == '|') return 1;
        if (ch == '(') return 0;
        return 0;
    }

    public void RewriterRE(){
        StringBuilder sb = new StringBuilder(re);
        int len=sb.length();

        for (int i = 1; i < len; i++) {
            int j=i-1;
            if(Isch(sb.charAt(i)) && (Isch(sb.charAt(j)) || sb.charAt(j)=='*'||sb.charAt(j) ==')')){
                sb.insert(i,'.');
                i++;len++;
            }
            if(sb.charAt(i)=='('&&(Isch(sb.charAt(j)) || sb.charAt(j) =='*'||sb.charAt(j) == ')')){
                sb.insert(i, ".");
                i++;len++;
            }
        }
        setRe(sb.toString());
    }

    public String PolishString() {
        RewriterRE();
        Stack<Character> opStack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < re.length(); i++) {
            if (Isch(re.charAt(i))) sb.append(re.charAt(i));
            else {
                if (re.charAt(i) == '(') opStack.push(re.charAt(i));
                else if (re.charAt(i) == ')') {
                    while (opStack.peek() != '(') {
                        sb.append(opStack.pop());
                    }
                    opStack.pop();
                } else {
                    if (!opStack.empty()) {
                        while (Priority(opStack.peek()) >= Priority(re.charAt(i))) {
                            sb.append(opStack.pop());
                            if (opStack.empty()) break;
                        }
                        opStack.push(re.charAt(i));
                    } else opStack.push(re.charAt(i));
                }
            }
        }
        while (!opStack.empty()){
            sb.append(opStack.pop());
        }
        setRe(sb.toString());
        return sb.toString();
    }

}
