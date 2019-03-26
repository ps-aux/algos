const go = (a, b) => {}

const t = (a, b) =>
    it(`${a} -> ${b}`, () => {
        expect(go(a)).toBe(b)
    })

const allSubstrings = str => {
    return ['a', '']
}

it.skip('allSubstrings', () => {
    expect(allSubstrings('abc')).toEqual(expect.arrayContaining(['', 'a']))
})

describe.skip('tests', () => {
    t('a', 'a')
    t('ab', '')
    t('aba', 'aba')
    t('abac', 'aba')
    t('abbc', 'bb')
    t('xabcbay', 'abcba')
})
