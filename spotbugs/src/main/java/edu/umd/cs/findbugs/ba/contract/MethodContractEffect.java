/**
 *
 */
package edu.umd.cs.findbugs.ba.contract;

import edu.umd.cs.findbugs.ba.npe.IsNullValue;

/**
 * @author Guillaume Toison
 */
public class MethodContractEffect {
    public static final MethodContractEffect FAIL = new MethodContractEffect(true, null);

    private boolean fail;
    private MethodContractValueConstraint valueConstraint;

    private MethodContractEffect(boolean fail, MethodContractValueConstraint valueConstraint) {
        this.fail = fail;
        this.valueConstraint = valueConstraint;
    }

    public static MethodContractEffect of(MethodContractValueConstraint constraint) {
        return new MethodContractEffect(false, constraint);
    }

    public boolean isFail() {
        return fail;
    }

    public MethodContractValueConstraint getValueConstraint() {
        return valueConstraint;
    }

    @Override
    public String toString() {
        return fail ? "fail" : valueConstraint.toString();
    }

    public IsNullValue getNullValue() {
        if (!fail) {
            return valueConstraint.getNullValue();
        }

        return null;
    }
}
