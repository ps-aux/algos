import {swap} from 'src/old/sorting'

export const selectionSort = array => {

    for (let i = 0; i < array.length; i++) {
        // invariant: subarray <0, i) is sorted and contains min elements
        const minIndex = findMin(i, array.length - 1)
        if (minIndex !== i)
            swap(array, i, minIndex)
    }

    function findMin(l, h) {
        let min = array[l]
        let minIndex = l
        for (let i = l + 1; i <= h; i++) {
            if (array[i] < min) {
                min = array[i]
                minIndex = i
            }
        }
        return minIndex
    }
}