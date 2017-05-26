package cn.com.ailbb.Dao;

import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/17.
 */
public interface ErrorAnalysisDao {

    Map<String, List<Map<String,Object>>> getErrCodeValueRate(Map<String, String> map);
}
