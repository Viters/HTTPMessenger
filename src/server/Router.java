package server;

public abstract class Router {
    public Response route(Request request) {
        if (request.isGet()) {
            return getRoute(request);
        }
        else if (request.isPost()) {
            return postRoute(request);
        }
        else if (request.isOption())
            return ResponseFactory.allowCORS();

        return null;
    }

    public abstract Response getRoute(Request request);

    public abstract Response postRoute(Request request);
}
