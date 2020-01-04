package ch01Craft.section03;


import ch01Craft.ASTNode;
import ch01Craft.TokenReader;
import ch01Craft.section02.SimpleLexer;
import org.junit.Test;

/**
 * Created by JJBOOM on 2020/01/04.
 */
public class SimpleCalculatorTest {
    @Test
    public void intDeclare() throws Exception {
        String[] decalares = {
                "int age ;",
                "int age = 30 ;",
                "int age = 1 + 4 * 4;",
        };

        SimpleLexer lexer = new SimpleLexer();
        SimpleCalculator calculator = new SimpleCalculator();
        for (String declare : decalares) {
            TokenReader reader = lexer.tokenize(declare);
            ASTNode node = calculator.intDeclare(reader);
            calculator.dumpAST(node, "");
            System.out.println("=============================================");
        }
    }

    @org.junit.Test
    public void evaluate() throws Exception {
    }

}