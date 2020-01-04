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
        String s1 = "int age = 40;";
        String s2="int age = 1 + 4 * 4;";


        SimpleLexer lexer = new SimpleLexer();
        SimpleCalculator calculator = new SimpleCalculator();
        TokenReader reader = lexer.tokenize(s1);
        ASTNode node = calculator.intDeclare(reader);

        calculator.dumpAST(node, "");
    }

    @org.junit.Test
    public void evaluate() throws Exception {
    }

}