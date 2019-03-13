const alphabetStart = 97
const alphabetEnd = 122

const anagaramId = w => {
    const counts = []

    for (let i = 0; i < w.length; i++) {
        const c = w.charCodeAt(i)
        const count = counts[c] || 0
        counts[c] = count + 1
    }

    const res = []
    for (let i = alphabetStart; i < +alphabetEnd; i++) {
        const count = counts[i]

        for (let j = 0; j < count; j++) res.push(String.fromCharCode(i))
    }

    return res.join(',')
}

it('anagramId', () => {
    expect(anagaramId('aab')).toBe(anagaramId('baa'))
    expect(anagaramId('c')).toBe(anagaramId('c'))
    expect(anagaramId('abc')).not.toBe(anagaramId('abe'))
})

const groupAnagrams = arr => {
    const map = {}

    // Put to buckets
    arr.forEach(w => {
        const id = anagaramId(w)
        const bucket = map[id] || (map[id] = [])
        bucket.push(id)
    })

    const res = []
    // flatten
    Object.values(map).forEach(b => b.forEach(w => res.push(w)))

    return res
}

const test = arr => {}

describe('test', () => {
    it('case 1', () => {
        const res = groupAnagrams(['aba', 'c', 'hu', 'baa', 'uh', 'aab'])
        console.log(res)
    })
})
