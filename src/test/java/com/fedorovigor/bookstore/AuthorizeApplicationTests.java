package com.fedorovigor.bookstore;

import org.junit.jupiter.api.Test;

import java.util.*;

//@SpringBootTest
class AuthorizeApplicationTests {


	private Comparable[] aux = new String[10000];

	@Test
	void contextLoads() {

		var input = Arrays.asList("h" ,"d", "q","p","Q","[").toArray(new String[0] ) ;

		sort1(input);

		System.out.println(isSorted(input));
		show(input);
	}

	void merge(Comparable[] a, int lo, int mid, int hi) {
		int i =lo;
		int j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		for (int k = 0; k < hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}
	}

	void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;

			for (int j = i+1; j < N; j++) {
				if (less(a[j],a[min]))
					min = j;
			}

			exch(a, i, min);
		}
	}

	void sort1(Comparable[] a) {
		int N = a.length;

		for (int i = 1; i < N; i++) {
			for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
				exch(a,j,j-1);
			}
		}
	}

	boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}

	void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	void show(Comparable[] a) {
		for (var i : a)
			System.out.println(i + " ");
	}
	
	boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i-1]))
				return false;
		}

		return true;
	}

}
