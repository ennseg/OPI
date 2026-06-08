package com.example.MBeans;

import javax.management.*;

public class PointStats extends NotificationBroadcasterSupport implements PointStatsMBean {

    private int totalPoints = 0;
    private int missedPoints = 0;
    private int notificationSequence = 0;

    public void recordPoint(boolean success) {
        incrementTotalPoints();

        if (!success) {
            incrementMissedPoints();
        }

        if (getTotalPoints() % 15 == 0) {
            Notification n = new Notification("PointStats", this, ++notificationSequence, System.currentTimeMillis(), "Всего точек: " + totalPoints + ", значение кратно 15");
            sendNotification(n);
        }
    }

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getMissedPoints() {
        return missedPoints;
    }

    private void incrementTotalPoints() {
        totalPoints++;
    }

    private void incrementMissedPoints() {
        missedPoints++;
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(
                        new String[]{"PointStats"},
                        Notification.class.getName(),
                        "Количество точек стало кратным 15"
                )
        };
    }
}
