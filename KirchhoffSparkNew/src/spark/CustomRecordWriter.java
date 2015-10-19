package spark;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CustomRecordWriter extends RecordWriter<Integer, String> {

	private ArrayList<Integer> aps = null;
	private ArrayList<String> image = null;
	private PrintWriter pw=null;

	public CustomRecordWriter(FSDataOutputStream fsOut) {
		// TODO Auto-generated constructor stub
		aps = new ArrayList<Integer>();
		image = new ArrayList<String>();
		pw=new PrintWriter(fsOut);
	}

	@Override
	public void close(TaskAttemptContext context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		this.getSort(aps, image);
		for(int i=0;i<aps.size();i++){
			pw.write(aps.get(i)+"#"+image.get(i)+"\n");
		}
		pw.close();
	}

	@Override
	public void write(Integer key, String value) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		aps.add(key);
		image.add(value);
	}

	private void getSort(ArrayList<Integer> aps, ArrayList<String> image) {
		for (int i = 0; i < aps.size(); i++) {
			for (int j = 0; j < aps.size() - i - 1; j++) {
				if (aps.get(j) > aps.get(j + 1)) {
					int temp = aps.get(j);
					aps.set(j, aps.get(j + 1));
					aps.set(j + 1, temp);
					String ff = image.get(j);
					image.set(j, image.get(j + 1));
					image.set(j + 1, ff);
				}
			}
		}
	}

}
