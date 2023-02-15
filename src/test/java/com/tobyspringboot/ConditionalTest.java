package com.tobyspringboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class ConditionalTest {

    @Test
    void conditional() {
        // true
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration(Config1.class)
                .run(context -> {
                    Assertions.assertThat(context).hasSingleBean(Config1.class);
                    Assertions.assertThat(context).hasSingleBean(MyBean.class);
                });

//        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
//        ac.register(Config1.class);
//        ac.refresh();
//
//        MyBean bean = ac.getBean(MyBean.class);

        // false
//        AnnotationConfigWebApplicationContext ac2 = new AnnotationConfigWebApplicationContext();
//        ac2.register(Config2.class);
//        ac2.refresh();
//
//        MyBean bean2 = ac2.getBean(MyBean.class);
//
        contextRunner.withUserConfiguration(Config2.class)
                .run(context -> {
                    Assertions.assertThat(context).doesNotHaveBean(Config2.class);
                    Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
                });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface BooleanConditional{
        boolean value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(TrueCondition.class)
    @interface TrueConditional{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(FalseCondition.class)
    @interface FalseConditional{}


    @Configuration
    @BooleanConditional(true)
    static class Config1 {

        @Bean
        MyBean myBean() {
            return new MyBean();
        }

    }

    @Configuration
    @BooleanConditional(false)
    static class Config2{
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    static class MyBean {}

    static class TrueCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }

    static class FalseCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }

    static class BooleanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            return (Boolean) annotationAttributes.get("value");
        }
    }
}
