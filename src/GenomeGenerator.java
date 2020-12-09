import java.util.*;

public class GenomeGenerator {

    Random random;

    private int[] addEliminatedGens(int[] genome){
        TreeMap<Integer, Integer> gensNumberMap = new TreeMap<>();
        for(int i = 0; i < 8; i++){
            gensNumberMap.put(i,0);
        }
        for(int i = 0; i < 32; i++){
            gensNumberMap.put(genome[i], gensNumberMap.get(genome[i])+1);
        }
        for(int i = 0; i < 8; i++){
            if(gensNumberMap.get(i) == 0){
                gensNumberMap.put(i, 1);
                int lastKey = gensNumberMap.lastKey();
                if(gensNumberMap.get(lastKey) < 2) lastKey--;
                gensNumberMap.put(lastKey, gensNumberMap.get(lastKey)-1);
            }
        }
        System.out.println(gensNumberMap);
        int k = 0;
        for(int i = 0; i < 8 ; i++){
            for(int j = 0; j < gensNumberMap.get(i); j++){
                genome[k] = i;
                k++;
            }
        }
        return genome;
    }

    public int[] generateRandomGenome(){
        int[] genome = new int[32];
        for(int i = 0; i < 32; i++){
            genome[i] = this.random.nextInt(8);
        }
        genome = addEliminatedGens(genome);
        Arrays.sort(genome);
        return genome;
    }

    public int[] generateChildGenome(Animal animal1, Animal animal2){
        int firstCut = random.nextInt(31);
        int secondCut = random.nextInt(31);
        int[] genome = new int[32];
        for(int i = 0; i < Math.min(firstCut,secondCut); i++){
            genome[i] = animal1.genome[i];
        }
        for(int i = Math.min(firstCut, secondCut); i < Math.max(firstCut, secondCut); i++){
            genome[i] = animal2.genome[i];
        }
        for(int i = Math.max(firstCut,secondCut); i < 32; i++){
            genome[i] = animal1.genome[i];
        }
        genome = addEliminatedGens(genome);
        return genome;
    }

    GenomeGenerator(Random random){
        this.random = random;
    }
}
