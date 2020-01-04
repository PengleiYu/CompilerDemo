package ch01Craft.section03;

import ch01Craft.*;
import ch01Craft.section02.SimpleLexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JJBOOM on 2020/01/04.
 */
public class SimpleCalculator {


    public SimpleASTNode intDeclare(TokenReader reader) throws Exception {
        SimpleASTNode node = null;
        Token token = reader.peek();
        if (token.getType() == TokenType.Int) {
            token = reader.read();
            SimpleASTNode intNode = new SimpleASTNode(ASTNodeType.IntDeclaration, token.getText());
            node = intNode;
            token = reader.peek();
            if (token.getType() == TokenType.Identifier) {
                token = reader.read();
                SimpleASTNode identifierNode = new SimpleASTNode(ASTNodeType.Identifier, token.getText());
                intNode.addChild(identifierNode);
                token = reader.peek();
                if (token.getType() == TokenType.Assignment) {
                    token=reader.read();
                    SimpleASTNode assignmentNode=new SimpleASTNode(ASTNodeType.AssignmentStmt,token.getText());
                    identifierNode.addChild(assignmentNode);
                    SimpleASTNode child = additive(reader);
                    if (child != null) {
                        assignmentNode.addChild(child);
                    } else {
                        throw new Exception("非法的初始化，需要一个表达式");
                    }
                }

            }
        }
        if (node != null) {
            token = reader.peek();
            if (token != null && token.getType() == TokenType.Semicolon) {
                reader.read();
            } else {
                throw new Exception("非法表达式，需要分号");
            }
        }
        return node;
    }

    private SimpleASTNode additive(TokenReader reader) throws Exception {
        SimpleASTNode node = null;
        SimpleASTNode child1 = multiplicative(reader);
        if (child1 != null) {
            Token token = reader.peek();
            if (token != null && (token.getType() == TokenType.Minus || token.getType() == TokenType.Plus)) {
                token = reader.read();
                ASTNode child2 = multiplicative(reader);
                if (child2 == null) {
                    throw new Exception("非法的加法表达式，需要右半部分");
                } else {
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                }
            } else {
                node = child1;
            }
        }
        return node;
    }

    private SimpleASTNode multiplicative(TokenReader reader) throws Exception {
        SimpleASTNode node = null;
        SimpleASTNode child1 = primary(reader);
        if (child1 != null) {
            Token token = reader.peek();
            if (token != null &&
                    (token.getType() == TokenType.Star || token.getType() == TokenType.Slash)) {
                token = reader.read();
                SimpleASTNode child2 = primary(reader);
                if (child2 == null) {
                    throw new Exception("非法乘法表达式，需要右半部分");
                } else {
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                }
            } else {
                node = child1;
            }
        }
        return node;
    }

    private SimpleASTNode primary(TokenReader reader) throws Exception {
        SimpleASTNode node = null;
        Token token = reader.peek();
        if (token != null) {
            if (token.getType() == TokenType.IntLiteral) {
                token = reader.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.getText());
            } else if (token.getType() == TokenType.Identifier) {
                token = reader.read();
                node = new SimpleASTNode(ASTNodeType.Identifier, token.getText());
            } else if (token.getType() == TokenType.LeftParen) {//左括号需要后继加法表达式和右括号
                reader.read();//消耗掉token
                node = additive(reader);
                if (node == null) {
                    throw new Exception("括号中需要一个加法表达式");
                } else {
                    token = reader.peek();
                    if (token != null && token.getType() == TokenType.RightParen) {
                        reader.read();
                    } else {
                        throw new Exception("需要右括号");
                    }
                }
            }
        }
        return node;
    }


    public void evaluate(String script) {
        ASTNode tree = parse(script);
        dumpAST(tree, "");
        evaluate(tree, "");
    }

    private void evaluate(ASTNode tree, String s) {

    }

    public void dumpAST(ASTNode node, String indent) {
        System.out.println(indent + node.getType() + " " + node.getText());
        for (ASTNode astNode : node.getChildren()) {
            dumpAST(astNode, indent + "\t");
        }
    }

    private ASTNode parse(String script) {
        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(script);
        return prog(tokens);
    }

    private ASTNode prog(TokenReader tokens) {
        return null;
    }

    private static class SimpleASTNode implements ASTNode {
        private ASTNode parent;
        private List<ASTNode> children = new ArrayList<>();
        private List<ASTNode> readOnlyChildren = Collections.unmodifiableList(children);
        private final ASTNodeType nodeType;
        private final String text;

        private SimpleASTNode(ASTNodeType nodeType, String text) {
            this.nodeType = nodeType;
            this.text = text;
        }

        @Override
        public ASTNode getParent() {
            return parent;
        }

        @Override
        public List<ASTNode> getChildren() {
            return children;
        }

        @Override
        public ASTNodeType getType() {
            return nodeType;
        }

        @Override
        public String getText() {
            return text;
        }

        public void addChild(ASTNode child) {
            if (child != null) {
                children.add(child);
            }
        }
    }
}
