package dsa.index;

import java.util.ArrayList;
import java.util.List;

import dsa.loader.dict_loader;
import dsa.model.entry;

public class sorted_array implements dict_index {
   private entry[] data = new entry[0];

   @Override
   public void build() {
      data = dict_loader.load().toArray(entry[]::new);
      merge_sort(0, data.length);
   }

   @Override
   public List<entry> search(String prefix, int k) {
      if (prefix == null || k <= 0 || data.length == 0) {
         return List.of();
      }

      int low = lower_bound(prefix);

      List<entry> result = new ArrayList<>();
      for (int i = low; i < data.length && data[i].word.startsWith(prefix); i++) {
         result.add(data[i]);
      }

      for (int i = 1; i < result.size(); i++) {
         entry current = result.get(i);
         int j = i - 1;

         while (j >= 0 && (result.get(j).zipf < current.zipf
               || (result.get(j).zipf == current.zipf
                     && result.get(j).word.compareTo(current.word) > 0))) {

            result.set(j + 1, result.get(j));
            j--;
         }
         result.set(j + 1, current);
      }

      if (result.size() > k) {
         return new ArrayList<>(result.subList(0, k));
      }
      return result;
   }

   public List<entry> search_lex(String prefix, int k) {
      List<entry> list = new ArrayList<>();
      if (prefix == null || k <= 0 || data.length == 0) {
         return list;
      }
      int low = lower_bound(prefix);
      while (low < data.length && list.size() < k && data[low].word.startsWith(prefix)) {
         list.add(data[low]);
         low++;
      }
      return list;
   }

   @Override
   public void add(entry item) {
      int low = lower_bound(item.word);
      if (low < data.length && data[low].word.equals(item.word)) {
         data[low] = item;
         return;
      }
      entry[] updated = new entry[data.length + 1];
      System.arraycopy(data, 0, updated, 0, low);
      updated[low] = item;
      System.arraycopy(data, low, updated, low + 1, data.length - low);
      data = updated;
   }

   private int lower_bound(String word) {
      int low = 0;
      int high = data.length;
      while (low < high) {
         int mid = (low + high) >>> 1;
         if (data[mid].word.compareTo(word) < 0) {
            low = mid + 1;
         } else {
            high = mid;
         }
      }
      return low;
   }

   @Override
   public int size() {
      return data.length;
   }

   @Override
   public void clear() {
      data = new entry[0];

   }

   private void merge_sort(int start, int end) {
      if (end - start < 2) {
         return;
      }
      int middle = start + (end - start) / 2;
      merge_sort(start, middle);
      merge_sort(middle, end);
      merge(start, middle, end);
   }

   private void merge(int start, int middle, int end) {
      entry[] merged = new entry[end - start];
      int left = start;
      int right = middle;
      int index = 0;

      while (left < middle && right < end) {
         if (data[left].word.compareTo(data[right].word) <= 0) {
            merged[index++] = data[left++];
         } else {
            merged[index++] = data[right++];
         }
      }
      while (left < middle) {
         merged[index++] = data[left++];
      }
      while (right < end) {
         merged[index++] = data[right++];
      }

      System.arraycopy(merged, 0, data, start, merged.length);
   }

   @Override
   public void delete(String word) {
      if (word == null || data.length == 0) {
         return;
      }
      int index = lower_bound(word);
      if (index == data.length || !data[index].word.equals(word)) {
         return;
      }

      entry[] updated = new entry[data.length - 1];
      System.arraycopy(data, 0, updated, 0, index);
      System.arraycopy(data, index + 1, updated, index, data.length - index - 1);
      data = updated;
   }

   public static void main(String[] args) {
      sorted_array index = new sorted_array();
      index.build();
      System.out.println(index.data.length);
      for (int i = 0; i < 5; i++) {
         System.out.println(index.search("car", 5).get(i).toString());
      }
      entry e = new entry("fxca", 0.8);
      index.add(e);
      System.out.println(index.data.length);

      index.delete("important");
      System.out.println(index.data.length);
   }

}
