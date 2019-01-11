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
            HashMap<String,Type> env = TypeCheck.check(expression);
            PrintWriter out = new PrintWriter("../../test3.asml"); // l'output de l'asml
            System.out.println("\n------ ASML GENERATED ---- \n");
            ASMLVisitor asmlv = new ASMLVisitor(env);
            expression.accept(asmlv);
            for(String x:asmlv.float_code) {
            	out.print(x);
            	System.out.print(x);
            }
            for(String x:asmlv.fun_code) {
            	out.print(x);
            	System.out.print(x);
            }
            out.close();
            for(String x:asmlv.code) {
            	out.print(x);
            	System.out.print(x);
            }
            out.close();
            
            
            //System.out.println("\nTEST VARFLOAT "+asmlv.new_varfloat);
            System.out.println("\n------ ARM GENERATED ---- \n");
           // ArmVisitor armv = new ArmVisitor();
            //expression.accept(armv);
            //System.out.println(); 
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

