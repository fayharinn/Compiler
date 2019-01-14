import ast.Exp;
import utils.*;
import visitor.*;
import java.io.*;
import java.util.HashMap;

public class Main {
    static public void main(String argv[]) {
        try {
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));

            Exp expression = (Exp) p.parse().value;
            assert (expression != null);

            System.out.println("------ Avant KNormalisation ------");
            expression.accept(new PrintVisitor());
            System.out.println();

            //Knormalisation

            Exp knorm = expression.accept(new KNormVisitor());
            System.out.println("\n------ Après KNormalisation ------");
            knorm.accept(new PrintVisitor());
            System.out.println();

            //Alpha Conversion

            Exp exp_alpha_conv = knorm.accept(new AlphaConvVisitor());

            System.out.println("------ AST AlphaConverted ------");
            exp_alpha_conv.accept(new PrintVisitor());
            System.out.println();

            //Let-expression/Linearisation du Let

            Exp letexp = exp_alpha_conv.accept(new LetExpressionsVisitor());

            System.out.println("------ AST Let Expressions ------");
            letexp.accept(new PrintVisitor());
            System.out.println();

            ClosureConversion visitorClosure = new ClosureConversion();
            //Closure Conversion
            Exp closConv = letexp.accept(visitorClosure);
            closConv = visitorClosure.moveToFront(closConv);

            System.out.println("------ AST Closure Conversion------");
            closConv.accept(new PrintVisitor());
            System.out.println();
            
            System.out.println("------ AST Register Allocation ------");
            HashMap<String, Integer> intervals = closConv.accept(new VariableIntervalVisitor());
            Exp reg = closConv.accept(new RegisterAllocationVisitor(intervals));
            reg.accept(new PrintVisitor());
            
            System.out.println("------ AST Conv ARM ------");
            reg.accept(new ArmVisitor());
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}