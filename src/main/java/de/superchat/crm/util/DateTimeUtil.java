package de.superchat.crm.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateTimeUtil {
    private DateTimeUtil() {
    }

    private static ZoneOffset defaultZone=ZoneOffset.UTC;

    public static  long now()
    {
        return LocalDateTime.now().toEpochSecond(defaultZone);
    }
    public static LocalDateTime fromEpochInSecond(long epochInSecond)
    {
      return  LocalDateTime.ofEpochSecond(epochInSecond,0,defaultZone);
    }

}
