package com.bis.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.bis.common.area.Point;
import com.bis.common.area.Polygon;
import com.bis.common.conf.Params;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util {

    private static final Logger LOG = Logger.getLogger(Util.class);

    private Util() {

    }

    /**
     * 判断是否是MAC地址格式
     * 
     * @param mac
     * @return
     */
    public static boolean isMac(String mac) {
        boolean result = false;
        // 正则校验MAC合法性
        String patternMac = "^[A-F0-9]{2}([:-]{1}[A-F0-9]{2}){5}$";
        if (Pattern.compile(patternMac).matcher(mac).find()) {
            result = true;
        }
        return result;
    }

    /**
     * 
     * @Title: isIp
     * @Description: 判断是否是ip地址格式
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {
        boolean result = false;
        // 正则校验ip合法性
        String patternIp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        if (Pattern.compile(patternIp).matcher(ip).find()) {
            result = true;
        }
        return result;
    }

    /**
     * 根据格式判断是ip或者mac进行转换
     * 
     * @param macOrIp
     * @return
     */
    public static String convertMacOrIp(String macOrIp) {
        String result = "";
        if (!StringUtils.isEmpty(macOrIp)) {
            String macOrIpt = macOrIp.toUpperCase().trim();
            if (isMac(macOrIpt)) {
                result = convertMac(macOrIpt);
            } else {
                result = convertIp(macOrIpt);
            }
        }
        return result.trim();
    }

    /**
     * mac转换16进制
     * 
     * @param mac
     * @return
     */
    public static String convertMac(String mac) {
        String result = mac.replaceAll("-", "");
        result = result.replaceAll(":", "");
        result = result.toLowerCase();
        return result;
    }

    /**
     * 16进制转换成ip地址
     * 
     * @param ip
     * @return
     */
    public static String convert(String ip) {
        Integer te = Integer.valueOf(ip, 16);
        int i = te.intValue();

        return (i >> 24 & 0xFF) + '.' + ((i >> 16) & 0xFF) + String.valueOf('.') + ((i >> 8) & 0xFF) + '.' + (i & 0xFF);
    }

    /**
     * ip地址转换16进制
     * 
     * @param ip
     * @return
     */
    public static String convertIp(String ip) {
        String[] iplist = ip.split("\\.");
        int ip0 = Integer.parseInt(iplist[0]);
        int ip1 = Integer.parseInt(iplist[1]);
        int ip2 = Integer.parseInt(iplist[2]);
        int ip3 = Integer.parseInt(iplist[3]);
        String result = Integer.toHexString((ip0 << 24) + (ip1 << 16) + (ip2 << 8) + ip3);
        for (int i = result.length(); i < 8; i++) {
            result = '0' + result;
        }
        return result;
    }

    /**
     * 日期型转指定格式字符型
     * 
     * @param date
     *            日期
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 时间戳转指定格式字符型
     * 
     * @param timestamp
     *            时间戳
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(long timestamp, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(timestamp);
    }

    /**
     * 时间字符型转个时间戳
     * 
     * @param timestamp
     *            时间戳
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long dateFormatStringtoLong(String data, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date da = null;
        try {
            da = formatter.parse(data);
        } catch (ParseException e) {
            Logger.getLogger(Util.class).info(e);
        }
        return da.getTime();
    }

    /**
     * 字符型日期转指定格式字符型
     * 
     * @param date
     *            日期字符串
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date dateStringFormat(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = formatter.parse(date);
        } catch (Exception e) {
            Logger.getLogger(Util.class).info(e);
        }
        return result;
    }

    /**
     * @Title: getRangeDay @Description: 获取两个时间之间的日期数组，格式由参数format决定 @param
     *         start 开始时间 @param end 结束时间 @param format 日期格式 @return List
     *         <String> @throws
     */
    public static List<String> getRangeDay(Date start, Date end, String format) {
        // 返回值
        List<String> result = new ArrayList<String>();
        // 日历型 用于日期加法，设置起始为start
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        Date tempDate = new Date(start.getTime());
        // 当起始日期小于等于结束日期，运行循环体内逻辑
        while (tempDate.getTime() <= end.getTime()) {
            // 将该起始日期转换格式后添加进返回数组中
            result.add(dateFormat(start, format));
            // 日期加1后 作为新的起始日期
            c.add(Calendar.DATE, 1);
            tempDate = c.getTime();
        }

        return result;
    }

    /**
     * @Title: getMinute
     * @Description:秒转为分，保留两位小数
     * @param time
     * @param size
     * @return
     */
    public static String getMinute(long time, int size) {
        if (size == 0) {
            return "0";
        } else {
            float b = ((float)time / 1000) / 60;
            float averageTime = b / size;
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(averageTime);
        }

    }

    /**
     * 
     * @Title: getTwoPointNumber
     * @Description: float保留两位小数
     * @param f
     * @return
     */
    public static float getTwoPointNumber(float f) {
        if (f == 0) {
            return 0;
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            return Float.parseFloat(df.format(f));
        }
    }

    /**
     * 
     * @Title: sToM
     * @Description: 秒转化为带两位小数点的分
     * @param f
     * @return
     */
    public static float sToM(float f) {
        if (f == 0) {
            return 0;
        } else {
            int a = (int) (f / 60);
            float b = (f % 60) / 60;
            DecimalFormat df = new DecimalFormat("0.00");
            return a + Float.parseFloat(df.format(b));
        }
    }

    /**
     * 
     * @Title: getAge
     * @Description: 生日转化为岁数
     * @param birthDate
     * @return
     */
    public static int getAge(Date birthDate) {
        if (birthDate == null)
            throw new RuntimeException("出生日期不能为null");

        int age = 0;

        Date now = new Date();

        SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_M = new SimpleDateFormat("MM");

        String birth_year = format_y.format(birthDate);
        String this_year = format_y.format(now);

        String birth_month = format_M.format(birthDate);
        String this_month = format_M.format(now);

        // 初步，估算
        age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

        // 如果未到出生月份，则age - 1
        if (this_month.compareTo(birth_month) < 0)
            age -= 1;
        if (age < 0)
            age = 0;
        return age;
    }

    /**
     * 
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param flag
     *            时间段标识，1：day，2：mouth
     * @return
     */
    public static Map<String, Object> getPeriodList(String startTime, String endTime, int flag) {
        Map<String, Object> result = new HashMap<String, Object>(2);

        Date start = Util.dateStringFormat(startTime, Params.YYYYMMDDHHMMSS);
        Date end = Util.dateStringFormat(endTime, Params.YYYYMMDDHHMMSS);

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        Date tmpDate = cal.getTime();
        long endTimeInSec = end.getTime();

        if (flag == 1) {
            long time = tmpDate.getTime();
            String keyTime;
            Boolean b = tmpDate.before(end);
            while (b || time == endTimeInSec) {
                b = tmpDate.before(end);
                keyTime = Util.dateFormat(cal.getTime(), Params.YYYYMMDD2);
                result.put(keyTime, 0);
                cal.add(Calendar.DATE, 1);
                tmpDate = cal.getTime();
            }
        } else if (flag == 2) {
            long time = tmpDate.getTime();
            String keyTime;
            Boolean b = tmpDate.before(end);
            while (b || time == endTimeInSec) {
                b = tmpDate.before(end);
                keyTime = Util.dateFormat(cal.getTime(), Params.YYYYMMM);
                result.put(keyTime, 0);
                cal.add(Calendar.MONTH, 1);
                tmpDate = cal.getTime();
            }
        }
        return result;
    }

    /**
     * 
     * @Title: getPeriodMonthList
     * @Description: 得到一定时间段的月度驻留统计表名
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getPeriodMonthList(String startTime, String endTime) {
        Date start = Util.dateStringFormat(startTime, Params.YYYYMMM);
        Date end = Util.dateStringFormat(endTime, Params.YYYYMMM);
        List<String> dateList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        String keyTime;
        while (!cal.getTime().after(end)) {
            keyTime = Util.dateFormat(cal.getTime(), Params.YYYYMM);
            dateList.add(Params.SHOPLOCATION + keyTime);
            cal.add(Calendar.MONTH, 1);
        }

        return dateList;
    }

    /**
     * 
     * @Title: getLastNumMonths
     * @Description: 取得过去num个月的指定格式数组
     * @param num
     * @param format
     * @return
     */
    public static String[] getLastNumMonths(int num, String format) {
        Calendar cal = Calendar.getInstance();
        String[] result = new String[num];
        for (int i = num - 1; i >= 0; i--) {
            cal.add(Calendar.MONTH, -1);
            String str = Util.dateFormat(cal.getTime(), format);
            result[i] = str;
        }
        return result;
    }

    public static String getDouble(int count, int allcount) {
        DecimalFormat df = new DecimalFormat("######0.00");
        String result = df.format((((float) (allcount / count)) * 2 / 60));
        return result;
    }

    /**
     * 
     * @Title: getLastNumDays
     * @Description: 取得过去num天的指定格式数组
     * @param num
     * @param format
     * @return
     */
    public static String[] getLastNumDays(int num, String format) {
        Calendar cal = Calendar.getInstance();
        String[] result = new String[num];
        for (int i = num - 1; i >= 0; i--) {
            cal.add(Calendar.DATE, -1);
            String str = Util.dateFormat(cal.getTime(), format);
            result[i] = str;
        }
        return result;
    }

    /**
     * @Title: getSHA256
     * @Description: SHA256加密
     * @param msg
     *            要加密的信息
     * @return
     */
    public static String getSHA256(String msg) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(msg.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;

    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * @Title: downFile
     * @Description: 从FTP服务器下载文件
     * @param ip
     *            FTP服务器ip
     * @param port
     *            FTP端口
     * @param username
     *            账号
     * @param password
     *            密码
     * @param remotePath
     *            FTP路径（例：\\：默认FTP根目录，\\SVA\\：FTP根目录下SVA文件夹）
     * @param fileName
     *            下载文件名称
     * @param localPath
     *            保存本地下载路径
     * @param type
     *            0：下载对应路径在的文件 1：下载对应路径下的所有文件
     * @return
     */
    public static boolean downFtpFile(String ip, int port, String username, String password, String remotePath,
            String fileName, String localPath, int type) {
        boolean success = false;
        LOG.debug("FTP param ip:" + ip + ",port:" + port + ",userName:" + username + ",password:" + password
                + ",remotePath:" + remotePath + ",fileName:" + fileName + ",localPath:" + localPath + ",type:" + type);
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.setConnectTimeout(50000);
            ftp.connect(ip, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                LOG.debug("FTP is reply:" + reply);
                System.out.println("FTP is reply:" + reply);
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            // ftp.enterLocalPassiveMode();
            FTPFile[] fs = ftp.listFiles();
            LOG.debug("FTP FTPFile size" + fs.length);
            for (FTPFile ff : fs) {
                if (type == 0) {
                    if (ff.getName().equals(fileName)) {
                        File localFile = new File(localPath + "/" + ff.getName());
                        if (!localFile.exists()) {
                            localFile.createNewFile();
                        }
                        OutputStream is = new FileOutputStream(localFile);
                        ftp.retrieveFile(ff.getName(), is);
                        is.close();
                    }
                } else {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftp.logout();
            success = true;
        } catch (IOException e) {
            success = false;
            LOG.error("FTP ERROR:" + e);
            System.out.println("FTP ERROR:" + e.toString());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    LOG.error("FTP close ftp error:" + ioe);
                }
            }
        }
        LOG.debug("FTP result:" + success);
        return success;
    }

    public static void deleteAll(File file) {

        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteAll(files[i]);
                files[i].delete();
            }
        }
    }

    public static boolean isInArea(Point point, String pointsArray)
    {
        JSONArray jsonArray = JSONArray.fromObject(pointsArray);
        List<Point> pointList = new ArrayList<Point>();
        for (int i = 0; i < jsonArray.size(); i++)
        {
          JSONObject pointJsonObject = (JSONObject)jsonArray.get(i);
          double x = pointJsonObject.getDouble("x");
          double y = pointJsonObject.getDouble("y");
          pointList.add(new Point(Double.valueOf(x), Double.valueOf(y)));
        }
        Polygon polygon = Polygon.initPolygon(pointList);
        if (polygon.contains(point)) {
          return true;
        }
      return false;
    }
    
}