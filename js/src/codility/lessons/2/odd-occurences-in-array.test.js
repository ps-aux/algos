function solution (A) {
  const counts = {}

  const addNum = num => {
    if (counts[num] == null)
      counts[num] = 1
    else
      counts[num] = counts[num] + 1
  }

  A.forEach(addNum)

  for (let e of Object.entries(counts))
    if (e[1] % 2 === 1)
      return parseInt(e[0])

}

it('test2', () => {
  expect(solution([9, 3, 9, 3, 9, 7, 9])).toEqual(7)
})