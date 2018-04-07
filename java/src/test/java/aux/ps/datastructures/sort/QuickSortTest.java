package aux.ps.datastructures.sort;


public class QuickSortTest implements SortSpec {


    @Override
    public int[] sort(int[] array) {
        return new QuickSort().doSort(array);
    }

}
