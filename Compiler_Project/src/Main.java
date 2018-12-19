import ast.Exp;
import type.TInt;
import type.TUnit;
import type.Type;
import utils.*;
import visitor.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    static public void main(String argv[]) {
        try {
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            
            Exp expression = (Exp) p.parse().value;
            assert (expression != null);

            System.out.println("------ AST ------");
            expression.accept(new PrintVisitor());
            System.out.println();

            Environement env = new Environement();
            env.ajouterVar("x",new TInt());
            env.ajouterVar("x",new TUnit());

            /*TypeCheckVisitor typeChecker = new TypeCheckVisitor();
            expression.accept(typeChecker,new TUnit());
            typeChecker.printEnvironement();
            */
            System.out.println("------ utils.Height of the AST ----");
            int height = Height.computeHeight(expression);
            System.out.println("using utils.Height.computeHeight: " + height);
            
            ObjVisitor<Integer> v = new HeightVisitor();
            height = expression.accept(v);
            System.out.println("using HeightVisitor: " + height);
            
            //TypeCheck.check(expression);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

