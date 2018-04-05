import create from './bst'
import {random} from 'faker'
import {add, range, take} from 'ramda'

const randomArray = (size = 10) =>
    range(0, size)
        .map(() => random.number(100))

const createFilled = (size = 20) =>
    randomArray(size)
        .reduce((a, v) => a.add(v), create())

describe('bst', () => {
    it('size()', () => {
        let bst = create()
            .add(3)
            .add(4)

        expect(bst.size()).toBe(2)

    })

    it('add()', () => {
        let bst = create()

        expect(bst.find(3)).toBe(undefined)
        bst = bst.add(3)

        expect(bst.find(3).value).toBe(3)

    })

    it('find() existing value', () => {
        const bst = createFilled()
            .add(4)

        expect(bst.find(4).value).toBe(4)

    })

    it('find() missing value', () => {
        const bst = createFilled()

        expect(bst.find(-1897489758759)).toBe(undefined)

    })

    it('visit()', () => {
        const visit = jest.fn()
        const bst = create()
            .add(2)
            .add(1)
            .add(3)

        bst.visit(visit)

        const {calls} = visit.mock
        expect(calls.length).toBe(3)

        expect(calls[0][0]).toMatchObject({value: 2})
        expect(calls[1][0]).toMatchObject({value: 1})
        expect(calls[2][0]).toMatchObject({value: 3})


    })

    it ('reduce()', () => {
        const bst = create()
            .add(4)
            .add(2)
            .add(8)

        expect(bst.reduce(add, 100)).toBe(114)

    })
})