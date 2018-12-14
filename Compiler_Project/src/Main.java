import ast.Exp;
import type.TUnit;
import type.Type;
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

            System.out.println("------ AST ------");
            expression.accept(new PrintVisitor());
            System.out.println();
            TypeCheckVisitor typeCheking = new TypeCheckVisitor();
            expression.accept(typeCheking,typeCheking.getEnv(),new TUnit(),new HashMap<Type,Type>());
            System.out.println();
            
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

