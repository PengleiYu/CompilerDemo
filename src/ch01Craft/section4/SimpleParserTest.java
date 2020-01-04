package ch01Craft.section4;

import ch01Craft.ASTNode;
import org.junit.Test;

/**
 * Created by JJBOOM on 2020/01/04.
 */
public class SimpleParserTest {
    @Test
    public void evaluate() throws Exception {
        String[] scripts = {
                "1+2;",
                "1+2+3+4;",
                "2*3;",
                "2*3*4*5;",
                "2*3/4;",
                "4+2-3+3*3/4*5;",
        };
        SimpleParser parser = new SimpleParser();

        for (String script : scripts) {
            ASTNode node = parser.parse(script);
            //            parser.dumpAST(node, "");
            parser.evaluate(node, "");
            System.out.println("============================================================");
        }
    }

    @Test
    public void parse() throws Exception {
        String[] scripts = {
                "int a = 30; int b; b= 40; a = b; b= 1+2+3; 2*3*4;",
        };
        SimpleParser parser = new SimpleParser();
        for (String script : scripts) {
            ASTNode astNode = parser.parse(script);
            parser.dumpAST(astNode, "");
        }
    }
}