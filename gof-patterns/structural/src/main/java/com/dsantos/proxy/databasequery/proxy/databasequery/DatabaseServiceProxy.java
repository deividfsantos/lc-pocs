package com.dsantos.proxy.databasequery.proxy.databasequery;

public class DatabaseServiceProxy implements DatabaseService {

    private final DatabaseService dbService;
    private final String user;
    private final String pwd;

    public DatabaseServiceProxy(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
        this.dbService = new DatabaseServiceImpl();
    }

    private boolean isAuthorized() {
        return "admin".equals(user) && "admin123".equals(pwd);
    }

    @Override
    public void connect(String dbUrl) throws Exception {
        if (isAuthorized()) {
            dbService.connect(dbUrl);
        } else {
            throw new Exception("Unauthorized access to database.");
        }
    }

    @Override
    public void executeQuery(String query) throws Exception {
        if (isAuthorized()) {
            dbService.executeQuery(query);
        } else {
            throw new Exception("Unauthorized access to execute query.");
        }
    }
}