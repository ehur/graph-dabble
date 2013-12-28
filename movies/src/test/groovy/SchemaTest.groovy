import com.netflix.nfgraph.build.NFBuildGraph
import com.netflix.nfgraph.compressed.NFCompressedGraph
import com.netflix.nfgraph.util.OrdinalMap
import graphdabble.GraphUtil
import graphdabble.MovieSchema
import spock.lang.Specification

class SchemaTest extends Specification {

    private MovieSchema schema
    private NFBuildGraph buildGraph
    private OrdinalMap<String> movieOrdinals = new OrdinalMap<String>();
    private OrdinalMap<String> actorOrdinals = new OrdinalMap<String>();
    private OrdinalMap<String> ratingOrdinals = new OrdinalMap<String>();
    private int theMatrix,theMatrix2,reeves,fishbourne,r

    public void setup(){
        schema = new MovieSchema()
        buildGraph = new NFBuildGraph(schema.getSchema())
        setupMovieOrdinals()
        setupActorOrdinals()
        setupRatingOrdinals()
        addRelationships()
    }

    def "all stars of the Matrix"(){
        given:
            NFCompressedGraph compressedGraph = buildGraph.compress()
            GraphUtil util = new GraphUtil(compressedGraph)
        when:
            def costars = util.findAllStarsInMovie(theMatrix)
        then:
            costars.size() == 2
            costars.contains(reeves)
            costars.contains(fishbourne)
    }

    def "all stars of the Matrix 2"(){
        given:
            NFCompressedGraph compressedGraph = buildGraph.compress()
            GraphUtil util = new GraphUtil(compressedGraph)
        when:
            def costars = util.forConnectionProperty(MovieSchema.STARRING_CONNECTION).forNodeType(MovieSchema.MOVIE_NODE).forNodeOrdinal(theMatrix).find()
        then:
            costars.size() == 2
            costars.contains(reeves)
            costars.contains(fishbourne)
    }

    def "all movies starring reeves"(){
        given:
            NFCompressedGraph compressedGraph = buildGraph.compress()
            GraphUtil util = new GraphUtil(compressedGraph)
        when:
            def moviesWithReeves = util.forConnectionProperty(MovieSchema.STARRED_IN_CONNECTION).forNodeType(MovieSchema.ACTOR_NODE).forNodeOrdinal(reeves).find()
        then:
            moviesWithReeves.size() == 2
            moviesWithReeves.contains(theMatrix)
            moviesWithReeves.contains(theMatrix2)
    }

    def setupMovieOrdinals(){
        theMatrix = movieOrdinals.add("The Matrix")
        theMatrix2 = movieOrdinals.add("The Matrix 2")
    }
    def setupActorOrdinals(){
        reeves = actorOrdinals.add("reeves")
        fishbourne = actorOrdinals.add("fishbourne")
    }
    def setupRatingOrdinals(){
        r = ratingOrdinals.add("r")
    }
    def addRelationships(){
        buildGraph.addConnection("Actor", reeves, "starredIn", theMatrix);
        buildGraph.addConnection("Actor", reeves, "starredIn", theMatrix2);
        buildGraph.addConnection("Actor", fishbourne, "starredIn", theMatrix);
        buildGraph.addConnection("Movie", theMatrix, "starring", reeves);
        buildGraph.addConnection("Movie", theMatrix2, "starring", reeves);
        buildGraph.addConnection("Movie", theMatrix, "starring", fishbourne);
        buildGraph.addConnection("Movie", theMatrix, "rated", r);
    }



}
