package aux.ps.datastructures.sort;


public class MergeSortTest implements SortSpec {


    @Override
    public int[] sort(int[] array) {
        return new MergeSort().doSort(array);
    }

}
