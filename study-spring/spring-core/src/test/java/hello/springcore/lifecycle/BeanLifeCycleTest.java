package hello.springcore.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            System.out.println("NetworkClient Bean created.");
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring:dev");
            return networkClient;
        }

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient2 networkClient2() {
            System.out.println("NetworkClient Bean created.");
            NetworkClient2 networkClient2 = new NetworkClient2();
            networkClient2.setUrl("http://hello-spring:dev");
            return networkClient2;
        }
    }
}
