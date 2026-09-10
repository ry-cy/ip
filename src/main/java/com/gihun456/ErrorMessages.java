package com.gihun456;

/**
 * Stores error messages used throughout the application.
 */
public final class ErrorMessages {
    // Gihun456
    public static final String TODO_DESCRIPTION_EMPTY = "할 일의 설명은 비워 둘 수 없습니다.";
    public static final String UNSUPPORTED_OPERATION = "지원되지 않는 명령입니다.";
    public static final String UNEXPECTED_ERROR = "예기치 않은 오류가 발생했습니다. 다시 시도해 주세요.";

    // Ui
    public static final String ERROR_PREFIX = "오류: ";

    // Parser
    public static final String EMPTY_COMMAND = "명령어가 비어 있습니다.";
    public static final String DEADLINE_DESCRIPTION_EMPTY = "마감일의 설명은 비워 둘 수 없습니다.";
    public static final String DEADLINE_BY_MISSING = "마감일에는 '/by'가 포함되어야 합니다.";
    public static final String DEADLINE_DUE_DATE_EMPTY = "마감일의 기한은 비워 둘 수 없습니다.";
    public static final String EVENT_DESCRIPTION_EMPTY = "일정의 설명은 비워 둘 수 없습니다.";
    public static final String EVENT_FROM_TO_MISSING = "일정에는 '/from'과 '/to'가 포함되어야 합니다.";
    public static final String EVENT_FROM_BEFORE_TO = "일정에서는 '/from'이 '/to'보다 앞에 와야 합니다.";
    public static final String EVENT_START_DATE_EMPTY = "일정의 시작 날짜는 비워 둘 수 없습니다.";
    public static final String EVENT_END_DATE_EMPTY = "일정의 종료 날짜는 비워 둘 수 없습니다.";

    // TaskList
    public static final String INVALID_TASK_NUMBER = "작업 번호가 올바르지 않습니다.";
    public static final String EMPTY_KEYWORD = "검색어는 비워 둘 수 없습니다.";

    // Deadline
    public static final String DEADLINE_DATE_EMPTY = "마감일 날짜는 비워 둘 수 없습니다.";
    public static final String INVALID_DEADLINE_FORMAT =
            "마감일 형식이 올바르지 않습니다. dd/MM/yyyy 또는 yyyy-MM-dd 형식을 사용하고, 필요한 경우 공백 뒤에 HHmm을 입력해 주세요.";

    // Event
    public static final String EVENT_DATE_EMPTY = "일정 날짜는 비워 둘 수 없습니다.";
    public static final String INVALID_EVENT_DATE_FORMAT =
            "일정 날짜 형식이 올바르지 않습니다. dd/MM/yyyy 또는 yyyy-MM-dd 형식을 사용하고, 필요한 경우 공백 뒤에 HHmm을 입력해 주세요.";

    // Storage
    public static final String CANNOT_ACCESS_FILE = "작업 파일에 접근할 수 없습니다.";
    public static final String CANNOT_PARSE_TASK = "작업을 해석할 수 없습니다.";

    private ErrorMessages() {
    }

    // Operation
    /**
     * Creates an error message for an unsupported command.
     *
     * @param input Command text that was not recognised.
     * @return Error message containing the invalid command.
     */
    public static String invalidOperation(String input) {
        return "잘못된 명령: " + input;
    }
}
