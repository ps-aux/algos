const go = (str) => {

    let longest = 0
    let start = 0
    let map = []

    for (let i = 0; i < str.length; i++) {
        const code = str.charCodeAt(i)
        const previousDuplicatePosition = map[code]

        // Ignore duplicates before start
        if (previousDuplicatePosition >= start) {
            start = previousDuplicatePosition + 1
        }
        map[code] = i

        if (i - start + 1 > longest)
            longest = i - start + 1
    }

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
