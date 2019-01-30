package neural_network;

import java.util.Arrays;


public class NeuralNetwork {

	private double[][] output;
	private double[][][] weights;
	private double[][] bias;
	
	public final int[] LAYER_SIZES;
	public final int NETWORK_SIZE;
	public final int INPUT_SIZE;
	public final int OUTPUT_SIZE;
	
	public NeuralNetwork(int... LAYER_SIZES) {
		this.LAYER_SIZES = LAYER_SIZES;
		this.NETWORK_SIZE = LAYER_SIZES.length;
		this.INPUT_SIZE = LAYER_SIZES[0];
		this.OUTPUT_SIZE = LAYER_SIZES[NETWORK_SIZE-1];
		
		this.output = new double[NETWORK_SIZE][];
		this.weights = new double[NETWORK_SIZE][][];
		this.bias = new double[NETWORK_SIZE][];
		
		for (int i = 0; i < NETWORK_SIZE; i++) {
			this.output[i] = new double[LAYER_SIZES[i]];
			this.bias[i] = NetworkTools.createRandomArray(LAYER_SIZES[i], 0.3, 0.7);
			
			if(i > 0) {
				this.weights[i] = NetworkTools.createRandomArray(LAYER_SIZES[i], LAYER_SIZES[i-1], -0.3, 0.5);

			}
		}
		
	}
	
	public double[] calculate(double... input) {
		if(input.length != INPUT_SIZE) return null;
		this.output[0] = input;
		for (int layer = 1; layer < NETWORK_SIZE; layer++) {
			for(int neuron = 0; neuron < LAYER_SIZES[layer]; neuron++) {
				
				double sum = bias[layer][neuron];;
				for(int prevNeuron = 0; prevNeuron < LAYER_SIZES[layer-1]; prevNeuron++) {
					sum += output[layer-1][prevNeuron] * weights[layer][neuron][prevNeuron];
				}
				output[layer][neuron] = sigmoid(sum);
				
			}
		}
		return output[NETWORK_SIZE-1];
	}
	
	private double sigmoid(double x) {
		return 1d/(1 + Math.exp(-x));
	}
	
	public static void main(String[] args) {
		NeuralNetwork net = new NeuralNetwork(4,2,3,4);
		double[] output = net.calculate(0.2,0.1,0.8,0.6);
		System.out.println(Arrays.toString(output));
		output = net.calculate(0.2,0.1,0.8,0.6);
		System.out.println(Arrays.toString(output));
		output = net.calculate(0.2,0.1,0.8,0.6);
		System.out.println(Arrays.toString(output));
	}
}
