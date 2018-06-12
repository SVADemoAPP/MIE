package com.bis.web.auth;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;  
import java.io.InputStreamReader;   
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.text.NumberFormat;

import org.apache.log4j.Logger;  

public class SystemInfo {
	private static final int CPUTIME = 500;   
    private static final int PERCENT = 100;   
    private static final int FAULTLENGTH = 10;
    private static final Logger LOG = Logger.getLogger(SystemInfo.class);
    	//获得cpu使用率   
    	public  int getCpuRatioForWindows() {   
             try {   
                 String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";  
                 // 取进程信息   
                 long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));   
                 Thread.sleep(CPUTIME);   
                 long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));   
                 if (c0 != null && c1 != null) {   
                     long idletime = c1[0] - c0[0];   
                     long busytime = c1[1] - c0[1];
                     int a = Double.valueOf(PERCENT * (busytime)*1.0 / (busytime + idletime)).intValue();
                     long t = System.currentTimeMillis();
                     return a;   
                 } else {   
                     return 0;   
                 }   
             } catch (Exception e) {   
                 e.printStackTrace(); 
                 LOG.error(e);
                 return 0;   
             }   
         }   
      
    	//读取cpu相关信息   
        private static long[] readCpu(final Process proc) {   
            long[] retn = new long[2];   
            try {   
                proc.getOutputStream().close();   
                InputStreamReader ir = new InputStreamReader(proc.getInputStream());   
                LineNumberReader input = new LineNumberReader(ir);   
                String line = input.readLine();   
                if (line == null || line.length() < FAULTLENGTH) {   
                    return null;   
                }   
                int capidx = line.indexOf("Caption");   
                int cmdidx = line.indexOf("CommandLine");   
                int rocidx = line.indexOf("ReadOperationCount");   
                int umtidx = line.indexOf("UserModeTime");   
                int kmtidx = line.indexOf("KernelModeTime");   
                int wocidx = line.indexOf("WriteOperationCount");   
                long idletime = 0;   
                long kneltime = 0;   
                long usertime = 0;   
                while ((line = input.readLine()) != null) {   
                    if (line.length() < wocidx) {   
                        continue;   
                    }   
                    // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,   
                    // ThreadCount,UserModeTime,WriteOperation   
                    String caption =substring(line, capidx, cmdidx - 1).trim();   
                    String cmd = substring(line, cmdidx, kmtidx - 1).trim();   
                    if (cmd.indexOf("wmic.exe") >= 0) {   
                        continue;   
                    }   
                    String s1 = substring(line, kmtidx, rocidx - 1).trim();   
                    String s2 = substring(line, umtidx, wocidx - 1).trim();   
                    if (caption.equals("System Idle Process") || caption.equals("System")) {   
                        if (s1.length() > 0)   
                            idletime += Long.valueOf(s1).longValue();   
                        if (s2.length() > 0)   
                            idletime += Long.valueOf(s2).longValue();   
                        continue;   
                    }   
                    if (s1.length() > 0)   
                        kneltime += Long.valueOf(s1).longValue();   
                    if (s2.length() > 0)   
                        usertime += Long.valueOf(s2).longValue();   
                }   
                retn[0] = idletime;   
                retn[1] = kneltime + usertime;   
                return retn;   
            } catch (Exception e) {   
                e.printStackTrace();
                LOG.error(e);
            } finally {   
                try {   
                    proc.getInputStream().close();   
                } catch (Exception e) {   
                    e.printStackTrace();
                    LOG.error(e);
                }   
            }   
            return null;   
        }   
      
        /**  
       * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：  
       * @author www.zuidaima.com  
       * @param src 要截取的字符串  
       * @param start_idx 开始坐标（包括该坐标)  
       * @param end_idx 截止坐标（包括该坐标）  
       * @return  
       */   
       private static String substring(String src, int start_idx, int end_idx) {   
	       byte[] b = src.getBytes();   
	       String tgt = "";   
	       for (int i = start_idx; i <= end_idx; i++) {   
	        tgt += (char) b[i];   
	       }   
	       return tgt;
      }   
    
        //获取内存使用情况
        public  int getEMS() {  
            StringBuffer sb=new StringBuffer();
            NumberFormat nf = NumberFormat.getInstance();
            OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();  
            //系统物理内存总计
            double a = (double)osmb.getTotalPhysicalMemorySize()/ 1024 / 1024/ 1024;
            //系统物理可用内存总计
            double b = (double)osmb.getFreePhysicalMemorySize()/ 1024 / 1024/ 1024;
            double c = (1-b/a)*100;
            int d = (int)c;
            return d;  
        }
        
        //获取磁盘使用情况
        public  int getDisk() {
        	SystemInfo si= new SystemInfo();
        	 long a = 0;
        	 long b = 0;
            // 获取工程所在路径
            String c = si.getClass().getResource("/").getPath().substring(1, 2);  
	        String dirName = c + ":/";  
	        File win = new File(dirName);
	        if (win.exists()) {  
	            long total = (long) win.getTotalSpace();  
	            long free = (long) win.getFreeSpace();  
	            a = a + total;
	            b = b+ free;
	        }  
            double compare = (1 - b * 1.0 / a) * 100;
            int d =(int) compare;
            return d;  
        }

}
