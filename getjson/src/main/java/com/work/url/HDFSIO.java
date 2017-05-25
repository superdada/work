package com.work.url;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;

/**
 * Created by wang on 2017/5/24.
 */
public class HDFSIO {
    private static Logger logger = Logger.getLogger(HDFSIO.class);

    public void WriteFile(String hdfsLocation,String content,String username) {
        if(hdfsLocation == null || content == null)
        {
            logger.error("hdfsLocation or content is null!");
            return;
        }

        try {
            System.setProperty("hadoop.home.dir", "D:\\hadoop-common-2.2.0-bin-master");
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsLocation),conf,username);
            FSDataOutputStream hdfsOutStream = fs.create(new Path(hdfsLocation));
            hdfsOutStream.write(content.getBytes("UTF-8"));
            hdfsOutStream.flush();
            hdfsOutStream.close();
            fs.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void ReadFile(String hdfs,String username) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(hdfs),conf);
        FSDataInputStream hdfsInStream = fs.open(new Path(hdfs));

        byte[] ioBuffer = new byte[1024];
        int readLen = hdfsInStream.read(ioBuffer);
        while(readLen!=-1)
        {
            System.out.write(ioBuffer, 0, readLen);
            readLen = hdfsInStream.read(ioBuffer);
        }
        hdfsInStream.close();
        fs.close();
    }
}
