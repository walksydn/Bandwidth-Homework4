import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class Graph {
    private LinkedList<Integer>[] adjList;
    private int numVertices;
    private int numEdges;
    public Graph(String pathname){
        Path p = Paths.get(pathname);

        try {
            List<String> lines = Files.readAllLines(p);
            numVertices = Integer.parseInt(lines.get(0));
            numEdges = Integer.parseInt(lines.get(1));
            adjList = new LinkedList[numVertices];

            //Initialize adjList
            for (int i = 0; i < numVertices; i++){
                adjList[i] = new LinkedList<Integer>();
            }

            //Read from file and add edges
            Scanner s;
            for (int i = 2; i < lines.size(); i++) {
                s = new Scanner(lines.get(i));
                int index1 = s.nextInt()-1;
                int index2 = s.nextInt()-1;
                adjList[index1].add(index2);
                adjList[index2].add(index1);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public int getNumVertices(){ return numVertices; }

    public int getNumEdges(){ return numEdges; }

    public LinkedList<Integer> getAdjList(int i){ return adjList[i]; }

}
