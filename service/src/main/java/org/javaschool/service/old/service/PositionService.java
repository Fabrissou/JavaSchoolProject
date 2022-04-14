package org.javaschool.service.old.service;

import org.javaschool.data.old.dao.PositionDao;

public class PositionService {
    private PositionDao positionDao = new PositionDao();

    public void get(long id) {
        positionDao.get(id);
    }

    public void save(String position) {
        positionDao.save(position);
    }

    public void delete(long id) {
        positionDao.delete(id);
    }

}
