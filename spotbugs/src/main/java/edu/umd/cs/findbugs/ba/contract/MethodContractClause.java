/**
 *
 */
package edu.umd.cs.findbugs.ba.contract;

import java.util.List;

import edu.umd.cs.findbugs.ba.npe.IsNullValue;
import edu.umd.cs.findbugs.util.Strings;

/**
 * @author Guillaume Toison
 */
public class MethodContractClause {
    private List<MethodContractValueConstraint> arguments;
    private MethodContractEffect effect;

    public MethodContractClause(List<MethodContractValueConstraint> arguments, MethodContractEffect effect) {
        this.arguments = arguments;
        this.effect = effect;
    }

    public List<MethodContractValueConstraint> getArguments() {
        return arguments;
    }

    public MethodContractEffect getEffect() {
        return effect;
    }

    public boolean matches(IsNullValue[] parametersValues) {
        for (int i = 0; i < parametersValues.length; i++) {
            if (!arguments.get(i).matches(parametersValues[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return Strings.join(", ", arguments, Object::toString) + " -> " + effect;
    }
}
