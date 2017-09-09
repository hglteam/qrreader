
import java.io.*;
import java.util.Scanner;

public class Main {

	public static class ProcessRunnable implements Runnable
	{
		
		private boolean mustStop;
		private String fileName;
		private boolean forceExit;
		
		public boolean isForceExit() {
			return forceExit;
		}

		public void setForceExit(boolean forceExit) {
			this.forceExit = forceExit;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				try {
					Process p = Runtime.getRuntime().exec("/usr/bin/python2.7 " + fileName);
					InputStream input = null;
					BufferedReader rd = null;
					String line = null;
					
					while(p.isAlive())
					{
						if(getMustStop())
						{
							p.destroyForcibly();
							p.waitFor();
							return;
						}
					}
					
					input = p.getInputStream();
					rd = new BufferedReader(new InputStreamReader(input));
					line = rd.readLine();
					System.out.println(line + "\n");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
		}

		public boolean getMustStop() {
			return mustStop;
		}

		public void setMustStop(boolean mustStop) {
			this.mustStop = mustStop;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ProcessRunnable process = new ProcessRunnable();
		Scanner scan = new Scanner(System.in);
		Thread thread = new Thread(process);
		
		process.setFileName((args.length > 0) ? args[0] : "mainOne.py");
		thread.start();
		
		while(true)
		{
			String line = scan.nextLine();
			
			if(process.isForceExit() || line.contains("exit")) {
				process.setMustStop(true);
				try {
					thread.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
		scan.close();
	}

}
