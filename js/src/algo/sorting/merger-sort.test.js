import ms from 'src/algo/sorting/merge-sort'

let arrays = [
    [3, 8, 6, 2, 1, 10, 3]]
// [1, -6, 4, 5, 6, 2, 4, 2, 2, 2, 2, 9, 1000],
// [2, 4, 5, 6, 7, 8, 9]]


const sorted = arr =>
    arr.slice().sort((a, b) => a - b)

it('mergeArrays works', () => {
    const input = [
        [
            [1, 2, 3], [1, 2, 3]],
        [
            [5, 7, 9, 12, 13], [1, 2, 3]],
        [
            [2], [4, 89, 1000]],
        [
            [], [1, 2, 3]],
        [
            [], []],
        [
            [90000], [0]],
        [
            [3, 3, 3], [3, 3, 3, 3, 3, 3]
        ]
    ]

    input.forEach(([arr1, arr2]) => {
        const arr = [...arr1, ...arr2]
        const aux = []
        const sortedArr = sorted(arr)

        ms._mergeArrays(arr, aux,
            0, arr1.length, arr1.length + arr2.length)

        expect(arr)
            .toEqual(sortedArr)
    })
})

