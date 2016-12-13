import assert from 'assert'

/**
 *
 * @param array native array
 * @param a first index
 * @param b second index
 */
function swap(array, a, b) {
    console.debug('swapping', a, b)
    assert(a !== b)
    const tmp = array[b]
    array[b] = array[a]
    array[a] = tmp
}

/**
 * Knuth shuffle implementation of shuffle
 */
function shuffle(arrayRecorder) {
    const array = arrayRecorder


}


/**
 * Performs selection sort algoritm with the given array recorder
 * @param array array recorder
 */
function selectionSort(arrayRecorder) {
    const array = arrayRecorder

    for (let i = 0; i < array.size(); i++) {
        array.select(i) // Visualization call

        var minIndex = i
        var minVal = array.get(i)
        // Find min
        for (let j = i + 1; j < array.size(); j++) {
            array.examine(j) //Visualization call
            const val = array.get(j)
            if (val < minVal) {
                minIndex = j
                minVal = val
            }
        }

        if (minIndex !== i)
            array.swap(i, minIndex)
    }

}

export {swap, selectionSort}