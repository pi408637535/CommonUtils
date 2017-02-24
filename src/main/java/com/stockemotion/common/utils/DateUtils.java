package com.stockemotion.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by wode4 on 2016/10/18.
 */
public class DateUtils
{
    private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("MM/dd yyyy");

    private static final SimpleDateFormat SDF_MIN = new SimpleDateFormat("HH:mm");

    private static final SimpleDateFormat SDF_SENCEND = new SimpleDateFormat("HH:mm:ss");

    private static final SimpleDateFormat SDF_DATE_TIME = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final SimpleDateFormat SDF_MD = new SimpleDateFormat("MM/dd");

    private static final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat SDF_YMDHM = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private static final SimpleDateFormat SDF_YMDHMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat SDF_HOUR = new SimpleDateFormat("HH");

    private static String defaultDatePattern = "yyyy-MM-dd HH:mm";

    public static String continueTime(Long startTime, Long... endTime)
    {
        Date now = new Date();
        long l = now.getTime() - startTime;
        if (endTime.length > 0)
        {
            l = endTime[0] - startTime;
        }
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);

        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return convert(hour) + ":" + convert(min) + ":" + convert(s);
    }

    public static String convert(long hms)
    {
        String str = "";
        if (hms < 10)
        {
            str = "0" + hms;
        }
        else
        {
            str = ObjectUtils.toString(hms);
        }
        return str;
    }

    public static String continueSecTime(Long startTime, Long... endTime)
    {
        Date now = new Date();
        long l = now.getTime() - startTime;
        if (endTime.length > 0)
        {
            l = endTime[0] - startTime;
        }
        long s = l / (1000);
        return ObjectUtils.toString(s);
    }

    public static String long2DateTime(Long currentTime)
    {
        String date = SDF_DATE_TIME.format(new Date(currentTime));
        return date;
    }

    public static String long2YMDHMS(Long currentTime)
    {
        String date = SDF_YMDHMS.format(new Date(currentTime));
        return date;
    }

    public static String long2YMDHM(Long currentTime)
    {
        String date = SDF_YMDHM.format(new Date(currentTime));
        return date;
    }

    public static String long2YMDHMSS(Long currentTime)
    {
        String date = SDF_YMDHMSS.format(new Date(currentTime));
        return date;
    }

    public static String long2Date(Long time) {
        String date = "";
        if (isCurrentYear(new Date(time))) {
            date = SDF_MD.format(new Date(time));
        } else {
            date = SDF_DATE.format(new Date(time));
        }

        return date;
    }

    public static String long2DateStandard(Long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long lt = null;
        if(time == null)
            lt = System.currentTimeMillis();
        else
            lt = new Long(time);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String long2YMD(Long time)
    {
        String date = "";
        if (isCurrentYear(new Date(time)))
        {
            date = SDF_MD.format(new Date(time));
        }
        else
        {
            date = SDF_DATE.format(new Date(time));
        }
        return date;
    }

    public static String long2YMD1(Long time)
    {
        String date = "";
        date = SDF_YMD.format(new Date(time));
        return date;
    }

    public static String long2HM(Long time)
    {
        String hm = SDF_MIN.format(new Date(time));
        return hm;
    }

    public static String long2HMS(Long time)
    {
        String hm = SDF_SENCEND.format(new Date(time));
        return hm;
    }

    public static String long2H(Long time)
    {
        String h = SDF_HOUR.format(new Date(time));
        return h;
    }

    public static boolean isCurrentYear(Date otherDate)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR) - 1900;
        calendar.setTime(otherDate);
        int otherYear = calendar.get(Calendar.YEAR) - 1900;
        if (currentYear != otherYear)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static String long2StrDate(Long time)
    {
        if (time == null)
        {
            return "时间未知！";
        }
        Integer interMin = sec2Min(long2Seconds(System.currentTimeMillis()) - long2Seconds(time));
        if (interMin <= 5)
        {
            return "刚刚";
        }
        else if (interMin <= 30 && interMin > 5)
        {
            return interMin + "分钟前";
        }
        else if (interMin > 30)
        {
            if (time < getDateRange(-1))
            {
                return long2YMD(time);
            }
            else
            {
                return long2HM(time);
            }
        }
        else
        {
            return long2YMD(time);
        }
    }

    /**
     * 获取时间的秒数
     *
     * @return
     */
    public static Integer long2Seconds(Long time)
    {
        return Long.valueOf(time / 1000l).intValue();
    }

    /**
     * 秒数转分钟
     *
     * @param seconds
     * @return
     */
    private static Integer sec2Min(Integer seconds)
    {
        Integer min = seconds / 60;
        return min;
    }

    /**
     * 秒数转小时
     *
     * @param seconds
     * @return
     */
    private static Integer sec2Hour(Integer seconds)
    {
        Integer hour = seconds / 60 / 60;
        return hour;
    }

    public static String sec2HMS(Integer seconds)
    {
        StringBuilder HMS = new StringBuilder();
        Integer hour = sec2Hour(seconds);
        Integer min = sec2Min(seconds) % 60;
        Integer sec = seconds % 60;
        HMS.append(hour > 9 ? hour : ("0" + hour)).append(":").append(min > 9 ? min : ("0" + min)).append(":")
            .append(sec > 9 ? sec : ("0" + sec));
        return HMS.toString();
    }

    public static Integer checkDate(Long time)
    {
        Long yesterday = getDateRange(-1); //昨天24点
        Long beforeDay = getDateRange(-2); //前天24点
        Long ago = getDateRange(-3);
        if (time >= yesterday)
        {
            return 0; //今天
        }
        else if (time < beforeDay && time > ago)
        {
            return 2; //前天
        }
        else if (time <= ago)
        {
            return 3; //更早
        }
        else
        {
            return 1; //昨天
        }
    }

    /**
     * 获取24点时间，range（0今天，-1昨天，-2前天）
     *
     * @param range
     * @return
     */
    public static Long getDateRange(Integer range)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(calendar.DATE, range);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取24点时间，range（0今天，-1昨天，-2前天）
     *
     * @param range
     * @return
     */
    public static Date getDate(Integer range)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(calendar.DATE, range);
        return calendar.getTime();
    }

    public static final Timestamp getCurrentTimestamp()
    {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }

    public static final String getCurrentFormatTime(String format)
    {
        Date nowTime=new Date();
        //  System.out.println(nowTime);
        SimpleDateFormat time=new SimpleDateFormat(format);
        return  time.format(nowTime);
    }

    public static final Date getSpecialFormatTime(String format) throws ParseException {
        SimpleDateFormat sdf =new SimpleDateFormat(format);
        Date date = sdf.parse(format);
        return date;
    }
    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern()
    {
        return defaultDatePattern;
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException
    {
        return org.apache.commons.lang3.StringUtils.isBlank(strDate) ? null : parse(strDate, getDatePattern());
    }
    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException
    {
        return org.apache.commons.lang3.StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(
                pattern).parse(strDate);
    }

    public static void main(String[] args)
    {
        Long timestamp = System.currentTimeMillis();
        System.out.println(DateUtils.long2Date(timestamp));

    }
}
