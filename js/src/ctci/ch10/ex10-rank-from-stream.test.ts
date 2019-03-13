const Processor = () => {
    const arr = []

    return {
        track: (n: number) => {
            arr.push(n)
            arr.sort((a, b) => a - b)
        },
        getRankOfNumber: (n: number) => {
            for (let i = 0; i < arr.length; i++) {
                if (arr[i] > n) return i
            }
            return -1
        }
    }
}

describe('test', () => {
    const p = Processor()
    ;[5, 1, 4, 4, 5, 9, 7, 13, 3].forEach(p.track)

    expect(p.getRankOfNumber(1)).toBe(0)
    expect(p.getRankOfNumber(3)).toBe(1)
    expect(p.getRankOfNumber(4)).toBe(3)
})
