package MG1;

	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.PrintWriter;

public class Simulation {
		public static void main(String[] args) {
			 File dir = new File("logs");
		        if (!dir.exists()) {
		        	dir.mkdir();
		        }
		        // logs directory for mm1
		        File dir2 = new File("logs/mg1");
		        if (!dir2.exists()) {
		        	dir2.mkdir();
		        }
	 
		      double lambda = 70;
		      double ts = 0.015; //mean service time Ts
		      double length = 100; //run time   
		      Controller c1 = new Controller(lambda,ts,length);       
		        
		      File file = new File("logs/mg1/sim1_log.txt"); 
		      PrintWriter out;
				try {
					out = new PrintWriter(file);
					System.out.println("SIMULATION 1: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length);
			    	out.println("SIMULATION 1: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length + "\n");
			    	out.println("FINAL STATISTICS AT END OF LOG\n");
			        c1.run(out);
			        System.out.println("Simulation complete.");
			        System.out.println("Log: logs/mm1/sim1_log.txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} 
				lambda = 65;
		        Controller c2 = new Controller(50,0.015,100);

		        file = new File("logs/mg1/sim2_log.txt"); 
		        try {
					out = new PrintWriter(file);
					System.out.println("SIMULATION 2: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length);
			    	out.println("SIMULATION 2: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length + "\n");
			    	out.println("FINAL STATISTICS AT END OF LOG\n");
			        c2.run(out);
			        System.out.println("Simulation complete.");
			        System.out.println("Log: logs/mg1/sim2_log.txt");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
		        
		        lambda = 60;
		        Controller c3 = new Controller(65,0.015,100);
		        
		        file = new File("logs/mg1/sim3_log.txt"); 
		        try {
					out = new PrintWriter(file);
					System.out.println("SIMULATION 3: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length);
			    	out.println("SIMULATION 3: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length + "\n");
			    	out.println("FINAL STATISTICS AT END OF LOG\n");
			        c3.run(out);
			        System.out.println("Simulation complete.");
			        System.out.println("Log: logs/mg1/sim3_log.txt");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
		        
		        lambda = 50;
		        Controller c4 = new Controller(50,0.015,100);
		        
		        file = new File("logs/mg1/sim4_log.txt"); 
		        try {
					out = new PrintWriter(file);
					System.out.println("SIMULATION 4: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length);
			    	out.println("SIMULATION 4: lambda = " + lambda + ", Ts = " + ts + ", Sim length = " + length + "\n");
			    	out.println("FINAL STATISTICS AT END OF LOG\n");
			        c4.run(out);
			        System.out.println("Simulation complete.");
			        System.out.println("Log: logs/mg1/sim4_log.txt");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
		}
	
}
