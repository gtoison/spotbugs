/**
 *
 */
package edu.umd.cs.findbugs.ba.contract;

import edu.umd.cs.findbugs.ba.npe.IsNullValue;

/**
 * @author Guillaume Toison
 */
public enum MethodContractValueConstraint {
    /**
     * Any value
     */
    ANY_VALUE,

    /**
     * A null value
     */
    NULL_VALUE,

    /**
     * A value statically proved to be not-null
     */
    NON_NULL_VALUE,

    /**
     * true boolean value
     */
    BOOLEAN_TRUE,

    /**
     * false boolean value
     */
    BOOLEAN_FALSE,
    ;

    public static MethodContractValueConstraint of(String value) {
        switch (value) {
        case "_":
            return ANY_VALUE;
        case "null":
            return NULL_VALUE;
        case "!null":
            return NON_NULL_VALUE;
        case "true":
            return BOOLEAN_TRUE;
        case "false":
            return BOOLEAN_FALSE;
        default:
            throw new IllegalArgumentException("Unexpected value constraint: '" + value + "'");
        }
    }

    IsNullValue getNullValue() {
        switch (this) {
        case NULL_VALUE:
            return IsNullValue.nullValue();
        case NON_NULL_VALUE:
            return IsNullValue.nonNullValue();
        default:
            return null;
        }
    }

    boolean matches(IsNullValue isNullValue) {
        switch (this) {
        case ANY_VALUE:
            return true;
        case NULL_VALUE:
            return isNullValue.isDefinitelyNull();
        case NON_NULL_VALUE:
            return isNullValue.isDefinitelyNotNull();
        default:
            return false;
        }
    }

    @Override
    public String toString() {
        switch (this) {
        case ANY_VALUE:
            return "_";
        case NULL_VALUE:
            return "null";
        case NON_NULL_VALUE:
            return "!null";
        case BOOLEAN_TRUE:
            return "true";
        case BOOLEAN_FALSE:
            return "false";
        default:
            throw new IllegalArgumentException("Unexpected constraint: '" + name() + "'");
        }
    }
}
