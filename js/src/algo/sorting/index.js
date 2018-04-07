import {insertionSort} from 'src/algo/sorting/insertion-sort'
import {bubbleSort} from 'src/algo/sorting/bubble-sort'
import {mergeSort} from 'src/algo/sorting/merge-sort'
import {quickSort} from 'src/algo/sorting/quick-sort'
import {selectionSort} from 'src/algo/sorting/selection-sort'

export const sorts = {
    bubble: bubbleSort,
    insertion: insertionSort,
    merge: mergeSort,
    quick: quickSort,
    selection: selectionSort
}