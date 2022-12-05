package org.example.application.game.server;

import org.example.application.game.server.dto.Request;
import org.example.application.game.server.dto.Response;
import org.example.application.game.server.exception.UnsupportedProtocolException;
import org.example.application.game.server.util.RequestBuilder;
import org.example.application.game.server.util.RequestReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private BufferedReader in;
    private PrintWriter out;

    private final Socket socket;
    private final Application application;

    public RequestHandler(Socket socket, Application application) {
        this.socket = socket;
        this.application = application;
    }

    @Override
    public void run() {
        try {
            Request request = getRequest();
            Response response = application.handle(request);
            sendResponse(response);
        } catch (IOException ignored) {
        } catch (UnsupportedProtocolException e) {
            e.printStackTrace();
        } finally {
            closeRequest();
        }
    }

    private Request getRequest() throws IOException, UnsupportedProtocolException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        Request request = new Request();
//        request.authorizeRequest();
        String request = RequestReader.read(in);




        return RequestBuilder.build(request);
    }

    private void sendResponse(Response response) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);

        out.write(response.toString());
    }

    private void closeRequest() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

