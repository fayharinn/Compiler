import ast.Exp;
import type.TUnit;
import utils.*;
import visitor.*;
import java.io.*;

public class Main {
    static public void main(String argv[]) {
        try {
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            
            Exp expression = (Exp) p.parse().value;
            assert (expression != null);

            System.out.println("------ AST ------");
            expression.accept(new PrintVisitor());
            System.out.println();


            TypeCheckVisitor typeChecker = new TypeCheckVisitor();
            expression.accept(typeChecker,new TUnit());
            typeChecker.printEnvironement();

            System.out.println("------ utils.Height of the AST ----");
            int height = Height.computeHeight(expression);
            System.out.println("using utils.Height.computeHeight: " + height);
            
            ObjVisitor<Integer> v = new HeightVisitor();
            height = expression.accept(v);
            System.out.println("using HeightVisitor: " + height);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

