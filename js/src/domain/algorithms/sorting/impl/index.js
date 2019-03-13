import { insertionSort } from 'src/domain/algorithms/sorting/impl/insertion-sort'
import { bubbleSort } from 'src/domain/algorithms/sorting/impl/bubble-sort'
import { mergeSort } from 'src/domain/algorithms/sorting/impl/merge-sort'
import { quickSort } from 'src/domain/algorithms/sorting/impl/quick-sort'
import { selectionSort } from 'src/domain/algorithms/sorting/impl/selection-sort'

export const sorts = {
    bubble: bubbleSort,
    insertion: insertionSort,
    merge: mergeSort,
    quick: quickSort,
    selection: selectionSort
}
