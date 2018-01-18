package com.jindz.excel.util;

import java.io.File;

/**
 * Created by xie.xie on 2017/11/3.
 */
public class DeleteDirectory {
    /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    public static Boolean doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
