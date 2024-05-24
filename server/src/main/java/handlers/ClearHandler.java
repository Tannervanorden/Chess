package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private Gson gson = new Gson();
    private ClearService clearService = new ClearService();


}
