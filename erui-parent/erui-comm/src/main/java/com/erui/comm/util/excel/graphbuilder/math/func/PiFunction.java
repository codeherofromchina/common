package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The constant Pi.

@see Math#PI
*/
public class PiFunction implements Function {

	public PiFunction() {}

	/**
	Returns the constant Pi regardless of the input.
	*/
	public double of(double[] d, int numParam) {
		return Math.PI;
	}

	/**
	Returns true only for 0 parameters, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 0;
	}

	public String toString() {
		return "pi()";
	}
}