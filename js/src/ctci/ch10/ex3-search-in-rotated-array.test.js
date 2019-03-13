const isInArray = (arr, val, start, end) => {
    const first = arr[start]
    const last = arr[end - 1]

    if (first <= last)
        // normally sorted
        return first <= val && val <= last
    // else contains inflection point
    else return val >= first || val <= last
}

const search = (arr, val, start = 0, end = arr.length) => {
    console.log(start, end)
    if (start === end)
        if (arr[start] === val) return start
        else return -1

    const mid = Math.floor((start + end) / 2)

    if (isInArray(arr, val, start, mid)) return search(arr, val, start, mid)

    if (!isInArray(arr, val, mid, end)) return -1

    return search(arr, val, mid, end)
}

describe('isInArray', () => {
    const test = (arr, val, yes = true) =>
        expect(isInArray(arr, 0, arr.length, val)).toBe(yes)

    it('case 1', () => {
        const arr = [1, 2, 3]
        test(arr, 2)
        test(arr, 1)
        test(arr, 3)
        test(arr, 4, false)
    })

    it('case 2', () => {
        const arr = [4, 5, 1]
        test(arr, 4)
        test(arr, 5)
        test(arr, 1)
        test(arr, 2, false)
    })
})

describe('test', () => {
    it('case 1', () => {
        const res = search([15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], 5)

        expect(res).toBe(8)
    })
})
