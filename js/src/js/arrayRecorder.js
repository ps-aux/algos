import {swap} from './sorting'

export default class ArrayRecorder {

    /**
     * @param array native array
     */
    constructor(array) {
        // The real array used in the recording pass
        this._result = array
        // make copy of array
        this.initial = array.slice(0)
        this.actions = []
    }

    /** Standard array manipulation API */
    put(index, val) {
        this._validateIndex(index)
        this._result[index] = val
        this.actions.push({
            type: 'put',
            index: index,
            val: val
        })
    }

    get(index) {
        this._validateIndex(index)
        return this._result[index]
    }

    _validateIndex(index) {
        if (index > this.size() - 1)
            throw 'Index out of bound ' + index
    }

    size() {
        return this._result.length
    }

    swap(a, b) {
        this._validateIndex(a)
        this._validateIndex(a)

        swap(this._result, a, b)
        this.actions.push({
            type: 'swap',
            indexA: a,
            indexB: b
        })
    }

    /** Recording API */

    select(index) {
        this.actions.push({
            type: 'select',
            index: index
        })
    }

    examine(index) {
        this.actions.push({
            type: 'examine',
            index: index
        })
    }

    result() {
        return {
            initial: this.initial,
            actions: this.actions
        }
    }


    /**
     * Select with priority
     * @param index
     * @param ord the priority
     */
    _selectOrd(index, ord) {
    }

}