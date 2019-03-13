const arr = [1, 4, 3, 4, 2]

const scores = [100, 100, 50, 40, 40, 20, 10]
const alice = [5, 25, 50, 120]

const calcCounts = arr =>
    Object.entries(
        arr.reduce((a, x) => ((a[x] = (a[x] || 0) + 1), a), {})
    ).sort(([k1, _1], [k2, _2]) => k2 - k1)

const score = (n, counts) => counts.filter(s => s[0] > n).length + 1

const go = (al, scores) => {
    const counts = calcCounts(scores)

    al.map(a => score(a, counts)).forEach(s => console.log(s))
}

it('test', () => {
    console.log(go(alice, scores))
})
