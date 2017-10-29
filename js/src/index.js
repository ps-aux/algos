import './style/index.sass'
import ReactDOM from 'react-dom'
import React from 'react'
import {BrowserRouter} from 'react-router-dom'
import ArraySort from 'src/components/ArraySort'


const rootEl = window.document.getElementById('root')


ReactDOM.render(<BrowserRouter>
    <ArraySort/>
</BrowserRouter>, rootEl)


