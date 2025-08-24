/**
 *
 */
package edu.umd.cs.findbugs.ba.contract;

import java.util.Collections;
import java.util.List;

import edu.umd.cs.findbugs.ba.npe.IsNullValue;
import edu.umd.cs.findbugs.util.Strings;

/**
 * @author Guillaume Toison
 */
public class MethodContract {
    private List<MethodContractClause> clauses;

    public MethodContract(List<MethodContractClause> clauses) {
        this.clauses = Collections.unmodifiableList(clauses);
    }

    public List<MethodContractClause> getClauses() {
        return clauses;
    }

    @Override
    public String toString() {
        return Strings.join("; ", clauses, Object::toString);
    }

    public IsNullValue computeValue(IsNullValue[] parametersValues) {
        for (MethodContractClause clause : clauses) {
            if (clause.matches(parametersValues)) {
                return clause.getEffect().getNullValue();
            }
        }
        return null;
    }
}
