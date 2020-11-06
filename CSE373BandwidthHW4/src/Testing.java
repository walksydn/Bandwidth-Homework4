public class Testing {

    public static void main(String[] args) {
        Graph g = new Graph("g-bt-12-11.txt");
        FindBandwidth fb = new FindBandwidth(g);
        System.out.println("Minimum Bandwidth: " + fb.getMinBandwidth());
        System.out.println("Permutation: ");
        System.out.print("     ");
        int[] perm = fb.getMinPermutation();
        for(int i = 0; i < perm.length; i++) {
            System.out.print((perm[i]+1) + " ");
        }
    }
}


