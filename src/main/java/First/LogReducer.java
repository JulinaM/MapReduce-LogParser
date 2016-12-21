package First;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by me on 10/22/16.
 */
public class LogReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private enum HTTPRequest{
        GET,
        POST,
        DELETE;
    }

    private Map<String, Integer> requestmap = new HashMap<String, Integer>();

    private void prepareMap(){
        requestmap = new HashMap<String, Integer>();
        requestmap.put(String.valueOf(HTTPRequest.GET), 0);
        requestmap.put(String.valueOf(HTTPRequest.POST), 0);
        requestmap.put(String.valueOf(HTTPRequest.DELETE), 0);
    }

    private void updateMap(String value){
        requestmap.put(value, requestmap.get(value)+1);
    }

   /* private void mapToString(){
        StringBuilder result = new StringBuilder("");
        for (String key : requestmap.keySet()) {
            result.append(key+":"+requestmap.get(key));
        }
    }*/

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
//        this.prepareMap();
        int sum = 0;
        for (IntWritable value : values) {
          /*  String request = value.toString();
            this.updateMap(request.split("\\s")[0].toUpperCase());*/
            sum++;
        }
//        Text result = new Text(this.requestmap.toString());
        context.write(key, new IntWritable(sum));
    }
}
