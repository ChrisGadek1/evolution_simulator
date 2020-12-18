import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Genome {
    private int[] gens;
    private Map<Integer, Integer> gensNumber = new HashMap<>();

    public Genome(int[] gens){
        this.gens = gens;
        for(int i = 0; i < 32; i++){
            if(gensNumber.get(gens[i]) == null) gensNumber.put(gens[i], 0);
            gensNumber.put(gens[i], gensNumber.get(gens[i])+1);
        }
    }

    int[] getGens(){
        return this.gens;
    }

    public Genome copyGenome(){
        return new Genome(this.gens.clone());
    }

    @Override
    public String toString(){
        return this.gensNumber.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genome genome = (Genome) o;
        return Arrays.equals(gens, genome.gens);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(gens);
    }
}
