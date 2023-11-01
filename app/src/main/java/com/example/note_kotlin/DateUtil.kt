package com.example.note_kotlin

import androidx.core.net.ParseException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 描述: 主要功能格式化时间工具类
 */
object DateUtil {
    // 日期格式年份，例如：2022，2023
    const val FORMAT_YYYY = "yyyy"
    const val FORMAT = "yyyy-MM-dd HH:mm:ss"

    // 其他格式常量...
    private val DEFAULT_TIMEZONE = TimeZone.getDefault()
    private val DefaultTimeZone = TimeZone.getDefault()

    /**
     * 获取当前时间的字符串表示
     *
     * @param pattern 时间格式
     * @return 当前时间的字符串表示
     */
    @JvmStatic
    fun getCurrentDate(pattern: String?): String {
        return formatToStr(Date(), pattern)
    }

    /**
     * 将时间戳格式化为指定格式的字符串
     *
     * @param timestamp 时间戳
     * @param pattern   时间格式
     * @return 格式化后的时间字符串
     */
    fun formatToStr(timestamp: Long, pattern: String?): String {
        return formatToStr(Date(timestamp), pattern)
    }

    /**
     * 将日期对象格式化为指定格式的字符串
     *
     * @param date    日期对象
     * @param pattern 时间格式
     * @return 格式化后的时间字符串
     */
    fun formatToStr(date: Date?, pattern: String?): String {
        val dateFormat = getDateFormat(pattern)
        return dateFormat.format(date)
    }

    /**
     * 获取指定格式的日期格式化对象
     *
     * @param pattern 时间格式
     * @return 日期格式化对象
     */
    private fun getDateFormat(pattern: String?): DateFormat {
        val dateFormat = SimpleDateFormat(pattern)
        dateFormat.timeZone = DEFAULT_TIMEZONE
        return dateFormat
    }

    /**
     * 格式化字符串时间为指定格式
     *
     * @param dateString 字符串时间
     * @param format     格式
     * @return 格式化后的时间字符串
     */
    fun formatStringDate(dateString: String?, format: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat = SimpleDateFormat(format)
        try {
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: java.text.ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取当前时间的日期对象
     *
     * @return 当前时间的日期对象
     */
    val currentTime: Date
        get() = Date()

    /**
     * 将日期对象格式化为指定格式的时间字符串
     *
     * @param date    日期对象
     * @param pattern 时间格式
     * @return 格式化后的时间字符串
     */
    fun formatTime(date: Date?, pattern: String?): String {
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(date)
    }

    /**
     * 解析指定格式的时间字符串为日期对象
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 解析后的日期对象
     * @throws ParseException 解析异常
     */
    @Throws(ParseException::class)
    fun parseTime(time: String?, pattern: String?): Date {
        val sdf = SimpleDateFormat(pattern)
        return try {
            sdf.parse(time)
        } catch (e: java.text.ParseException) {
            throw RuntimeException(e)
        }
    }

    /**
     * 计算两个日期之间的时间差，返回指定时间单位的差值
     *
     * @param date1     第一个日期对象
     * @param date2     第二个日期对象
     * @param timeUnit  时间单位
     * @return 时间差的差值
     */
    fun getTimeDifference(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
        val difference = date2.time - date1.time
        return timeUnit.convert(difference, TimeUnit.MILLISECONDS)
    }

    /**
     * 判断指定时间是否在给定时间区间内
     *
     * @param time      待判断的时间
     * @param startTime 时间区间的开始时间
     * @param endTime   时间区间的结束时间
     * @return 如果指定时间在时间区间内，返回 true；否则返回 false
     */
    fun isInTimeRange(time: Date, startTime: Date?, endTime: Date?): Boolean {
        return time.after(startTime) && time.before(endTime)
    }

    /**
     * 判断指定年份是否为闰年
     *
     * @param year 年份
     * @return 如果是闰年，返回 true；否则返回 false
     */
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * 获取指定日期对象的年份
     *
     * @param date 日期对象
     * @return 年份
     */
    fun getYearFromDate(date: Date?): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.YEAR]
    }

    /**
     * 获取指定日期对象的月份
     *
     * @param date 日期对象
     * @return 月份
     */
    fun getMonthFromDate(date: Date?): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.MONTH] + 1
    }

    /**
     * 获取指定日期对象的星期
     *
     * @param date 日期对象
     * @return 星期，1 表示星期一，2 表示星期二，依次类推
     */
    fun getWeekdayFromDate(date: Date?): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.DAY_OF_WEEK]
    }
}