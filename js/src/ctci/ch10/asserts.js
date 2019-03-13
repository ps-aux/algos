export const assertedSorted = A => {
    for (let i = 0; i < A.length - 1; i++)
        if (A[i] > A[i + 1])
            throw new Error(`Is not sorted: ${A[i]} > ${A[i + 1]}`)
}
