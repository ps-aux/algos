const moveByOne = arr => {
  if (!arr.length)
    return arr

  const lastIdx = arr.length - 1
  const last = arr[lastIdx]

  for (let i = lastIdx - 1; i >= 0; i--) {
    arr[i + 1] = arr[i]
  }
  arr[0] = last
}

function solution (A, K) {
  for (let i = 0; i < K; i++) {
    moveByOne(A)
  }
  return A
}

it('tests', () => {
  expect(solution([3, 8, 9, 7, 6], 3)).toEqual([9, 7, 6, 3, 8])
  expect(solution([0, 0, 0], 1)).toEqual([0, 0, 0])
  expect(solution([1, 2, 3, 4], 4)).toEqual([1, 2, 3, 4])

})
