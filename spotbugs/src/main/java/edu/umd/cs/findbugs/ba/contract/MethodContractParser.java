/**
 *
 */
package edu.umd.cs.findbugs.ba.contract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillaume Toison
 */
public class MethodContractParser {

    public static MethodContract parse(String value) {
        State state = State.CONSTRAINT;

        List<MethodContractClause> clauses = new ArrayList<>();
        List<MethodContractValueConstraint> constraints = new ArrayList<>();

        for (String token : parseTokens(value)) {
            switch (state) {
            case CONSTRAINT:
                switch (token) {
                case ",":
                    // ignore
                    break;
                case "->":
                    state = State.EFFECT;
                    break;
                default:
                    constraints.add(MethodContractValueConstraint.of(token));
                    break;
                }

                break;
            case EFFECT:
                MethodContractEffect effect;

                switch (token) {
                case "fail":
                    effect = MethodContractEffect.FAIL;
                    break;
                default:
                    effect = MethodContractEffect.of(MethodContractValueConstraint.of(token));
                    break;
                }

                clauses.add(new MethodContractClause(constraints, effect));
                state = State.CLAUSE_END;

                break;
            case CLAUSE_END:
                switch (token) {
                case ";":
                    constraints = new ArrayList<>();
                    state = State.CONSTRAINT;
                    break;
                default:
                    throw new IllegalStateException("Expected ';' after clause end");
                }
                break;
            default:
                break;
            }
        }

        return new MethodContract(clauses);
    }

    public static List<String> parseTokens(String value) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            int c = value.charAt(i);

            switch (c) {
            case ' ': {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current = new StringBuilder();
                }
                break;
            }
            case ',':
            case ';': {
                tokens.add(current.toString());
                tokens.add(Character.toString(c));
                current = new StringBuilder();
                break;
            }
            case '-': {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                }
                current = new StringBuilder(Character.toString(c));
                break;
            }
            case '>': {
                current.append(Character.toString(c));
                tokens.add(current.toString());
                current = new StringBuilder();
                break;
            }
            default: {
                current.append(Character.toString(c));
            }
            }
        }

        tokens.add(current.toString());

        return tokens;
    }

    private enum State {
        CONSTRAINT,
        CONSTRAINT_ARROW,
        EFFECT,
        CLAUSE_END,
    }
}
