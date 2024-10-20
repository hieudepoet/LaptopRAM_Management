package DAO_RAM;

import DAO.DAO;
import model.RAMItem;

public interface RAM_DAO extends DAO<RAMItem> {
    void search();
    void saveToFile();
}