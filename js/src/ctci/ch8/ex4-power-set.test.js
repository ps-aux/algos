const powerSet = set => {
  if (set.size === 0)
    return new Set()
  // TODO can this second if be removed ?
  if (set.size === 1)
    return new Set([new Set(), set])

  const [head, ...tail] = set

  const sub = powerSet(new Set(tail))

  const res = new Set(sub)

  sub.forEach(s =>
    res.add(new Set([head, ...s])))

  return res
}

const testIt = (input, expected) => {
  input = new Set(input)
  expected = new Set(expected.map(arr => new Set(arr)))

  const res = powerSet(input)

  expect(res).toEqual(expected)
}

it('power set', () => {

  testIt([], [])
  testIt([1], [[1], []])
  testIt([1, 2], [[1], [2], [1, 2], []])

  testIt([1, 2, 3], [
    [], [1, 2, 3],
    [1], [2], [3],
    [1, 2], [1, 3], [3, 2]
  ])

})
