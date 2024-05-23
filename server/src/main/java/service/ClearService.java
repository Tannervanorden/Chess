package service;

import dataaccess.Database;
import dataaccess.DataAccessException;

public class ClearService {
    public void clear() throws DataAccessException {
        Database db = new Database();
        db.clear()
    }

}
