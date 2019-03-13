export const powerSet = (list, acc = []) => {
    if (list.length < 1) {
        acc.push([])
    } else {
        const [h, ...tail] = list
        powerSet(tail, acc)
        const cp = acc.slice()

        cp.forEach(x => {
            acc.push(x.concat(h))
        })
    }

    return acc
}
