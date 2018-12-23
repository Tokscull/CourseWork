package server.service;

public class Developer {//strategy
    ServiceImpl service;

    public void setService(ServiceImpl service) {
        this.service = service;
    }

    public void executeService(String sqlString , Long idDeleteColumn ) {
        service.delete(sqlString, idDeleteColumn);
    }
}
