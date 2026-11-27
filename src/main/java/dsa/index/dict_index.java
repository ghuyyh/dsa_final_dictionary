package dsa.index;

import java.util.List;

import dsa.model.entry;

public interface dict_index {


    void build(List<entry> entries);


    List<entry> searchPrefix(String prefix, int k);

    void add(entry item);


    int size();

    void clear();
}
