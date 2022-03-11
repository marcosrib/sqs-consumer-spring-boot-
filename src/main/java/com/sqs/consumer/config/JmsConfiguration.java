package com.sqs.consumer.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.simpleemail.model.transform.RawMessageStaxUnmarshaller;
import com.amazonaws.services.sqs.AmazonSQS;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.util.ErrorHandler;

@Slf4j
@EnableJms
@RequiredArgsConstructor
@Configuration
public class JmsConfiguration {


    private SQSConnectionFactory connectionFactory;
    private final AmazonSqsClient amazonSQS;
    @PostConstruct
    public void init() {
        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
       connectionFactory = new SQSConnectionFactory(providerConfiguration, amazonSQS.getClient());
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory);
        factory.setErrorHandler(new ErrorHandler() {
            @Override
            public void handleError(Throwable t) {
                log.error(t.getLocalizedMessage());
            }
        });
        factory.setDestinationResolver(new DynamicDestinationResolver());
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate() {
        return new JmsTemplate(this.connectionFactory);
    }
}

