
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import utils.Log;
import utils.LogParser;
import utils.ServerReputation;

import java.io.IOException;

/**
 * Created by jmaharjan on 12/21/16.
 */
public class MaliciousDetector {

    private static class MyMapper extends Mapper<LongWritable, Text, Text, NullWritable>{
        LogParser logParser = new LogParser();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            logParser.parse(value.toString());
            Log log = logParser.getLog();
            context.write(new Text(log.getIpAdress()), NullWritable.get());
        }
    }

    private static class MyReducer extends Reducer< Text, NullWritable, IntWritable, Text>{
        ServerReputation serverReputation = new ServerReputation();

        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            serverReputation.checkReputation(key.toString());
            context.write(serverReputation.isBlackList() ? new IntWritable(1): new IntWritable(0), new Text(key.toString()));
        }
    }


    public static void main(String[] args) {
        System.out.println("This is the Driver class! \nLoading ...");

        if (args.length != 2) {
            System.err.println("Usage: Log LogParser <input path> <output path>");
            System.exit(-1);
        }

        Job job = null;
        try {
            job = new Job();
            job.setJarByClass(MaliciousDetector.class);
            job.setJobName("Log LogParser -- Check Reputation");
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            job.setMapperClass(MyMapper.class);
            job.setReducerClass(MyReducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NullWritable.class);
            job.setOutputKeyClass(IntWritable.class);
            job.setOutputValueClass(Text.class);
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
