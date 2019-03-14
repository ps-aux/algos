const x = () =>
    123

const Processor = () => {
    const arr: number[] = []

    return {
        track: (n: number) => {
            arr.push(n)
            arr.sort((a, b) => a - b)
        },
        getRankOfNumber: (n: number) => {
            for (let i = 0; i < arr.length; i++) {
                if (arr[i] > n) return i - 1
            }
            return -1
        }
    }
}

it('test', () => {
    const p = Processor()
    ;[5, 1, 4, 4, 5, 9, 7, 13, 3].forEach(p.track)

    expect(p.getRankOfNumber(1)).toBe(0)
    expect(p.getRankOfNumber(3)).toBe(1)
    expect(p.getRankOfNumber(4)).toBe(3)
})

type TreeNode = {
    val: number,
    count: number,
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
        if (!left)
            node.children[0] = createNode(val)
        else
            add(left, val)
    } else if (val > node.val) {
        const right = node.children[1]
        if (!right)
            node.children[1] = createNode(val)
        else
            add(right, val)
    }
}

it('bst', () => {
    const t = createNode(2)
    add(t, 4)
    console.log(t)

})
