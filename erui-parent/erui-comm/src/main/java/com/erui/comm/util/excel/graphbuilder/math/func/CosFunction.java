package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The cosine function.

@see Math#cos(double)
*/
public class CosFunction implements Function {

	public CosFunction() {}

	/**
	Returns the cosine of the angle value at index location 0.
	*/
	public double of(double[] d, int numParam) {
		return Math.cos(d[0]);
	}

	/**
	Returns true only for 1 parameter, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 1;
	}

	public String toString() {
		return "cos(x)";
	}
}