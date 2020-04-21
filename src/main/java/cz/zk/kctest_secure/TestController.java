package cz.zk.kctest_secure;

import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    private final HttpServletRequest request;

    @Autowired
    public TestController(HttpServletRequest request) {
        this.request = request;
    }

    private final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private Service service;

    @PreAuthorize("permitAll()")
    @RequestMapping(value="/open", method=RequestMethod.PUT)
    public String handleOpenInfoRequest() {
        log.info("PUT: /Open");
        return  service.ResponseOpenPut();
    }

    @GetMapping(value = "/open")
    @PreAuthorize("hasRole('rtl_user')")
    public String handleOpenGetRequest(Model model) {
        log.info("GET: /open");
        return ("GET om Open processed!!!");
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('rtl_user')")
    public String handleUserInfoRequest(Model model) {
        log.info("GET: /Users");
        return ("Ringo, George, Paul, John");
    }

    @GetMapping(value = "/users/id")
    @PreAuthorize("hasRole('rtl_user')")
    public String handleUserId() {
        return  String.format("users/id: EmailVerified=%s", getEmailVerified());
    }

    @PreAuthorize("hasRole('rtl_admin')")
    @GetMapping(value = "/songs")
    public String handleSongsInfoRequest() {
        log.info("GET: /Songs");
        return ("Yellow submarine");
    }

    @PreAuthorize("permitAll()")
    @DeleteMapping(value = "/songs")
    public String handleSongsPutRequest() {
        return ("Songs DELETE request");
    }


    private boolean getEmailVerified() {
        KeycloakSecurityContext cont = getKeycloakSecurityContext();
        return cont.getToken().getEmailVerified();
    }

    /**
     * The KeycloakSecurityContext provides access to several pieces of information
     * contained in the security token, such as user profile information.
     */
    private KeycloakSecurityContext getKeycloakSecurityContext() {
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }

}
