package nl.teuno.testcontainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class JmsService {

    Logger logger = LoggerFactory.getLogger(JmsService.class);
    private final JmsTemplate jmsTemplate;
    private final String QUEUE_NAME = "testcontainers";

    public JmsService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = QUEUE_NAME)
    public void onMessage(final Message<String> message) {
        String body = message.getPayload();
        logger.info(body);
    }

    public boolean sendMessage(String body) {
        try {
            jmsTemplate.convertAndSend(QUEUE_NAME, body);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
