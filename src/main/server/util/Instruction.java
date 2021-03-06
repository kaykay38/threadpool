package main.server.util;
/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description instruction set (need further implementation)
 * @create 2022-05-08 17:50
 */
public class Instruction {

    /**
     * Execute an instruction given as a String, <br>
     * should handle all possible cases <br>
     * only allowed case: "operator,operand,operand" without space, or "KILL" <br>
     * operator should be within the provided instruction set <br>
     * @date 2022/5/8~21:20
     * @param instruction instruction
     * @throws RuntimeException if the instruction is KILL
     * @throws IllegalArgumentException if the instruction is illegal
     * @return the result of the instruction as a double, otherise throws RuntimeException
     */
    public static double execute(String instruction) {
        String[] instructionArr = instruction.split(",");
        String operator;
        double num1, num2;
        if (instructionArr.length >= 3) {
            operator = instructionArr[0].toUpperCase();
            try {
                num1 = Double.parseDouble(instructionArr[1]);
                num2 = Double.parseDouble(instructionArr[2]);
            } catch(NumberFormatException e) {
                throw new IllegalArgumentException("Invalid instruction, not a number");
            }
            switch (operator) {
                case "ADD":
                    return num1 + num2;
                case "SUB":
                    return num1 - num2;
                case "MUL":
                    return num1 * num2;
                case "DIV":
                    if (num2 == 0) {
                        throw new IllegalArgumentException("Invalid instruction, cannot divide by zero");
                    }
                    return num1 / num2;
                default:
                    throw new IllegalArgumentException("Invalid instruction");
            }
        }
        else if ("KILL".equalsIgnoreCase(instruction)) {
            throw new RuntimeException("KILL instruction received");
        }
        else {
            throw new IllegalArgumentException("Invalid instruction");
        }
    }
}