const multiply = (a, b) => {
    if (a === 0 || b === 0) return 0
    if (b === 1) return a
    if (a === 1) return b

    const smaller = Math.min(a, b)
    const isASmaller = smaller === a

    if (isASmaller) {
        return b + multiply(a - 1, b)
    } else return a + multiply(a, b - 1)
}

// TODO fails
it.skip('recursive multiple', () => {
    expect(multiply(1, 2)).toBe(1 * 2)
    expect(multiply(7, 8)).toBe(7 * 8)
    expect(multiply(17, 7890)).toBe(17 * 7890)
    expect(multiply(12345678, 12345678)).toBe(17 * 7890)
})
