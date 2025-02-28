package org.example.expert.domain.common.constant;

public final class RegexpConst {

    public static final String EMAIL_REGEXP = "^[a-zA-Z0-9-_]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEXP = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
}
