package cn.com.ailbb.service;

import java.util.Map;

/**
 * Created by xzl on 2017/5/17.
 */
public interface ErrorAnalysisService {

    Map<String, Object> getErrCodeValueRate(Map<String, String> map);
}
