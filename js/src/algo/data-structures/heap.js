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
        data[k] = pV
        data[p] = v
        swimUp(p)
    }

    const isOutOfBounds = k => k > data.length - 1

    const node = k =>
        isOutOfBounds(k) ? null : {
            value: data[k],
            left: node(k * 2),
            right: node(k * 2 + 1)
        }

    return {
        insert: el => {
            data.push(el)
            swimUp(data.length - 1)
        },

        min: () => {

        },

        asTree: () => node(1)
    }
}