import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ViterbiTest {

	@Test
	public void ClassExampleTestMaxPr() {
		double[][] A = new double[2][2];
		A[0][0] = 0.7;
		A[0][1] = 0.3;
		A[1][0] = 0.25;
		A[1][1] = 0.75;
		
		double[][] B = new double[2][2];
		B[0][0] = 0.8;
		B[0][1] = 0.2;
		B[1][0] = 0.3;
		B[1][1] = 0.7;
		
		double[] pi = new double[2];
		pi[0] = 0.45;
		pi[1] = 0.55;
		
		Viterbi v = new Viterbi(pi, A, B);
		
		int[]omega = {1,1,1,2}; //NNND
		int[]omega1 = {1,1,1,2,1}; //NNNDN
		int[] omega2 = {1,1,1,2,1,1}; //NNNDNN
		int[] omega3 = {1,1,1,2,1,2,2,1}; //NNNDNDDN
		int[] omega4 = new int[1200]; //300 N's then 300 D's...till 1200.
		
		for(int i = 0; i < 1200; i++) {
		if(i >= 0 && i < 300) {
			omega4[i] = 1;
		}
		else if(i >= 300 && i < 600) {
			omega4[i] = 2;
		}
		else if(i >= 600 && i < 900) {
			omega4[i] = 1;
		}
		else if(i >= 900 && i < 1200) {
			omega4[i] = 2;
		}
		}
		
		int[] omega5 = new int[2000]; //500 N's then 500 D's...till 2000.
		
		for(int i = 0; i < 2000; i++) {
		if(i >= 0 && i < 500) {
			omega5[i] = 1;
		}
		else if(i >= 500 && i < 1000) {
			omega5[i] = 2;
		}
		else if(i >= 1000 && i < 1500) {
			omega5[i] = 1;
		}
		else if(i >= 1500 && i < 2000) {
			omega5[i] = 2;
		}
		}
		
int[] omega6 = new int[2004]; //500 N's then 500 D's...till 2000.
//then 4 more
		
		for(int i = 0; i < 2000; i++) {
		if(i >= 0 && i < 500) {
			omega6[i] = 1;
		}
		else if(i >= 500 && i < 1000) {
			omega6[i] = 2;
		}
		else if(i >= 1000 && i < 1500) {
			omega6[i] = 1;
		}
		else if(i >= 1500 && i < 2000) {
			omega6[i] = 2;
		}
		}
		omega6[2000] = 1;
		omega6[2001] = 1;
		omega6[2002] = 1;
		omega6[2003] = 2;
		
		
		assertTrue("class eg NNND",Math.abs(v.maxScore(omega, false) 
				- 0.023708159999999) < 1e-15);
		assertTrue("class eg NNNDN",Math.abs(v.maxScore(omega1, false) 
				- 0.005334335999999) < 1e-15);
		assertTrue("class eg NNNDNN",Math.abs(v.maxScore(omega2, false) 
				-0.0012002255999999998) < 1e-15);
		assertTrue("class eg NNNDNN",Math.abs(v.maxScore(omega3, false) 
				-3.3081218099999986E-4) < 1e-15);
		assertTrue("class eg length 1200",Math.abs(v.maxScore(omega4, false) 
				-3.74E-321) < 1e-15);
		assertTrue("class eg length 2000 underflow true",
				Math.abs(v.maxScore(omega5, true) 
				- (-1227.479545276702)) < 1e-15);
		assertTrue("class eg length 2004 underflow true",
				Math.abs(v.maxScore(omega6, true) 
				- (-1231.8092679279068)) < 1e-15);
	}

	
	@Test
	public void ClassExampleTestBestSeq() {
		double[][] A = new double[2][2];
		A[0][0] = 0.7;
		A[0][1] = 0.3;
		A[1][0] = 0.25;
		A[1][1] = 0.75;
		
		double[][] B = new double[2][2];
		B[0][0] = 0.8;
		B[0][1] = 0.2;
		B[1][0] = 0.3;
		B[1][1] = 0.7;
		
		double[] pi = new double[2];
		pi[0] = 0.45;
		pi[1] = 0.55;
		
		Viterbi v = new Viterbi(pi, A, B);
		
		int[]omega = {1,1,1,2}; //NNND
		int[]omega1 = {1,1,1,2,1}; //NNNDN
		int[] omega2 = {1,1,1,2,1,1}; //NNNDNN
		int[] omega3 = {1,1,1,2,1,2,2,1}; //NNNDNDDN
		int[] omega4 = new int[1200]; //300 N's then 300 D's...till 1200.
		
		for(int i = 0; i < 1200; i++) {
		if(i >= 0 && i < 300) {
			omega4[i] = 1;
		}
		else if(i >= 300 && i < 600) {
			omega4[i] = 2;
		}
		else if(i >= 600 && i < 900) {
			omega4[i] = 1;
		}
		else if(i >= 900 && i < 1200) {
			omega4[i] = 2;
		}
		}
		
		int[] omega5 = new int[2000]; //500 N's then 500 D's...till 2000.
		
		for(int i = 0; i < 2000; i++) {
		if(i >= 0 && i < 500) {
			omega5[i] = 1;
		}
		else if(i >= 500 && i < 1000) {
			omega5[i] = 2;
		}
		else if(i >= 1000 && i < 1500) {
			omega5[i] = 1;
		}
		else if(i >= 1500 && i < 2000) {
			omega5[i] = 2;
		}
		}
		
int[] omega6 = new int[2004]; //500 N's then 500 D's...till 2000.
//then 4 more
		
		for(int i = 0; i < 2000; i++) {
		if(i >= 0 && i < 500) {
			omega6[i] = 1;
		}
		else if(i >= 500 && i < 1000) {
			omega6[i] = 2;
		}
		else if(i >= 1000 && i < 1500) {
			omega6[i] = 1;
		}
		else if(i >= 1500 && i < 2000) {
			omega6[i] = 2;
		}
		}
		omega6[2000] = 1;
		omega6[2001] = 1;
		omega6[2002] = 1;
		omega6[2003] = 2;
		
		Integer[] arr1 = {1,1,1,2};
		Integer[] arr2 = {1,1,1,2,2};
		Integer[] arr3 = {1,1,1,2,2,2};
		Integer[] arr4 = {1, 1, 1, 2, 2, 2, 2, 2};
		assertTrue("class eg NNND",(v.bestSeq(omega, false).equals(Arrays.asList(arr1))));
		assertTrue("class eg NNNDN",(v.bestSeq(omega1, false).equals(Arrays.asList(arr2))));
		assertTrue("class eg NNNDNN",v.bestSeq(omega2, false).equals(Arrays.asList(arr3)));
		assertTrue("class eg NNNDNN",v.bestSeq(omega3, false).equals(Arrays.asList(arr4)));
		assertTrue("class eg length 1200",Math.abs(v.maxScore(omega4, false) 
				-3.74E-321) < 1e-15);
		assertTrue("class eg length 2000 underflow true",
				Math.abs(v.maxScore(omega5, true) 
				- (-1227.479545276702)) < 1e-15);
		assertTrue("class eg length 2004 underflow true",
				Math.abs(v.maxScore(omega6, true) 
				- (-1231.8092679279068)) < 1e-15);

	}
	
	@Test
	public void TestbestSeqSet() {
		double[][] A = new double[2][2];
		A[0][0] = 0.3;
		A[0][1] = 0.7;
		A[1][0] = 0.7;
		A[1][1] = 0.3;
		
		double[][] B = new double[2][2];
		B[0][0] = 0.7;
		B[0][1] = 0.3;
		B[1][0] = 0.3;
		B[1][1] = 0.7;
		
		double[] pi = new double[2];
		pi[0] = 0.5;
		pi[1] = 0.5;
		
		Viterbi v = new Viterbi(pi, A, B);
		int[]omega = {1,2}; //ND
		Integer[] arr1 = {1,1};
		Integer[] arr2 = {1,2};
		List<Integer> l1 = Arrays.asList(arr1);
		List<Integer> l2 = Arrays.asList(arr2);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		//list.add(l1);
		list.add(l2);
		assertTrue("", v.bestSeqSet(omega, false).equals(list));
		
	}
	
	@Test
	public void TestbestSeqSet2() {
		double[][] A = new double[2][2];
		A[0][0] = 0.5;
		A[0][1] = 0.5;
		A[1][0] = 0.5;
		A[1][1] = 0.5;
		
		double[][] B = new double[2][2];
		B[0][0] = 0.5;
		B[0][1] = 0.5;
		B[1][0] = 0.5;
		B[1][1] = 0.5;
		
		double[] pi = new double[2];
		pi[0] = 0.5;
		pi[1] = 0.5;
		
		Viterbi v = new Viterbi(pi, A, B);
		int[]omega = {1,2}; //ND
		Integer[] arr1 = {1,1};
		Integer[] arr2 = {2,2};
		List<Integer> l1 = Arrays.asList(arr1);
		List<Integer> l2 = Arrays.asList(arr2);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(l1);
		list.add(l2);
		assertTrue("", v.bestSeqSet(omega, false).equals(list));
		
	}
	
	@Test
	public void TestbestSeqSet3() {
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
		Integer[] arr1 = {1,1};
		Integer[] arr2 = {1,2};
		List<Integer> l1 = Arrays.asList(arr1);
		List<Integer> l2 = Arrays.asList(arr2);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(l1);
		list.add(l2);
		assertTrue("", v.bestSeqSet(omega, false).equals(list));
		
	}
	
	@Test
	public void TestMaxScoreSet() {
		double[][] A = new double[2][2];
		A[0][0] = 0.6;
		A[0][1] = 0.4;
		A[1][0] = 0.4;
		A[1][1] = 0.6;
		
		double[][] B = new double[2][2];
		B[0][0] = 0.6;
		B[0][1] = 0.4;
		B[1][0] = 0.4;
		B[1][1] = 0.6;
		
		double[] pi = new double[2];
		pi[0] = 0.6;
		pi[1] = 0.4;
		
		Viterbi v = new Viterbi(pi, A, B);
		int[]omega = {1,2}; //NDN
		Integer[] arr1 = {1,1};
		Integer[] arr2 = {1,2};
		List<Integer> l1 = Arrays.asList(arr1);
		List<Integer> l2 = Arrays.asList(arr2);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(l1);
		list.add(l2);
		double[] result = {0.0864, 0.0576, 0.0256};
		assertTrue("", Math.abs(v.maxScoreSet(omega, 4, false)[0] - 0.0864) < 1e-15);
		assertTrue("", Math.abs(v.maxScoreSet(omega, 4, false)[2] - 0.0576) < 1e-15);
		assertTrue("", Math.abs(v.maxScoreSet(omega, 4, false)[3] - 0.0256) < 1e-15);
		
	}
	
	
	@Test
	public void testKgreater() {
		boolean thrown = true;
		try {
			double[][] A = new double[2][2];
			A[0][0] = 0.6;
			A[0][1] = 0.4;
			A[1][0] = 0.4;
			A[1][1] = 0.6;
			
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
			Integer[] arr1 = {1,1};
			Integer[] arr2 = {1,2};
			List<Integer> l1 = Arrays.asList(arr1);
			List<Integer> l2 = Arrays.asList(arr2);
			List<List<Integer>> list = new ArrayList<List<Integer>>();
			list.add(l1);
			list.add(l2);
			double[] result = {0.0864, 0.0576, 0.0256};
			assertTrue("", Math.abs(v.maxScoreSet(omega, 400, false)[0] - 0.0864) < 1e-15);
		  } catch (IllegalArgumentException e) {
		    thrown = true;
		  }
		  assertTrue(thrown);
	}
	
	@Test
	public void testWrongmatrices() {
		boolean thrown = true;
		try {
			double[][] A = new double[2][2];
			A[0][0] = 0.9;
			A[0][1] = 0.4;
			A[1][0] = 0.4;
			A[1][1] = 0.6;
			
			double[][] B = new double[2][2];
			B[0][0] = 0.6;
			B[0][1] = 0.4;
			B[1][0] = 0.4;
			B[1][1] = 0.6;
			
			double[] pi = new double[2];
			pi[0] = 0.6;
			pi[1] = 0.4;
			
			Viterbi v = new Viterbi(pi, A, B);
			
		  } catch (IllegalArgumentException e) {
		    thrown = true;
		  }
		  assertTrue(thrown);
	}
	
	@Test
	public void testDNA() {
		double[][] A = new double[2][2];
		A[0][0] = 0.5;
		A[0][1] = 0.5;
		A[1][0] = 0.4;
		A[1][1] = 0.6;
		
		double[][] B = new double[2][4];
		B[0][0] = 0.2;
		B[0][1] = 0.3;
		B[0][2] = 0.3;
		B[0][3] = 0.2;
		B[1][0] = 0.3;
		B[1][1] = 0.2;
		B[1][2] = 0.2;
		B[1][3] = 0.3;
		
		double[] pi = new double[2];
		pi[0] = 0.5;
		pi[1] = 0.5;
		Viterbi v = new Viterbi(pi, A, B);
		int[]omega = {3,3,2,1,2,4,3,1,1};
		int[]arr = {1, 1, 1, 1, 2, 2, 2, 2, 2};
		assertTrue("DNA example1",Math.abs(v.maxScore(omega, false) 
				- 4.2515E-8) < 1e-12);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(0) == 1);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(1) == 1);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(2) == 1);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(3) == 2);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(4) == 2);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(5) == 2);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(6) == 2);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(7) == 2);
		assertTrue("DNA example1 bestSeq",v.bestSeq(omega, false).get(8) == 2);
	}
	
	@Test
	public void testDNA2() {
		double[][] A = new double[2][2];
		A[0][0] = 0.5;
		A[0][1] = 0.5;
		A[1][0] = 0.4;
		A[1][1] = 0.6;
		
		double[][] B = new double[2][4];
		B[0][0] = 0.2;
		B[0][1] = 0.3;
		B[0][2] = 0.3;
		B[0][3] = 0.2;
		B[1][0] = 0.3;
		B[1][1] = 0.2;
		B[1][2] = 0.2;
		B[1][3] = 0.3;
		
		double[] pi = new double[2];
		pi[0] = 0.5;
		pi[1] = 0.5;
		Viterbi v = new Viterbi(pi, A, B);
		int[]omega = {3,3,2,1,2,4,3,1,1};
		int[] omega1 = {3,1,3,1,4,1,4,1,2,1,4,1,3,1,1,4,4,1,2,3}; //GAGATATACATAGAATTACG
		assertTrue("DNA example2",Math.abs(v.maxScore(omega1, false) 
				- 1.1658194985273035E-16) < 1e-15);
	}
	
	@Test
	public void TestWorstSeq() {
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
		assertEquals("", v.worstSeq(omega, false),(l1));
		
	}
	
	@Test
	public void TestbestSeqSet1000length() {
		double[][] A = new double[2][2];
		A[0][0] = 0.5;
		A[0][1] = 0.5;
		A[1][0] = 0.5;
		A[1][1] = 0.5;
		
		double[][] B = new double[2][2];
		B[0][0] = 0.5;
		B[0][1] = 0.5;
		B[1][0] = 0.5;
		B[1][1] = 0.5;
		
		double[] pi = new double[2];
		pi[0] = 0.5;
		pi[1] = 0.5;
		
		Viterbi v = new Viterbi(pi, A, B);
		int[]omega = new int[1000];
		for(int i = 0; i < 500; i++) {
			omega[i] = 1;
		}
		for(int i = 500; i < 1000; i++) {
			omega[i] = 2;
		}
		Integer[] arr1 = {1,1};
		Integer[] arr2 = {2,2};
		List<Integer> l1 = Arrays.asList(arr1);
		List<Integer> l2 = Arrays.asList(arr2);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(l1);
		list.add(l2);
		assertTrue("", Math.abs(v.maxScore(omega, true) - (-1386.2943611198643))
				< 1e-15);
		
	}

}
