package com.myproject.furnitureshop.enums;

public enum NotificationChannels {
    EMAIL("notification.email"),
    IN_APP("notification.inapp");

    private final String mqRouteKey;
    NotificationChannels(String mqRouteKey) {
        this.mqRouteKey = mqRouteKey;
    }

    public String getMqRouteKey() {
        return mqRouteKey;
    }
}
