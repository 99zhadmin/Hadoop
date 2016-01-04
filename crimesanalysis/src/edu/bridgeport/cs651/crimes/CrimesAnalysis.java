package edu.bridgeport.cs651.crimes;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CrimesAnalysis {
	public static void main(String[] args) throws Exception {
		Configuration config = new Configuration();
		//
		// if (args.length != 4) {
		// System.out
		// .printf("Usage: CrimesAnalysis <opreration opt> <input dir1> <input dir2> <output dir> \n");
		// System.exit(-1);
		// }
		/*
		 * opt: 0: join table 1: year analysis 2: time slot analysis 3: type analysis
		 * 4: income analysis
		 * 
		 * opt = 0, Usage: CrimesAnalysis <opreration opt> <input dir1> <input
		 * dir2> <output dir> \n"); opt in (1,2,3,4)Usage: CrimesAnalysis
		 * <opreration opt> <input dir> <output dir> \n");
		 */
		Job job = new Job();
		job.setJarByClass(CrimesAnalysis.class);
		job.setJobName("CrimesAnalysis");
		int opt = Integer.parseInt(args[0]);

		FileSystem fs = FileSystem.get(config);
		Path outpath = null;

		if (opt == 0) {
			MultipleInputs.addInputPath(job, new Path(args[1]),
					TextInputFormat.class, CrimesJoinMapper.class);
			MultipleInputs.addInputPath(job, new Path(args[2]),
					TextInputFormat.class, CommunityJoinMapper.class);

			outpath = new Path(args[3]);
			if (fs.exists(outpath)) {
				fs.delete(outpath, true);
			}
			if (fs.exists(outpath)) {
				fs.delete(outpath, true);
			}

			FileOutputFormat.setOutputPath(job, outpath);
			job.setReducerClass(CrimesJoinReducer.class);

			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
		} else {
			outpath = new Path(args[2]);
			if (fs.exists(outpath)) {
				fs.delete(outpath, true);
			}
			FileOutputFormat.setOutputPath(job, new Path(args[2]));
			FileInputFormat.setInputPaths(job, new Path(args[1]));

			job.setReducerClass(ShareCrimeReducer.class);

			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			switch (opt) {
			case 1: {// crimes year distribution

				job.setMapperClass(YearCrimeMapper.class);
				break;
			}

			case 2: {//time slot distribution
				job.setMapperClass(TimeslotCrimeMapper.class);
				break;
			}

			case 3: {//crimes type distribution 
				job.setMapperClass(TypeofCrimeMapper.class);
				break;
			}

			case 4: {//income crime relations
				job.setMapperClass(IncomeCrimeMapper.class);
				break;
			}
			case 5: {//unemployed crime relations
				job.setMapperClass(UnemployMapper.class);
				break;
			}
			case 6: {//diploma crime relations
				job.setMapperClass(DiplomaMapper.class);
				break;
			}

			default: {
				break;
			}

			}
		}

		boolean success = job.waitForCompletion(true);
		System.exit(success ? 0 : 1);
	}
}
