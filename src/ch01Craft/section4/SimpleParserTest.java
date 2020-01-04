package ch01Craft.section4;

import ch01Craft.ASTNode;
import org.junit.Test;

/**
 * Created by JJBOOM on 2020/01/04.
 */
public class SimpleParserTest {
    @Test
    public void parse() throws Exception {
        String[] scripts = {
                "1+2",
                "1+2+3+4",
                "2*3",
                "2*3*4*5",
                "2*3/4",
                "4+2-3+3*3/4*5",
        };
        SimpleParser parser = new SimpleParser();

        for (String script : scripts) {
            ASTNode node = parser.parse(script);
            //            parser.dumpAST(node, "");
            parser.evaluate(node, "");
            System.out.println("============================================================");
        }

    }

}