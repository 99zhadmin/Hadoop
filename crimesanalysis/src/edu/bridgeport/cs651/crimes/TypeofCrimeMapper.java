package edu.bridgeport.cs651.crimes;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TypeofCrimeMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
        String line = value.toString();
        String[] recs = line.split(",");
        if(!recs[0].equalsIgnoreCase("ID")){
        	String crimetype = recs[6];
        	context.write(new Text(crimetype), new IntWritable(1));
        }
	}

}
