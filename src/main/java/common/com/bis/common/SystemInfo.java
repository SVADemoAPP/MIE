package com.bis.common;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

public class SystemInfo {
    private static final Logger LOG = Logger.getLogger(SystemInfo.class);
    public static long memory() {
        Sigar sigar = null;
        Mem mem = null;
        try {
            if (sigar==null) {
                sigar = new Sigar();
            }
            mem = sigar.getMem();
           
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return mem.getUsed() * 100 / mem.getTotal();
    }

    public static int cpu() {
        Sigar sigar = null;
        int combined = 0;
        int count = 0;
        try {
            if (sigar==null) {
                sigar = new Sigar(); 
            }
            CpuPerc cpuList[] = null;
            cpuList = sigar.getCpuPercList();
            for(CpuPerc cpuPerc : cpuList){
                combined += cpuPerc.getCombined();
                count++;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        if (combined==0) {
            combined = (int) (0.9*count);
        }
        return combined*100/count;
    }

    public static int file(){
        Sigar sigar = null;
        FileSystem fslist[] = null;
        try {
            if (sigar==null) {
                sigar = new Sigar(); 
            }
            fslist = sigar.getFileSystemList();
        } catch (Exception e) {
           LOG.error(e.getMessage());
        }
        int usePercent = 0;
        int count = 0;
        try {
        	for (int i = 0; i < fslist.length; i++) {
                FileSystem fs = fslist[i];
                FileSystemUsage usage = null;
                usage = sigar.getFileSystemUsage(fs.getDirName());
                switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    usePercent += usage.getUsePercent() * 100D;
                    count++;
                    break;
                case 3:// TYPE_NETWORK ：网络
                    break;
                case 4:// TYPE_RAM_DISK ：闪存
                    break;
                case 5:// TYPE_CDROM ：光驱
                    break;
                case 6:// TYPE_SWAP ：页面交换
                    break;
                }
        	}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage());
		}
        return usePercent/count;
    }
}
