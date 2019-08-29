package org.billing.api.listeners;

import lombok.Getter;
import lombok.Setter;
import org.billing.api.entities.UserEntity;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Setter
@Getter
public class OnRegistrationCompleteListener extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserEntity userEntity;

    public OnRegistrationCompleteListener(UserEntity userEntity, Locale locale, String appUrl) {
        super(userEntity);
        this.userEntity = userEntity;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}

