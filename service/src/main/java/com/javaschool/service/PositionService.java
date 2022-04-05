package com.javaschool.service;

import dao.PositionDao;

public class PositionService {
    private PositionDao positionDao = new PositionDao();

    public void get(long id) {
        positionDao.get(id);
    }

    public void save(String position) {
        positionDao.save(position);
    }

    public void delete(String position) {
        positionDao.delete(position);
    }

}
