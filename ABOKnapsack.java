
import java.util.*;
import java.util.List;


public class ABOKnapsack {
    Random rand = new Random();
    int populationSize;
    int nbr_iter;
    double l1;
    double l2; 
    double lambda; 
    Item[] items;
    Buffalo bestBuffalo;
    float capacity;
    public ABOKnapsack(double l1, double l2, int population, int nbr_iter,List<float[]> items, float capacity) {
    	this.l1 = l1;
    	this.l2 = l2;
    	this.nbr_iter = nbr_iter;
    	this.populationSize = population;
    	this.items = new Item[items.size()];
    	this.capacity = capacity;
    	int i = 0;
    	for(float[] item : items) {
    		Item temp = new Item(item[1], item[2]);
    		this.items[i] = temp;
    		i++;
    	}
	    this.solve();
    }
    
    public void solve() {
        

        List<Buffalo> buffaloes = initializePopulation();

        // Iteratively update buffaloes
        for (int i = 0; i < nbr_iter; i++) {
            Buffalo globalBest = findGlobalBest(buffaloes);
            Buffalo[] localBests = findLocalBests(buffaloes);

            for (int j = 0; j < buffaloes.size(); j++) {
                // Update exploitation
                Buffalo buffalo = buffaloes.get(j);
                float localBestFitness = localBests[j].fitness;

                buffalo.exploitation += (float)(l1 * (globalBest.fitness - buffalo.fitness)) + 
                                        (float)(l2 * (localBestFitness - buffalo.fitness));

                // Update location with constraint checking
                double scaling = lambda;
                buffalo.solution = updateLocation(buffalo.solution, buffalo.exploitation, scaling);

                // Ensure the solution does not exceed capacity
                buffalo.fitness = calculateFitness(buffalo.solution);
                buffalo.totalWeight = calculateTotalWeight(buffalo.solution);

                // If the total weight exceeds capacity, adjust the solution to meet the constraint
                if (buffalo.totalWeight > capacity) {
                    adjustSolutionToMeetCapacity(buffalo);
                    buffalo.fitness = calculateFitness(buffalo.solution);
                    buffalo.totalWeight = calculateTotalWeight(buffalo.solution);
                }
            }
        }

        // Get the best solution after all generations
        bestBuffalo = findGlobalBest(buffaloes);
    }

    // Initialize the buffalo population with valid random solutions
    List<Buffalo> initializePopulation() {
        List<Buffalo> buffaloes = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Boolean> solution = generateValidSolution();

            float fitness = calculateFitness(solution);
            float totalWeight = calculateTotalWeight(solution);
            buffaloes.add(new Buffalo(solution, fitness, totalWeight));
        }

        return buffaloes;
    }

    // Generate a valid random solution within the capacity
    List<Boolean> generateValidSolution() {
        List<Boolean> solution = new ArrayList<>();
        float totalWeight = 0.0f;

        for (Item item : items) {
            boolean include = rand.nextBoolean();
            if (include) {
                totalWeight += item.weight;
            }

            if (totalWeight > capacity) {
                // If adding the item exceeds capacity, do not include it
                solution.add(false);
                totalWeight -= item.weight; // Undo the weight addition
            } else {
                solution.add(include);
            }
        }

        return solution;
    }

    // Calculate the fitness (total value) of a given solution
    float calculateFitness(List<Boolean> solution) {
        float totalWeight = 0.0f;
        float totalValue = 0.0f;

        for (int i = 0; i < solution.size(); i++) {
            if (solution.get(i)) {
                totalWeight += items[i].weight;
                totalValue += items[i].value;
            }
        }

        // If weight exceeds capacity, set fitness to zero
        if (totalWeight > capacity) {
            return 0.0f;
        }

        return totalValue;
    }

    // Calculate the total weight of a given solution
    float calculateTotalWeight(List<Boolean> solution) {
        float totalWeight = 0.0f;

        for (int i = 0; i < solution.size(); i++) {
            if (solution.get(i)) {
                totalWeight += items[i].weight;
            }
        }

        return totalWeight;
    }

    // Find the global best solution in the population
    Buffalo findGlobalBest(List<Buffalo> buffaloes) {
        return buffaloes.stream()
                .max(Comparator.comparingDouble(b -> b.fitness))
                .orElseThrow(() -> new IllegalStateException("Population is empty"));
    }

    // Find the local best solution for each buffalo in the population
    Buffalo[] findLocalBests(List<Buffalo> buffaloes) {
        Buffalo[] localBests = new Buffalo[buffaloes.size()];

        for (int i = 0; i < buffaloes.size(); i++) {
            Buffalo best = null;

            if (i > 0) {
                best = buffaloes.get(i - 1);
            }

            if (i < buffaloes.size() - 1) {
                if (best == null || buffaloes.get(i + 1).fitness > best.fitness) {
                    best = buffaloes.get(i + 1);
                }
            }

            localBests[i] = best;
        }

        return localBests;
    }

    // Update the solution based on the exploitation value
    List<Boolean> updateLocation(List<Boolean> solution, float exploitation, double scaling) {
        List<Boolean> newSolution = new ArrayList<>();

        for (Boolean item : solution) {
            double changeProbability = exploitation / scaling;
            if (rand.nextDouble() < changeProbability) {
                newSolution.add(!item); // Flip the boolean
            } else {
                newSolution.add(item);
            }
        }

        return newSolution;
    }

    // Adjust the solution to meet the weight capacity constraint
    void adjustSolutionToMeetCapacity(Buffalo buffalo) {
        // Remove items until the total weight is within the capacity limit
        for (int i = buffalo.solution.size() - 1; i >= 0; i--) {
            if (buffalo.solution.get(i)) {
                buffalo.solution.set(i, false);
                buffalo.totalWeight -= items[i].weight;

                if (buffalo.totalWeight <= capacity) {
                    break;
                }
            }
        }
    }
        List<Boolean> getBestSolution(){
    	return bestBuffalo.solution;
    }
    float getBestFitness() {
    	return bestBuffalo.fitness;
    }
    float getWeight() {
        return bestBuffalo.totalWeight;
    }
}