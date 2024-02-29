package alekseybykov.pets.mg.gui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author bykov.alexey
 * @since 17.02.2021
 */
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"alekseybykov.pets.mg"})
public class ApplicationConfig { }
