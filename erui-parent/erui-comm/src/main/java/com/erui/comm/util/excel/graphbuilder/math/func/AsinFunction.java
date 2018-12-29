package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The arc sine function.

@see Math#asin(double)
*/
public class AsinFunction implements Function {

	public AsinFunction() {}

	/**
	Returns the arc sine of the value at index location 0.
	*/
	public double of(double[] d, int numParam) {
		return Math.asin(d[0]);
	}

	/**
	Returns true only for 1 parameter, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 1;
	}

	public String toString() {
		return "asin(x)";
	}
}