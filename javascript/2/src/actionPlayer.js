import {swap} from './sorting'
import assert from 'assert'

export default class ActionPlayer {

    constructor(record, delay = 500) {
        this.record = record
        this.delay = delay

        // Setup model object
        this.model = record.initial.map(val=> {
            return {
                val: val,
                selected: false,
                examined: false
            }
        })
    }

    /**
     * Model upon which will be played
     */
    getModel() {
        return this.model
    }


    play() {
        assert.ok(!this.played, 'Already played')
        this.played = true
        this._playNext()
    }


    _playNext() {
        if (this.record.actions.length === 0) {
            console.debug('Playing done')
            return
        }

        const a = this.record.actions.shift()
        this._playAction(a)

        setTimeout(() => this._playNext(), this.delay)
    }

    _playAction(a) {
        console.debug('Playing action', a)
        if (a.type === 'swap') {
            const tmp = this.model[a.indexA].val

            this.model[a.indexA].val = this.model[a.indexB].val
            this.model[a.indexB].val = tmp
        }
        else if (a.type === 'select') {
            this.model.forEach(el => el.selected = false)
            this.model[a.index].selected = true
        }
        else if (a.type === 'examine') {
            this.model.forEach(el => el.examined = false)
            this.model[a.index].examined = true
        }
        else
            throw 'Unknown action'
    }

}