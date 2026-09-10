package com.gihun456.ui;

/**
 * Stores user-facing messages displayed by the application.
 */
public final class UiMessages {
    public static final String BANNER = """
                         __                    _   _  
                        /__ o |_      ._ |_|_ |_  |_  
                        \\_| | | | |_| | |  |   _) |_) 
                               
                         """;

    public static final String GREETING = "안녕하세요! 저는 Gihun456입니다.\n무엇을 도와드릴까요?";
    public static final String FAREWELL = "안녕히 가세요. 조만간 또 뵙기를 바랍니다!";
    public static final String SEP = "____________________________________________________________";

    public static final String LIST_TASKS = "목록에 있는 작업은 다음과 같습니다:";
    public static final String LIST_MATCHING_TASKS = "목록에 있는 일치하는 작업은 다음과 같습니다:";
    public static final String NO_MATCHING_TASKS = "일치하는 작업을 찾을 수 없습니다.";
    public static final String ADD_TASK = "알겠습니다. 해당 작업을 추가했습니다:";
    public static final String REMOVE_TASK = "알겠습니다. 해당 작업을 삭제했습니다:";
    public static final String MARK_TASK = "좋아요! 이 작업을 완료된 것으로 표시했습니다:";
    public static final String UNMARK_TASK = "네, 이 작업을 아직 완료되지 않은 것으로 표시했습니다:";
    public static final String EMPTY_STORAGE = "저장 공간이 비어 있습니다.";

    private UiMessages() {
    }
}
