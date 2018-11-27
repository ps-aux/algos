const alphabetStart = 97
const alphabetEnd = 122

const anagaramId = w => {
  const counts = []

  for (let i = 0; i < w.length; i++) {
    const c = w.charCodeAt(i)
    const count = counts[c] || 0
    counts[c] = count + 1
  }

  const res = []
  for (let i = alphabetStart; i < +alphabetEnd; i++) {
    const count = counts[i]

    if (count)
      res.push(`${i}=${count}`)
  }

  return res.join(',')
}

it('anagramId', () => {

  expect(anagaramId('aab')).toBe(anagaramId('baa'))
  expect(anagaramId('c')).toBe(anagaramId('c'))
  expect(anagaramId('abc')).not.toBe(anagaramId('abe'))

})

const groupAnagrams = arr => {

  return arr
  .map(w => [w, anagaramId(w)])
  .sort((a, b) => a[1] < b[1])
  .map(p => p[0])

}

const test = (arr) => {

}

describe('test', () => {
  it('case 1', () => {
    const res = groupAnagrams(['aba', 'c', 'hu', 'baa', 'uh', 'aab'])
    // expect(A).toMatchObject('')

    console.log(res)

  })

})

