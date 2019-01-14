import ast.Exp;
import utils.*;
import visitor.*;
import java.io.*;

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

            Exp letexp= exp_alpha_conv.accept(new LetExpressionsVisitor());

            System.out.println("------ AST Let Expressions ------");
            letexp.accept(new PrintVisitor());
            System.out.println();

            //Closure Conversion
            ClosureConversion visitorClosure = new ClosureConversion();
            Exp closConv = letexp.accept(visitorClosure);
            closConv = visitorClosure.moveToFront(closConv);

            System.out.println("------ AST Closure Conversion------");
            closConv.accept(new PrintVisitor());
            System.out.println();
            System.out.println();

            System.out.println("------ utils.Height of the AST ----");
            int height = Height.computeHeight(expression);
            System.out.println("using utils.Height.computeHeight: " + height);

            ObjVisitor<Integer> v = new HeightVisitor();
            height = expression.accept(v);
            System.out.println("using HeightVisitor: " + height);



            System.out.println("------ utils.Height of the AST ----");
            height = Height.computeHeight(knorm);
            System.out.println("using utils.Height.computeHeight: " + height);

            height = knorm.accept(v);
            System.out.println("using HeightVisitor: " + height);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

