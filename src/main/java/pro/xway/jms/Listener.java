package pro.xway.jms;

import pro.xway.controller.MainPage;
import pro.xway.jms.message.Message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

@MessageDriven
        (
                activationConfig =
                        {
                                @ActivationConfigProperty(
                                        propertyName = "destinationType",
                                        propertyValue = "javax.jms.Queue"
                                ),
                                @ActivationConfigProperty(
                                        propertyName = "destination",
                                        propertyValue = "java:jboss/jms/queue/cargo"
                                )
                        },
                mappedName = "java:jboss/jms/queue/cargo"
        )
public class Listener implements MessageListener {
    @Override
    public void onMessage(javax.jms.Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Serializable object = objectMessage.getObject();
            if (object instanceof Message) {
                Message contractMessage = (Message) object;
                sendMessageTo1C(contractMessage.getDescription());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    private void sendMessageTo1C(String message) {
        System.out.println(message);
    }

}
