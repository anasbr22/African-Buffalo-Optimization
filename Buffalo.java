
import java.util.List;

class Buffalo {
    List<Boolean> solution;
    float fitness;
    float totalWeight;
    float exploitation;

    Buffalo(List<Boolean> solution, float fitness, float totalWeight) {
        this.solution = solution;
        this.fitness = fitness;
        this.totalWeight = totalWeight;
        this.exploitation = 0.0f;
    }
}
