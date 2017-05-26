package cn.com.ailbb.interfaces;

import cn.com.ailbb.obj.RealTimeOnlineData1;
import cn.com.ailbb.obj.RealTimeOnlineData2;
import cn.com.ailbb.obj.RealTimeOnlineData3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by WildMrZhang on 2017/3/15.
 */
public
interface RealTimeOnlineInterface {
//    /**
//     *
//     * @return
//     */
//    RealTimeOnlineData1 handler1();
      RealTimeOnlineData1 handler1(Map<String,String> params);

//    /**
//     *
//     * @param index 枚举值 : 'healthy', 'hit_rate', 'data_rate'
//     * @param IPtype 枚举值 : 'data_center', 'user'
//     * @return
//     */
//    RealTimeOnlineData2 handler2(String index, String IPtype);

    RealTimeOnlineData2 handler2(Map<String,String> params);

//    /**
//     *
//     * @param index 枚举值 : 'healthy', 'hit_rate', 'data_rate'
//     * @param timeType 枚举值 : '3hours', 'today', '7days', '30days', 'custom'
//     * @param startTime 当timeType为'custom'时取值，格式待定
//     * @param endTime 当timeType为'custom'时取值，格式待定
//     * @return
//     */
//    RealTimeOnlineData3 handler3(String index, String timeType, String startTime, String endTime);

    RealTimeOnlineData3 handler3(Map<String,String> params);

//    public RealTimeOnlineData3 handler4(Map<String,String> params);
}
