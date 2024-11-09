
import java.util.*;

public class ABOForTSP {
	private Random random = new Random();
    private double[][] distanceMatrix;

    private int NUM_BUFFALOES;  
    private int NUM_ITERATIONS; 
    private int NUM_CITIES;
    private double L1; 
    private double L2; 
    private double LAMBDA;
    private List<Integer> bogmax;
    private double bogmaxDistance;

    public double calculateTourDistance(List<Integer> tour) {
        double distance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += distanceMatrix[tour.get(i)][tour.get(i + 1)];
        }
        // Complete the loop (return to the first city)
        distance += distanceMatrix[tour.get(tour.size() - 1)][tour.get(0)];
        return distance;
    }
    public ABOForTSP(int NUM_BUFFALOES, int NUM_ITERATIONS, double L1, double L2, double[][] distanceMatrix) {
    	this.NUM_BUFFALOES = NUM_BUFFALOES;
    	this.NUM_ITERATIONS = NUM_ITERATIONS;
    	this.L1 = L1;
    	this.L2 = L2;
    	this.distanceMatrix = distanceMatrix;
    	this.LAMBDA = random.nextDouble(0.5,0.9);
    	this.solver();
    }
    // Main method to run the ABO algorithm
    public void solver() {
        NUM_CITIES = distanceMatrix.length;

		List<List<Integer>> buffaloes = new ArrayList<>();
		for (int i = 0; i < NUM_BUFFALOES; i++) {
		    List<Integer> tour = new ArrayList<>();
		    for (int j = 1; j < NUM_CITIES; j++) {
		        tour.add(j);
		    }
		    Collections.shuffle(tour);
		    tour.add(0, 0);  
		    buffaloes.add(tour);
		}

		
		bogmax = getBestTour(buffaloes);
		bogmaxDistance = calculateTourDistance(bogmax);

		
		List<List<Integer>> bopmax = new ArrayList<>(buffaloes);
		List<Double> bopmaxDistances = new ArrayList<>();
		for (List<Integer> tour : bopmax) {
		    bopmaxDistances.add(calculateTourDistance(tour));
		}

		// Step 3-5: Main ABO loop
		for (int iteration = 0; iteration < NUM_ITERATIONS; iteration++) {
		    // Step 2: Update buffaloes' exploitation (adapted for TSP)
		    for (int i = 0; i < buffaloes.size(); i++) {
		        List<Integer> tour = buffaloes.get(i);
		        List<Integer> newTour = new ArrayList<>(tour);

		        // Swap based on learning rates
		        if (random.nextDouble() < L1) {
		            performRandomSwap(newTour);
		        }

		        if (random.nextDouble() < L2) {
		            performRandomSwap(newTour);
		        }

		        double newDistance = calculateTourDistance(newTour);

		        // Update the best individual position
		        if (newDistance < bopmaxDistances.get(i)) {
		            bopmax.set(i, new ArrayList<>(newTour));
		            bopmaxDistances.set(i, newDistance);
		        }

		        // Update the best overall group maximum
		        if (newDistance < bogmaxDistance) {
		            bogmax = new ArrayList<>(newTour);
		            bogmaxDistance = newDistance;
		        }

		        buffaloes.set(i, new ArrayList<>(newTour));
		    }

		    
		    for (int i = 0; i < buffaloes.size(); i++) {
		        if (random.nextDouble() < (1 / LAMBDA)) {
		            List<Integer> tour = buffaloes.get(i);
		            performRandomSwap(tour);
		        }
		    }
		}
    }

    // Helper method to perform a random swap within a tour
    public void performRandomSwap(List<Integer> tour) {
        int city1 = random.nextInt(NUM_CITIES - 1) + 1;
        int city2 = random.nextInt(NUM_CITIES - 1) + 1;
        Collections.swap(tour, city1, city2);
    }

    // Helper method to get the best tour in a list of tours
    public List<Integer> getBestTour(List<List<Integer>> tours) {
        List<Integer> bestTour = null;
        double bestDistance = Double.MAX_VALUE;
        for (List<Integer> tour : tours) {
            double distance = calculateTourDistance(tour);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestTour = new ArrayList<>(tour);
            }
        }
        return bestTour;
    }
    public double getBestDistance() {
    	return bogmaxDistance;
    }
    public List<Integer> getBestRoute() {
    	return bogmax;
    }
}