package utility;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 21:18
 */
public class MyUtility {

    /**
     * utility.MyUtility.parseInstruction():
     * utility function for splitting a String form instruction <br>
     * should handle all possible cases <br>
     * only allowed case: "operator,operand,operand" without space, or "KILL" <br>
     * operator should be within the provided insturction set <br>
     * @date 2022/5/8~21:20
     * @param i instruction
     * @throws IllegalArgumentException if the instruction is illegal
     * @return java.lang.String[]
     * @return String[0] -> operand 1 (KILL fills it with "0")
     * @return String[1] -> operand 2 (KILL fills it with "0")
     * @return String[2] -> operator
     */
    public static String[] parseInstruction(String i) {
        String[] ret = i.split(",");

        return ret;



    }

    /**
     * utility.MyUtility.execute():
     * execute a instruction (need further implementation)
     * @date 2022/5/8~21:22
     * @param op1 first operand
     * @param op2 second operand
     * @param operator + - * / kill
     * @throws RuntimeException if the operator is KILL
     * @return double
     */
    public static double execute(double op1, double op2, InstructionSet operator) {
        switch (operator) {
            case ADD:
                return op1 + op2;
            case MUL:
                return op1 * op2;
            case KILL:
                throw new RuntimeException();


        }

        return -1;
    }
}
