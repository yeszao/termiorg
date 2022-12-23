package org.termi.common.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;

public class TemplateUtil {
    public static String stringRender(String template, Map<String, Object> variables) {
        return render(template, variables, defaultStringTemplateEngine());
    }

    public static String fileRender(String template, Map<String, Object> variables) {
        return render(template, variables, defaultFileTemplateEngine());
    }

    public static String classpathRender(String template, Map<String, Object> variables) {
        return render(template, variables, defaultClasspathTemplateEngine());
    }

    public static String render(String template, Map<String, Object> variables, TemplateEngine engine) {
        Context context = new Context();
        variables.forEach(context::setVariable);
        return engine.process(template, context);
    }

    public static TemplateEngine defaultStringTemplateEngine() {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(new StringTemplateResolver());
        return templateEngine;
    }

    public static TemplateEngine defaultFileTemplateEngine() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("templates/widgets/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }

    public static TemplateEngine defaultClasspathTemplateEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/widgets/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }
}
