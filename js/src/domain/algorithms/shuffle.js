const rndInt = max => Math.floor(Math.random() * max)

export const shuffle = list => {
    for (let i = 0; i < list.length; i++) list.swap(i, rndInt(list.length - 1))

    return list
}
