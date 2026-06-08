import javax.management.*;
import javax.management.remote.*;
import java.util.*;

public class ProbeMBeans {
    public static void main(String[] args) throws Exception {
        String url = "service:jmx:remote+http://localhost:9990";
        Map<String,Object> env = new HashMap<>();
        env.put(JMXConnector.CREDENTIALS, new String[]{"admin","Admin#1234"});
        try (JMXConnector c = JMXConnectorFactory.connect(new JMXServiceURL(url), env)) {
            MBeanServerConnection mbs = c.getMBeanServerConnection();

            System.out.println("=== PointStats ===");
            ObjectName ps = new ObjectName("com.example:type=PointStats");
            for (MBeanAttributeInfo a : mbs.getMBeanInfo(ps).getAttributes())
                System.out.println("  attr: " + a.getName() + " (" + a.getType() + ") R=" + a.isReadable() + " W=" + a.isWritable());
            System.out.println("  TotalPoints = " + mbs.getAttribute(ps, "TotalPoints"));
            System.out.println("  MissedPoints = " + mbs.getAttribute(ps, "MissedPoints"));

            System.out.println("=== Area ===");
            ObjectName ar = new ObjectName("com.example:type=Area");
            for (MBeanAttributeInfo a : mbs.getMBeanInfo(ar).getAttributes())
                System.out.println("  attr: " + a.getName() + " (" + a.getType() + ") R=" + a.isReadable() + " W=" + a.isWritable());
            System.out.println("  Area = " + mbs.getAttribute(ar, "Area"));
            System.out.println("  R    = " + mbs.getAttribute(ar, "R"));

            System.out.println("=== OS info via java.lang:type=OperatingSystem ===");
            ObjectName os = new ObjectName("java.lang:type=OperatingSystem");
            System.out.println("  Name    = " + mbs.getAttribute(os, "Name"));
            System.out.println("  Version = " + mbs.getAttribute(os, "Version"));
            System.out.println("  Arch    = " + mbs.getAttribute(os, "Arch"));
        }
    }
}
