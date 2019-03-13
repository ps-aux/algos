export const mergeLists = (a, b) => {
    const pickSmaller = () => {
        if (!a.length && b.length) throw new Error('Both lists are empty')

        if (!a.length) return b.shift()

        if (!b.length) return a.shift()

        const aEl = a[0]
        const bEl = b[0]

        if (aEl < bEl) return a.shift()
        else return b.shift()
    }

    const finalSize = a.length + b.length
    const res = []

    while (res.length < finalSize) res.push(pickSmaller())

    return res
}
