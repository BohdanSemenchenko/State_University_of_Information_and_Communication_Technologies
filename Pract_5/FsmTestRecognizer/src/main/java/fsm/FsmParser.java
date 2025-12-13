package fsm;

public class FsmParser {

/*
    public State processStrict(String input) {
        State currentState = State.S;

        if (input == null) return State.S;

        for (char c : input.toCharArray()) {
            switch (currentState) {
                case S:
                    if (c == 'T') currentState = State.S1;
                    else currentState = State.S;
                    break;

                case S1:
                    if (c == 'E') currentState = State.S2;
                    else currentState = State.S;
                    break;

                case S2:
                    if (c == 'S') currentState = State.S3;
                    else currentState = State.S;
                    break;

                case S3:
                    if (c == 'T') currentState = State.F;
                    else currentState = State.S;
                    break;

                case F:
                    currentState = State.F;
                    break;
            }

        }
        return currentState;
*/


    public State processFixed(String input) {
        State currentState = State.S;
        if (input == null) return State.S;

        for (char c : input.toCharArray()) {
            switch (currentState) {
                case S:
                    if (c == 'T') currentState = State.S1;
                    break;

                case S1:
                    if (c == 'E') currentState = State.S2;
                    else if (c == 'T') currentState = State.S1;
                    else currentState = State.S;
                    break;

                case S2:
                    if (c == 'S') currentState = State.S3;
                    else if (c == 'T') currentState = State.S1;
                    else currentState = State.S;
                    break;

                case S3:
                    if (c == 'T') currentState = State.F;
                    else if (c == 'T') currentState = State.S1;
                    else currentState = State.S;
                    break;

                case F:
                    currentState = State.F;
                    break;
            }
        }
        return currentState;
    }
}