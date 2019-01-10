import ast.Exp;
import type.TFloat;
import type.TFun;
import type.TInt;
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


            AllEnvironements gho = new AllEnvironements();
            gho.ajouterVar("x", new TInt());
            gho.ajouterVar("y", new TInt());
            gho.ajouterVar("z", new TFloat());

            gho.créerEnvironementLocale();
            gho.ajouterVar("mouataz",new TFloat());
            gho.ajouterVar("Yves",new TFloat());
            gho.ajouterVar("Axel",new TFloat());
            gho.ajouterVar("Roger",new TFloat());


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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

