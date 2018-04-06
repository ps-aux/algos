import {swap} from 'src/old/sorting'

export const bubbleSort = array => {
    // Little bit effective version of bubble sort which
    // counts on the end of array to be sorted
    for (let i = array.length - 1; i > 0; i--) {
        // invariant: subarray <i, array.length) is sorted
        let j = 0
        while (j < i) {
            if (array[j] > array[j + 1])
                swap(array, j, j + 1)
            j++
        }
    }
}