package com.hippalus.accountingsystem;

import com.hippalus.accountingsystem.domain.models.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import java.math.BigDecimal;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ApplicationStartupConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final Environment env;

    public static Money LIMIT;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        LIMIT = Money.of(new BigDecimal(Objects.requireNonNull(env.getProperty("purchasingspecialist.limitValue"))));
    }
}