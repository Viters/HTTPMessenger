package server;

/**
 * Created by Viters on 10.01.2017.
 */
public abstract class Router {
    public Response route(Request request) {
        if (request.isGet()) {
            return getRoute(request);
        }
        else if (request.isPost()) {
            return postRoute(request);
        }
        else if (request.isOption())
            return ResponseFactory.AllowCORS();

        return null;
    }

    public abstract Response getRoute(Request request);

    public abstract Response postRoute(Request request);
}
