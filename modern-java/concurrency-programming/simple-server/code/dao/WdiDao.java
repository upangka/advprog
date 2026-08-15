///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package dao;

import java.util.List;

import loader.WdiLoader;
import model.WdiRecord;

/**
 * WdiDao
 */
public class WdiDao {

    private List<WdiRecord> data;
    public WdiDao(){
         this.data =  WdiLoader.load();
    }
}