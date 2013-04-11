package random;

public class MultLCG {

	//mod 131071 or mersenne 2^17-1
	//3,61
	//xn = 3xn-1 mod 131071
	
	private int seed;
	public MultLCG(int seed)
	{
		if(seed ==0)seed = 11102;//0 wont work
		this.seed = seed;
	}
	
	public double nextDouble(){
		seed = 3*seed % 131071;
		return ((double)seed)/131070D;
	}
	
	public int nextInt(){
		return seed = 3*seed % 131071;
	}
	
	
	//Generate an exponential distribution, mean = 1/lamda
	public double exponentialVariate(double lamda){
		return -1/lamda*Math.log(nextDouble());
		
	}
	
	//testing, in general there is one missed value at 0, and one double hit at a*seed
	// for X_n = a*X_n-1_ mod b
	public static void main(String[] args){
		MultLCG rand = new MultLCG(110112);
//		int i = 0;
//		
//		//sanity check that the period is 131070 seed with 1
//		for(; i< 11;i++)
//		{
//			System.out.print(rand.nextDouble() + " ");
//		}
//		
//		for(; i<131070; i++){
//			rand.nextDouble();
//		}
//		
//		System.out.println();
//		for(; i< 131081;i++)
//		{
//			System.out.print(rand.nextDouble() + " ");
//		}
//		
//		System.out.println();
//		//reseed random
//		rand = new MultLCG(0);
//		int[] test = new int[131071];
//		i = 0;
//		//fill an array
//		
//		for(;i<131071;i++)
//		{
//			int next = rand.nextInt();
//			test[next]++; 
//		}
//		
//		int misses = 0;
//		int hits = 0;
//		i = 0;
//		for(;i<131070;i++)
//		{
//			if(test[i]!=1)
//			{
//				System.out.println(i+" "+test[i]);
//			}
//			if(test[i]<1)misses++;
//			if( test[i]>1)hits++;
//		}
//		System.out.println("hits: "+hits);
//		System.out.println("misses: "+misses);
		
		
		for(int i = 0;i<100;i++)
		{
			System.out.println(rand.exponentialVariate(1D/10D));
		}
		
	}
}
