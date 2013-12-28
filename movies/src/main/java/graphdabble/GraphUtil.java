package graphdabble;

import com.netflix.nfgraph.OrdinalIterator;
import com.netflix.nfgraph.OrdinalSet;
import com.netflix.nfgraph.compressed.NFCompressedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphUtil {

    private NFCompressedGraph graph;
    private String forConnectionProperty;
    private String forNodeType;
    private int forNodeOrdinal;

    public GraphUtil(NFCompressedGraph forGraph) {
        graph = forGraph;
    }

    public NFCompressedGraph getGraph() {
        return graph;
    }

    public void setGraph(NFCompressedGraph graph) {
        this.graph = graph;
    }

    public List<Integer> findAllStarsInMovie(int movieOrdinal){
        return findAllConnectionsToNode("Movie", movieOrdinal, "starring");
    }

    public List<Integer> findAllConnectionsToNode(String nodeType, int movieOrdinal, String connectionProperty){
        OrdinalIterator iter = graph.getConnectionIterator(nodeType, movieOrdinal, connectionProperty);
        int currentOrdinal = iter.nextOrdinal();
        List<Integer> connections = new ArrayList<Integer>();
        while(currentOrdinal != OrdinalIterator.NO_MORE_ORDINALS) {
            connections.add(currentOrdinal);
            currentOrdinal = iter.nextOrdinal();
        }
        return connections;
    }

    public GraphUtil forConnectionProperty(String property){
        this.forConnectionProperty = property;
        return this;
    }
    public GraphUtil forNodeType(String nodeType){
        this.forNodeType = nodeType;
        return this;
    }
    public GraphUtil forNodeOrdinal(int nodeOrdinal){
        this.forNodeOrdinal = nodeOrdinal;
        return this;
    }
    public List<Integer> find(){
        return findAllConnectionsToNode(this.forNodeType, this.forNodeOrdinal, this.forConnectionProperty);
    }

}
