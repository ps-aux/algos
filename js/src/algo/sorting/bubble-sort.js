export const bubbleSort = list => {
    // Little bit effective version of bubble onSort which
    // counts on the end of array to be sorted
    for (let i = list.length - 1; i > 0; i--) {
        // invariant: subarray <i, array.length) is sorted
        let j = 0
        while (j < i) {
            if (list[j] > list[j + 1])
                list.swap(j, j + 1)
            j++
        }
    }

    return list
}