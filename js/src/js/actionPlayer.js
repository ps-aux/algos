import {swap} from './sorting'
import assert from 'assert'

export default class ActionPlayer {

    constructor(record, speed) {
        this.record = record
        this.delay = 1000 / speed

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

    setSpeed(speed) {
        this.delay = 1000 / speed
    }


    play(callback) {
        assert.ok(!this.playing, 'Already playing')
        this.playing = true
        this._playNext()
        this.callback = callback
    }

    stop() {
        console.debug('Stopping')
        this.playing = false
    }


    _playNext() {
        if (!this.playing)
            return

        if (this.record.actions.length === 0) {
            console.debug('Playing done')
            this.callback()
            this.playing = false
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
        else if (a.type === 'put') {
            this.model[a.index].val = a.val
        }
        else
            throw 'Unknown action'
    }

}