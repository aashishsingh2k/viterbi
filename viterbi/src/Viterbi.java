import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue; 

public   class    Viterbi    {
//   creates   a   new   Viterbi-type   gsm

private double[] pi;
private double[][] A;
private double[][] B;
private double finalpr;
private double[] tempPrArr;
private double[] tempPrArr1;
private Integer[][] arr;
private Integer[][] arr1;
private Integer[][] arrhelp;
private Integer[] finalArray;
private Integer[][] finalArray2D;
	
public     Viterbi    (double[] pi, double[][] A, double[][] B ){
	
	if(A == null || B == null || pi == null) {
		throw new IllegalArgumentException("some or all of arguments are null!"); 
	}
	
	if(A.length != A[0].length) {
		throw new IllegalArgumentException("A is not an n x n matrix!"); 
	}
	
	if(A.length != pi.length) {
		throw new IllegalArgumentException("A and pi have different dimensions!"); 
	}
	
	double x = 0.0;
	for(int i = 0; i < A.length; i++) {
		x = 0.0;
		for(int j = 0; j < A[0].length; j++) {
			x = x + A[i][j];
			}
		if(x != 1) {
			throw new IllegalArgumentException("elements in a row of A don't add upto 1");
		}
	}
	
	x = 0.0;
	for(int i = 0; i < pi.length; i++) {
		x = x + pi[i];
	}
	if(x != 1) {
		throw new IllegalArgumentException("elements in pi don't add upto 1");
	}
	
	x = 0.0;
	for(int i = 0; i < B.length; i++) {
		x = 0.0;
		for(int j = 0; j < B[0].length; j++) {
			x = x + B[i][j];
			}
		if(x != 1) {
			throw new IllegalArgumentException("elements in a row of B don't add upto 1");
		}
	}
	
	this.pi = pi;
	this.A = A;
	this.B = B;
}  
//   each   of   the   following   methods   takes   in   a   boolean   variable   named   underflow 
//   if   underflow   ==   true,   your   method   should   be   adjusted   so   that   it   deals   with 
//   underflow   (use   logs   appropriately   as   suggested   in   the   notes),   otherwise   your 
//   method   should   not   deal   with   underflow


//   computes   and   prints   the   probability   of   a   particular   sequence   of   occurring 
public   double     probOfSeq (int[] omega , List<Integer> seq, boolean underflow ) {
	if(seq == null) {
		throw new IllegalArgumentException("seq is null!"); 
	}
	if(seq.size() == 0) {
		throw new IllegalArgumentException("seq size is 0"); 
	}
	if(omega == null) {
		throw new IllegalArgumentException("omega is null!"); 
	}
	if(omega.length == 0) {
		throw new IllegalArgumentException("omega size is 0"); 
	}
	
	double pr = 0.0;
	if(underflow) {
	pr = Math.log(seq.get(0) * pi[seq.get(0) - 1] * B[seq.get(0) - 1][omega[0] - 1]);	
	}
	else {
		pr = seq.get(0) * pi[seq.get(0) - 1] * B[seq.get(0) - 1][omega[0] - 1];
	}
	for(int i = 1; i < seq.size(); i++) {
		if(!underflow){
			pr = pr * A[seq.get(i-1) - 1][seq.get(i) - 1] * B[seq.get(i)  -1][omega[i] - 1];
		}
		else{
			pr = pr + Math.log(A[seq.get(i-1) - 1][seq.get(i) - 1] * B[seq.get(i)  -1][omega[i] - 1]);
		}
	}
	System.out.println("Probability of given sequence: " + pr);
	return pr;
}

//   computes   and   prints   the   sequence   with   the   
//highest   probability   of   occurring 
public   List<Integer>     bestSeq (int[] omega, boolean underflow ){
	if(omega == null) {
		throw new IllegalArgumentException("omega is null!"); 
	}
	if(omega.length == 0) {
		throw new IllegalArgumentException("omega size is 0"); 
	}
	
	arr = new Integer[A.length][omega.length];
	//tempPrArr stores Probability in current time t for states ending in 
	//indices 1,2,3....n
	tempPrArr = new double[A.length];
	double[] copy = new double[A.length];
 	arrhelp = new Integer[A.length][omega.length];
	
 	int count = 0; //count is keeps track of which 
 	//cycle we are between 0 to T
 	
 	//first reach start state.
	for(int i= 0; i < pi.length; i++) {
		if(!underflow) {
		tempPrArr[i] = pi[i] * B[i][omega[0] - 1];
		}
		else{
			tempPrArr[i] = Math.log(pi[i] * B[i][omega[0] - 1]);	
		}
		arr[i][count] = i;
		
	}
	
	count++;
	
	copy = tempPrArr;
	for(count = 1; count < omega.length; count++) {
		copy = Arrays.copyOf(tempPrArr, A.length);
		
	for(int i = 0; i < A.length; i++) {
		
		double maxTemp = Double.NEGATIVE_INFINITY;
		double temp = 0.0;
		for(int j = 0; j < A.length; j++) {
			
			//calculate temp for all final states i reachable from j = 0 
			//to n-1 and then pick the max temp
			temp = 0.0;
			if(!underflow) {
			temp = copy[j] * A[j][i] * B[i][omega[count] - 1]; 
			}
			else {
			temp = copy[j] + Math.log(A[j][i] * B[i][omega[count] - 1]); 	
			}
			if(temp > maxTemp) {
				maxTemp = temp;
				arr[i][count] = arr[j][count - 1];
				arrhelp[i][count] = j;
				
			}
			
		}
		tempPrArr[i] = maxTemp;
	}
	
	}
	
	//traverse over tempPrArr
	//ind  will provide row index of arr[][] to get sequence.
	int ind = 0;
	for(int i = 0; i < tempPrArr.length; i++){
		double max = Double.NEGATIVE_INFINITY;
		if(tempPrArr[i] > max) {
			
			max = tempPrArr[i];
			finalpr = max;
			ind = i;
			
		}
	}
	//backtracking
	finalArray = new Integer[omega.length];
	int x = arr[ind][omega.length - 1];
	finalArray[0] = x + 1;
	int indf = ind;
	int cmp = 0;
	for(int m = omega.length - 1; m >= 1; m--) {
		if(m != omega.length - 1) {
			cmp = arrhelp[finalArray[m] - 1][m];
		}
		else{
			cmp = arrhelp[ind][omega.length - 1];
			
		}
		for(int n = 0; n < A.length; n++) {
			if(arr[n][m - 1] == x) {
				if(n == cmp) {	
					finalArray[m - 1] = n + 1;
				}
			}
		}
	}
	finalArray[omega.length - 1] = ind + 1;
	System.out.print("Highest probability sequence: ");
	for(int i = 0; i < finalArray.length; i++) {
		System.out.print(finalArray[i] + ", ");
	}
	System.out.println("");
	
	return Arrays.asList(finalArray);
}

//   computes   and   prints   the   sequence   with   the   lowest   probability   of   occurring 
public   List<Integer>     worstSeq (int[] omega, boolean underflow ){
	if(omega == null) {
		throw new IllegalArgumentException("omega is null!"); 
	}
	if(omega.length == 0) {
		throw new IllegalArgumentException("omega size is 0"); 
	}
	
	arr1 = new Integer[A.length][omega.length];
	//tempPrArr stores Probability in current time t for states ending in 
	//indices 1,2,3....n
	tempPrArr1 = new double[A.length];
	double[] copy = new double[A.length];
 	Integer[][] arrhelp1 = new Integer[A.length][omega.length];
	
 	int count = 0; //count is keeps track of which 
 	//cycle we are between 0 to T
 	
 	//first reach start state.
	for(int i= 0; i < pi.length; i++) {
		if(!underflow) {
		tempPrArr1[i] = pi[i] * B[i][omega[0] - 1];
		}
		else{
			tempPrArr1[i] = Math.log(pi[i] * B[i][omega[0] - 1]);	
		}
		arr1[i][count] = i;
		
	}
	
	count++;
	
	copy = tempPrArr1;
	for(count = 1; count < omega.length; count++) {
		copy = Arrays.copyOf(tempPrArr1, A.length);
		
	for(int i = 0; i < A.length; i++) {
		
		double minTemp = Double.POSITIVE_INFINITY;
		double temp = 0.0;
		for(int j = 0; j < A.length; j++) {
			
			//calculate temp for all final states i reachable from j = 0 
			//to n-1 and then pick the max temp
			temp = 0.0;
			if(!underflow) {
			temp = copy[j] * A[j][i] * B[i][omega[count] - 1]; 
			}
			else {
			temp = copy[j] + Math.log(A[j][i] * B[i][omega[count] - 1]); 	
			}
			if(temp < minTemp) {
				minTemp = temp;
				//tempPrArr[i] = temp;
				arr1[i][count] = arr1[j][count - 1];
				arrhelp1[i][count] = j;
				
			}
			
		}
		tempPrArr1[i] = minTemp;
	}
	
	}
	
	//traverse over tempPrArr
	//ind  will provide row index of arr[][] to get sequence.
	int ind = 0;
	for(int i = 0; i < tempPrArr1.length; i++){
		double min = Double.POSITIVE_INFINITY;
		if(tempPrArr1[i] < min) {
			
			min = tempPrArr1[i];
			finalpr = min;
			ind = i;
			
		}
	}
	//backtracking
	Integer[] finalArray1 = new Integer[omega.length];
	int x = arr1[ind][omega.length - 1];
	finalArray1[0] = x + 1;
	int cmp1 = 0;
	for(int m = omega.length - 1; m >= 1; m--) {
		if(m != omega.length - 1) {
			cmp1 = arrhelp1[finalArray1[m] - 1][m];
		}
		else {
			cmp1 = arrhelp1[ind][omega.length - 1];
		}
		for(int n = 0; n < A.length; n++) {
			if(arr1[n][m - 1] == x) {
				if(n == cmp1) {
					finalArray1[m - 1] = n + 1;
				}
			}
		}
	}
	finalArray1[omega.length - 1] = ind + 1;
	System.out.print("Lowest probability sequence: ");
	for(int i = 0; i < finalArray1.length; i++) {
		System.out.print(finalArray1[i] + ", ");
		
	}
	System.out.println("");
	
	return Arrays.asList(finalArray1);
}

private class intArrComparator implements Comparator<Integer[]> {
    @Override
    public int compare(Integer[] x, Integer[] y)
    {
        if(x.length < y.length) return -1;
        else if(x.length == y.length){
        	if(Arrays.equals(x, y)) return 0;
        	else{
        		int a = x.length - 1;
        		while(a > 0) {
        			//start from back of array and go till you forward till you get distinct indices
        			if(x[a] < y[a]) return -1;
        			if(x[a] > y[a]) return 1;
        			else{
        				a--;
        			}
        		}
        	}
        	
        }
        return 1;
    }
}

private class myComparator implements Comparator<Double> {
    @Override
    public int compare(Double x, Double y) {
    	if(Math.abs(x - y) > 1e-15) return 1;
    	if(Math.abs(x - y) < 1e-15) return 0;
    	return -1;
    }
    }
//for   t   =   1,   ...,   T   and   j   =   1,   ...,   n,  
//compute   predl(j,t)   to   be   the   lowest   index   l 
//such   that   tscore(l)   (at   time   t)   is   maximum,
//  and   predr(j,   t)   to   be   the   largest 
//index   r   such   that   tscore(r)   (at   time   t)   
//is   maximum.   If   l   =   r,   then   predl(j,t)   = 
//predr(j,t).   Compute   and   print   the   sequence   
//with   the   highest   probability
//corresponding   to   the   indices   in   predl,   and
//the   sequence   with   the   highest
//probability   corresponding   to   the   indices   in   predr.  
public   List<List<Integer>>    bestSeqSet (int[] omega, boolean underflow ){
	
	if(omega == null) {
		throw new IllegalArgumentException("omega is null!"); 
	}
	if(omega.length == 0) {
		throw new IllegalArgumentException("omega size is 0"); 
	}
	
	List<List<Integer>> l = new ArrayList<List<Integer>>();
	bestSeq(omega, underflow);
	PriorityQueue<Double> q1 = new PriorityQueue<Double>(Collections.reverseOrder()); 
	for(int i = 0; i < tempPrArr.length; i++){
	q1.add(tempPrArr[i]);
	}
	
	double x = q1.peek();
	List<Integer> strInd = new ArrayList<Integer>();
	for(int i = 0; i < tempPrArr.length; i++){
	if(!underflow) {
	if(Math.abs(x - tempPrArr[i]) < 1e-15 && Math.abs(x - 0.0) > 1e-15) {
	strInd.add(i);	
	}
	}
	else{
		if(Math.abs(x - tempPrArr[i]) < 1e-15 && 
				Math.abs(x -Double.NEGATIVE_INFINITY) > 1e-15) {
			
			strInd.add(i);	
			}
	}
	}
	
	if(strInd.size() == 0) return l;
	if(strInd.size() == 1) {
		List<Integer> l1 = Arrays.asList(finalArray);
		Iterator i = l1.iterator();
		while(i.hasNext()) {
			System.out.print(i.next() + ", ");
		}
		System.out.println("");
		l.add(l1);
		return l;
	}
	if(strInd.size() >= 2) {
		PriorityQueue<Integer[]> q2 = new PriorityQueue<Integer[]>(new intArrComparator());
		PriorityQueue<Integer[]> q3 = 
				new PriorityQueue<Integer[]>(Collections.reverseOrder(new intArrComparator())); 
				finalArray2D = new Integer[strInd.size()][omega.length];
				for(int i = 0; i < strInd.size(); i++){
				int x1 = arr[strInd.get(i)][omega.length - 1];
				finalArray2D[i][0] = x1 + 1;
				for(int m = omega.length - 1; m >= 1; m--) {
					for(int n = 0; n < A.length; n++) {
						if(arr[n][m - 1] == x) {
							if(n == arrhelp[n][m]) {
								finalArray2D[i][m] = n + 1;
							}
						}
					}
				}
				finalArray2D[i][omega.length - 1] = strInd.get(i) + 1;
				}
				
				for(int a = 0; a < strInd.size(); a++) {
					for(int b = 0; b < omega.length; b++) {
						System.out.println("finalArr2D[" 
					+ a + "][" + b + "] = " + finalArray2D[a][b]);
					}
				}
				
				for(int i = 0; i < strInd.size(); i++){
					q2.add(finalArray2D[i]);
					q3.add(finalArray2D[i]);
				}
				
		l.add(Arrays.asList(q2.peek()));
		l.add(Arrays.asList(q3.peek()));
		
		System.out.print("low index max pr");
		for(int a = 0; a < q2.peek().length; a++) {
			//if(a != q2.peek().length - 1) {
			System.out.print(q2.peek()[a] + ", ");
			//}
			//else System.out.print(q2.peek()[a]);
		}
		System.out.println("");
		System.out.print("High index max pr");
		for(int a = 0; a < q3.peek().length; a++) {
			//if(a != q3.peek().length - 1) {
			System.out.print(q3.peek()[a] + ", ");
			//}
			//else System.out.print("FUCK" + q3.peek()[a] + ", ");
		}
		System.out.println("");
		
		return l;
	}
	return l;
}


//   computes   and   prints   the   highest   probability   found   at   time   T. 
public   double     maxScore (int[] omega, boolean underflow ){
	if(omega == null) {
		throw new IllegalArgumentException("omega is null!"); 
	}
	if(omega.length == 0) {
		throw new IllegalArgumentException("omega size is 0"); 
	}
	
	bestSeq(omega,underflow);
	System.out.println("Highest Probability found at Time T: " + finalpr);
	return finalpr;
}

//   computes   and   prints   the   k   distinct   highest   probabilities   found   at   time   T 
public   double[]     maxScoreSet (int[] omega, int k, boolean underflow ){
	if(omega == null) {
		throw new IllegalArgumentException("omega is null!"); 
	}
	if(omega.length == 0) {
		throw new IllegalArgumentException("omega size is 0"); 
	}
	
	if(k < 0) {
		throw new IllegalArgumentException("k must be positive");
	}
	
	
	if(k == 0) {
	double[] f = {};
	return f;
	}
	
	double[][] finArrPr2D = new double[A.length][k];
	double[] fill = new double[A.length];
	double[] iniArr = new double[A.length];
	PriorityQueue<Double> pq = new PriorityQueue<Double>(Collections.reverseOrder(new myComparator()));
	
	double[][] cpy = new double[A.length][k];
	
	for(int i = 0; i < A.length; i++) {
		iniArr[i] = pi[i] * B[i][omega[0] - 1];

		//pq.add(iniArr[i]);
		
	}
	
	int count = 1;

	for(int i = 0; i < iniArr.length; i++) {
		for(int j = 0; j < Math.min(A.length, k); j++) {
		if(underflow) {
		if(!pq.contains(iniArr[j] + Math.log(A[j][i] * B[i][omega[count] - 1]))) {
		pq.add(iniArr[j] + Math.log(A[j][i] * B[i][omega[count] - 1]));
		}
		}
		else {
			if(!pq.contains(iniArr[j] * A[j][i] * B[i][omega[count] - 1])) {
				pq.add(iniArr[j] * A[j][i] * B[i][omega[count] - 1]);
				}
		}
		}
		if(pq.size() >= k) fill[i] = k;
		else fill[i] = pq.size();
		int r = 0;
		while(pq.size() != 0 && r < k) {
			finArrPr2D[i][r] = pq.poll();
			r++;
		}
	}
	
	
	if(omega.length > 2) {
	for(count = 2; count < omega.length; count++) {
		
		for(int a=0; a<A.length; a++) {
			  for(int b=0; b<A[0].length; b++) {
			    cpy[a][b]=finArrPr2D[a][b];
			  }
		}
		
		for(int i = 0; i < A.length; i++) {
			for(int j = 0; j < A.length; j++) {
				
				for(int r = 0; r < Math.min(fill[i], k); r++) {
					if(underflow) {
					if(!pq.contains(cpy[j][r] + 
							Math.log(A[j][i] * B[i][omega[count] - 1]))); {
					pq.add(cpy[j][r] + Math.log(A[j][i] * B[i][omega[count] - 1]));
							}
					}
					else {
						if(!pq.contains(cpy[j][r] * A[j][i] * B[i][omega[count] - 1])) {
							pq.add(cpy[j][r] * A[j][i] * B[i][omega[count] - 1]);
							}
						
					}
					
				}
			}
			if(pq.size() >= k) fill[i] = k;
			else fill[i] = pq.size();
			int r = 0;
			while(pq.size() != 0 && r < k) {
				finArrPr2D[i][r] = pq.poll();
				r++;
			}
		}
	}
	}
	
	PriorityQueue<Double> finpq = new PriorityQueue<Double>(Collections.reverseOrder());
	for(int a = 0; a < A.length; a++) {
		for(int b = 0; b < k; b++) {
		finpq.add(finArrPr2D[a][b]); 
		}
	}
	
	if(finpq.size() < k) {
		throw new IllegalArgumentException("Value of k is too large!");
	}
	double[] finArr = new double[k];
	for(int i = 0; i < k; i++) {
		finArr[i] = -1;
	}
	
	int c = 0;	
	while(c < k && finpq.size() != 0) {
		finArr[c] = finpq.poll();
		c++;
	}
	
	int ct = 0;
	for(int a = 0; a < k; a++) {
		if(finArr[a] != -1) {
		ct++;	
		}
	}
	
	double[] trimFinArr = new double[ct];
	for(int a = 0; a < ct; a++) {
		trimFinArr[a] = finArr[a];
	}
	
	System.out.println("top k probabilties are: ");
	for(int a = 0; a < ct; a++) {
		System.out.print(trimFinArr[a] + ", ");
	}
	
return trimFinArr;	
}

public static void main(String args[]){
	double[][] A = new double[2][2];
	A[0][0] = 0.6;
	A[0][1] = 0.4;
	A[1][0] = 0.6;
	A[1][1] = 0.4;
	
	double[][] B = new double[2][2];
	B[0][0] = 0.6;
	B[0][1] = 0.4;
	B[1][0] = 0.4;
	B[1][1] = 0.6;
	
	double[] pi = new double[2];
	pi[0] = 0.6;
	pi[1] = 0.4;
	
	Viterbi v = new Viterbi(pi, A, B);
	int[]omega = {1,2}; //ND
	Integer[] arr1 = {2,2};
	Integer[] arr2 = {1,2};
	List<Integer> l1 = Arrays.asList(arr1);
	List<Integer> l2 = Arrays.asList(arr2);
	List<List<Integer>> list = new ArrayList<List<Integer>>();
	list.add(l1);
	list.add(l2);
	v.worstSeq(omega, false);
}


}
