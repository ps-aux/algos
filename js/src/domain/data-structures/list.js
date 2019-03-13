const swap = (array, a, b) => {
    const tmp = array[b]
    array[b] = array[a]
    array[a] = tmp
}

export const List = (data = []) => {
    data = data.slice()
    const steps = []

    let swapping = false
    data.swap = (a, b) => {
        swapping = true
        swap(data, a, b)
        steps.push(data.slice())
        swapping = false
    }

    data.steps = steps
    data.toArray = () => data.slice()

    data.clone = () => List(data.toArray())

    return new Proxy(data, {
        get: (o, a) => {
            // Swap is standalone primitive operation for us
            const r = o[a]
            if (!swapping) {
                steps.push(data.slice())
            }
            return r
        }
    })
}
