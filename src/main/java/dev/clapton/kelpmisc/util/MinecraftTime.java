package dev.clapton.kelpmisc.util;

public class MinecraftTime {
    public static final int MC_DAY = 24000;
    public static final int MC_HOUR = 1000;
    public static final float MC_MINUTE = (float) MC_HOUR / 60;

    public static String getDayTimeString (Long gameTime) {
        float time = gameTime + 6000; // add 6000 ticks because 0 ticks = 6AM

        int hours = (int) ((time % MC_DAY) / MC_HOUR);
        int minutes = (int) ((time % MC_HOUR) / MC_MINUTE);

        return String.format("%02d:%02d", hours, minutes);
    }
}
