package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The exp function.

@see Math#exp(double)
*/
public class ExpFunction implements Function {

	public ExpFunction() {}

	/**
	Returns Euler's number, <i>e</i>, raised to the exponent of the value at index location 0.
	*/
	public double of(double[] d, int numParam) {
		return Math.exp(d[0]);
	}

	/**
	Returns true only for 1 parameter, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 1;
	}

	public String toString() {
		return "exp(x)";
	}
}