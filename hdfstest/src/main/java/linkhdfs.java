/**
 * Created by wang on 2017/4/11.
 */
import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;

public class linkhdfs {

    public void WriteFile(String hdfs) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(hdfs),conf);
        FSDataOutputStream hdfsOutStream = fs.create(new Path(hdfs));
        hdfsOutStream.writeChars("hello");
        hdfsOutStream.close();
        fs.close();
    }
    public void ReadFile(String hdfs) throws IOException {
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

    public static void main(String[] args) throws IOException {
        String hdfs = "hdfs://10.100.28.132:8020/tmp/hello.txt";
        linkhdfs t = new linkhdfs();
        t.WriteFile(hdfs);
    }
}
