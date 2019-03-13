const isPeekAndValley = arr => {
  let prev = null
  let prevType = null

  for (const el of arr) {
    if (prev === el)
      continue

    let currentType

    // Can be determined if previous value exists
    if (prev != null)
      if (prev > el)
        currentType = 'valley'
      else
        currentType = 'peak'

    if (prevType)
      if (prevType == currentType)
      // No alternating
        return false

    prevType = currentType
    prev = el
  }

  return true

}

test('isPeekAndValley()', () => {
  expect(isPeekAndValley([5, 1, 3, 2, 3])).toBeTruthy()
  expect(isPeekAndValley([1, 2])).toBeTruthy()
  expect(isPeekAndValley([1, 2, 3])).toBeFalsy()
  expect(isPeekAndValley([3, 2, 1])).toBeFalsy()
  expect(isPeekAndValley([2, 4, 3, 1])).toBeFalsy()
})

const peaksAndValleys = arr => {
  // Copy and sort
  arr = arr.slice().sort()
  let lo = 0
  let hi = arr.length - 1

  const res = []

  while (lo < hi) {
    res.push(arr[lo])
    lo++
    res.push(arr[hi])
    hi--
  }
  // hi === lo
  res.push(arr[lo])

  return res
}

it('test', () => {
  const input = [5, 3, 1, 2, 3]

  let output = peaksAndValleys(input)
  expect(isPeekAndValley(output)).toBeTruthy()

  output = peaksAndValleys([1, 1, 2])
  expect(isPeekAndValley(output)).toBeTruthy()

  output = peaksAndValleys([1, 3, 4, 5, 1, 1])
  expect(isPeekAndValley(output)).toBeTruthy()
})
