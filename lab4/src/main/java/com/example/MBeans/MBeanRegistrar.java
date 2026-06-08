package com.example.MBeans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import javax.management.*;
import java.lang.management.ManagementFactory;

@ApplicationScoped
public class MBeanRegistrar {

    public static PointStats pointStats;
    public static Area area;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object startupEvent) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            pointStats = new PointStats();
            ObjectName pointStatsName = new ObjectName(
                    "com.example:type=PointStats"
            );
            if (!mbs.isRegistered(pointStatsName)) {
                mbs.registerMBean(pointStats, pointStatsName);
            }

            area = new Area();
            ObjectName areaName = new ObjectName(
                    "com.example:type=Area"
            );
            if (!mbs.isRegistered(areaName)) {
                mbs.registerMBean(area, areaName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка регистрации MBean", e);
        }
    }
}