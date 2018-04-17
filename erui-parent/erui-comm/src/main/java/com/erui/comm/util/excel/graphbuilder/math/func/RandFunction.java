package com.erui.comm.util.excel.graphbuilder.math.func;

/**
The random function.

@see Math#random()
*/
public class RandFunction implements Function {

	public RandFunction() {}

	/**
	Returns a random value in the range [0, 1) that does not depend on the input.
	*/
	public double of(double[] d, int numParam) {
		return Math.random();
	}

	/**
	Returns true only for 0 parameters, false otherwise.
	*/
	public boolean acceptNumParam(int numParam) {
		return numParam == 0;
	}

	public String toString() {
		return "rand()";
	}
}