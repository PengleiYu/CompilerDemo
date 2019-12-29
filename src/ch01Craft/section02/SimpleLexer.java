package ch01Craft.section02;

import ch01Craft.Token;
import ch01Craft.TokenType;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个简单的手写的词法分析器
 */
public class SimpleLexer {
    public static void main(String[] args) {
        String s1 = "age >= 45";
        String s2 = "int age = 40";
        String s3 = "2+3*5";

        SimpleLexer lexer = new SimpleLexer();

        lexer.tokenize(s1);
        dumpTokens(lexer.tokens);
        //        lexer.tokenize(s2);
        //        dumpTokens(lexer.tokens);
        //        lexer.tokenize(s3);
        //        dumpTokens(lexer.tokens);
    }

    private static void dumpTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token.getText() + "\t\t" + token.getType());
        }

    }

    private StringBuffer tokenText = new StringBuffer();
    private List<Token> tokens = new ArrayList<>();
    private SimpleToken token = new SimpleToken();

    /**
     * 状态机，根据当前所处的状态和读到的字符决定下一步走向
     *
     * @param code 代码字符串
     */
    private void tokenize(String code) {
        tokens = new ArrayList<>();
        tokenText = new StringBuffer();
        token = new SimpleToken();
        int ich;
        char ch = 0;
        DfaState state = DfaState.Initial;
        try (CharArrayReader reader = new CharArrayReader(code.toCharArray())) {
            while ((ich = reader.read()) != -1) {
                ch = (char) ich;
                switch (state) {
                    case Initial:
                        state = initToken(ch);//当前是初始状态，根据读到的字符决定走向哪种状态
                        break;
                    case Id:
                        if (isAlpha(ch) || isDigit(ch)) {//当前是Id状态，又读到了字母或数字，则保持当前状态
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case IntLiteral:
                        if (isDigit(ch)) {//当前是int字面值状态，又读到了数字
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case GT:
                        if (ch == '=') {//当前是大于号，又读到了等于号，进入大于等于状态
                            state = DfaState.GE;
                            token.tokenType = TokenType.GE;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case GE:
                        state = initToken(ch);//大于等于号状态不再接受其他字符
                        break;
                }
            }
                /*末尾的-1，用于终结最后一个token*/
            if (tokenText.length() > 0) {
                initToken(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DfaState initToken(char ch) {
        /*进入初始化状态时清理其他状态*/
        if (tokenText.length() > 0) {
            token.text = tokenText.toString();
            tokens.add(token);

            tokenText = new StringBuffer();
            token = new SimpleToken();
        }

        DfaState newState = DfaState.Initial;
        if (isAlpha(ch)) {
            newState = DfaState.Id;
            token.tokenType = TokenType.Identifier;
            tokenText.append(ch);
        } else if (isDigit(ch)) {
            newState = DfaState.IntLiteral;
            token.tokenType = TokenType.IntLiteral;
            tokenText.append(ch);
        } else if (ch == '>') {
            newState = DfaState.GT;
            token.tokenType = TokenType.GT;
            tokenText.append(ch);
        }
        return newState;
    }

    private boolean isAlpha(char ch) {
        return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z');
    }

    private boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    private boolean isBland(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\n';
    }


    private enum DfaState {
        Initial, Id, Id_int1, Id_int2, Id_int3, IntLiteral, GT, GE
    }

    private static class SimpleToken implements Token {
        TokenType tokenType;
        String text;

        @Override
        public TokenType getType() {
            return tokenType;
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return tokenType + " : " + text;
        }
    }
}
