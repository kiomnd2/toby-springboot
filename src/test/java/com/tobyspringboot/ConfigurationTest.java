package com.tobyspringboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ConfigurationTest {

    @Test
    void configuration() {
//        Assertions.assertThat(new Common()).isSameAs(new Common()); // 당연히 실패
//
//        MyConfig myConfig = new MyConfig();
//
//        Bean1 bean1 = myConfig.bean1();
//        Bean2 bean2 = myConfig.bean2();
//
//        Assertions.assertThat(bean1.common).isNotEqualTo(bean2.common); // 당연히 다른 값이 나옴 실패
//

        // 기본적으로 proxyBeanMethods 가 true 인 경우
//        //
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(MyConfig.class );
        ac.refresh();

        Bean1 bean = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        // 프록시에서 캐시해서 사용하기 때문
        Assertions.assertThat(bean.common).isSameAs(bean2.common);
    }


    @Test
    void proxyCommonMethod() {
        // 캐싱해서 사용
        MyConfigProxy myConfigProxy = new MyConfigProxy();
        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    // 스프링 어플리케이션 내에서 프록시 방법을 사용해 캐시해서 사용
    static class MyConfigProxy extends MyConfig {

        private Common common;

        @Override
        Common common() {
            if (common == null) common = super.common();
            return common;
        }
    }


    // 프록시로 만들어 캐시하지 않음
    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        public Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        public Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {
    }
}
