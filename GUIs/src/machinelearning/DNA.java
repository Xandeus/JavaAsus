package machinelearning;

import java.util.Random;

public class DNA {
	Random rand = new Random();
	char[] genes;
	float fitness;
	static String target = "cat in the hat";

	DNA() {
		genes = new char[target.length()];
		for (int i = 0; i < genes.length; i++) {
			genes[i] = (char) (rand.nextInt((128 - 31) + 32));
		}
	}
	public static void setTarget(String phrase){
		target = phrase;
	}
	void fitness() {
		int score = 0;
		for (int i = 0; i < genes.length; i++) {
			if (genes[i] == target.charAt(i)) {
				score++;
			}
		}
		fitness = (float) (score) / target.length();
	}

	DNA crossover(DNA partner) {
		DNA child = new DNA();
		int midpoint = (rand.nextInt(genes.length));
		for (int i = 0; i < genes.length; i++) {
			if (i > midpoint)
				child.genes[i] = genes[i];
			else
				child.genes[i] = partner.genes[i];
		}
		return child;
	}

	void mutate(Double mutationRate) {
		for (int i = 0; i < genes.length; i++) {
			if (Math.random() < mutationRate) {
				genes[i] = (char) (rand.nextInt((128 - 31) + 32));
			}
		}
	}

	String getPhrase() {
		return new String(genes);
	}

}
