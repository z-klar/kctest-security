package cz.zk.kctest_secure;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class Service {

    public String ResponseOpenPut() {
        return("Response Open PUT ....");
    }


}
