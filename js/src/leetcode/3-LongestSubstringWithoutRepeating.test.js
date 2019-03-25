const go = (str) => {

    let current = 0
    let longest = 0
    let map = new Map()

    for (let i = 0; i < str.length; i++) {
        const c = str[i]

        if (map.has(c)) {

            // Finalize old
            if (current > longest)
                longest = current

            const previousDuplicatePosition = map.get(c)

            // Reset
            map = new Map()
            // The initial state take into consideration distance from the duplicate
            // as it contains unique chars
            current = i - previousDuplicatePosition - 1

            for (let j = previousDuplicatePosition + 1; j < i; j++) {
                // Add previous to the map
                map.set(str[j], j)
            }
        }
        current++
        map.set(c, i)
    }
    if (current > longest)
        longest = current

    return longest
}

const t = (a, b) =>
    it(`${a} -> ${b}`, () => {
        expect(go(a)).toBe(b)
    })

describe('tests', () => {
    t('abcabcbb', 3)
    t('bbbbb', 1)
    t('pwwkew', 3)
    t('aab', 2)
    t('dvdf', 3)
})
