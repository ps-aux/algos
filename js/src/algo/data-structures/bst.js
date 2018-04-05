/**
 * Immutable binary search tree
 */

const size = n => !isNode(n) ?
    0 : n.children.reduce((a, c) => a + size(c), 0) + 1

const isNode = n => n && n.value != null

const visit = (n, onVisit) => {
    if (!isNode(n))
        return
    onVisit(n)
    n.children.forEach(c => visit(c, onVisit))
}

const find = (n, val) => {
    if (!isNode(n))
        return
    if (n.value === val)
        return n

    for (let c of n.children) {
        const found = find(c, val)
        if (found)
            return found
    }
}

const reduce = (n, reducer, acc) =>
    (visit(n, n => acc = reducer(acc, n.value)), acc)


const bstAdd = (n, value, steps = []) => {
    if (value === n.value)
        return n
    if (!isNode(n))
        return {
            value,
            _size: 1,
            children: [{}, {}]
        }

    steps.push(n)
    const cp = {
        value: n.value,
        _size: n._size + 1,
        children: n.children.slice()
    }

    const [left, right] = cp.children
    if (value < n.value) {
        cp.children[0] = bstAdd(left, value, steps)
    } else {
        cp.children[1] = bstAdd(right, value, steps)
    }

    return cp
}


const bindMethods = obj => {

    const bind = fun => (...args) => {
        const res = fun(obj, ...args)
        if (isNode(res))
            return bindMethods(res)
        return res
    }

    obj.add = bind(bstAdd)
    obj.find = bind(find)
    obj.size = bind(size)
    obj.visit = bind(visit)
    obj.reduce = bind(reduce)

    obj.toString = function () {
        return `Bsg size:${this.size}`
    }

    return obj
}

const bst = () => bindMethods({})

export default bst
