package fsm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FsmParserTest {

    private final FsmParser parser = new FsmParser();

    @ParameterizedTest
    @CsvSource({
            "abcTESTabc, F",
            "TEST,       F",
            "TTEST,      F",
            "TETEST,     F",
            "TEST,       F",
            "abcTES,     S3",
            "abcTE,      S2",
            "abcT,       S1",
            "NOPE,       S",
            "'',         S"
    })

    /*
    void testStrictImplementation(String input, State expectedState) {
        State result = parser.processStrict(input);
        assertEquals(expectedState, result, "Ошибка на строке: " + input);
    }
*/

    void testFixedImplementation(String input, State expectedState) {
        State result = parser.processFixed(input);
        assertEquals(expectedState, result, "Fixed logic failed: " + input);
    }
}
