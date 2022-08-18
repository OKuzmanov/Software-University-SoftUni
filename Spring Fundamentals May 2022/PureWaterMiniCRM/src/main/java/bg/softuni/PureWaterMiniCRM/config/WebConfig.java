package bg.softuni.PureWaterMiniCRM.config;

import bg.softuni.PureWaterMiniCRM.services.RegisterLoggerInterceptor;
import bg.softuni.PureWaterMiniCRM.services.MaintenanceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LocaleChangeInterceptor localeChangeInterceptor;
    private final MaintenanceInterceptor maintenanceInterceptor;
    private final RegisterLoggerInterceptor registerLoggerInterceptor;

    @Autowired
    public WebConfig(LocaleChangeInterceptor localeChangeInterceptor, MaintenanceInterceptor maintenanceInterceptor,
                     RegisterLoggerInterceptor registerLoggerInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
        this.maintenanceInterceptor = maintenanceInterceptor;
        this.registerLoggerInterceptor = registerLoggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(maintenanceInterceptor);
        registry.addInterceptor(registerLoggerInterceptor);
    }
}
