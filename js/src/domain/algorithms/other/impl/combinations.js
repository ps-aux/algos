import { flatMap } from 'src/domain/algorithms/other/impl/utils'

export const combinations = list => {
    if (list.length === 0)
        return [[]]

    return flatMap(el => {
        const rest = list.filter(x => x !== el)

        return combinations(rest)
            .map(l => [el].concat(l))
    }, list)
}