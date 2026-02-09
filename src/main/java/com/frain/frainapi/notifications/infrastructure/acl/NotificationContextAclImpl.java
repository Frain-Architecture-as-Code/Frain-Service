package com.frain.frainapi.notifications.infrastructure.acl;

import com.frain.frainapi.notifications.interfaces.acl.NotificationContextAcl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationContextAclImpl implements NotificationContextAcl {

    @Override
    public String sendNotification(String recipient, String sender, String message, String resourceId, String type) {
        return "";
    }
}
