export const selectionSort = list => {
    for (let i = 0; i < list.length; i++) {
        // invariant: subarray <0, i) is sorted and contains min elements
        const minIndex = findMin(i, list.length - 1)
        if (minIndex !== i) list.swap(i, minIndex)
    }

    function findMin(l, h) {
        let min = list[l]
        let minIndex = l
        for (let i = l + 1; i <= h; i++) {
            if (list[i] < min) {
                min = list[i]
                minIndex = i
            }
        }
        return minIndex
    }

    return list
}
