const assertMagic = (arr, i) => expect(arr[i]).toBe(i)

const findMagic = (arr, start = 0, end = arr.length) => {
    if (start === end - 1) {
        return arr[start] === start ? start : -1
    }

    const mid = Math.ceil((end - start) / 2)

    const val = arr[mid]
    if (val === mid) return mid

    return val > mid
        ? findMagic(arr, start, start + mid)
        : findMagic(arr, start + mid, end)
}

const testIt = (input, expected) => {
    const res = findMagic(input)
    expect(res).toBe(expected)
}

it('is magic', () => {
    testIt([-1, 0, 2, 4, 7, 9, 15, 10, 27, 30], 2)
    testIt([0], 0)
    testIt([8, 7, 3], -1)
    testIt([1, 2, 3], -1)
    testIt([-200, -100, -10, 0, 1, 2, 3, 6], -1)
})
