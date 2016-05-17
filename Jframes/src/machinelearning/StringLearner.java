package machinelearning;

import java.util.ArrayList;
import java.util.Random;

public class StringLearner {
	public static void main(String[] args) {
		
		float mutationRate;
		int totalPopulation = 150;
		DNA[] population;
		Random rand = new Random();
		ArrayList<DNA> matingPool = new ArrayList<DNA>();

		mutationRate = 0.01f;

		population = new DNA[totalPopulation];
		for (int i = 0; i < population.length; i++) {
			population[i] = new DNA();
		}
		DNA h = population[0];
		int cycle = 1;
		while(h.fitness != 1){
			System.out.println("Generation: " + cycle);
			matingPool.clear();
			for (int i = 0; i < population.length; i++) {
				population[i].fitness();
				//System.out.println(population[i].fitness);
			}

			for (int i = 0; i < population.length; i++) {
				int n = (int) (population[i].fitness * 100);
				for (int j = 0; j < n; j++) {
					matingPool.add(population[i]);
				}
			}
			//Showing DNA over a certain fitnesh
			h = population[0];
			for(DNA x : population){
				if(x.fitness > h.fitness){
					h = x;
				}
			}
			System.out.println("Highest fitness: " + h.fitness + "\nPhrase: " + h.getPhrase() + "\n");
			//Mating
			for (int i = 0; i < population.length; i++) {
				int a = rand.nextInt(matingPool.size());
				int b = rand.nextInt(matingPool.size());
				DNA partnerA = matingPool.get(a);
				DNA partnerB = matingPool.get(b);
				DNA child = partnerA.crossover(partnerB);
				child.mutate(mutationRate);

				population[i] = child;
			}
			cycle++;
		}
	}
	
}
