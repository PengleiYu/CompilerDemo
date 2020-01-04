package ch01Craft.section03;


import ch01Craft.ASTNode;
import ch01Craft.TokenReader;
import ch01Craft.section02.SimpleLexer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        Object[][] scripts = {
                {"1+2", 3},
                {"3-4", -1},
                {"3*4", 12},
                {"3/4", 0},
                {"4+2*3", 10},
                {"8-5/2", 6},
                {"2*2+4", 8},
                {"9/2-2", 2},
                {"3 * (1 +2)", 9},
                {"1 + 2 + 3", 6},
                {"(1 + 2) + 3)", 6},
                {"1 + (2 + 3)", 6},
                {"2 * 3 * 4", 24},
                {"(2 * 3) * 4", 24},
        };

        SimpleCalculator calculator = new SimpleCalculator();
        for (Object[] pair : scripts) {
            int result = calculator.evaluate((String) pair[0]);
            assertEquals(pair[1], result);
            System.out.println("============================================================================");
        }
    }

    @Test
    public void leftRecursive() throws Exception {
        String s1 = "1+2+3+4";
        SimpleCalculator calculator=new SimpleCalculator();
        calculator.evaluate(s1);
    }

}