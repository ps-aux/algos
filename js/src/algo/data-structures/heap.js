export const heap = () => {
    const data = ['-']

    const parent = k => Math.floor(k / 2)

    const isRoot = k => k === 1

    const left = k => k * 2

    const right = k => k * 2 + 1

    const swimUp = k => {
        if (isRoot(k))
            return
        const p = parent(k)
        const v = data[k]
        const pV = data[p]
        if (pV >= v)
            return
        // Swap parent and child
        data[k] = data[p]
        data[p] = v
        swimUp(p)
    }

    const node = k => {
        const value = data[k]
        return value ? {
            value,
            left: node(k * 2),
            right: node(k * 2 + 1)
        } : null
    }

    return {
        insert: el => {
            data.push(el)
            swimUp(data.length - 1)
        },

        asTree: () => node(1)
    }
}