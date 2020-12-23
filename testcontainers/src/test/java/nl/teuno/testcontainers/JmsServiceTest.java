package nl.teuno.testcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class JmsServiceTest {

    @Autowired
    private JmsService jmsService;

    /**
     * The service AMQP port to expose in the test.
     */
    private static final int ARTEMIS_PORT = 61616;
    /**
     * This name should exactly match the service in the docker compose.
     * If it does not match the container will not start!
     */
    public static final String ARTEMIS_NAME = "artemismq";

    /**
     * Start the docker compose file.
     * The services are not allowed to have a container_name property specified!
     */
    @Container
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("src/docker/docker-compose-artemis.yml"))
                    .withExposedService(ARTEMIS_NAME, ARTEMIS_PORT); //multiple exposed is allowed

    @DynamicPropertySource
    static void activemqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.artemis.port", () -> environment.getServicePort(ARTEMIS_NAME, ARTEMIS_PORT));
        registry.add("spring.artemis.host", () -> environment.getServiceHost(ARTEMIS_NAME, ARTEMIS_PORT));
    }

    @Autowired
    private JmsTemplate jmsTemplate;
    private final String QUEUE_NAME = "testcontainers";


    private int countMessages() {
        return jmsTemplate.browse(QUEUE_NAME, (session, browser) -> Collections.list(browser.getEnumeration())
                .size());
    }

    @Test
    void testSendingJMS() {
        String testMessage = "testSendingJMS ";
        boolean isSend = jmsService.sendMessage(testMessage);
        assertThat(isSend).isTrue();
    }

    @Test
    void testListeningForMessages() {
        String testMessage = "testListeningForMessages ";
        for (int i = 1; i < 11; i++)
            jmsService.sendMessage(testMessage + i);

        while (countMessages() > 0)
            System.out.println("number of pending messages: " + countMessages());

        assertThat(countMessages()).isZero();
    }
}
