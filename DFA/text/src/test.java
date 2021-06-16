import java.io.File;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        Regular regular = new Regular(str);
        String re = regular.PolishString();
        System.out.println("后缀表达式："+re);
        MinDFA minDFA = new MinDFA();
        minDFA.StartMinDFA(re);

        System.out.println("NFA、DFA、MinDFA已成功生成！");

    }
}
