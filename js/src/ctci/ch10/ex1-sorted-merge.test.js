const naive = (A, B) => {
    const tmp = []
    let ai = 0
    let bi = 0
    let i = 0
    while (i < A.length + B.length) {
        const a = A[ai]
        const b = B[bi]

        if (b === undefined || a < b) {
            tmp[i] = a
            ai++
        } else {
            tmp[i] = b
            bi++
        }
        i++
    }

    return tmp
}

it('naive', () => {
    const r = merge([1, 6, 10], [2, 3, 8])
    expect(r).toMatchObject([1, 2, 3, 6, 8, 10])
})

const fromEnd = (A, B) => {
    let i = A.length - 1
    let ai = A.length - 1 - B.length
    let bi = B.length - 1

    while (i > -1) {
        const a = A[ai]
        const b = B[bi]
        // Undefined is always lt any integer
        if (ai > -1 && (bi < 0 || a > b)) {
            A[i] = a
            ai--
        } else {
            A[i] = b
            bi--
        }
        i--
    }
}

describe('fromEnd', () => {
    it('case 1', () => {
        const A = [1, 6, 10, null, null, null]
        fromEnd(A, [2, 3, 8])
        expect(A).toMatchObject([1, 2, 3, 6, 8, 10])
    })

    it('case 2', () => {
        const A = [1, 6, , null, null]
        fromEnd(A, [2, 3, 4])
        expect(A).toMatchObject([1, 2, 3, 4, 6])
    })

    it('edge case 2', () => {
        const A = [10, null, null, null]
        fromEnd(A, [2, 3, 8])
        expect(A).toMatchObject([2, 3, 8, 10])
    })
})
