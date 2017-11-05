import 'babel-polyfill'
import './style/index.sass'
import ReactDOM from 'react-dom'
import React from 'react'
import {BrowserRouter} from 'react-router-dom'
import ArraySort from 'src/components/ArraySort'


const rootEl = window.document.getElementById('root')

const it = {
    [Symbol.iterator]: function* () {
        yield 1
    }
}

const gen = function * () {
    yield 1
    yield 2
    yield 3
    yield 4
}

window.it = it
window.gen = gen


ReactDOM.render(<BrowserRouter>
    <ArraySort/>
</BrowserRouter>, rootEl)


