package cn.com.ailbb.server.impl;

import cn.com.ailbb.manage.SystemManage;
import cn.com.ailbb.server.dao.SystemDao;
import cn.com.ailbb.server.mapper.SystemMapper;
import org.springframework.stereotype.Service;
import cn.com.ailbb.server.pojo.System;

import javax.annotation.Resource;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
@Service(value="systemDao")
public class SystemDaoImpl implements SystemDao {
    @Resource
    private SystemMapper systemMapper;

    @Override
    public synchronized System getSystemInfo() {
        return systemMapper.getInfo();
    }

    @Override
    public synchronized long upDateVisits(long visits) {
        systemMapper.upDateVisits(visits);
        return visits;
    }

    @Override
    public synchronized double upDateVersion(double version) {
        systemMapper.upDateVersion(version);
        return version;
    }

    @Override
    public String upDateLanguageType(String languageType) {
        systemMapper.upDateLanguageType(languageType);
        return languageType;
    }

    @Override
    public int upDateMinAccessTime(int minAccessTime) {
        systemMapper.upDateMinAccessTime(minAccessTime);
        return minAccessTime;
    }

    @Override
    public int upDateMinHandlerTime(int minHandlerTime) {
        systemMapper.upDateMinHandlerTime(minHandlerTime);
        return minHandlerTime;
    }

}
