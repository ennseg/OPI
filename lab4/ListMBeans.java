import javax.management.*;
import javax.management.remote.*;
import java.util.*;

public class ListMBeans {
    public static void main(String[] args) throws Exception {
        String url = "service:jmx:remote+http://localhost:9990";
        Map<String, Object> env = new HashMap<>();
        env.put("org.jboss.remoting-jmx.timeout", "60");
        String[] creds = {"admin", "Admin#1234"};
        env.put(JMXConnector.CREDENTIALS, creds);

        System.out.println("Connecting to " + url + " ...");
        try (JMXConnector c = JMXConnectorFactory.connect(new JMXServiceURL(url), env)) {
            MBeanServerConnection mbs = c.getMBeanServerConnection();
            System.out.println("Connected. MBean count: " + mbs.getMBeanCount());
            System.out.println("\n--- All domains ---");
            for (String d : mbs.getDomains()) System.out.println("  " + d);

            System.out.println("\n--- com.example:* ---");
            Set<ObjectName> names = mbs.queryNames(new ObjectName("com.example:*"), null);
            if (names.isEmpty()) System.out.println("  (none found)");
            else names.forEach(n -> System.out.println("  " + n));

            System.out.println("\n--- java.lang:* (first 5) ---");
            mbs.queryNames(new ObjectName("java.lang:*"), null)
               .stream().limit(5).forEach(n -> System.out.println("  " + n));
        }
    }
}
