package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The arc cosine function.

@see Math#acos(double)
*/
public class AcosFunction implements Function {

	public AcosFunction() {}

	/**
	Returns the arc cosine of the value at index location 0.
	*/
	public double of(double[] d, int numParam) {
		return Math.acos(d[0]);
	}

	/**
	Returns true only for 1 parameter, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 1;
	}

	public String toString() {
		return "acos(x)";
	}
}