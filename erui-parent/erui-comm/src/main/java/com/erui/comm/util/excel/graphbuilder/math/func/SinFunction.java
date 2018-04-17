package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The sine function.

@see Math#sin(double)
*/
public class SinFunction implements Function {

	public SinFunction() {}

	/**
	Returns the sine of the angle value at index location 0.
	*/
	public double of(double[] d, int numParam) {
		return Math.sin(d[0]);
	}

	/**
	Returns true only for 1 parameter, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 1;
	}

	public String toString() {
		return "sin(x)";
	}
}