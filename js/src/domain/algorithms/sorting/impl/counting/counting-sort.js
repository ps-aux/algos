export const countingSort = input => {
    /**
     * Example input [4,1,1,2]
     */

    /**
     * Count each
     * [0, 1, 2, 3, 4]
     * [u, 2, 1, u, 1]
     * u = undefined
     *
     */
    const counts = []
    input.forEach(i => {
        const count = counts[i] || 0
        counts[i] = count + 1
    })

    /**
     * Add counts
     * [0, 1, 2, 3, 4]
     * [0, 2, 3, 3, 4]
     *
     */
    let total = 0
    counts.forEach((c, i) => {
        counts[i] = total + (c || 0)
        total = counts[i]
    })

    // assert total = input.length

    const out = []

    /**
     * 'Flatten'
     * [1, 1, 2, 4]
     */
    input.forEach(v => {
        const idx = counts[v] - 1
        out[idx] = v
        counts[v] = counts[v] - 1
    })

    return out
}

export const alternativeCountingSort = input => {
    /**
     * Example input [4,1,1,2]
     */

    /**
     * Count each
     * [0, 1, 2, 3, 4]
     * [u, 2, 1, u, 1]
     * u = undefined
     *
     */
    const counts = []
    input.forEach(i => {
        const count = counts[i] || 0
        counts[i] = count + 1
    })

    /**
     * 'Flatten'
     * [1, 1, 2, 4]
     */
    const res = []
    counts.forEach((c, i) => {
        if (!c) return

        // Push for each
        for (let j = 0; j < c; j++) {
            res.push(i)
        }
    })

    return res
}
