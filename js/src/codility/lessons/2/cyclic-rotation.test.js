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

/**
 * TODO finish
 */
function solutionInPlace (A, K) {

  const move = (current, by) => {
    const max = A.length - 1
    let next = current + by
    if (next > max)
      next = next - max
    else if (next < 0)
      next = max - next

    return next
  }

  const firstTo = A.length - 1
  const firstToVal = A[firstTo]
  let to = firstTo
  let from = move(to, -4)

  while (true) {
    console.log(from, '->', to)
    if (firstTo === from)
      return
    to = move(to, -1)
    from = move(from, -1)
  }
}

it('tests', () => {
  expect(solution([3, 8, 9, 7, 6], 3)).toEqual([9, 7, 6, 3, 8])
  expect(solution([0, 0, 0], 1)).toEqual([0, 0, 0])
  expect(solution([1, 2, 3, 4], 4)).toEqual([1, 2, 3, 4])

})
