package com.myproject.furnitureshop.enums;

public enum NotificationTemplates {
    OTP_EMAIL("verify-otp-email")

    ;
    private final String templateName;

    NotificationTemplates(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return this.templateName;
    }
}
