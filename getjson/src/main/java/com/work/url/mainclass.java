package com.work.url;
import org.apache.log4j.Logger;

/**
 * Created by wang on 2017/5/23.
 */
public class mainclass {

    private static Logger logger = Logger.getLogger(mainclass.class);

    public static void main(String[] args){
        if(args.length==0){
            logger.error("Have no args, you should add YYYYMM");
            return;
        }
        GetJson getJson = new GetJson(args[0]);

    }
}
