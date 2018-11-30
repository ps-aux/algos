import microtime from 'microtime'
import { range } from 'ramda'
import { randomArray } from 'src/domain/algorithms/array'

const createTest = (size, {min, max}) => {
    const data = randomArray(size, {max, min})

    return {
        name: `size: ${size}, max=${max}, min=${min}`,
        data
    }

}

const sizes = [10, 100, 1000, 10000, 100000, 1000000, 10000000]

const measure = fun => {
    const start = microtime.now()
    fun()
    return microtime.now() - start
}

export const benchmarkSort = (
    name, alg, {max, min, repeatCount = 5} = {}) => {

    console.log('benchmarking', name)
    const tests = sizes.map(size => createTest(size, {max, min}))

    tests.forEach(t => {
        console.log('testing', t.name)
        const times = range(0, repeatCount).map((() => {
            const data = t.data.slice()
            return measure(() => alg(data))
        })).map(u => Math.ceil((u / 1000)))

        console.log('times', times)
    })

}

