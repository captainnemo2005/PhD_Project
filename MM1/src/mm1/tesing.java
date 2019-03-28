package mm1;
import java.util.Random;

public class tesing {
  //  public static final double mean  = 5;
    public static final double sigma = 0.0001;
    public static final int lambda = 3;
	public static double getPoisson(double lambda) {
	   	double L = Math.exp(-lambda);
			  double p = 1.0;
			  float k = 0;

			  do {
			    k++;
			    p *= Math.random();
			  } while (p > L);
			  
			  return 1/(k - 1);
		}
    public static double exponential(int mu) {
    	Random r = new Random();
    	double x = Math.log(1-r.nextDouble())/(-mu);
    	return x;
    }
    
    public static double GaussianDistribs(double mu) {
    	Random r = new Random();
    	double x = r.nextGaussian()*sigma + mu;
    	return x;
    } 
	public static void main(String[] argv) {
		int lambda = 50;
		double a;
		for(int i = 1; i < 100 ; i++) {
			a = getPoisson(lambda);
			System.out.println(getPoisson(lambda));
		}
	}
}
