const go = (a, b) => {
    let aIdx = 0
    let bIdx = 0

    let aDone = false
    let bDone = false

    const merged = []
    while (!aDone || !bDone) {
        if (aDone) {
            merged.push(b[bIdx])
            bIdx++
        } else if (bDone) {
            merged.push(a[aIdx])
            aIdx++
        } else {
            const aVal = a[aIdx]
            const bVal = b[bIdx]

            if (aVal < bVal) {
                merged.push(aVal)
                aIdx++
            } else {
                merged.push(bVal)
                bIdx++
            }
        }

        aDone = aIdx >= a.length
        bDone = bIdx >= b.length
    }

    if (merged.length % 2 === 1) {
        return merged[Math.floor(merged.length / 2)]
    }

    const m1 = Math.floor(merged.length / 2)
    const m2 = m1 + 1
    return (m1 + m2) / 2
}

const t = (arr1, arr2, b) =>
    it(`${arr1}, ${arr2} -> ${b}`, () => {
        expect(go(arr1, arr2)).toBe(b)
    })

describe('tests', () => {

    // TODO the solution should be in log time not in linear time
    t([1, 2], [3], 2)
    t([1, 2], [3, 4], 2.5)
})
