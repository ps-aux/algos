import assert from 'assert'
import _ from 'lodash'
import ActionPlayer from './actionPlayer'
import Display from './display'
import ArrayRecorder from './arrayRecorder'
import {selectionSort} from  './sorting'

// Turn off debug
const debug = true
if (!debug)
    console.debug = () => {
    }


document.getElementById('play').onclick = start

const orig = _.range(40, 0)
const recorder = new ArrayRecorder(orig)

selectionSort(recorder)

const display = new Display(document.getElementById('canvas'), 3)
console.log('result is', recorder.result)
const player = new ActionPlayer(recorder.result(), 100)

display.setArrayModel(player.getModel())


function start() {
    console.log('starting')
    player.play()
}


function swap(array, a, b) {
    console.debug('swapping', a, b)
    assert(a !== b)
    const tmp = array[b]
    array[b] = array[a]
    array[a] = tmp
}

