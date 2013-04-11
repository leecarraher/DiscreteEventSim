package random;

import java.math.BigInteger;
import java.util.Vector;

public class Primitive {
	public static void main(String[] argsv){
		primitive(Integer.parseInt(argsv[0]));
	}
	
	private static int primitive(int i)
	{
		
		BigInteger n = new BigInteger(""+i);
		if(!n.isProbablePrime(1))System.out.println("not prime");
		for(int j = 0; j<i;j++){
			if(calcPayment(j,i)) System.out.println(j);
		}
		return -1;
		
	}
    private static boolean calcPayment(int pn,int pm)
    {
       
            BigInteger n = new BigInteger(""+pn);
            BigInteger m = new BigInteger(""+pm);
            Vector<BigInteger> vector = new Vector<BigInteger>(m.intValue());
            
            for(int i = 0; i < m.intValue(); i++)
                vector.add(new BigInteger("0"));

            for(int j = 1; j < m.intValue(); j++)
            {
                BigInteger intj = new BigInteger("" + j);
                if(m.gcd(intj).intValue() == 1)
                    vector.setElementAt(new BigInteger("1"), n.modPow(intj, m).intValue());
            }

            int k;
            for(k = 0; k < m.intValue(); k++)
            {
                BigInteger intm = new BigInteger("" + k);
                if(m.gcd(intm).intValue() == 1)
                {
                    BigInteger biginteger4 = (BigInteger)vector.elementAt(intm.intValue());
                    if(biginteger4.intValue() == 0)
                        k = m.intValue() + 1;
                }
            }

            if(k == m.intValue() + 2)
                return false;
            else
                return true;
    }
}
