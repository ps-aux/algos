import {swap} from 'src/old/sorting'

export const insertionSort = array => {

    for (let i = 0; i < array.length; i++) {
        // invariant: subarray <0, i) is sorted
        putInPlace(i)
    }

    function putInPlace(i) {
        while (i > 0 && array[i] < array[i - 1]) {
            swap(array, i, i - 1)
            i--
        }
    }
}