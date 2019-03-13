export const assertSorted = arr => {
    for (let i = 0; i < arr.length - 1; i++) {
        if (arr[i + 1] < arr[i])
            throw new Error(
                `Error is not sorted. i + 1 (${arr[i + i]}) < i (${arr[i]})`
            )
    }
}
