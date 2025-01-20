package backend.academy.project2.typer;

import java.io.PrintStream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MessageTyper {
    private static final String GREEN_FORE = "\33[1;35m";
    private static final String RED_FORE = "\33[1;31m";
    private static final int TIME_TO_SLEEP_MILLS = 21;
    private static final PrintStream PRINTER = System.out;

    private MessageTyper() {
    }

    public static void typeLikeGPT(Message message, boolean isMistake) {
        typeLikeGPT(message.toString(), isMistake);
    }

    public static void typeLikeGPT(String message, boolean isMistake) {
        for (char ch : message.toCharArray()) {
            if (isMistake) {
                PRINTER.print(RED_FORE + ch);
            } else {
                PRINTER.print(GREEN_FORE + ch);
            }
            try {
                Thread.sleep(TIME_TO_SLEEP_MILLS);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
        PRINTER.println();
    }
}
