import java.util.LinkedList;

public class FindBandwidth {

    private int[] minPermutation;
    private int minBandwidth;
    private final Graph graph;


    public FindBandwidth(Graph g) {
        graph = g;
        minPermutation = new int[g.getNumVertices()];
        minBandwidth = Integer.MAX_VALUE;
        findMinimumPermutation();
    }

    /**
     * @return the minimum permutation of the passed in graph
     */
    public int[] getMinPermutation() {
        return minPermutation;
    }

    /**
     * @return the minimum bandwidth of the passed in graph
     */
    public int getMinBandwidth() {
        return minBandwidth;
    }

    /**
     * Finds the permutation with the minimum bandwidth of the graph
     */
    private void findMinimumPermutation() {
        int[] v = new int[graph.getNumVertices()];
        for(int i = 0; i < v.length; i++) {
            v[i] = i;
        }
        int[] curr = new int[graph.getNumVertices()];

        boolean[] inPerm = new boolean[graph.getNumVertices()];
        getPermutations(v, curr, inPerm, 0, graph.getNumVertices());
    }

    /**
     * Finds the permutations of the given list
     * @param vertexes the array of vertexes
     * @param curr the current partial solution
     * @param inPerm inPerm[i] is true if vertex i is already in the solution
     * @param sizeOfList size of the current solution
     * @param sizeOfSolution size of the desired solution
     */ //TODO maybe we can put some tasty dynamic programming here
    private void getPermutations(int[] vertexes, int[] curr, boolean[] inPerm, int sizeOfList, int sizeOfSolution) {
        if(sizeOfList == sizeOfSolution) {
            checkPermutation(curr);
        } else {
            for(int i = 0; i < sizeOfSolution; i++) {
                if(!inPerm[i]) {
                    curr[sizeOfList] = vertexes[i];
                    inPerm[i] = true;
                    if(checkPartialPermutation(curr, sizeOfList+1)) {
                        getPermutations(vertexes, curr, inPerm, sizeOfList+1, sizeOfSolution);
                    }
                    inPerm[i] = false;
                }
            }
        }
    }

    /**
     * Checks if the current permutation is the new minimum permutation
     * @param v the current permutation
     */
    private void checkPermutation(int[] v) {
        int bw = getBandwidth(v);
        if(bw < minBandwidth) {
            minBandwidth = bw;
            minPermutation = v.clone();
        }
    }

    /**
     * Checks if the current partial permutation is above the current minimum bandwidth
     * @param curr current partial permutation
     * @return false when the partial permutation is not viable
     */ //TODO this is also kinda slow..idk how to fix that though?
    private boolean checkPartialPermutation(int[] curr, int size) {
        if(size > 1) {
            boolean[] inPerm = new boolean[curr.length];
            for(int i = 0; i < curr.length; i++) {
                for(int j = 0; j < size; j++) {
                    if(curr[j] == i) {
                        inPerm[i] = true;
                    }
                }
            }
            for(int i = 0; i < size; i++) {
                LinkedList<Integer> adj = graph.getAdjList(curr[i]);
                for(int j = 0; j < adj.size(); j++) {
                    int x = adj.get(j); // x, y are the values to find distance between
                    int y = curr[i];
                    if(inPerm[x] && inPerm[y]) {
                        int l = 0; // l is the distance between i and x
                        boolean foundFirst = false;
                        boolean foundSecond = false;
                        for(int n = 0; n < size; n++) {
                            if(!foundFirst && !foundSecond && (curr[n] == x || curr[n] == y)) {
                                foundFirst = true;
                                l++;
                            } else if (foundFirst && !foundSecond && !(curr[n] == x || curr[n] == y)){
                                l++;
                            } else if (foundFirst && !foundSecond && (curr[n] == x || curr[n] == y)) {
                                foundSecond = true;
                            }

                        }
                        if(l > minBandwidth) {
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }

    /**
     * Finds the bandwidth of the permutation passed into the function
     * @param permutation the permutation
     * @return the bandwidth
     */
    private int getBandwidth(int[] permutation) {
        int bw = 0; // bandwidth of this permutation
        for(int i = 0; i < permutation.length; i++) {
            LinkedList<Integer> adj = graph.getAdjList(i);
            for(int j = 0; j < adj.size(); j++) {
                int x = adj.get(j); // i, x are the values to find distance between
                int l = 0; // l is the distance between i and x
                boolean foundFirst = false;
                boolean foundSecond = false;
                for(int n = 0; n < permutation.length; n++) {
                    if(!foundFirst && !foundSecond && (permutation[n] == x || permutation[n] == i)) {
                        foundFirst = true;
                        l++;
                    } else if (foundFirst && !foundSecond && !(permutation[n] == x || permutation[n] == i)){
                        l++;
                    } else if (foundFirst && !foundSecond && (permutation[n] == x || permutation[n] == i)) {
                        foundSecond = true;
                    }

                }
                if(l > bw) {
                    bw = l;
                }
            }
        }
        return bw;
    }
}
