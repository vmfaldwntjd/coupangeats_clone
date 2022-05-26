package com.example.demo.src.event.query;

public class EventQuery {
    public static String getEventTopListQuery = "select E.event_id, event_image_url from (\n" +
            "    SELECT event_id,\n" +
            "           status\n" +
            "    FROM event\n" +
            "    WHERE is_top = 1 AND status = 1\n" +
            ") E join (\n" +
            "    SELECT event_id,\n" +
            "           image_id,\n" +
            "           url as event_image_url\n" +
            "    FROM event_image\n" +
            "    WHERE image_id = 1\n" +
            ") EI on E.event_id = EI.event_id ;";
}
