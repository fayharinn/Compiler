import ast.Exp;
import utils.Lexer;
import utils.Parser;
import visitor.*;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;

public class ArmTest {
    static public void main(String argv[]) {

        try{
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));

            Exp expression = (Exp) p.parse().value;
            assert (expression != null);

            //reductions
            expression = expression.accept(new KNormVisitor());
            expression = expression.accept(new AlphaConvVisitor());
            expression = expression.accept(new LetExpressionsVisitor());


            //closure
            ClosureConversion visitorClosure = new ClosureConversion();
            expression = expression.accept(visitorClosure);
            expression = visitorClosure.moveToFront(expression);

            //allocation des registres
            HashMap<String, Integer> intervals = expression.accept(new VariableIntervalVisitor());
            expression = expression.accept(new RegisterAllocationVisitor(intervals));

            //generation ARM
            System.setOut(new PrintStream(new File(argv[1])));
            ArmVisitor visitorArm = new ArmVisitor();
            expression.accept(visitorArm);

        } catch(Exception e){
            System.err.println(e);
        }

    }
}
