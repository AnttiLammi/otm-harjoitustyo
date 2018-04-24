/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.daot;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author antlammi
 * @param <T>
 * @param <K>
 */
public interface Dao<T, K> {
    T findOne(K key) throws SQLException, ClassNotFoundException;
    List<T> findAll() throws SQLException, ClassNotFoundException;
    T saveOrUpdate(T object) throws SQLException, ClassNotFoundException;
    void delete(K key) throws SQLException, ClassNotFoundException;
}
