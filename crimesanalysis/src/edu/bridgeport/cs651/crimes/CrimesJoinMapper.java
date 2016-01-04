package edu.bridgeport.cs651.crimes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CrimesJoinMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String outkey;
		String outvalue;
		String[] recs = value.toString().split(",");
		if (!recs[0].equals("ID")) {
			outkey = String.valueOf(recs[13]);// community id
			StringBuilder sb = new StringBuilder();
			Date date = null;
			try {
				date = parseDate(recs[2], "MM/dd/yyyy hh:mm:ss a");
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
			String id = recs[0];
			String year = recs[17];
			String month = getMonth(date);
			String day = getDay(date);
			String time = getTime(date);
			String timeperiod = getTimePeriod(date);
			String type = recs[5];
			String arrest = recs[8];
			String domestic = recs[9];
			if(year == null || year.length() != 4) return;
			sb.append(id).append(",").append(year).append(",").append(month).append(",").append(day)
					.append(",").append(time).append(",").append(timeperiod)
					.append(",").append(type).append(",").append(arrest).append(",")
					.append(domestic).append(",");
			outvalue = "A" + sb.toString();
			context.write(new Text(outkey), new Text(outvalue));
		}

	}

	/*
	 * get the specific format string of date
	 * 
	 * @param dateString the original string of date
	 * 
	 * @param original format string pattern
	 * 
	 * @param target string format pattern
	 */
	private Date parseDate(String dateString, String sPattern)
			throws ParseException {

		SimpleDateFormat sf = new SimpleDateFormat(sPattern);
		Date date = sf.parse(dateString);
		return date;
	}

	private String getMonth(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date).substring(4,
				6);
	}

	private String getDay(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date).substring(6,
				8);
	}

	private String getTime(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date).substring(8,
				14);
	}

	private String getTimePeriod(Date date) {
		int hour = date.getHours();
		if (hour > 0 && hour <= 4) {
			return "0:00 - 4:00 AM";
		} else if (hour > 4 && hour <= 8) {
			return "4:00 - 8:00 AM";
		} else if (hour > 8 && hour <= 12) {
			return "8:00 - 12:00 AM";
		} else if (hour > 12 && hour <= 16) {
			return "12:00 - 4:00 PM";
		} else if (hour > 16 && hour <= 20) {
			return "4:00 - 8:00 PM";
		} else {
			return "8:00 - 12:00 PM";
		}
	}

}
