package com.devoteam.dls.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author Devoteam Munich,Germany, work students (Besmir Beka, Bastien
 *         Thibaud).
 * 
 * @version 1.0.
 * @since 06.08.2018.
 *
 *
 */
public class SecurityContextNotFoundException extends AuthenticationException {

    public SecurityContextNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public SecurityContextNotFoundException(String msg) {
        super(msg);
    }
}
