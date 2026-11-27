package dsa.index;

import java.util.List;

import dsa.model.entry;

public interface dict_index {


    void build();


    List<entry> search(String prefix, int k);

    void add(entry item);

    void delete(String word);


    int size();

    void clear();
}
