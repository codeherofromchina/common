package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The tangent function.

@see Math#tan(double)
*/
public class TanFunction implements Function {

	public TanFunction() {}

	/**
	Returns the tangent of the angle value at index location 0.
	*/
	public double of(double[] d, int numParam) {
		return Math.tan(d[0]);
	}

	/**
	Returns true only for 1 parameter, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 1;
	}

	public String toString() {
		return "tan(x)";
	}
}