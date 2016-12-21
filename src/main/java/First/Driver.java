package First;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by me on 10/22/16.
 */
public class Driver {

    public static void main(String[] args) {
        System.out.println("This is the First.Driver class! \nLoading ...");

        if (args.length != 2) {
            System.err.println("Usage: Log LogParser <input path> <output path>");
            System.exit(-1);
        }

        Job job = null;
        try {
            job = new Job();
            job.setJarByClass(Driver.class);
            job.setJobName("Log LogParser");
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            job.setMapperClass(LogMapper.class);
            job.setReducerClass(LogReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
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
