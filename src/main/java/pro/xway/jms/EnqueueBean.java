package pro.xway.jms;

import pro.xway.jms.message.Message;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // Создаем еще одну транцакция, даже если создана
public class EnqueueBean {


    @Asynchronous // Создаем новый поток, чтоб быстрее отпустить клиента
    public void sendMessage(String message) {
        Connection connection = null;
        try {
            Message user = new Message(message); // Message должен быть Serializable
            connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            session.createProducer(cargoQueue).send(session.createObjectMessage(user));
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (JMSException e) {
                }
        }
    }


    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory factory; // Настройки WildFLY

    @Resource(mappedName = "java:/jms/queue/cargo")
    private Queue cargoQueue; // Настройки WildFLY


//    --------------------------------------------------------- Настройки  WildFLY (standalonefull.xml) --------------------------------------------


//    <subsystem xmlns="urn:jboss:domain:messaging-activemq:2.0">
//            <server name="default">
//                <security-setting name="#">
//                    <role name="guest" send="true" consume="true" create-non-durable-queue="true" delete-non-durable-queue="true"/>
//                </security-setting>
//                <address-setting name="#" dead-letter-address="jms.queue.DLQ" expiry-address="jms.queue.ExpiryQueue" max-size-bytes="10485760" page-size-bytes="2097152" message-counter-history-day-limit="10"/>
//                <address-setting name="jms.queue.cargo" redelivery-delay="30000" max-delivery-attempts="-1"/>
//                <http-connector name="http-connector" socket-binding="http" endpoint="http-acceptor"/>
//                <http-connector name="http-connector-throughput" socket-binding="http" endpoint="http-acceptor-throughput">
//                    <param name="batch-delay" value="50"/>
//                </http-connector>
//                <in-vm-connector name="in-vm" server-id="0">
//                    <param name="buffer-pooling" value="false"/>
//                </in-vm-connector>
//                <http-acceptor name="http-acceptor" http-listener="default"/>
//                <http-acceptor name="http-acceptor-throughput" http-listener="default">
//                    <param name="batch-delay" value="50"/>
//                    <param name="direct-deliver" value="false"/>
//                </http-acceptor>
//                <in-vm-acceptor name="in-vm" server-id="0">
//                    <param name="buffer-pooling" value="false"/>
//                </in-vm-acceptor>
//                <jms-queue name="ExpiryQueue" entries="java:/jms/queue/ExpiryQueue"/>
//                <jms-queue name="DLQ" entries="java:/jms/queue/DLQ"/>
//                <jms-queue name="cargo" entries="java:/jms/queue/cargo"/>
//                <connection-factory name="InVmConnectionFactory" entries="java:/ConnectionFactory" connectors="in-vm"/>
//                <connection-factory name="RemoteConnectionFactory" entries="java:jboss/exported/jms/RemoteConnectionFactory" connectors="http-connector"/>
//                <pooled-connection-factory name="activemq-ra" entries="java:/JmsXA java:jboss/DefaultJMSConnectionFactory" connectors="in-vm" transaction="xa"/>
//            </server>
//        </subsystem>
}
