const swap = (array, a, b) => {
    const tmp = array[b]
    array[b] = array[a]
    array[a] = tmp
}


export const list = (data = []) => {
    data = data.slice()
    const steps = []

    data.swap = (a, b) => {
        swap(data, a, b)
        steps.push(data.slice())
    }

    data.steps = steps

    data.toArray = () => data.slice()

    return data
}


