package kristiania.enterprise.exam.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
* NOTE: Copied from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/c3c3ef72b2d123f6848fff249cda4d943a2ed87a/intro/spring/jsf/src/main/java/org/tsdes/intro/spring/jsf/RedirectForwardHandler.java
* */

@Controller
public class RedirectForwardHandler {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String forward(){
        return "forward:index.xhtml";
    }
}