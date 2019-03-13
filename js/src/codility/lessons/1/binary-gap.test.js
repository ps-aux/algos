const toBinary = n => {
    if (n === 0 || n === 1) return n.toString()
    const q = Math.floor(n / 2)
    const rem = (n - q * 2).toString()
    return toBinary(q) + rem
}

function solution(N) {
    const bin = toBinary(N)

    let inGap = false
    let gapLen = 0
    let max = 0
    let previous
    for (let n of bin.split('')) {
        if (n === '1') {
            if (inGap) {
                // Close gap
                max = Math.max(gapLen, max)
                gapLen = 0
            }
            inGap = false
        } else {
            if (inGap) gapLen++
            else if (previous === '1') {
                inGap = true
                gapLen = 1
            }
        }
        previous = n
    }

    return max
}

it('binary conversion', () => {
    expect(toBinary(0)).toBe('0')
    expect(toBinary(1)).toBe('1')
    expect(toBinary(2)).toBe('10')
    expect(toBinary(1234)).toBe('10011010010')
})

it('test', () => {
    expect(solution(1041)).toBe(5)
    expect(solution(32)).toBe(0)
})
