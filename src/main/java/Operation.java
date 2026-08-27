public enum Operation {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    BYE("bye");

    private final String operationStr;

    private Operation(String operationStr) {
        this.operationStr = operationStr;
    }

    public static Operation fromInput(String input) throws GihunException {
        for (Operation op : Operation.values()) {
            if (op.operationStr.equalsIgnoreCase(input)) {
                return op;
            }
        }

        throw new GihunException("Invalid operation: " + input);
    }    
}