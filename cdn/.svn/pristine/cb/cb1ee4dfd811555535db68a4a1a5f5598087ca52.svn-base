package cn.com.ailbb.server.dao;

import cn.com.ailbb.server.pojo.System;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
public interface SystemDao {
    /**
     * 获取系统配置信息
     * @return
     */
    System getSystemInfo();

    /**
     * 更新访问次数
     * @param visits
     * @return
     */
    long upDateVisits(long visits);

    /**
     * 更新版本
     * @param version
     * @return
     */
    double upDateVersion(double version);

    /**
     * 更新系统语言
     * @param languageType
     * @return
     */
    String upDateLanguageType(String languageType);

    /**
     * 更新最小记录访问间隔时间
     * @param minAccessTime
     * @return
     */
    int upDateMinAccessTime(int minAccessTime);

    /**
     * 更新最小请求数据库系统版本信息时间
     * @param minHandlerTime
     * @return
     */
    int upDateMinHandlerTime(int minHandlerTime);
}
