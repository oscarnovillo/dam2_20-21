package log;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class mainLog {

    public static void main(String[] args) {
        log.error("error");
        log.info("info");
        log.debug("debug");
    }
}
