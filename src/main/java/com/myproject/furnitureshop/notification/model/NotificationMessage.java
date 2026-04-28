package com.myproject.furnitureshop.notification.model;

import com.myproject.furnitureshop.enums.NotificationChannels;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationMessage {
    private String recipient;
    private String subject;
    private String templateName;
    private NotificationChannels channel;
    private Map<String, Object> templateVariables;
}
