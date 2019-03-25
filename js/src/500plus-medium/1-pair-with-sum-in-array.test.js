const go = (arr, sum) => {
    for (let i = 0; i < arr.length; i++) {
        for (let j = i; j < arr.length; j++) {
            const a = arr[i]
            const b = arr[j]
            if (a + b === sum) return [a, b]
        }
    }
}

const t = (arr, sum, hasResult = true) =>
    it(`${arr} and ${sum}`, () => {
        const r = go(arr, sum)
        if (!hasResult) {
            expect(r).toBeUndefined()
        } else expect(r[0] + r[1]).toBe(sum)
    })

describe('tests', () => {
    t([1, 2, 3, 4], 5)
    t([1, 1], 2)
    t([1, 1, 1, 1, 1, 1, 1, 10], 11)
    t([1, 3, 4], 10, false)
})
