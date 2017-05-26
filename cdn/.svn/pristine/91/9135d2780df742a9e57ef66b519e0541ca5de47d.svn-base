package cn.com.ailbb.server.Provider;

/**
 * Created by xzl on 2017/5/22.
 */
import cn.com.ailbb.server.pojo.MenuInfo;
import org.apache.ibatis.jdbc.SQL;
import java.util.*;
public class MenuInfoSqlProvider {

    public String getMenuById(Map map){
        List<Integer> menulist = (List<Integer>) map.get("list");
        StringBuffer sqllimit =new StringBuffer("");
        for(int i = 0;i < menulist.size(); i++){
            sqllimit.append(String.valueOf(menulist.get(i)));
            if(i != menulist.size() -1){
                sqllimit.append(", ");
            }else{
                sqllimit.append(" )");
            }
        }
        return new SQL() {{
            SELECT("*");
            FROM("data2.cdn_menuinfo");
            WHERE("menuid in ("+sqllimit.toString());

        }}.toString();

  }
}
