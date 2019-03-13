import { range } from 'ramda'
import { random } from 'faker'

export const randomArray = (size = 10, { max = size, min = 0 } = {}) =>
    range(0, size).map(() => random.number({ min, max }))
