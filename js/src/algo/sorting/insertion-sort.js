export const insertionSort = list => {

    for (let i = 0; i < list.length; i++) {
        // invariant: subarray <0, i) is sorted
        putInPlace(i)
    }

    function putInPlace(i) {
        while (i > 0 && list[i] < list[i - 1]) {
            list.swap(i, i - 1)
            i--
        }
    }

    return list
}