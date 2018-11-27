export const countingSort = input => {
  const counts = []

  input.forEach(i => {
    const count = counts[i] || 0
    counts[i] = count + 1
  })

  let total = 0
  counts.forEach((c, i) => {
    counts[i] = total + (c || 0)
    total = counts[i]
  })

  // counts.forEach((v, i) => console.log(v, i))

  const out = []

  console.log(input)
  console.log(counts)
  input.forEach(v => {
    const idx = counts[v] - 1
    console.log('adding ',  idx, v)
    out[idx] = v
    counts[v] = counts[v] - 1
  })

  return out
}



