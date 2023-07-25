package com.swirldslabs.voting.error;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FreemarkerErrorEndpoint implements ErrorController {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(FreemarkerErrorEndpoint.class);

    @ExceptionHandler(Exception.class)
    public String handleError(final Model model, final Exception exception) {
        logger.error("Error in handler", exception);

        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        model.addAttribute("stacktrace", sw.toString());
        return "error";
    }
}
