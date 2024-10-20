package DAO;

import java.util.List;

public interface DAO<T> {
    //Create
    void add();

    //Read
    List<T> getAll(); 

    //Update
    void update();

    //Delete
    void delete();
}
