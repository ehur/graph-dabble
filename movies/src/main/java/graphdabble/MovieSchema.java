package graphdabble;

import com.netflix.nfgraph.spec.NFGraphSpec;
import com.netflix.nfgraph.spec.NFNodeSpec;
import com.netflix.nfgraph.spec.NFPropertySpec;

import static com.netflix.nfgraph.spec.NFPropertySpec.*;

public class MovieSchema {

    private NFGraphSpec schema;
    public static final String MOVIE_NODE="Movie";
    public static final String ACTOR_NODE="Actor";
    public static final String RATING_NODE="Rating";
    public static final String STARRING_CONNECTION="starring";
    public static final String RATED_CONNECTION="rated";
    public static final String COSTARRED_WITH_CONNECTION="costarredWith";
    public static final String STARRED_IN_CONNECTION="starredIn";
    public static final String PREMIERED_IN_CONNECTION="premieredIn";

    public MovieSchema() {
        init();
    }

    public void init(){
        NFGraphSpec schema = new NFGraphSpec(
                new NFNodeSpec("Actor",
                        new NFPropertySpec("starredIn", "Movie", MULTIPLE | COMPACT),
                        new NFPropertySpec("costarredWith", "Actor", MULTIPLE | HASH),
                        new NFPropertySpec("premieredIn", "Movie", SINGLE)
                ),
                new NFNodeSpec("Movie",
                        new NFPropertySpec("rated", "Rating", SINGLE),
                        new NFPropertySpec("starring", "Actor", MULTIPLE | HASH)),
                new NFNodeSpec("Rating")
        );
        this.schema = schema;
    }

    public NFGraphSpec getSchema() {
        return schema;
    }

    public void setSchema(NFGraphSpec schema) {
        this.schema = schema;
    }
}
