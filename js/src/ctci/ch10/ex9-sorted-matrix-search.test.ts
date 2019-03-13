const search = (
    mtx: number[][],
    y: number, x: number,
    val: number): number[] => {

    const mSize = mtx.length
    const nSize = mtx[0].length

    const xyVal = mtx[y] [x]
    if (xyVal === val)
        return [y, x]

    if (xyVal > val)
        return null

    if (xyVal)

        if (y === mSize)
            return null
    if (x === nSize)
        return null

    const inColDir = search(mtx, y, x + 1, val)

    if (inColDir)
        return inColDir

    y = y + 1
    while (y < mSize) {
        const v = mtx[y][x]
        if (v === val)
            return [y, x]
        if (v > val)
            break
        y++
    }

    return null
}

it('test', () => {

    const mtx = [
        [1, 5, 8, 12],
        [6, 7, 10, 13],
        [7, 8, 11, 14]
    ]

    const r = search(mtx, 0, 0, 5)
    console.log(r)

    expect(search(mtx, 0, 0, 5)).toEqual([0, 1])
    expect(search(mtx, 0, 0, 7)).toEqual([1, 1])
    expect(search(mtx, 0, 0, 1)).toEqual([0, 0])
    expect(search(mtx, 0, 0, 6)).toEqual([1, 0])
    expect(search(mtx, 0, 0, 14)).toEqual([2, 3])

})

