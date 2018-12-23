import ast.Exp;
import type.Type;
import utils.*;
import visitor.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    static public void main(String argv[]) {
        try {
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            
            Exp expression = (Exp) p.parse().value;
            assert (expression != null);

            System.out.println("------ AST ------");
            expression.accept(new PrintVisitor());
            System.out.println(); 

            
            System.out.println("------ utils.Height of the AST ----");
            int height = Height.computeHeight(expression);
            System.out.println("using utils.Height.computeHeight: " + height);
            
            ObjVisitor<Integer> v = new HeightVisitor();
            height = expression.accept(v);
            System.out.println("using HeightVisitor: " + height);
            
            
            System.out.println("\n------ Type checking ----\n");
            TypeCheck.check(expression);
            
            System.out.println("\n------ ASML GENERATED ---- \n");
            ASMLVisitor asmlv = new ASMLVisitor();
            expression.accept(asmlv);
            for(String x:asmlv.code) {
            	System.out.print(x);
            }
            
            
            System.out.println("\n------ ARM GENERATED ---- \n");
            ArmVisitor armv = new ArmVisitor();
            expression.accept(armv);
            System.out.println(); 
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

