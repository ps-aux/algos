const x = () => 123

type TreeNode = {
  val: number
  count: number
  children: TreeNode[]
}

const createNode = (val: number): TreeNode => ({
  val,
  count: 1,
  children: []
})

const add = (node: TreeNode, val: number) => {
  if (node.val === val) {
    node.count++
  } else if (val < node.val) {
    const left = node.children[0]
    if (!left) node.children[0] = createNode(val)
    else add(left, val)
  } else if (val > node.val) {
    const right = node.children[1]
    if (!right) node.children[1] = createNode(val)
    else add(right, val)
  }
}

const nodesUpTo = (
  node: TreeNode, val: number, acc: TreeNode[] = []): TreeNode[] | null => {

  const left = node.children[0]
  const right = node.children[1]

  if (left) {
    const res = nodesUpTo(left, val, acc)
    if (res)
      return res
  }

  acc.push(node)
  // We hit the end
  if (node.val === val)
    return acc

  if (right)
    return nodesUpTo(right, val, acc)

  return null
}

const Ranker = (rootVal: number) => {
  const t = createNode(rootVal)
  return {
    add: (v: number) => add(t, v),
    rankOf: (v: number) => {
      const res = nodesUpTo(t, v)
      if (!res)
        throw new Error(`Value ${v} not found`)

      // Exclude the 'index' of the matching value
      return res.reduce((acc, v) => acc + v.count, 0) - 1
    }
  }
}

it('Ranker', () => {

  const tree = Ranker(2)

  expect(tree.rankOf(2)).toBe(0)

  tree.add(1)
  expect(tree.rankOf(2)).toBe(1)

  tree.add(1)
  expect(tree.rankOf(2)).toBe(2)

  tree.add(10)
  expect(tree.rankOf(2)).toBe(2)

  tree.add(3)
  expect(tree.rankOf(10)).toBe(4)

  expect(tree.rankOf(1)).toBe(0)
})

const Processor = () => {
  let ranker: any

  return {
    track: (n: number) => {
      if (!ranker)
        ranker = Ranker(n)
      else
        ranker.add(n)
    },
    getRankOfNumber: (n: number) => ranker.rankOf(n)
  }
}

it('test', () => {
  const p = Processor()
  ;[5, 1, 4, 4, 5, 9, 7, 13, 3].forEach(p.track)

  expect(p.getRankOfNumber(1)).toBe(0)
  expect(p.getRankOfNumber(3)).toBe(1)
  expect(p.getRankOfNumber(4)).toBe(3)
  expect(p.getRankOfNumber(7)).toBe(6)
})

