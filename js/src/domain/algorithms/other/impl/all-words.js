import { powerSet } from 'src/domain/algorithms/other/impl/power-set'
import { combinations } from 'src/domain/algorithms/other/impl/combinations'
import { flatMap } from 'src/domain/algorithms/other/impl/utils'

export const allWords = string => {
    const s = powerSet(string)

    console.log(s)
    return flatMap(x => combinations(x), s).map(l => l.join(''))
}