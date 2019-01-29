
package ppdm.core;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Nov 20, 2008
 */
public class TimeToStr{
  public static String secondsToString(long time){
    int seconds = (int)(time % 60);
    int minutes = (int)((time/60) % 60);
    int hours = (int)((time/3600) % 24);
    String secondsStr = (seconds<10 ? "0" : "")+ seconds;
    String minutesStr = (minutes<10 ? "0" : "")+ minutes;
    String hoursStr = (hours<10 ? "0" : "")+ hours;
    return new String(hoursStr + ":" + minutesStr + ":" + secondsStr);
  }
}