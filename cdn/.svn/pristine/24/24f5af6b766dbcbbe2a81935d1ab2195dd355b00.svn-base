package cn.com.ailbb.server.Provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/26.
 */
public class UserInfoSqlProvider {

    public String getMenuById(Map map){
        List<Integer> groudidlist = (List<Integer>) map.get("list");
        StringBuffer sqllimit =new StringBuffer("");
        for(int i = 0;i < groudidlist.size(); i++){
            sqllimit.append(String.valueOf(groudidlist.get(i)));
            if(i != groudidlist.size() -1){
                sqllimit.append(", ");
            }else{
                sqllimit.append(" )");
            }
        }
        return new SQL() {{
            SELECT("*");
            FROM("data2.cdn_userinfo");
            WHERE("ugroupid in ("+sqllimit.toString());

        }}.toString();
    }

    public String updatePartUser(Map<String,Object> map){


       return  new SQL() {
           {
               String tmpsql ="";
               UPDATE("cdn_userinfo");
               for(String key : map.keySet()){
                   if(key !="username"){
                       if(key =="sex" || key=="osid" || key =="ugroupid" || key =="status" || key=="level" || key =="remember_me"){
                           tmpsql = key+"="+map.get(key);
                       }
                       else{
                           tmpsql = key+"='"+map.get(key)+"'";
                       }
                       SET(tmpsql);
                   }else{
                       tmpsql = key+"='"+map.get(key)+"'";
                       WHERE(tmpsql);
                   }

               };

           }
       }.toString();
    }
}
