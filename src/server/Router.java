package server;

import server.exceptions.UnknownMethodException;

public abstract class Router {
    public Response route(Request request) throws UnknownMethodException {
        if (request.isGet()) {
            return getRoute(request);
        }
        else if (request.isPost()) {
            return postRoute(request);
        }
        else if (request.isOptions())
            return ResponseFactory.allowCORS();

        throw new UnknownMethodException();
    }

    public abstract Response getRoute(Request request);

    public abstract Response postRoute(Request request);
}
