import { powerSet } from 'src/algo/other/power-set'
import { combinations } from 'src/algo/other/combinations'
import { flatMap } from 'src/algo/other/utils'

export const allWords = string => {
    const s = powerSet(string)

    console.log(s)
    return flatMap(x => combinations(x), s).map(l => l.join(''))
}