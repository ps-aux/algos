import React from 'react'
import Tree from 'src/components/tree/Tree'
import ArraySort from 'src/components/ArraySort'
import {heap} from 'src/algo/data-structures/heap'
import {range} from 'ramda'

const sort = false
const tree = true

const h = heap()

range(1, 10).forEach(v => h.insert(v))

let root = {
    value: 'A',
    left: {
        value: 'B',
        left: {
            value: 'C'
        }
    },
    right: {
        value: 'D'
    }
}


root = h.asTree()


const App = () =>
    <div className="app">
        {tree && <Tree root={root}/>}
        {sort && <ArraySort/>}
    </div>

export default App