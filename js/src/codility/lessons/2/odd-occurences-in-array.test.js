function solution(A) {
    let res = 0
    A.forEach(n => {
        res = res ^ n
    })

    return res
}

it('test', () => {
    expect(solution([9, 3, 9, 3, 9, 7, 9])).toEqual(7)
})
