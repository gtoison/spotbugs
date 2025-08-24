/**
 *
 */
package edu.umd.cs.findbugs.ba.contract;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

/**
 * @author Guillaume Toison
 */
class MethodContractParserTest {

    @ValueSource(strings = {
        "_, null -> null; _, !null -> !null",
        "_,null->null;_,!null->!null",
    })
    @ParameterizedTest
    void parseTest(String text) {
        MethodContract contract = MethodContractParser.parse(text);

        List<MethodContractClause> clauses = contract.getClauses();
        assertEquals(2, clauses.size());

        assertEquals(2, clauses.get(0).getArguments().size());
        assertEquals(MethodContractValueConstraint.ANY_VALUE, clauses.get(0).getArguments().get(0));
        assertEquals(MethodContractValueConstraint.NULL_VALUE, clauses.get(0).getArguments().get(1));
        assertFalse(clauses.get(0).getEffect().isFail());
        assertEquals(MethodContractValueConstraint.NULL_VALUE, clauses.get(0).getEffect().getValueConstraint());

        assertEquals(2, clauses.get(1).getArguments().size());
        assertEquals(MethodContractValueConstraint.ANY_VALUE, clauses.get(1).getArguments().get(0));
        assertEquals(MethodContractValueConstraint.NON_NULL_VALUE, clauses.get(1).getArguments().get(1));
        assertFalse(clauses.get(1).getEffect().isFail());
        assertEquals(MethodContractValueConstraint.NON_NULL_VALUE, clauses.get(1).getEffect().getValueConstraint());

        assertEquals("_, null -> null; _, !null -> !null", contract.toString());
    }
}
